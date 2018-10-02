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
