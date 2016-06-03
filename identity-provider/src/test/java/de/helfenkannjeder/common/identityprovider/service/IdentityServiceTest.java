package de.helfenkannjeder.common.identityprovider.service;

import de.helfenkannjeder.common.identityprovider.cucumber.util.IdentityObjectMother;
import de.helfenkannjeder.common.identityprovider.domain.DomainAuthenticationProvider;
import de.helfenkannjeder.common.identityprovider.domain.Identity;
import de.helfenkannjeder.common.identityprovider.domain.IdentityStatus;
import de.helfenkannjeder.common.identityprovider.domain.repository.IdentityRepository;
import de.helfenkannjeder.common.identityprovider.matchers.IdentityMatcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalAnswers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IdentityServiceTest {

    private IdentityService identityService;
    private IdentityRepository identityRepositoryMock;

    @Before
    public void setUp() throws Exception {
        identityRepositoryMock = mock(IdentityRepository.class);
        when(identityRepositoryMock.save((Identity) any())).then(AdditionalAnswers.returnsFirstArg());
        identityService = new IdentityService(identityRepositoryMock, null);
    }

    @Test
    public void createHelfenKannJederIdentityWithEmptyExternalIdShouldCreateUnconfirmedIdentity() throws Exception {
        Identity helfenKannJederIdentity = IdentityObjectMother.anyValidIdentity()
                .setAuthProvider(DomainAuthenticationProvider.HELFENKANNJEDER)
                .setExternalId(null);
        Identity createdIdentity = identityService.createIdentity(helfenKannJederIdentity);

        assertThat(createdIdentity, IdentityMatcher.matchesIdentity()
                .withStatus(IdentityStatus.INACTIVE)
                .withAnyConfirmationCode());
    }
}