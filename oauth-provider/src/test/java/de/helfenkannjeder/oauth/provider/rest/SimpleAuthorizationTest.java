package de.helfenkannjeder.oauth.provider.rest;

import de.helfenkannjeder.oauth.provider.MockMvcOAuthLogin;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
@Transactional
@DirtiesContext
public class SimpleAuthorizationTest extends AbstractOAuthControllerTest {

    @Test
    public void testAuthorizationAdmin() throws Exception {
        getAuthorizationAdmin();
    }

    @Test
    public void testAuthorizationDefaultUser() throws Exception {
        getAuthorizationDefaultUser();
    }

    @Test
    public void testAuthorizationDefaultUserWithPassword() throws Exception {
        getAuthorizationDefaultUserWithPassword();
    }

    @Test
    public void loginAuthorizationEndpoint_withoutPassword_returnsUnauthorized() throws Exception {
        // Act
        mockMvc.perform(get(MockMvcOAuthLogin.ENDPOINT_OAUTH_AUTHORIZATION)
                .param("response_type", "code")
                .param("redirect_uri", "/")
                .param("client_id", "come2help-web")
        )
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void refreshToken_withRefreshToken_returnsNewToken() throws Exception {
        // Arrange
        MockMvcOAuthLogin.OAuthInformation oAuthInformation = mockMvcOAuthLogin.getAccessTokenWithPassword("come2help-web", "secret", AbstractOAuthControllerTest.DEFAULT_USER, AbstractOAuthControllerTest.DEFAULT_PASSWORD);

        // Act + Assert
        mockMvcOAuthLogin.getAccessTokenWithRefreshToken("come2help-web", "secret", oAuthInformation.getRefreshToken());
    }
}
