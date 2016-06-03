package de.helfenkannjeder.common.identityprovider.cucumber.util;

import de.helfenkannjeder.common.identityprovider.domain.Identity;

public class IdentityObjectMother {

    public static Identity anyValidIdentity() {
        return new Identity()
                .setGivenName("Max")
                .setSurname("Muster")
                .setEmail("max@muster.com")
                .setPhone("+49232");
    }

    public static Identity anyInvalidIdentity() {
        return anyValidIdentity().setEmail("invalidEmail");
    }
}
