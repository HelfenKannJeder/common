package de.helfenkannjeder.common.identityprovider.rest.mapping;

import de.helfenkannjeder.common.identityprovider.domain.Identity;
import de.helfenkannjeder.identity.provider.api.dto.IdentityDto;

public final class IdentityDtoMapper {

	private IdentityDtoMapper() {
	}

	public static IdentityDto createFullDto(Identity identity) {
		return new IdentityDto(identity.getId(), AuthenticationProviderMapper.mapToApi(identity.getAuthProvider()),
				identity.getExternalId(), identity.getEmail(), identity.getGivenName(), identity.getSurname(), identity.getPhone());
	}

	public static Identity createIdentity(IdentityDto user) {
		return new Identity(user.getId(), AuthenticationProviderMapper.mapToDomain(user.getAuthProvider()),
				user.getExternalId(), user.getEmail(), user.getGivenName(), user.getSurname(), user.getPhone());
	}
}
