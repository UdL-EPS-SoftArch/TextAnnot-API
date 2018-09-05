Feature: Find Metadata Templates by Field name and type

  Scenario: Search MetadataTemplates which defines a specific Field with a specific name and type
    Given I login as "admin" with password "password"
    Given A MetadataTemplate with name "name" defines a MetadataField with name "FName" and type "FType"
    When I find MetadataTemplates by MetadataField name "FName" and type "FType"
    Then I get the list with a MetadataTemplate with name "name"

  Scenario: Search MetadataTemplates which defines a specific FieldName
    Given I login as "admin" with password "password"
    Given A MetadataTemplate with name "name" which defines a MetadataFields with name "FName"
    When I find MetadataTemplate by MetadataField name "FName"
    Then I get the list with a MetadataTemplate with name "name"

  Scenario: Search MetadataTemplates which defines a specific FieldType
    Given I login as "admin" with password "password"
    Given A MetadataTemplate with name "name" defines a MetadataField with type "FType"
    When I find MetadataTemplate by MetadataField type "FType"
    Then I get the list with a MetadataTemplate with name "name"

