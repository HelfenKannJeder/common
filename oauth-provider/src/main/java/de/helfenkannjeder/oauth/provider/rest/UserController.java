package de.helfenkannjeder.oauth.provider.rest;

import de.helfenkannjeder.oauth.provider.domain.OAuthUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
@RestController
public class UserController {

    @RequestMapping("/user/information")
    public OAuthUser currentUser(Principal user) {
        return new OAuthUser(user.getName(), null);
    }

}
