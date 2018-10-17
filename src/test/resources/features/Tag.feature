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

  Scenario: Create a new tag as an admin
    Given I login as "admin" with password "password"
    When I create a new tag with name ""
    Then The response code is 400
    And The error message is "must not be blank"

  Scenario: Try to register new tag as a linguistic
    Given I login as "linguist" with password "password"
    When I create a new tag with name "noauthenticating"
    Then The response code is 401

  Scenario: Create a Tag linked to a Tag Hierarchy as admin
    Given I login as "admin" with password "password"
    And Exists a TagHierarchy with name "hierarchy"
    And Exists a Tag with name "Tag"
    When I create a new tag with name "Tag" defined in the tag hierarchy "hierarchy"
    Then The tag hierarchy "hierarchy" defines a tag with the text "Tag text..."
    And The response code is 204

  Scenario: Setting the parent of a given tag
    Given I login as "admin" with password "password"
    And Exists a TagHierarchy with name "hierarchy"
    And I create the parent Tag with name "parent"
    And I create the child Tag with name "child"
    When I set the parent with name "parent" to child with name "child"
    And I create link between parent with name "parent" and child with name "child"
    Then Parent with name "parent" was set correctly to the child with name "child"

  Scenario: Linking parent and child with different tag hierarchy
    Given I login as "admin" with password "password"
    And Exists a TagHierarchy with name "hierarchy1"
    And I create the parent Tag with name "parent"
    And Exists a TagHierarchy with name "hierarchy2"
    And I create the child Tag with name "child"
    When I set the parent with name "parent" to child with name "child"
    And I try to link between parent with name "parent" and child with name "child"
    Then The response code is 400
    And The error message is "Invalid tag hierarchy"

  Scenario: Linking parent without tag hierarchy with child with tag hierarchy
    Given I login as "admin" with password "password"
    And I create the parent Tag with name "parent"
    And Exists a TagHierarchy with name "hierarchy"
    And I create the child Tag with name "child"
    When I set the parent with name "parent" to child with name "child"
    And I try to link between parent with name "parent" and child with name "child"
    Then The response code is 400
    And The error message is "Invalid tag hierarchy"

  Scenario: Linking parent with tag hierarchy with child without tag hierarchy
    Given I login as "admin" with password "password"
    And I create the child Tag with name "child"
    And Exists a TagHierarchy with name "hierarchy"
    And I create the parent Tag with name "parent"
    When I set the parent with name "parent" to child with name "child"
    And I try to link between parent with name "parent" and child with name "child"
    Then The response code is 400
    And The error message is "Invalid tag hierarchy"




