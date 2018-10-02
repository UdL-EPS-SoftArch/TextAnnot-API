@tag
Feature: Tag
  In order to allow an administrator to define annotation tags
  As an admin
  I want to create a new tag inside a tag hierarchy

  Scenario: Create a new tag as an admin
    Given I login as "admin" with password "password"
    When I create a new tag with name "newtag"
    Then The response code is 201
    And It has been created a new tag with name "newtag" and Id 1

  Scenario: Try to register new tag without authenticating
    Given I'm not logged in
    When I create a new tag with name "noauthenticating"
    Then The response code is 401
    And It hasn't been created a tag with name "noauthenticating" and Id 1

  Scenario: Create a new tag as an admin
    Given I login as "admin" with password "password"
    When I create a new tag with name ""
    Then The response code is 400
    And The error message is "must not be blank"

  Scenario: Try to register new tag as a linguistic
    Given I login as "linguist" with password "password"
    When I create a new tag with name "noauthenticating"
    Then The response code is 401
    And It has not been created a tag with name "noauthenticating" and Id 1

  Scenario: Create a Tag linked to a Tag Hierarchy as admin
    Given I login as "admin" with password "password"
    And Exists a TagHierarchy with name "hierarchy"
    And Exists a Tag with name "Tag"
    When I create a new tag with name "Tag" defined in the tag hierarchy "admin tags"
    Then The tag hierarchy "admin tags" defines a tag with the text "Tag text..."
    And The response code is 204

