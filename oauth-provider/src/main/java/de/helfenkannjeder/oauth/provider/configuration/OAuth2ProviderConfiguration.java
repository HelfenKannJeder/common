package de.helfenkannjeder.oauth.provider.configuration;

import de.helfenkannjeder.oauth.provider.security.OAuthProviderAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
@Configuration
public class OAuth2ProviderConfiguration extends AuthorizationServerConfigurerAdapter {

    public static final String CLIENT_CREDENTIALS = "client_credentials";
    public static final String AUTHORIZATION_CODE = "authorization_code";
    public static final String PASSWORD = "password";
    public static final String SCOPE_DEFAULT = "default";
    public static final String REFRESH_TOKEN = "refresh_token";

    private TokenStore tokenStore = new InMemoryTokenStore();

    @Value("${oauth.client.admin.clientId}")
    private String adminClientId;

    @Value("${oauth.client.admin.secret}")
    private String adminSecret;

    @Value("${oauth.client.come2help.clientId}")
    private String come2helpClientId;

    @Value("${oauth.client.come2help.secret}")
    private String come2helpClientSecret;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter:off
        clients.inMemory()
                .withClient(adminClientId)
                .secret(adminSecret)
                .authorizedGrantTypes(CLIENT_CREDENTIALS, REFRESH_TOKEN)
                .scopes(SCOPE_DEFAULT)
                .authorities(OAuthProviderAuthority.ROLE_ADMIN.getAuthority(),
                        OAuthProviderAuthority.ROLE_USER.getAuthority())
            .and()
                .withClient(come2helpClientId)
                .secret(come2helpClientSecret)
                .authorizedGrantTypes(AUTHORIZATION_CODE, REFRESH_TOKEN)
                .scopes(SCOPE_DEFAULT)
                .authorities(OAuthProviderAuthority.ROLE_USER.getAuthority());
        // @formatter:on
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder,
                                UserDetailsService userDetailsService) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}