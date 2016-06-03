package de.helfenkannjeder.oauth.provider.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
@RestController
public class UserController {

    @RequestMapping("/userInformation")
    public Principal currentUser(Principal user) {
        return user;
    }

}
