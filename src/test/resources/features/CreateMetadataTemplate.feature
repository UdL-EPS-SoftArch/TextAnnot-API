Feature: Create new Metadata Template
  In order to have Metadata Templates
  As an admin
  I want to create a new Metadata Template

  Scenario: Create a new Metadata Template as admin
    Given I login as "admin" with password "password"
    When I create a new Metadata Template with name "mtTest"
    Then The response code is 201
    And The metadata template name is "mtTest"

  Scenario: Create a new Metadata Template as linguist
    Given I login as "linguist" with password "password"
    When I create a new Metadata Template with name "mtTest"
    Then The response code is 401

  Scenario: Create a new Metadata Template as admin without name
    Given I login as "admin" with password "password"
    When I create a new Metadata Template with name ""
    Then The response code is 400
    And The error message is "must not be blank"

  Scenario: Check that a Metadata Template has 1 sample
    Given I login as "admin" with password "password"
    When There is a single Sample with text "Test"
    When I create a new metadata Template "mtTest" with the previous sample
    Then The metadataTemplate with name "mtTest" have 1 samples