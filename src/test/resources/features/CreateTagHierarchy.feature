Feature: Create New TagHierarChy
  In order to allow a new linguist to tagHierarChy a sample
  As a linguist
  I want to create a new tagHierarchy

  Scenario: Register a new tagHierarchy as linguist
    Given I login as "user" with password "password"
    When I register a new tagHierarchy with name "test"
    Then The response code is 201
    And It has been created a new tagHierarchy with name "test"

  Scenario: Try to register new tagHierarchy without authentication
    Given I'm not logged in
    When I register a new tagHierarchy with name "test"
    Then The response code is 401
    And It has not been created a tagHierarchy with name "test"

  Scenario: Register new tagHierarchy with empty name
    Given I login as "user" with password "password"
    When I register a new tagHierarchy with name ""
    Then The response code is 400
    And The error message is "may not be empty"