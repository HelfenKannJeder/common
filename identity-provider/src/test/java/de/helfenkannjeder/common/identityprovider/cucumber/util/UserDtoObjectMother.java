package de.helfenkannjeder.common.identityprovider.cucumber.util;

import de.helfenkannjeder.common.identityprovider.rest.dto.IdentityDto;

public class UserDtoObjectMother {

    public static IdentityDto anyValidUserDto() {
        return IdentityDto.createFullDto(UserObjectMother.anyValidUser());
    }

    public static IdentityDto anyInvalidUserDto() {
        return IdentityDto.createFullDto(UserObjectMother.anyInvalidUser());
    }
}
