Feature: Create New Tag
  In order to allow a new linguist to tag a sample
  As a linguist
  I want to create a new tag

  Scenario: Register a new tag as linguist
    Given I login as "user" with password "password"
    When I register a new tag with name "test"
    Then The response code is 201
    And It has been created a new tag with name "test"

  Scenario: Try to register new tag without authenticating
    Given I'm not logged in
    When I register a new tag with name "test"
    Then The response code is 401
    And It has not been created a tag with name "test"

  Scenario: Register new tag with empty name
    Given I login as "user" with password "password"
    When I register a new tag with name ""
    Then The response code is 400
    And The error message is "may not be empty"

  Scenario: Register new tag with parent tag
    Given I login as "user" with password "password"
    And there is a created tag with name "parent"
    When I register a new tag with name "child" for tag with name "parent"
    Then The response code is 201
    And It has been created a new tag with name "child" for tag with text "parent"
