package de.helfenkannjeder.oauth.provider.rest;

import de.helfenkannjeder.oauth.provider.MockMvcOAuthLogin;
import de.helfenkannjeder.oauth.provider.api.OAuthProviderUserManagementApi;
import de.helfenkannjeder.oauth.provider.api.dto.UserRequestDto;
import de.helfenkannjeder.oauth.provider.api.dto.UserResponseDto;
import de.helfenkannjeder.oauth.provider.configuration.OAuth2ProviderApplication;
import de.helfenkannjeder.oauth.provider.configuration.OAuth2ProviderConfiguration;
import de.helfenkannjeder.oauth.provider.domain.OAuthUser;
import de.helfenkannjeder.oauth.provider.domain.repository.OAuthUserRepository;
import de.helfenkannjeder.oauth.provider.security.OAuthProviderAuthority;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.util.HashMap;

import static java.util.Collections.singleton;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest(randomPort = true)
@SpringApplicationConfiguration(classes = OAuth2ProviderApplication.class)
@Transactional
public class UserManagementControllerTest {

    public static final String RESOURCE_PREFIX = "/admin";
    public static final String DEFAULT_USER = "my-default-user";
    public static final String DEFAULT_PASSWORD = "my-default-password";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private OAuthUserRepository oAuthUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private MockMvcOAuthLogin mockMvcOAuthLogin;

    private UserRequestDto userRequestDto;

    private Authentication createAuthentication(String grantType, OAuthProviderAuthority... authorities) {

        StringBuilder authoritiesBuilder = new StringBuilder();
        for (OAuthProviderAuthority authority : authorities) {
            authoritiesBuilder.append(",").append(authority);
        }
        ClientDetails client = new BaseClientDetails("oauth-provider-admin", null, OAuth2ProviderConfiguration.SCOPE_DEFAULT, grantType, authoritiesBuilder.substring(1));
        return new OAuth2Authentication(new TokenRequest(new HashMap<>(), "oauth-provider-admin", singleton(OAuth2ProviderConfiguration.SCOPE_DEFAULT), grantType).createOAuth2Request(client), null);
    }

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .addFilters(springSecurityFilterChain)
                .build();
        userRequestDto = new UserRequestDto("my-user", "my-password");

        oAuthUserRepository.save(new OAuthUser(DEFAULT_USER, passwordEncoder.encode(DEFAULT_PASSWORD)));
    }


    @Test
    public void create_withAdminAndUsernameAndPassword_returnsNewUserId() throws Exception {
        // Arrange
        String username = "my-user";
        String password = "my-password";

        // Act
        MvcResult result = this.mockMvc.perform(post(RESOURCE_PREFIX + OAuthProviderUserManagementApi.CREATE)
                .header("Authorization", getAuthorizationAdmin())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(userRequestDto))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn();

        // Assert
        assertNotNull(result);
        UserResponseDto userResponseDto = objectMapper.readValue(result.getResponse().getContentAsString(), UserResponseDto.class);
        String userId = userResponseDto.getId();
        assertNotNull(userId);
        OAuthUser oAuthUser = oAuthUserRepository.findOne(Long.valueOf(userId));
        assertEquals(username, oAuthUser.getUsername());
        assertTrue(passwordEncoder.matches(password, oAuthUser.getPassword()));
    }

    @Test
    public void create_withAdminAndDuplicateUsernameInLowerCase_returnsConflict() throws Exception {
        // Arrange
        userRequestDto.setUsername(DEFAULT_USER.toLowerCase());

        // Act
        this.mockMvc.perform(post(RESOURCE_PREFIX + OAuthProviderUserManagementApi.CREATE)
                .header("Authorization", getAuthorizationAdmin())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(userRequestDto))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )

                // Assert
                .andExpect(status().isConflict());
    }

    @Test
    public void create_withAdminAndDuplicateUsernameInUpperCase_returnsConflict() throws Exception {
        // Arrange
        userRequestDto.setUsername(DEFAULT_USER.toUpperCase());

        // Act
        this.mockMvc.perform(post(RESOURCE_PREFIX + OAuthProviderUserManagementApi.CREATE)
                .header("Authorization", getAuthorizationAdmin())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(userRequestDto))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )

                // Assert
                .andExpect(status().isConflict());
    }

    @Test
    public void create_withNotAdmin_returns403() throws Exception {
        // Act + Assert
        this.mockMvc.perform(post(RESOURCE_PREFIX + OAuthProviderUserManagementApi.CREATE)
                .header("Authorization", getAuthorizationDefaultUser())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(userRequestDto))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isForbidden());
    }

    @Test
    public void create_withNoAuthentication_returns401() throws Exception {
        // Act + Assert
        this.mockMvc.perform(post(RESOURCE_PREFIX + OAuthProviderUserManagementApi.CREATE)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(userRequestDto))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isUnauthorized());
    }

    private String getAuthorizationAdmin() throws Exception {
        return mockMvcOAuthLogin.getAccessTokenWithClientSecret("oauth-provider-admin", "default-secret");
    }

    private String getAuthorizationDefaultUser() throws Exception {
        return mockMvcOAuthLogin.getAccessTokenWithAuthorizationCode("come2help-web", "secret", DEFAULT_USER, DEFAULT_PASSWORD);
    }
}