package de.helfenkannjeder.oauth.provider.rest;

import de.helfenkannjeder.oauth.provider.api.OAuthProviderUserManagementApi;
import de.helfenkannjeder.oauth.provider.api.dto.UserRequestDto;
import de.helfenkannjeder.oauth.provider.api.dto.UserResponseDto;
import de.helfenkannjeder.oauth.provider.domain.OAuthUser;
import de.helfenkannjeder.oauth.provider.domain.repository.OAuthUserRepository;
import de.helfenkannjeder.oauth.provider.exception.UsernameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public UserResponseDto create(@Valid @RequestBody UserRequestDto userRequestDto) {
        assertUserDoesNotExists(userRequestDto);

        String password = passwordEncoder.encode(userRequestDto.getPassword());
        OAuthUser user = oAuthUserRepository.save(new OAuthUser(userRequestDto.getUsername(), password));
        return new UserResponseDto(String.valueOf(user.getId()));
    }

    @Override
    @RequestMapping(value = UPDATE, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") String id, @Valid @RequestBody UserRequestDto userRequestDto) {
        OAuthUser user = oAuthUserRepository.findOne(Long.valueOf(id));

        if (!user.getUsername().equals(userRequestDto.getUsername())) {
            assertUserDoesNotExists(userRequestDto);
        }

        user.setUsername(userRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        oAuthUserRepository.save(user);
    }

    private void assertUserDoesNotExists(@RequestBody UserRequestDto userRequestDto) {
        if (oAuthUserRepository.findOneByUsernameIgnoreCase(userRequestDto.getUsername()) != null) {
            throw new UsernameAlreadyExistsException();
        }
    }

    @Override
    @RequestMapping(value = DELETE, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        throw new RuntimeException("Not yet implemented");
    }
}