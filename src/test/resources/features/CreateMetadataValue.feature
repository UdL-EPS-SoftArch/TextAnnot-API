Feature: Create New metadataValue
  In order to allow a new linguist to create a new values
  As a linguist
  I want to create a new metadataValue

  Scenario: Register a new metadataValue as linguist
    Given I login as "user" with password "password"
    When I register a new metadataValue with value "test"
    Then The response code is 201
    And It has been created a new metadataValue with value "test" and Id 1

  Scenario: Try to register new metadataValue without authenticating
    Given I'm not logged in
    When I register a new metadataValue with value "test"
    Then The response code is 401
    And It has not been created a metadataValue with value "test" and Id 1

  Scenario: Register new metadataValue with empty value
    Given I login as "user" with password "password"
    When I register a new metadataValue with value ""
    Then The response code is 400
    And The error message is "may not be empty"

  Scenario: Register new metadataValue for existing Sample
    Given I login as "user" with password "password"
    And there is a created Sample with text "test_sample"
    When I register a new metadataValue with value "test_metadata"
    Then The response code is 201
    And It has been created a new metadataValue with value "test" and Id 1 for Sample 0 with text "test_sample"

  Scenario: Register new metadataValue for existing Sample
    Given I login as "user" with password "password"
    And there is a created Sample with text "test_sample"
    When I register a new metadataValue with value "test_metadata" for Sample "test_sample"
    Then The response code is 201
    And It has been created a new metadataValue with value "test" and Id 1 for Sample with text "test_sample"
