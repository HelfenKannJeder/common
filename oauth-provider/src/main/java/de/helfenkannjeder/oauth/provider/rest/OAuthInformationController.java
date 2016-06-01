package de.helfenkannjeder.oauth.provider.rest;

import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
@RestController
@EnableResourceServer
public class OAuthInformationController {

    @RequestMapping("/userInformation")
    public Principal user(Principal user) {
        return user;
    }
}