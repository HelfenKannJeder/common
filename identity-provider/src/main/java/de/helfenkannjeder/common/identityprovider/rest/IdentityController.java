package de.helfenkannjeder.common.identityprovider.rest;

import de.helfenkannjeder.common.identityprovider.domain.Identity;
import de.helfenkannjeder.common.identityprovider.rest.dto.IdentityDto;
import de.helfenkannjeder.common.identityprovider.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("identities/")
@Transactional
public class IdentityController {

	@Autowired
	private IdentityService identityService;

	@RequestMapping(method = RequestMethod.POST)
	public IdentityDto createUser(@Valid IdentityDto identityDto) {
		Identity identity = IdentityDto.createUser(identityDto);
		identity = identityService.createIdentity(identity);
		return IdentityDto.createFullDto(identity);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public IdentityDto getUser(@NotNull @PathVariable("id") Long id) {
		return IdentityDto.createFullDto(identityService.findById(id));
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public IdentityDto updateUser(@NotNull @PathVariable("id") Long id, @Valid IdentityDto identityDto) {
		Identity identity = IdentityDto.createUser(identityDto);
		identity = identityService.updateIdentity(id, identity);
		return IdentityDto.createFullDto(identity);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void deleteUser(@NotNull @PathVariable("id") Long id) {
		identityService.deleteIdentityById(id);
	}
}
