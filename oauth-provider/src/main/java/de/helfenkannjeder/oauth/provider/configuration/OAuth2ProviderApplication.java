package de.helfenkannjeder.oauth.provider.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
@SpringBootApplication
@EnableResourceServer
@EnableAuthorizationServer
@ComponentScan(basePackages = "de.helfenkannjeder.oauth.provider")
public class OAuth2ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OAuth2ProviderApplication.class, args);
    }

}
