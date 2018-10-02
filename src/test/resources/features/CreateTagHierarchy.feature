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
    When I create a new Tag Hierarchy with name "tag hierarchy"
    Then The response code is 401

  Scenario: Create a new Tag Hierarchy as linguist is not allowed
    Given I'm not logged in
    When I create a new Tag Hierarchy with name "tag hierarchy"
    Then The response code is 401

  Scenario: Check that a Tag Hierarchy has 1 sample
    Given I login as "admin" with password "password"
    And Exists a Sample with text "Test"
    And Exists a Tag Hierarchy with name "tag hierarchy"
    When I set the previous Sample tagged by the previous Tag Hierarchy
    Then The tag hierarchy "tag hierarchy" tags a sample with text "Test"

  Scenario: Link a Sample with a Tag Hierarchy as linguist
    Given I login as "linguist" with password "password"
    And Exists a Sample with text "Test"
    And Exists a Tag Hierarchy with name "tag hierarchy"
    When I set the previous Sample tagged by the previous Tag Hierarchy
    Then The response code is 401

  Scenario: Create a Sample linked to a Tag Hierarchy as linguist
    Given I login as "user" with password "password"
    And Exists a Tag Hierarchy with name "linguistic tags"
    When I create a new sample with text "Sample text..." tagged by the tag hierarchy "linguistic tags"
    Then The tag hierarchy "linguistic tags" tags a sample with text "Sample text..."
