package de.helfenkannjeder.common.identityprovider.cucumber.stepdefinitions;

import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.helfenkannjeder.common.identityprovider.cucumber.IdentityProviderApiTestFacade;
import de.helfenkannjeder.common.identityprovider.cucumber.configuration.TestApplicationConfiguration;
import de.helfenkannjeder.common.identityprovider.cucumber.transformers.HTTPStatusTransformer;
import de.helfenkannjeder.common.identityprovider.cucumber.util.IdentityDtoObjectMother;
import de.helfenkannjeder.common.identityprovider.matchers.IdentityDtoMatcher;
import de.helfenkannjeder.common.identityprovider.rest.dto.IdentityDto;
import org.hamcrest.CoreMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ContextConfiguration(classes = TestApplicationConfiguration.class, initializers = ConfigFileApplicationContextInitializer.class)
public class IdentitySteps {

    private final IdentityProviderApiTestFacade facade;
    private IdentityDto identityDto = new IdentityDto();
    private ResponseEntity<IdentityDto> createIdentityResponseEntity;
    private ResponseEntity<IdentityDto> updateIdentityResponseEntity;

    @Autowired
    public IdentitySteps(IdentityProviderApiTestFacade facade) {
        this.facade = facade;
    }

    @Given("^any valid identity$")
    public void any_valid_identity() throws Throwable {
        identityDto = IdentityDtoObjectMother.anyValidIdentityDto();
    }

    @Given("^any invalid identity$")
    public void any_invalid_identity() throws Throwable {
        identityDto = IdentityDtoObjectMother.anyInvalidIdentityDto();
    }

    @When("^the identity is created$")
    public void the_identity_is_created() throws Throwable {
        the_identity_creation_request_is_send();
        assertThat("could not create the identity", facade.getLatestHttpStatusCode().is2xxSuccessful());
    }

    @When("^the identity create request is send$")
    public void the_identity_creation_request_is_send() throws Throwable {
        createIdentityResponseEntity = facade.createIdentity(identityDto);
        identityDto = createIdentityResponseEntity.getBody();
    }

    @Then("^the response contains the created identity")
    public void the_response_contains_the_created_identity() throws Throwable {
        assertThat(createIdentityResponseEntity.getBody(), CoreMatchers.is(IdentityDtoMatcher.matchesIdentityDto(facade.getLastCreatedIdentity())));
    }

    @Given("^an existing identity")
    public void an_existing_identity() throws Throwable {
        any_valid_identity();
        the_identity_is_created();
    }

    @Given("^the user changes a property of the identity")
    public void the_user_changes_a_property_of_the_identity() throws Throwable {
        identityDto = createIdentityResponseEntity.getBody();
        identityDto.setSurname("newSurname");
    }

    @When("^the identity is updated$")
    public void the_identity_is_updated() throws Throwable {
        updateIdentityResponseEntity = facade.updateIdentity(identityDto);
        identityDto = updateIdentityResponseEntity.getBody();
    }

    @When("^the identity is deleted")
    public void the_identity_is_deleted() throws Throwable {
        facade.deleteIdentity(identityDto.getId());
    }

    @Then("^the updated identity is returned$")
    public void the_updated_identity_is_returned() throws Throwable {
        assertThat(updateIdentityResponseEntity.getBody(), CoreMatchers.is(IdentityDtoMatcher.matchesIdentityDto(identityDto)));
    }

    @Given("^a non existing identity")
    public void a_non_existing_identity() throws Throwable {
        any_valid_identity();
        identityDto.setId(-5L);
    }

    @Then("^the returned status code is (.+)$")
    public void theReturnedStatusCodeIs(@Transform(HTTPStatusTransformer.class) HttpStatus httpStatus) throws Throwable {
        assertThat(facade.getLatestHttpStatusCode(), is(httpStatus));
    }

    @When("^the identity id is re-used$")
    public void theIdentityIdIsReUsed() throws Throwable {
        identityDto = createIdentityResponseEntity.getBody();
        identityDto.setEmail("newUserEmail@test.helfenkannjeder.de");
        the_identity_creation_request_is_send();
    }

    @And("^the user modifies the identity with invalid data$")
    public void theUserModifiesTheIdentityWithInvalidData() throws Throwable {
        identityDto = createIdentityResponseEntity.getBody();
        identityDto.setEmail("invalidEmail");
    }
}
