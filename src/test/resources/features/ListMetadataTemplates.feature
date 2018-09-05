Feature: List Metadata Template

  Scenario: List a metadata template as admin
    Given I login as "admin" with password "password"
    And There is a metadata template with name "metadataTemplateExample"
    When I get all MetadataTemplates
    Then The response code is 200
    And The respone contains only a MetadataTemplate with name "metadataTemplateExample"

  Scenario: List 4 metadata templates as admin
    Given I login as "admin" with password "password"
    And There are 4 MetadataTemplates
    When I get all MetadataTemplates
    Then The response code is 200
    And The response is a MetadataTemplatesList with 4 items

  Scenario: List a metadata template as linguist
    Given I login as "user" with password "password"
    And There is a metadata template with name "metadataTemplateExample"
    When I get all MetadataTemplates
    Then The response code is 200
    And The respone contains only a MetadataTemplate with name "metadataTemplateExample"

  Scenario: List 0 metadata templates as linguist
    Given I login as "user" with password "password"
    And There are 0 MetadataTemplates
    When I get all MetadataTemplates
    Then The response code is 200
    And The response is a MetadataTemplatesList with 0 items

  Scenario: List a metadata template as no user login
    Given I'm not logged in
    And There is a metadata template with name "metadataTemplateExample"
    When I get all MetadataTemplates
    Then The response code is 401


