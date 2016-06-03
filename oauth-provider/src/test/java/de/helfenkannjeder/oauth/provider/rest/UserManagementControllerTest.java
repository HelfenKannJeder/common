package de.helfenkannjeder.oauth.provider.rest;

import de.helfenkannjeder.oauth.provider.api.OAuthProviderUserManagementApi;
import de.helfenkannjeder.oauth.provider.api.dto.UserRequestDto;
import de.helfenkannjeder.oauth.provider.api.dto.UserResponseDto;
import de.helfenkannjeder.oauth.provider.configuration.OAuth2ProviderApplication;
import de.helfenkannjeder.oauth.provider.domain.OAuthUser;
import de.helfenkannjeder.oauth.provider.domain.repository.OAuthUserRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest
@SpringApplicationConfiguration(classes = OAuth2ProviderApplication.class)
public class UserManagementControllerTest {

    public static final String RESOURCE_PREFIX = "/admin";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private OAuthUserRepository oAuthUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void create_withUsernameAndPassword_returnsNewUserId() throws Exception {
        String username = "my-user";
        String password = "my-password";
        UserRequestDto user = new UserRequestDto(username, password);
        MvcResult result = this.mockMvc.perform(post(RESOURCE_PREFIX + OAuthProviderUserManagementApi.CREATE)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(user))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn();

        assertNotNull(result);
        UserResponseDto userResponseDto = objectMapper.readValue(result.getResponse().getContentAsString(), UserResponseDto.class);
        String userId = userResponseDto.getId();
        assertNotNull(userId);
        OAuthUser oAuthUser = oAuthUserRepository.findOne(Long.valueOf(userId));
        assertEquals(username, oAuthUser.getUsername());
        assertTrue(passwordEncoder.matches(password, oAuthUser.getPassword()));
    }
}