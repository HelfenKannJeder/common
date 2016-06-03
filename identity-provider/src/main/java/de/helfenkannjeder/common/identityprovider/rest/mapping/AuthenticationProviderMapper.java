package de.helfenkannjeder.common.identityprovider.rest.mapping;

import de.helfenkannjeder.common.identityprovider.domain.DomainAuthenticationProvider;
import de.helfenkannjeder.identity.provider.api.dto.AuthenticationProvider;

public final class AuthenticationProviderMapper {

	private AuthenticationProviderMapper() {
	}

	public static AuthenticationProvider mapToApi(DomainAuthenticationProvider authProvider) {
		return AuthenticationProvider.valueOf(authProvider.name());
	}

	public static DomainAuthenticationProvider mapToDomain(AuthenticationProvider authProvider) {
		return DomainAuthenticationProvider.valueOf(authProvider.name());
	}
}
