Feature: Add MetaDataField
  Add field correctly As an admin.
  I want to add a new metadafield.

  Scenario: Create a new metadataField as an admin
    Given I login as "admin" with password "password"
    When I create a new metadatafield with text "newfield" and type "field"
    Then The response code is 201
    And It has been created a new metadatafield with text "newfield" and type "field" and Id 1


  Scenario: Try to register new metadataField without authenticating
    Given I'm not logged in
    When I create a new metadatafield with text "noauthenticating" and type "field"
    Then The response code is 401
    And It has not been created a metadatafield with text "noauthenticating" and type "field" and Id 1


  Scenario: Create a new metadataField as an admin
    Given I login as "admin" with password "password"
    When I create a new metadatafield with text "" and type ""
    Then The response code is 400
    And The error message is "must not be blank"

  Scenario: Try to register new metadataField as a linguistic
    Given I login as "linguist" with password "password"
    When I create a new metadatafield with text "noauthenticating" and type "field"
    Then The response code is 401
    And It has not been created a metadatafield with text "noauthenticating" and type "field" and Id 1

  Scenario: Register new metadataField for existing metadataTemplate
    Given I login as "user" with password "password"
    And there is a created metadataTemplate with name "test_template"
    When I register a new metadataField with text "test_field" and type "field" for metadataTemplate with value "test_template"
    Then The response code is 201
    And It has been created a new metadataField with text "test_field"  for metadataTemplate with value "test_template"