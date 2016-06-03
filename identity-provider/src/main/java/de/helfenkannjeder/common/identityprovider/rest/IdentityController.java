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

@RestController
@RequestMapping("identities/")
@Transactional
public class IdentityController {

	@Autowired
	private IdentityService identityService;

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public IdentityDto getUser(@PathVariable Long id) {
		return IdentityDto.createFullDto(identityService.findById(id));
	}

	public IdentityDto createUser(IdentityDto identityDto) {
		Identity identity = IdentityDto.createUser(identityDto);
		identity = identityService.createUser(identity);
		return IdentityDto.createFullDto(identity);
	}
}
