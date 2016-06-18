package de.helfenkannjeder.oauth.provider.rest;

import de.helfenkannjeder.oauth.provider.MockMvcOAuthLogin;
import de.helfenkannjeder.oauth.provider.configuration.OAuth2ProviderApplication;
import de.helfenkannjeder.oauth.provider.domain.OAuthUser;
import de.helfenkannjeder.oauth.provider.domain.repository.OAuthUserRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest(randomPort = true)
@SpringApplicationConfiguration(classes = OAuth2ProviderApplication.class)
public abstract class AbstractOAuthControllerTest {

    public static final String DEFAULT_USER = "my-default-user";
    public static final String DEFAULT_PASSWORD = "my-default-password";
    @Autowired
    protected OAuthUserRepository oAuthUserRepository;

    MockMvc mockMvc;
    @Autowired

    PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private Filter springSecurityFilterChain;
    @Autowired
    private MockMvcOAuthLogin mockMvcOAuthLogin;

    @Before
    public void initMockMvc() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .addFilters(springSecurityFilterChain)
                .build();

        oAuthUserRepository.save(new OAuthUser(AbstractOAuthControllerTest.DEFAULT_USER, passwordEncoder.encode(AbstractOAuthControllerTest.DEFAULT_PASSWORD)));
    }

    protected String getAuthorizationAdmin() throws Exception {
        return mockMvcOAuthLogin.getAccessTokenWithClientSecret("oauth-provider-admin", "default-secret");
    }

    protected String getAuthorizationDefaultUser() throws Exception {
        return mockMvcOAuthLogin.getAccessTokenWithAuthorizationCode("come2help-web", "secret", AbstractOAuthControllerTest.DEFAULT_USER, AbstractOAuthControllerTest.DEFAULT_PASSWORD);
    }

    protected String getAuthorizationDefaultUserWithPassword() throws Exception {
        return mockMvcOAuthLogin.getAccessTokenWithPassword("come2help-web", "secret", AbstractOAuthControllerTest.DEFAULT_USER, AbstractOAuthControllerTest.DEFAULT_PASSWORD);
    }
}
