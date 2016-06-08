package de.helfenkannjeder.oauth.provider;

import de.helfenkannjeder.oauth.provider.configuration.OAuth2ProviderConfiguration;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
@Service
public class MockMvcOAuthLogin {

    public static final String ENDPOINT_OAUTH_TOKEN = "/oauth/token";
    public static final String ENDPOINT_OAUTH_AUTHORIZATION = "/oauth/authorize";
    public static final String ENDPOINT_CONFIRM_ACCESS = "/oauth/confirm_access";

    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Autowired
    public MockMvcOAuthLogin(WebApplicationContext webApplicationContext, Filter springSecurityFilterChain) {
        this.webApplicationContext = webApplicationContext;
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .addFilters(springSecurityFilterChain)
                .build();

    }

    public String getAccessTokenWithClientSecret(String clientId, String secret) throws Exception {
        return getAccessToken(post(ENDPOINT_OAUTH_TOKEN), OAuth2ProviderConfiguration.CLIENT_CREDENTIALS, clientId, secret);
    }

    public String getAccessTokenWithAuthorizationCode(String clientId, String secret, String username, String password) throws Exception {
        MockHttpSession mockSession = new MockHttpSession(webApplicationContext.getServletContext(), UUID.randomUUID().toString());

        mockMvc.perform(
                get(ENDPOINT_OAUTH_AUTHORIZATION)
                        .session(mockSession)
                        .header("Authorization", createBase64Auth(username, password))
                        .param("response_type", "code")
                        .param("redirect_uri", "/")
                        .param("client_id", clientId)
        )
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(ENDPOINT_CONFIRM_ACCESS))
                .andReturn().getResponse();

        MockHttpServletResponse response = mockMvc.perform(
                post(ENDPOINT_OAUTH_AUTHORIZATION)
                        .session(mockSession)
                        .header("Authorization", createBase64Auth(username, password))
                        .param("scope.default", "true")
                        .param("user_oauth_approval", "true")
                        .param("authorize", "Authorize")
        )
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse();

        String content = response.getRedirectedUrl();
        String code = content.split("=")[1];
        return getAccessTokenWithAuthorizationCode(clientId, secret, code);
    }

    private String getAccessTokenWithAuthorizationCode(String clientId, String secret, String code) throws Exception {
        MockHttpServletRequestBuilder post = post(ENDPOINT_OAUTH_TOKEN)
                .param("code", code)
                .param("redirect_uri", "/");
        return getAccessToken(post, OAuth2ProviderConfiguration.AUTHORIZATION_CODE, clientId, secret);
    }

    public String getAccessTokenWithPassword(String clientId, String secret, String username, String password) throws Exception {
        MockHttpServletRequestBuilder post = post(ENDPOINT_OAUTH_TOKEN)
                .param("username", username)
                .param("password", password);
        return getAccessToken(post, OAuth2ProviderConfiguration.PASSWORD, clientId, secret);
    }

    /*
     * Original from https://github.com/royclarkson/spring-rest-service-oauth/blob/master/src/test/java/hello/GreetingControllerTest.java
     */
    private String getAccessToken(MockHttpServletRequestBuilder post, String grantType, String clientId, String secret) throws Exception {

        // @formatter:off
        String content = mockMvc.perform(post
                .header("Authorization", createBase64Auth(clientId, secret))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("grant_type", grantType)
                        .param("scope", OAuth2ProviderConfiguration.SCOPE_DEFAULT)
                        .param("client_id", clientId)
                        .param("client_secret", secret))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.access_token", is(notNullValue())))
                .andExpect(jsonPath("$.token_type", is(equalTo("bearer"))))
                .andExpect(jsonPath("$.expires_in", is(greaterThan(4000))))
                .andReturn().getResponse().getContentAsString();
        // @formatter:on

        ObjectMapper objectMapper = new ObjectMapper();
        HashMap result = objectMapper.readValue(content, HashMap.class);

        return "Bearer " + result.get("access_token");
    }

    private String createBase64Auth(String username, String password) {
        return "Basic " + new String(Base64Utils.encode((username + ":" + password).getBytes()));
    }


}
