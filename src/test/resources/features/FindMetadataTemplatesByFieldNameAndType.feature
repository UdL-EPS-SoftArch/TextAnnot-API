Feature: Find Metadata Templates by Field name and type.
  "explicació"

  Scenario: Search MetadataTemplates which defines a specific Field with a specific name and type
    Given A MetadataTemplate with name "name" defines a MetadataField with name "FName" and type "Ftype"
    When I find MetadataTemplates by MetadataField name "FName" and type "Ftype"
    Then I get the list with a MetadataTemplate with name "name"

  Scenario: Search MetadataTemplates which defines a specific Field
    Given A MetadataTemplate with name "name" which defines a MetadataFields with name "FName"
    When I find MetadataTemplate by MetadataField name "Fname"
    Then I get the list with a MetadataTemplate with name "name"

  Scenario: Search MetadataTemplates which defines a specific type
    Given A MetadataTemplate with name "name" defines a MedatataField with type "Ftype"
    When I find MetadataTemplate by MetadataField type "Ftype"
    Then I get the list with a MetadataTemplate with name "name"

