Feature: List tags
  In order to allow the users to get the tags
  As an user
  I want to list the tags

  Scenario: Try to list tags without authenticating
    Given I'm not logged in
    When I list tags
    Then The response code is 401

  Scenario: Having 2 tags, list tags authenticated as admin
    Given I login as "admin" with password "password"
    Given I create a tag with name "a"
    Given I create a tag with name "b"
    When I list tags
    Then The response code is 200
    And The tag with name "a" is in the response
    And The tag with name "b" is in the response

  Scenario: Having 2 tags, list tags authenticated as a linguist
    Given I login as "user" with password "password"
    Given I create a tag with name "a"
    Given I create a tag with name "b"
    When I list tags
    Then The response code is 200
    And The tag with name "a" is in the response
    And The tag with name "b" is in the response

  Scenario: Having 0 tags, list tags authenticated
    Given I login as "user" with password "password"
    When I list tags
    Then The response code is 200
    And The tags' list is empty

  Scenario: Having 1 tag hierarchy, list tags authenticated as a linguist
    Given I login as "admin" with password "password"
    When I create a new Tag Hierarchy called "tag hierarchy"
    Given I create a tag with name "a" linked to the tag hierarchy called "tag hierarchy"
    Given I create a tag with name "b" linked to the tag hierarchy called "tag hierarchy"
    Given I login as "user" with password "password"
    When I list tags in the tag hierarchy called "tag hierarchy"
    Then The response code is 201

  Scenario: Having 1 tag hierarchy, list only tags in the tag hierarchy as a linguist
    Given I login as "admin" with password "password"
    When I create a new Tag Hierarchy called "tag hierarchy"
    Given I create a tag with name "c" not linked to any tag hierarchy
    Given I login as "user" with password "password"
    Then The tags' list is empty in the tag hierarchy called "tag hierarchy"


  Scenario: Try to list tags in a tag hierarchy without authenticating
    Given I'm not logged in
    And It exists a TagHierarchy with name "tag hierarchy"
    When I list tags in tag hierarchy "tag hierarchy"
    Then The response code is 401