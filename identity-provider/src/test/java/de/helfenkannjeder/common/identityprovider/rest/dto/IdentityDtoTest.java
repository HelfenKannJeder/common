package de.helfenkannjeder.common.identityprovider.rest.dto;

import de.helfenkannjeder.common.identityprovider.cucumber.util.IdentityDtoObjectMother;
import de.helfenkannjeder.common.identityprovider.cucumber.util.IdentityObjectMother;
import de.helfenkannjeder.common.identityprovider.domain.Identity;
import de.helfenkannjeder.common.identityprovider.matchers.IdentityDtoMatcher;
import de.helfenkannjeder.common.identityprovider.matchers.UserMatcher;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class IdentityDtoTest {

	@Test
	public void testIdentityToIdentityDto() {
		Identity identity = IdentityObjectMother.anyValidIdentity();
		IdentityDto dto = IdentityDto.createFullDto(identity);

		assertThat(dto, IdentityDtoMatcher.matchesIdentityDto()
				.withId(identity.getId())
				.withEmail(dto.getEmail())
				.withGivenName(dto.getGivenName())
				.withSurname(dto.getSurname())
				.withPhone(dto.getPhone()));
	}

	@Test
	public void testIdentityDtoToIdentity() {
		IdentityDto dto = IdentityDtoObjectMother.anyValidUserDto();
		Identity identity = IdentityDto.createUser(dto);

		assertThat(identity, UserMatcher.matchesUser().withEmail(dto.getEmail())
				.withSurname(dto.getSurname())
				.withGivenName(dto.getGivenName())
				.withPhone(dto.getPhone()));
	}
}
