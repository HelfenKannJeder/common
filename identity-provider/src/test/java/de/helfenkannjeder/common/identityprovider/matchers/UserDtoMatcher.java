package de.helfenkannjeder.common.identityprovider.matchers;

import de.helfenkannjeder.common.identityprovider.rest.dto.IdentityDto;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.Matchers.equalTo;

public class UserDtoMatcher extends TypeSafeDiagnosingMatcher<IdentityDto> {

	private Matcher<? super String> email = Matchers.anything();

	private Matcher<? super String> givenName = Matchers.anything();

	private Matcher<? super String> surname = Matchers.anything();

	private Matcher<? super String> phone = Matchers.anything();

	private Matcher<? super Boolean> adult = Matchers.anything();

	public static UserDtoMatcher matchesUser() {
		return new UserDtoMatcher();
	}

	public static UserDtoMatcher matchesUser(IdentityDto identityDto) {
		return new UserDtoMatcher().withEmail(identityDto.getEmail())
				.withGivenName(identityDto.getGivenName())
				.withSurname(identityDto.getSurname())
				.withPhone(identityDto.getPhone());
	}

	public UserDtoMatcher withEmail(String email) {
		this.email = equalTo(email);
		return this;
	}

	public UserDtoMatcher withGivenName(String givenName) {
		this.givenName = equalTo(givenName);
		return this;
	}

	public UserDtoMatcher withSurname(String surname) {
		this.surname = equalTo(surname);
		return this;
	}


	public UserDtoMatcher withPhone(String phone) {
		this.phone = equalTo(phone);
		return this;
	}

	public UserDtoMatcher withAdult(Boolean isAdult) {
		this.adult = equalTo(isAdult);
		return this;
	}

	@Override
	protected boolean matchesSafely(IdentityDto item, final Description mismatchDescription) {
		boolean matches = true;
		mismatchDescription.appendText("was IdentityDto");

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
		if (!phone.matches(item.getPhone())) {
			mismatchDescription.appendText(" with phone=").appendValue(item.getPhone());
			matches = false;
		}
		return matches;
	}

	@Override
	public void describeTo(Description description) {
		description
				.appendText(", with email=").appendDescriptionOf(email)
				.appendText(", with givenName=").appendDescriptionOf(givenName)
				.appendText(", with surname=").appendDescriptionOf(surname)
				.appendText(", with phone=").appendDescriptionOf(phone)
				.appendText(", with adult=").appendDescriptionOf(adult);
	}
}
