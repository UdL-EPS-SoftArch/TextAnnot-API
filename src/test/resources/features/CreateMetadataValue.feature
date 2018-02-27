Feature: Create New metadataValue
  In order to allow a new linguist to create a new values
  As a linquist
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
    And It has not been created a metadataValue with value "test"

  Scenario: Register new metadataValue with empty value
    Given I login as "user" with password "password"
    When I register a new metadataValue with value ""
    Then The response code is 400
    And The error message is "may not be empty"
    And It has not been created a new metadataValue

#  Scenario: Edit existing metadataValue as linguist
#    Given I login as "user" with password "password"
#    When I edit an existing metadataValue with Id 1
#    And I change the value to "new value"
#    Then The response code is 201
#    And It has changed the value of metadataValue with Id 1
#    And It has changed the value to "new value"
#
#  Scenario: Edit non existing metadata value as linguist
#    Given I login as "user" with password "password"
#    When I edit a non existing metadataValue with Id 5
#    Then The response code is 400
#    And It has not changed the value of metadataValue with Id 5

#  Scenario: Try to register a duplicate metadata value as user
#    Given I login as "user" with password "password"
#    When I create a new metadata with value "text"
#    Then The response code is 201
#    And It has been created a new meta data vulue with "text"

