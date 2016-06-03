package de.helfenkannjeder.common.identityprovider.cucumber.util;

import de.helfenkannjeder.common.identityprovider.rest.mapping.IdentityDtoMapper;
import de.helfenkannjeder.identity.provider.api.dto.AuthenticationProvider;
import de.helfenkannjeder.identity.provider.api.dto.IdentityDto;

public class IdentityDtoObjectMother {

	public static IdentityDto anyValidIdentityDto() {
		return new IdentityDto()
				.setId(42L)
				.setGivenName("Max")
				.setSurname("Muster")
				.setEmail("max@muster.com")
				.setPhone("+49232")
				.setAuthProvider(AuthenticationProvider.FACEBOOK)
				.setExternalId("external-id-bla");
	}

	public static IdentityDto anyInvalidIdentityDto() {
		return IdentityDtoMapper.createFullDto(IdentityObjectMother.anyInvalidIdentity());
	}
}
