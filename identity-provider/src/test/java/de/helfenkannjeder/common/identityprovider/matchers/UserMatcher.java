package de.helfenkannjeder.common.identityprovider.matchers;

import de.helfenkannjeder.common.identityprovider.domain.Identity;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.Matchers.equalTo;

public class UserMatcher extends TypeSafeDiagnosingMatcher<Identity> {

	private Matcher<? super String> id = Matchers.anything();

	private Matcher<? super String> authProvider = Matchers.anything();

	private Matcher<? super String> externalId = Matchers.anything();

	private Matcher<? super String> email = Matchers.anything();

	private Matcher<? super String> givenName = Matchers.anything();

	private Matcher<? super String> surname = Matchers.anything();

	private Matcher<? super String> phone = Matchers.anything();


	public static UserMatcher matchesUser() {
		return new UserMatcher();
	}

	public static UserMatcher matchesUser(Identity identity) {
		return new UserMatcher().withEmail(identity.getEmail())
				.withGivenName(identity.getGivenName())
				.withSurname(identity.getSurname())
				.withPhone(identity.getPhone());
	}

	public UserMatcher withEmail(String email) {
		this.email = equalTo(email);
		return this;
	}

	public UserMatcher withGivenName(String givenName) {
		this.givenName = equalTo(givenName);
		return this;
	}

	public UserMatcher withSurname(String surname) {
		this.surname = equalTo(surname);
		return this;
	}


	public UserMatcher withPhone(String phone) {
		this.phone = equalTo(phone);
		return this;
	}

	@Override
	protected boolean matchesSafely(Identity item, final Description mismatchDescription) {
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
				.appendText(", with phone=").appendDescriptionOf(phone);
	}
}
