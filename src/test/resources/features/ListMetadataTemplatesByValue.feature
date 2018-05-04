Feature: Find MetadataTemplates By Value which values a MetadataField that is defined in the MetadataTemplate
  In order to allow users
  As an user
  I want to

  Scenario: List MetadataTemplates that defines MetadataFields that values a MetadataValue with a specific value
    Given I login as "admin" with password "password"
    Given A MetadataTemplate with name "name" defines a MetadataField with name "FName" and type "FType" that values a MetadataValue with value "VValue"
    When I find MetadataTemplates by MetadataValue with value "VValue"
    Then I get a list with a MetadataTemplate with name "name"



