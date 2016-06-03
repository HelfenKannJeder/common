package de.helfenkannjeder.common.identityprovider.matchers;

import de.helfenkannjeder.identity.provider.api.dto.AuthenticationProvider;
import de.helfenkannjeder.identity.provider.api.dto.IdentityDto;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class IdentityDtoMatcher extends TypeSafeDiagnosingMatcher<IdentityDto> {

	private Matcher<? super Long> id = Matchers.anything();

	private Matcher<? super String> email = Matchers.anything();

	private Matcher<? super String> givenName = Matchers.anything();

	private Matcher<? super String> surname = Matchers.anything();

	private Matcher<? super AuthenticationProvider> authProvider = Matchers.anything();

	private Matcher<? super String> externalId = Matchers.anything();

	private Matcher<? super String> phone = Matchers.anything();

	private Matcher<? super String> status = Matchers.anything();

	private Matcher<? super String> confirmationCode = Matchers.anything();

	public static IdentityDtoMatcher matchesIdentityDto() {
		return new IdentityDtoMatcher();
	}

	public static IdentityDtoMatcher matchesIdentityDto(IdentityDto identityDto) {
		return new IdentityDtoMatcher().withId(identityDto.getId())
				.withEmail(identityDto.getEmail())
				.withGivenName(identityDto.getGivenName())
				.withSurname(identityDto.getSurname())
				.withAuthProvider(identityDto.getAuthProvider())
				.withExternalId(identityDto.getExternalId())
				.withPhone(identityDto.getPhone())
				.withStatus(identityDto.getStatus())
				.withConfirmationCode(identityDto.getConfirmationCode());
	}

	public IdentityDtoMatcher withId(Long id) {
		this.id = equalTo(id);
		return this;
	}

	public IdentityDtoMatcher withEmail(String email) {
		this.email = equalTo(email);
		return this;
	}

	public IdentityDtoMatcher withGivenName(String givenName) {
		this.givenName = equalTo(givenName);
		return this;
	}

	public IdentityDtoMatcher withSurname(String surname) {
		this.surname = equalTo(surname);
		return this;
	}

	public IdentityDtoMatcher withAuthProvider(AuthenticationProvider authProvider) {
		this.authProvider = equalTo(authProvider);
		return this;
	}

	public IdentityDtoMatcher withExternalId(String externalId) {
		this.externalId = equalTo(externalId);
		return this;
	}

	public IdentityDtoMatcher withPhone(String phone) {
		this.phone = equalTo(phone);
		return this;
	}

	public IdentityDtoMatcher withStatus(String status) {
		this.status = equalTo(status);
		return this;
	}

	public IdentityDtoMatcher withConfirmationCode(String confirmationCode) {
		this.confirmationCode = equalTo(confirmationCode);
		return this;
	}

	public IdentityDtoMatcher withAnyConfirmationCode() {
		this.confirmationCode = notNullValue();
		return this;
	}

	@Override
	protected boolean matchesSafely(IdentityDto item, final Description mismatchDescription) {
		boolean matches = true;
		mismatchDescription.appendText("was IdentityDto");

		if (!id.matches(item.getId())) {
			mismatchDescription.appendText(" with id=").appendValue(item.getId());
			matches = false;
		}
		if (!email.matches(item.getEmail())) {
			mismatchDescription.appendText(" with email=").appendValue(item.getEmail());
			matches = false;
		}
		if (!givenName.matches(item.getGivenName())) {
			mismatchDescription.appendText(" with givenName=").appendValue(item.getGivenName());
			matches = false;
		}
		if (!surname.matches(item.getSurname())) {
			mismatchDescription.appendText(" with surname=").appendValue(item.getSurname());
			matches = false;
		}
		if (!authProvider.matches(item.getAuthProvider())) {
			mismatchDescription.appendText(" with authProvider=").appendValue(item.getAuthProvider());
			matches = false;
		}
		if (!externalId.matches(item.getExternalId())) {
			mismatchDescription.appendText(" with externalId=").appendValue(item.getExternalId());
			matches = false;
		}
		if (!phone.matches(item.getPhone())) {
			mismatchDescription.appendText(" with phone=").appendValue(item.getPhone());
			matches = false;
		}
		if (!status.matches(item.getStatus())) {
			mismatchDescription.appendText(" with status=").appendValue(item.getStatus());
			matches = false;
		}
		if (!confirmationCode.matches(item.getConfirmationCode())) {
			mismatchDescription.appendText(" with confirmationCode=").appendValue(item.getConfirmationCode());
			matches = false;
		}
		return matches;
	}

	@Override
	public void describeTo(Description description) {
		description
				.appendText(", with id=").appendDescriptionOf(id)
				.appendText(", with email=").appendDescriptionOf(email)
				.appendText(", with givenName=").appendDescriptionOf(givenName)
				.appendText(", with surname=").appendDescriptionOf(surname)
				.appendText(", with authProvider=").appendDescriptionOf(authProvider)
				.appendText(", with externalId=").appendDescriptionOf(externalId)
				.appendText(", with phone=").appendDescriptionOf(phone)
				.appendText(", with status=").appendDescriptionOf(status)
				.appendText(", with confirmationCode=").appendDescriptionOf(confirmationCode);
	}
}
