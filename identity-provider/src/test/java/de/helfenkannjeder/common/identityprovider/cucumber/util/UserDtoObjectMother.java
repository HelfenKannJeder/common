package de.helfenkannjeder.common.identityprovider.cucumber.util;

import de.helfenkannjeder.common.identityprovider.rest.dto.UserDto;

public class UserDtoObjectMother {

    public static UserDto anyValidUserDto() {
        return UserDto.createFullDto(UserObjectMother.anyValidUser());
    }

    public static UserDto anyInvalidUserDto() {
        return UserDto.createFullDto(UserObjectMother.anyInvalidUser());
    }
}
