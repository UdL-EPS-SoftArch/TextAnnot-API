Feature: List Metadata Template
  "explicació"

  Scenario: List a metadata template as no admin login
    Given I'm not logged in
    And There is a metadata template with name "name"
    When I retrieve all metadata templates
    Then The response code is 401

  Scenario: List a metadata template as admin
    Given I login as "admin" with password "password"
    And There is a MetadataTemplate with name "name"
    When I retrieve all metadata templates
    Then The response code is 200
    And The respone contains a MetadataTemplate with name "name"

  Scenario: List 4 metadata templates as admin
    Given I login as "admin" with password "password"
    And There is a MetadataTemplate with name "name1"
    And There is a MetadataTemplate with name "name2"
    And There is a MetadataTemplate with name "name3"
    And There is a MetadataTemplate with name "name4"
    When I retrieve all MetadataTemplate
    Then The response code is 200
    And The response is a MetadataList[4]

  Scenario: List a metadata template as linguist
    Given I login as "linguist" with password "password"
    And There is a metadata template with name "name"
    When I retrieve all metadata templates
    Then The response code is 200
    And the response contains a MetadataTemplate with name "name"

  Scenario: List 4 metadata templates as linguist
    Given I login as "linguist" with password "password"
    And There is a MetadataTemplate with name "name1"
    And There is a MetadataTemplate with name "name2"
    And There is a MetadataTemplate with name "name3"
    And There is a MetadataTemplate with name "name4"
    When I retrieve all MetadataTemplate
    Then The response code is 200
    And The response is a MetadataList[4]