Feature: List Metadata Template
  "explicació"

  Scenario: List a metadata template as no user login
    Given I'm not logged in
    And There is a single metadata template with name "name"
    When I retrieve all MetadataTemplates
    Then The response code is 401

  Scenario: List a metadata template as admin
    Given I login as "admin" with password "password"
    And There is a single metadata template with name "name"
    When I retrieve all MetadataTemplates
    Then The response code is 200
    And The respone contains only a MetadataTemplate with name "name"

  Scenario: List 4 metadata templates as admin
    Given I login as "admin" with password "password"
    And There are 4 MetadataTemplates
    When I retrieve all MetadataTemplates
    Then The response code is 200
    And The response is a MetadataTemplatesList

  Scenario: List a metadata template as linguist
    Given I login as "linguist" with password "password"
    And There is a single metadata template with name "name"
    When I retrieve all MetadataTemplates
    Then The response code is 200
    And The respone contains only a MetadataTemplate with name "name"

  Scenario: List 4 metadata templates as linguist
    Given I login as "linguist" with password "password"
    And There are 4 MetadataTemplates
    When I retrieve all MetadataTemplates
    Then The response code is 200
    And The response is a MetadataTemplatesList