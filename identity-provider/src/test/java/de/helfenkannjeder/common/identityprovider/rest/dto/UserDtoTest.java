//package de.helfenkannjeder.common.identityprovider.rest.dto;
//
//import de.helfenkannjeder.common.identityprovider.cucumber.util.UserDtoObjectMother;
//import de.helfenkannjeder.common.identityprovider.cucumber.util.UserObjectMother;
//import de.helfenkannjeder.common.identityprovider.domain.Identitiy;
//import de.helfenkannjeder.common.identityprovider.matchers.UserMatcher;
//import org.junit.Test;
//
//import static de.helfenkannjeder.common.identityprovider.matchers.UserMatcher.matchesUser;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//public class UserDtoTest {
//
//    @Test
//    public void testUserToUserDto() {
//        Identitiy user = UserObjectMother.anyValidUser();
//        UserDto dto = UserDto.createFullDto(user);
//
//        assertThat(user, UserMatcher.matchesUser().withEmail(dto.getEmail())
//                .withGivenName(dto.getGivenName())
//                .withSurname(dto.getSurname())
//                .withAddress(AddressDto.createAddress(dto.getAddress()))
//                .withPhone(dto.getPhone()));
//    }
//
//    @Test
//    public void testVolunteerDtoToVolunteer() {
//        UserDto dto = UserDtoObjectMother.anyValidUserDto();
//        Identitiy user = UserDto.createUser(dto);
//
//        assertThat(user, UserMatcher.matchesUser().withEmail(dto.getEmail())
//                .withSurname(dto.getSurname())
//                .withGivenName(dto.getGivenName())
//                .withAddress(AddressDto.createAddress(dto.getAddress()))
//                .withPhone(dto.getPhone()));
//    }
//}
