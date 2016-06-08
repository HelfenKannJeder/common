package de.helfenkannjeder.oauth.provider.rest;

import de.helfenkannjeder.oauth.provider.api.OAuthProviderUserManagementApi;
import de.helfenkannjeder.oauth.provider.api.dto.UserRequestDto;
import de.helfenkannjeder.oauth.provider.api.dto.UserResponseDto;
import de.helfenkannjeder.oauth.provider.domain.OAuthUser;
import de.helfenkannjeder.oauth.provider.domain.repository.OAuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
@RestController
@RequestMapping("/admin")
public class UserManagementController implements OAuthProviderUserManagementApi {

    @Autowired
    private OAuthUserRepository oAuthUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @RequestMapping(value = CREATE, method = RequestMethod.POST)
    public UserResponseDto create(@RequestBody UserRequestDto userRequestDto) {
        // TODO: Test if user already exists
        String password = passwordEncoder.encode(userRequestDto.getPassword());
        OAuthUser user = oAuthUserRepository.save(new OAuthUser(userRequestDto.getUsername(), password));
        return new UserResponseDto(String.valueOf(user.getId()));
    }

    @Override
    @RequestMapping(value = UPDATE, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") String id, UserRequestDto userRequestDto) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    @RequestMapping(value = DELETE, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        throw new RuntimeException("Not yet implemented");
    }
}