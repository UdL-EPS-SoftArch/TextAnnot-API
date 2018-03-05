Feature: Add MetaDataField
  Add field correctly As an admin.
  I want to add a new metadafield.

  Scenario: Create a new metadatafield as an admin
    Given I login as "admin" with password "password"
    When I create a new metadatafield with text "newfield" and type "field"
    Then The response code is 201
    And It has been created a new metadatafield with text "newfield" and type "field" and Id 1


  Scenario: Try to register new metadatafield without authenticating
    Given I'm not logged in
    When I create a new metadatafield with text "noauthenticating" and type "field"
    Then The response code is 401
    And It has not been created a metadatafield with text "noauthenticating" and type "field" and Id 1