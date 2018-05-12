Feature: Find Samples By Metadata Field Value
  In order to allow users
  As an user
  I want to

  Scenario: Search Sample
    Given I login as "admin" with password "password"
    Given A Sample with text "text" with MetadataValue value "value" and MetadataField name "name"
    When I find Samples by MetadataValue value "value" and MetadataField name "name"
    Then I get the list with the Sample with text "text"

