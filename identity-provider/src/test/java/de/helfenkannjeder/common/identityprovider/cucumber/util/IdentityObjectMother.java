package de.helfenkannjeder.common.identityprovider.cucumber.util;

import de.helfenkannjeder.common.identityprovider.domain.AuthenticationProvider;
import de.helfenkannjeder.common.identityprovider.domain.Identity;

public class IdentityObjectMother {

	public static Identity anyValidIdentity() {
		return new Identity()
				.setId(42L)
				.setGivenName("Max")
				.setSurname("Muster")
				.setEmail("max@muster.com")
				.setPhone("+49232")
				.setAuthProvider(AuthenticationProvider.FACEBOOK)
				.setExternalId("external-id-bla");
	}

	public static Identity anyInvalidIdentity() {
		return anyValidIdentity().setEmail("invalidEmail");
	}
}
