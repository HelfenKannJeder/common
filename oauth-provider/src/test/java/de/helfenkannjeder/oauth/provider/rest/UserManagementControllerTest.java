package de.helfenkannjeder.oauth.provider.rest;

import de.helfenkannjeder.oauth.provider.api.OAuthProviderUserManagementApi;
import de.helfenkannjeder.oauth.provider.api.dto.UserRequestDto;
import de.helfenkannjeder.oauth.provider.api.dto.UserResponseDto;
import de.helfenkannjeder.oauth.provider.domain.OAuthUser;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
@Transactional
@DirtiesContext
public class UserManagementControllerTest extends AbstractOAuthControllerTest {

    public static final String RESOURCE_PREFIX = "/admin";

    private ObjectMapper objectMapper = new ObjectMapper();

    private UserRequestDto userRequestDto;

    @Before
    public void setUp() throws Exception {
        userRequestDto = new UserRequestDto("my-user", "my-password");
    }

    @Test
    public void create_withAdminAndUsernameAndPassword_returnsNewUserId() throws Exception {
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
        assertEquals(userRequestDto.getUsername(), oAuthUser.getUsername());
        assertTrue(passwordEncoder.matches(userRequestDto.getPassword(), oAuthUser.getPassword()));
    }

    @Test
    public void create_withAdminAndWithoutContent_returnsNoNewUser() throws Exception {
        // Act
        this.mockMvc.perform(post(RESOURCE_PREFIX + OAuthProviderUserManagementApi.CREATE)
                .header("Authorization", getAuthorizationAdmin())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{}")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_withAdminAndWithEmptyUsernameAndPassword_returnsNoNewUser() throws Exception {
        // Act
        this.mockMvc.perform(post(RESOURCE_PREFIX + OAuthProviderUserManagementApi.CREATE)
                .header("Authorization", getAuthorizationAdmin())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"username\":\"\",\"password\":\"\"}")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isBadRequest());
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

    @Test
    public void update_withAdminLoggedInAndNewUserInformation_verifyUserIsChanged() throws Exception {
        // Arrange
        Long userId = oAuthUserRepository.findOneByUsernameIgnoreCase(DEFAULT_USER).getId();

        // Act
        this.mockMvc.perform(put(RESOURCE_PREFIX + OAuthProviderUserManagementApi.UPDATE.replace("{id}", userId.toString()))
                .header("Authorization", getAuthorizationAdmin())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(userRequestDto))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isNoContent());

        // Assert
        OAuthUser oAuthUser = oAuthUserRepository.findOne(userId);
        assertEquals(userRequestDto.getUsername(), oAuthUser.getUsername());
        assertTrue(passwordEncoder.matches(userRequestDto.getPassword(), oAuthUser.getPassword()));
    }

    @Test
    public void update_withAdminLoggedInAndAlreadyExistingUsername_verifyUserIsNotChanged() throws Exception {
        // Arrange
        oAuthUserRepository.save(new OAuthUser(userRequestDto.getUsername(), "password"));

        Long userId = oAuthUserRepository.findOneByUsernameIgnoreCase(DEFAULT_USER).getId();

        // Act
        this.mockMvc.perform(put(RESOURCE_PREFIX + OAuthProviderUserManagementApi.UPDATE.replace("{id}", userId.toString()))
                .header("Authorization", getAuthorizationAdmin())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(userRequestDto))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isConflict());

        // Assert
        assertUserUnchanged(userId);
    }

    private void assertUserUnchanged(Long userId) {
        OAuthUser oAuthUser = oAuthUserRepository.findOne(userId);
        assertEquals(DEFAULT_USER, oAuthUser.getUsername());
        assertTrue(passwordEncoder.matches(DEFAULT_PASSWORD, oAuthUser.getPassword()));
    }

    @Test
    public void update_withAdminLoggedInOnlyChangingPassword_verifyUserIsChanged() throws Exception {
        // Arrange
        Long userId = oAuthUserRepository.findOneByUsernameIgnoreCase(DEFAULT_USER).getId();
        userRequestDto.setUsername(DEFAULT_USER);

        // Act
        this.mockMvc.perform(put(RESOURCE_PREFIX + OAuthProviderUserManagementApi.UPDATE.replace("{id}", userId.toString()))
                .header("Authorization", getAuthorizationAdmin())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(userRequestDto))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isNoContent());

        // Assert
        OAuthUser oAuthUser = oAuthUserRepository.findOne(userId);
        assertEquals(DEFAULT_USER, oAuthUser.getUsername());
        assertTrue(passwordEncoder.matches(userRequestDto.getPassword(), oAuthUser.getPassword()));
    }


    @Test
    public void update_withAdminLoggedInEmptyObject_verifyUserIsNotChanged() throws Exception {
        // Arrange
        Long userId = oAuthUserRepository.findOneByUsernameIgnoreCase(DEFAULT_USER).getId();

        // Act
        this.mockMvc.perform(put(RESOURCE_PREFIX + OAuthProviderUserManagementApi.UPDATE.replace("{id}", userId.toString()))
                .header("Authorization", getAuthorizationAdmin())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{}")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isBadRequest());

        // Assert
        assertUserUnchanged(userId);
    }

    @Test
    public void delete_withAdminLoggedIn_verifyUserIsRemoved() throws Exception {
        // Arrange
        Long userId = oAuthUserRepository.findOneByUsernameIgnoreCase(DEFAULT_USER).getId();

        // Act
        this.mockMvc.perform(delete(RESOURCE_PREFIX + OAuthProviderUserManagementApi.DELETE.replace("{id}", userId.toString()))
                .header("Authorization", getAuthorizationAdmin())
        )
                .andExpect(status().isNoContent());

        // Assert
        OAuthUser oAuthUser = oAuthUserRepository.findOne(userId);
        assertNull(oAuthUser);
    }

    @Test
    public void delete_withNormalUserLoggedIn_verifyUserIsNotRemoved() throws Exception {
        // Arrange
        Long userId = oAuthUserRepository.findOneByUsernameIgnoreCase(DEFAULT_USER).getId();

        // Act
        this.mockMvc.perform(delete(RESOURCE_PREFIX + OAuthProviderUserManagementApi.DELETE.replace("{id}", userId.toString()))
                .header("Authorization", getAuthorizationDefaultUser())
        )
                .andExpect(status().isForbidden());

        // Assert
        assertUserUnchanged(userId);
    }
}