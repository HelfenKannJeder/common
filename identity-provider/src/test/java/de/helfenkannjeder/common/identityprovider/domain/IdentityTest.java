package de.helfenkannjeder.common.identityprovider.domain;

import de.helfenkannjeder.common.identityprovider.cucumber.util.IdentityObjectMother;
import de.helfenkannjeder.common.identityprovider.matchers.IdentityMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class IdentityTest {

    @Test
    public void testUpdate() {
        Identity identity1 = IdentityObjectMother.anyValidIdentity();
        String email = "updated@email.de";
        String updatedGivenName = "updatedGivenName";
        String updatedSurname = "updatedSurname";
        String updatedPhone = "+49344";
        AuthenticationProvider updatedAuthProvider = AuthenticationProvider.GOOGLE;
        String updatedExternalId = "updatedExternalId";
        Identity identity2 = IdentityObjectMother.anyValidIdentity()
                .setEmail(email)
                .setGivenName(updatedGivenName)
                .setSurname(updatedSurname)
                .setPhone(updatedPhone)
                .setAuthProvider(updatedAuthProvider)
                .setExternalId(updatedExternalId);

        assertNotEquals(identity1, identity2);
        identity1.update(identity2);
        assertThat(identity1, IdentityMatcher.matchesIdentity(identity2));
    }
}
