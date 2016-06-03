package de.helfenkannjeder.common.identityprovider.rest.dto;

import de.helfenkannjeder.common.identityprovider.cucumber.util.IdentityDtoObjectMother;
import de.helfenkannjeder.common.identityprovider.cucumber.util.IdentityObjectMother;
import de.helfenkannjeder.common.identityprovider.domain.Identity;
import de.helfenkannjeder.common.identityprovider.domain.IdentityStatus;
import de.helfenkannjeder.common.identityprovider.matchers.IdentityDtoMatcher;
import de.helfenkannjeder.common.identityprovider.matchers.IdentityMatcher;
import de.helfenkannjeder.common.identityprovider.rest.mapping.AuthenticationProviderMapper;
import de.helfenkannjeder.common.identityprovider.rest.mapping.IdentityDtoMapper;
import de.helfenkannjeder.identity.provider.api.dto.AuthenticationProvider;
import de.helfenkannjeder.identity.provider.api.dto.IdentityDto;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class IdentityDtoMapperTest {

	@Test
	public void testIdentityToIdentityDto() {
		Identity identity = IdentityObjectMother.anyValidIdentity();
		IdentityDto dto = IdentityDtoMapper.createFullDto(identity);

		assertThat(dto, IdentityDtoMatcher.matchesIdentityDto()
				.withId(identity.getId())
				.withEmail(dto.getEmail())
				.withGivenName(dto.getGivenName())
				.withSurname(dto.getSurname())
				.withPhone(dto.getPhone())
				.withAuthProvider(AuthenticationProviderMapper.mapToApi(identity.getAuthProvider()))
				.withExternalId(identity.getExternalId()));
	}

	@Test
	public void testIdentityDtoToIdentity() {
		IdentityDto dto = IdentityDtoObjectMother.anyValidIdentityDto();
		Identity identity = IdentityDtoMapper.createIdentity(dto);

		assertThat(identity, IdentityMatcher.matchesIdentity()
				.withId(dto.getId())
				.withEmail(dto.getEmail())
				.withSurname(dto.getSurname())
				.withGivenName(dto.getGivenName())
				.withPhone(dto.getPhone())
				.withAuthProvider(AuthenticationProviderMapper.mapToDomain(dto.getAuthProvider()))
				.withExternalId(dto.getExternalId()));
	}

	@Test
	public void dtoToDomainShouldNotMapStatus() throws Exception {
		IdentityDto dto = new IdentityDto(null,AuthenticationProvider.FACEBOOK,null,null,null,null,null, IdentityStatus.INACTIVE.getApiName(),null);
		Identity identity = IdentityDtoMapper.createIdentity(dto);

		assertThat(identity, IdentityMatcher.matchesIdentity().withStatus(IdentityStatus.ACTIVE));
	}

	@Test
	public void dtoToDomainShouldNotMapConfirmationCode() throws Exception {
		IdentityDto dto = new IdentityDto(null, AuthenticationProvider.FACEBOOK,null,null,null,null,null,null,"confCode");
		Identity identity = IdentityDtoMapper.createIdentity(dto);

		assertThat(identity, IdentityMatcher.matchesIdentity().withConfirmationCode(null));
	}
}
