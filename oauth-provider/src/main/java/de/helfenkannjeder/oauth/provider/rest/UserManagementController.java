package de.helfenkannjeder.oauth.provider.rest;

import de.helfenkannjeder.oauth.provider.api.OAuthProviderUserManagementApi;
import de.helfenkannjeder.oauth.provider.api.dto.UserRequestDto;
import de.helfenkannjeder.oauth.provider.api.dto.UserResponseDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
@RestController
public class UserManagementController implements OAuthProviderUserManagementApi {

    @RequestMapping("/userInformation")
    public Principal currentUser(Principal user) {
        return user;
    }

    @Override
    @RequestMapping(value = CREATE, method = RequestMethod.POST)
    public UserResponseDto create(UserRequestDto userRequestDto) {
        return new UserResponseDto(null);
    }

    @Override
    @RequestMapping(value = UPDATE, method = RequestMethod.PUT)
    public void update(@RequestParam("id") String id, UserRequestDto userRequestDto) {

    }

    @Override
    @RequestMapping(value = DELETE, method = RequestMethod.DELETE)
    public void delete(@RequestParam("id") String id) {

    }
}