Feature: Creation of a TagHierarchies with its tags in a single shot
  As a Linguist
  I want to create TagHierarchies in a single shot
  So I can check if the tags associated to a hierarchy form a tre

  Scenario: Create a Tag Hierarchy correctly
    Given I login as "admin" with password "password"
    When I send the "valid_tag_hierarchy.json" Tag Hierarchy structure
    Then The response code is 201
    And Its tags have the correct parent/child relationship

  Scenario: Try to create a Tag Hierarchy which its tags form a tree
    Given I login as "admin" with password "password"
    When I send the "tree_tag_structure.json" Tag Hierarchy structure
    Then The response code is 400

  Scenario: Try to create a Tag Hierarchy with an invalid json structure
    Given I login as "admin" with password "password"
    When I send the "invalid_tag_hierarchy.json" Tag Hierarchy structure
    Then The response code is 400
