package de.helfenkannjeder.common.identityprovider.matchers;

import de.helfenkannjeder.common.identityprovider.domain.DomainAuthenticationProvider;
import de.helfenkannjeder.common.identityprovider.domain.Identity;
import de.helfenkannjeder.common.identityprovider.domain.IdentityStatus;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.Matchers.equalTo;

public class IdentityMatcher extends TypeSafeDiagnosingMatcher<Identity> {

	private Matcher<? super String> id = Matchers.anything();

	private Matcher<? super DomainAuthenticationProvider> authProvider = Matchers.anything();

	private Matcher<? super String> externalId = Matchers.anything();

	private Matcher<? super String> email = Matchers.anything();

	private Matcher<? super String> givenName = Matchers.anything();

	private Matcher<? super String> surname = Matchers.anything();

	private Matcher<? super String> phone = Matchers.anything();

	private Matcher<? super IdentityStatus> status = Matchers.anything();

	private Matcher<? super String> confirmationCode = Matchers.anything();

	public static IdentityMatcher matchesIdentity() {
		return new IdentityMatcher();
	}

	public static IdentityMatcher matchesIdentity(Identity identity) {
		return new IdentityMatcher().withId(identity.getId())
				.withEmail(identity.getEmail())
				.withGivenName(identity.getGivenName())
				.withSurname(identity.getSurname())
				.withAuthProvider(identity.getAuthProvider())
				.withExternalId(identity.getExternalId())
				.withPhone(identity.getPhone())
				.withStatus(identity.getStatus())
				.withConfirmationCode(identity.getConfirmationCode());
	}

	public IdentityMatcher withId(Long id) {
		this.id = equalTo(id);
		return this;
	}

	public IdentityMatcher withEmail(String email) {
		this.email = equalTo(email);
		return this;
	}

	public IdentityMatcher withGivenName(String givenName) {
		this.givenName = equalTo(givenName);
		return this;
	}

	public IdentityMatcher withSurname(String surname) {
		this.surname = equalTo(surname);
		return this;
	}

	public IdentityMatcher withAuthProvider(DomainAuthenticationProvider authProvider) {
		this.authProvider = equalTo(authProvider);
		return this;
	}

	public IdentityMatcher withExternalId(String externalId) {
		this.externalId = equalTo(externalId);
		return this;
	}

	public IdentityMatcher withPhone(String phone) {
		this.phone = equalTo(phone);
		return this;
	}

	public IdentityMatcher withStatus(IdentityStatus status) {
		this.status = equalTo(status);
		return this;
	}

	public IdentityMatcher withConfirmationCode(String confirmationCode) {
		this.confirmationCode = equalTo(confirmationCode);
		return this;
	}

	public IdentityMatcher withAnyConfirmationCode() {
		this.confirmationCode = Matchers.notNullValue();
		return this;
	}

	@Override
	protected boolean matchesSafely(Identity item, final Description mismatchDescription) {
		boolean matches = true;
		mismatchDescription.appendText("was Identity");

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
		if (!authProvider.matches(item.getAuthProvider())) {
			mismatchDescription.appendText(" with authProvider=").appendValue(item.getAuthProvider());
			matches = false;
		}
		if (!surname.matches(item.getSurname())) {
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
				.appendText("with id=").appendDescriptionOf(id)
				.appendText(", with email=").appendDescriptionOf(email)
				.appendText(", with givenName=").appendDescriptionOf(givenName)
				.appendText(", with authProvider=").appendDescriptionOf(authProvider)
				.appendText(", with externalId=").appendDescriptionOf(externalId)
				.appendText(", with phone=").appendDescriptionOf(phone)
				.appendText(", with status=").appendDescriptionOf(status)
				.appendText(", with confirmationCode=").appendDescriptionOf(confirmationCode);
	}
}
