package de.helfenkannjeder.common.identityprovider.cucumber.util;

import de.helfenkannjeder.common.identityprovider.rest.dto.IdentityDto;

public class IdentityDtoObjectMother {

    public static IdentityDto anyValidUserDto() {
        return new IdentityDto()
                .setId(42L)
                .setGivenName("Max")
                .setSurname("Muster")
                .setEmail("max@muster.com")
                .setPhone("+49232")
                .setAuthProvider("FACEBOOK")
                .setExternalId("external-id-bla");
    }

    public static IdentityDto anyInvalidIdentityDto() {
        return IdentityDto.createFullDto(IdentityObjectMother.anyInvalidIdentity());
    }
}
