package de.helfenkannjeder.common.identityprovider.cucumber.util;

import de.helfenkannjeder.common.identityprovider.domain.Identitiy;

public class UserObjectMother {

    public static Identitiy anyValidUser() {
        return new Identitiy()
                .setGivenName("Max")
                .setSurname("Muster")
                .setEmail("max@muster.com")
                .setPhone("+49232");
    }

    public static Identitiy anyInvalidUser() {
        return anyValidUser().setEmail("invalidEmail");
    }
}
