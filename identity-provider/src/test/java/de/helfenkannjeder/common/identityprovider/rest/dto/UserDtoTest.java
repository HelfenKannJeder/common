//package de.helfenkannjeder.common.identityprovider.rest.dto;
//
//import de.helfenkannjeder.common.identityprovider.cucumber.util.IdentityDtoObjectMother;
//import de.helfenkannjeder.common.identityprovider.cucumber.util.IdentityObjectMother;
//import de.helfenkannjeder.common.identityprovider.domain.Identity;
//import de.helfenkannjeder.common.identityprovider.matchers.UserMatcher;
//import org.junit.Test;
//
//import static de.helfenkannjeder.common.identityprovider.matchers.UserMatcher.matchesIdentityDto;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//public class UserDtoTest {
//
//    @Test
//    public void testUserToUserDto() {
//        Identity user = IdentityObjectMother.anyValidIdentity();
//        IdentityDto dto = IdentityDto.createFullDto(user);
//
//        assertThat(user, UserMatcher.matchesIdentityDto().withEmail(dto.getEmail())
//                .withGivenName(dto.getGivenName())
//                .withSurname(dto.getSurname())
//                .withAddress(AddressDto.createAddress(dto.getAddress()))
//                .withPhone(dto.getPhone()));
//    }
//
//    @Test
//    public void testVolunteerDtoToVolunteer() {
//        IdentityDto dto = IdentityDtoObjectMother.anyValidUserDto();
//        Identity user = IdentityDto.createUser(dto);
//
//        assertThat(user, UserMatcher.matchesIdentityDto().withEmail(dto.getEmail())
//                .withSurname(dto.getSurname())
//                .withGivenName(dto.getGivenName())
//                .withAddress(AddressDto.createAddress(dto.getAddress()))
//                .withPhone(dto.getPhone()));
//    }
//}
