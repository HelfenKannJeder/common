package de.helfenkannjeder.common.identityprovider.cucumber.util;

import de.helfenkannjeder.common.identityprovider.domain.User;

public class UserObjectMother {

    public static User anyValidUser() {
        return new User()
                .setGivenName("Max")
                .setSurname("Muster")
                .setEmail("max@muster.com")
                .setPhone("+49232");
    }

    public static User anyInvalidUser() {
        return anyValidUser().setEmail("invalidEmail");
    }
}
