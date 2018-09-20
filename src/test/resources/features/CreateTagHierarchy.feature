Feature: Create new Tag Hierarchy
  In order to have Tag Hierarchies
  As an admin
  I want to create a new Tag Hierarchy

  Scenario: Create a new Tag Hierarchy as admin
    Given I login as "admin" with password "password"
    When I create a new Tag Hierarchy with name "test tag hierarchy"
    Then The response code is 201
    And The Tag Hierarchy name is "test tag hierarchy"

  Scenario: Create a new Tag Hierarchy as admin without name
    Given I login as "admin" with password "password"
    When I create a new Tag Hierarchy with name ""
    Then The response code is 400
    And The error message is "must not be blank"

  Scenario: Create a new Tag Hierarchy as linguist is not allowed
    Given I login as "linguist" with password "password"
    When I create a new Metadata Template with name "tag hierarchy"
    Then The response code is 401
