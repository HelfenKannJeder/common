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
        DomainAuthenticationProvider updatedAuthProvider = DomainAuthenticationProvider.GOOGLE;
        String updatedExternalId = "updatedExternalId";
        IdentityStatus updatedStatus = IdentityStatus.INACTIVE;
        String updatedConfirmaitonCode = "updatedConfirmaitonCode";
        Identity identity2 = IdentityObjectMother.anyValidIdentity()
                .setEmail(email)
                .setGivenName(updatedGivenName)
                .setSurname(updatedSurname)
                .setPhone(updatedPhone)
                .setAuthProvider(updatedAuthProvider)
                .setExternalId(updatedExternalId)
                .setStatus(updatedStatus)
                .setConfirmationCode(updatedConfirmaitonCode);

        assertNotEquals(identity1, identity2);
        identity1.update(identity2);
        assertThat(identity1, IdentityMatcher.matchesIdentity(identity2));
    }
}
