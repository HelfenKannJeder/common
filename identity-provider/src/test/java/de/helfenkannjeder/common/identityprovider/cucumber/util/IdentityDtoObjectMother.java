package de.helfenkannjeder.common.identityprovider.cucumber.util;

import de.helfenkannjeder.common.identityprovider.rest.dto.IdentityDto;

public class IdentityDtoObjectMother {

    public static IdentityDto anyValidUserDto() {
        return IdentityDto.createFullDto(IdentityObjectMother.anyValidIdentity());
    }

    public static IdentityDto anyInvalidUserDto() {
        return IdentityDto.createFullDto(IdentityObjectMother.anyInvalidIdentity());
    }
}
