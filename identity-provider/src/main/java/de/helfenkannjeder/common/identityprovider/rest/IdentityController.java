package de.helfenkannjeder.common.identityprovider.rest;

import de.helfenkannjeder.common.identityprovider.domain.Identitiy;
import de.helfenkannjeder.common.identityprovider.rest.dto.UserDto;
import de.helfenkannjeder.common.identityprovider.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("identities/")
@Transactional
public class IdentityController {

	@Autowired
	private IdentityService identityService;

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public UserDto getUser(@PathVariable Long id) {
		return UserDto.createFullDto(identityService.findById(id));
	}

	public UserDto createUser(UserDto userDto) {
		Identitiy identitiy = UserDto.createUser(userDto);
		identitiy = identityService.createUser(identitiy);
		return UserDto.createFullDto(identitiy);
	}
}
