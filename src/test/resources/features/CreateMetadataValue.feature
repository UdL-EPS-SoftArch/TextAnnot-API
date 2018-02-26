Feature: Create New MetadataValue
  In order to allow a new linguist to create a new values
  As a linquist
  I want to create a new MetadataValue

  Scenario: Create a new MetadataValue as linguist
    Given I login as "user" with password "password"
    When I create a new MetadataValue with value "test"
    Then The response code is 201
    And It has been created a new MetadataValue with value "test"
    And It has been created with Id value of 1

  Scenario: Try to register new metadata value without authenticating
    Given I'm not logged in
    When I register a new MetadataValue with value "test"
    Then The response code is 401
    And It has not been created a MetadataValue with value "test"

  Scenario: Try to register a new empty MetadataValue as linguist
    Given I login as "user" with password "password"
    When I create a new MetadataValue with no value
    Then The response code is 401
    And It has not been created a new metadata

  Scenario: Edit existing MetadataValue as linguist
    Given I login as "user" with password "password"
    When I edit an existing MetadataValue with Id 1
    And I change the value to "new value"
    Then The response code is 201
    And It has changed the value of MetadataValue with Id 1
    And It has changed the value to "new value"

  Scenario: Edit non existing metadata value as linguist
    Given I login as "user" with password "password"
    When I edit a non existing MetadataValue with Id 5
    Then The response code is 400
    And It has not changed the value of MetadataValue with Id 5

#  Scenario: Try to register a duplicate metadata value as user
#    Given I login as "user" with password "password"
#    When I create a new metadata with value "text"
#    Then The response code is 201
#    And It has been created a new meta data vulue with "text"

