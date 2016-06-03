package de.helfenkannjeder.common.identityprovider.rest;

import de.helfenkannjeder.common.identityprovider.domain.AuthenticationProvider;
import de.helfenkannjeder.common.identityprovider.domain.Identity;
import de.helfenkannjeder.common.identityprovider.rest.dto.IdentityDto;
import de.helfenkannjeder.common.identityprovider.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;

@RestController
@RequestMapping("identities/")
@Transactional
public class IdentityController {

	@Autowired
	private IdentityService identityService;

	@RequestMapping(method = RequestMethod.POST)
	public IdentityDto createUser(@Valid @RequestBody IdentityDto identityDto) {
		Identity identity = IdentityDto.createUser(identityDto);

		if (identity.getAuthProvider() != AuthenticationProvider.HELFENKANNJEDER && (identity.getExternalId() == null || identity.getExternalId().isEmpty())) {
			throw new ConstraintViolationException("not.empty", new HashSet<>());
		}

		identity = identityService.createIdentity(identity);
		return IdentityDto.createFullDto(identity);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public IdentityDto getUser(@NotNull @PathVariable("id") Long id) {
		Identity identity = identityService.findById(id);
		return IdentityDto.createFullDto(identity);
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
