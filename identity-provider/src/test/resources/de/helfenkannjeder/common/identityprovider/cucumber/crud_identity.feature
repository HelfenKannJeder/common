Feature: Crud operations for identities

  Scenario: Create a identity
    Given any valid identity
    When the identity is created
    Then the response contains the created identity

  Scenario: Reject create of a identity with invalid data
    Given any invalid identity
    When the identity create request is send
    Then the returned status code is HTTP 400 Bad Request

  Scenario: Create a identity with existing id
    Given an existing identity
    When the identity id is re-used
    Then the returned status code is HTTP 400 Bad Request

  Scenario: Update an existing identity
    Given an existing identity
    And the user changes a property of the identity
    When the identity is updated
    Then the updated volunteer is returned

  Scenario: Update a non existing identity
    Given a non existing identity
    When the identity is updated
    Then the returned status code is HTTP 404 Not Found

  Scenario: Reject update of a identity with invalid data
    Given an existing identity
    And the user modifies the identity with invalid data
    When the identity is updated
    Then the returned status code is HTTP 400 Bad Request

  Scenario: Delete a non existing identity
    Given a non existing identity
    When the identity is deleted
    Then the returned status code is HTTP 204 No Content

  Scenario: Delete an existing identity
    Given an existing identity
    When the identity is deleted
    Then the returned status code is HTTP 204 No Content