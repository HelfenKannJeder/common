package de.helfenkannjeder.common.identityprovider.cucumber;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Iterables;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import de.helfenkannjeder.common.identityprovider.cucumber.util.IdentityApiRestClient;
import de.helfenkannjeder.common.identityprovider.rest.dto.IdentityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class IdentityProviderApiTestFacade {

    private final IdentityApiRestClient identityApiRestClient;

    private List<IdentityDto> createdIdentities;

    private HttpStatus latestRelevantStatusCode;

    @Autowired
    public IdentityProviderApiTestFacade(IdentityApiRestClient identityApiRestClient) {
        this.identityApiRestClient = identityApiRestClient;
    }

    @Before
    public void beforeScenario() {
        createdIdentities = new ArrayList<>();
    }

    @After
    public void afterScenario() {
        doCleanup();
    }

    public IdentityDto getLastCreatedIdentity() {
        return Iterables.getLast(createdIdentities);
    }

    public ResponseEntity<IdentityDto> updateIdentity(IdentityDto identity) {
        ResponseEntity<IdentityDto> responseEntity = identityApiRestClient.updateIdentity(identity);
        latestRelevantStatusCode = responseEntity.getStatusCode();
        return responseEntity;
    }

    public ResponseEntity<IdentityDto> createIdentity(IdentityDto identity) {
        ResponseEntity<IdentityDto> responseEntity = identityApiRestClient.createIdentity(identity);
        if (responseEntity.hasBody() && responseEntity.getStatusCode().is2xxSuccessful()) {
            IdentityDto createdIdentity = responseEntity.getBody();
            if(createdIdentity != null && createdIdentity.getId() != null) {
                createdIdentities.add(createdIdentity);
            }
        }
        latestRelevantStatusCode = responseEntity.getStatusCode();
        return responseEntity;
    }

    public ResponseEntity<IdentityDto> getIdentity(Long id) {
        return identityApiRestClient.getIdentity(id);
    }

    public HttpStatus deleteIdentity(Long id) {
        latestRelevantStatusCode = identityApiRestClient.deleteIdentity(id);
        return latestRelevantStatusCode;
    }

    public HttpStatus getLatestHttpStatusCode() {
        return latestRelevantStatusCode;
    }

    private void doCleanup() {
        for (IdentityDto identity : createdIdentities) {
            identityApiRestClient.deleteIdentity(identity.getId());
        }
        createdIdentities.clear();
    }
}
