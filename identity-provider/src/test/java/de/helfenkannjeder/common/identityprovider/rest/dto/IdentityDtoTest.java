package de.helfenkannjeder.common.identityprovider.rest.dto;

import de.helfenkannjeder.common.identityprovider.cucumber.util.IdentityDtoObjectMother;
import de.helfenkannjeder.common.identityprovider.cucumber.util.IdentityObjectMother;
import de.helfenkannjeder.common.identityprovider.domain.AuthenticationProvider;
import de.helfenkannjeder.common.identityprovider.domain.Identity;
import de.helfenkannjeder.common.identityprovider.matchers.IdentityDtoMatcher;
import de.helfenkannjeder.common.identityprovider.matchers.IdentityMatcher;
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
				.withPhone(dto.getPhone())
				.withAuthProvider(identity.getAuthProvider().getApiName())
				.withExternalId(identity.getExternalId()));
	}

	@Test
	public void testIdentityDtoToIdentity() {
		IdentityDto dto = IdentityDtoObjectMother.anyValidUserDto();
		Identity identity = IdentityDto.createIdentity(dto);

		assertThat(identity, IdentityMatcher.matchesIdentity()
				.withId(dto.getId())
				.withEmail(dto.getEmail())
				.withSurname(dto.getSurname())
				.withGivenName(dto.getGivenName())
				.withPhone(dto.getPhone())
				.withAuthProvider(AuthenticationProvider.getByApiName(dto.getAuthProvider()))
				.withExternalId(dto.getExternalId()));
	}
}
