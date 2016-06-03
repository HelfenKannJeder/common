package de.helfenkannjeder.common.identityprovider.rest.mapping;

import de.helfenkannjeder.common.identityprovider.domain.Identity;
import de.helfenkannjeder.identity.provider.api.dto.IdentityDto;

public final class IdentityDtoMapper {

	private IdentityDtoMapper() {
	}

	public static IdentityDto createFullDto(Identity identity) {
		return new IdentityDto(identity.getId(), AuthenticationProviderMapper.mapToApi(identity.getAuthProvider()),
				identity.getExternalId(), identity.getEmail(), identity.getGivenName(), identity.getSurname(), identity.getPhone(),
				identity.getStatus().getApiName(), identity.getConfirmationCode());
	}

	public static Identity createIdentity(IdentityDto identityDto) {
		// Do not copy status or confirmationCode value from dto to domain object. It is only allowed to change the status by identity/confirm/CODE endpoint
		return new Identity(identityDto.getId(), AuthenticationProviderMapper.mapToDomain(identityDto.getAuthProvider()),
				identityDto.getExternalId(), identityDto.getEmail(), identityDto.getGivenName(), identityDto.getSurname(), identityDto.getPhone());
	}
}
