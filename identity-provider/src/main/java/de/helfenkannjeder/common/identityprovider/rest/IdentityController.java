package de.helfenkannjeder.common.identityprovider.rest;

import de.helfenkannjeder.common.identityprovider.domain.DomainAuthenticationProvider;
import de.helfenkannjeder.common.identityprovider.domain.Identity;
import de.helfenkannjeder.common.identityprovider.rest.mapping.IdentityDtoMapper;
import de.helfenkannjeder.common.identityprovider.service.IdentityService;
import de.helfenkannjeder.identity.provider.api.dto.IdentityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;

@RestController
@RequestMapping("identities")
@Transactional
public class IdentityController {

	@Autowired
	private IdentityService identityService;

	@RequestMapping(method = RequestMethod.POST)
	public IdentityDto createIdentity(@Valid @RequestBody IdentityDto identityDto) {
		Identity identity = IdentityDtoMapper.createIdentity(identityDto);

		if (identity.getAuthProvider() != DomainAuthenticationProvider.HELFENKANNJEDER && (identity.getExternalId() == null || identity.getExternalId().isEmpty())) {
			throw new ConstraintViolationException("not.empty", new HashSet<>());
		}

		identity = identityService.createIdentity(identity);
		return IdentityDtoMapper.createFullDto(identity);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public IdentityDto getUser(@NotNull @PathVariable("id") Long id) {
		return IdentityDtoMapper.createFullDto(identityService.findById(id));
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public IdentityDto updateUser(@NotNull @PathVariable("id") Long id, @RequestBody @Valid IdentityDto identityDto) {
		identityDto.setId(id);
		Identity identity = IdentityDtoMapper.createIdentity(identityDto);
		identity = identityService.updateIdentity(identity);
		return IdentityDtoMapper.createFullDto(identity);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@NotNull @PathVariable("id") Long id) {
		identityService.deleteIdentityById(id);
	}
}
