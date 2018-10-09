Feature: Create Annotation
  In order to allow a linguist to annotate a text sample
  As a linguist
  I want to create a new annotation of a sample with a tag

  Scenario: Create a new annotation as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with start 0 and end 4
    Then The response code is 201
    And It has been created a new annotation with start 0, end 4 and reviewed is false

  Scenario: Create a new annotation with a wrong start as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with start -1 and end 4
    Then The response code is 400
    And The error message is "Invalid range"
    And It has not been created a new annotation

  Scenario: Create a new annotation with a wrong end as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with start 3 and end 0
    Then The response code is 400
    And The error message is "Invalid range"
    And It has not been created a new annotation

  Scenario: Create a new annotation with a wrong start and end as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with start -1 and end -5
    Then The response code is 400
    And The error message is "Invalid range"
    And It has not been created a new annotation

  Scenario: Create a new annotation with null start as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with null start and end 5
    Then The response code is 400
    And The error message is "Invalid range"
    And It has not been created a new annotation

  Scenario: Create a new annotation with null end as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with 0 start and null end
    Then The response code is 400
    And The error message is "Invalid range"
    And It has not been created a new annotation

  Scenario: Create a new annotation with null start and null end as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with null start and null end
    Then The response code is 400
    And The error message is "Invalid range"
    And It has not been created a new annotation

  Scenario: Attempting to create a new annotation without authenticating
    Given I'm not logged in
    And I create a new sample with text "abcdef"
    When I create a new annotation with start 0 and end 3
    Then The response code is 401
    And It has not been created a new annotation

  Scenario: Create a new annotation as a linguist with a tag associated
    Given I login as "admin" with password "password"
    And I create a new sample with text "abcdef"
    And I create a new tag with name "tagName"
    When I create a new annotation with start 0, end 4 and I associate a new tag with name "tagName"
    Then The response code is 201
    And It has been created a new annotation with start 0, end 4, reviewed is false and tagName "tagName"

  Scenario: Create two new annotations as a linguist with the same tag associated
    Given I login as "admin" with password "password"
    And I create a new sample with text "abcdef"
    And I create a new tag with name "tagName"
    And I create a new annotation with start 0, end 2 and I associate a new tag with name "tagName"
    When I create a new annotation with start 3, end 4 and I associate a new tag with name "tagName"
    Then The response code is 201
    And It has been created a new annotation with start 3, end 4, reviewed is false and tagName "tagName"

  Scenario: Change the tag of an annotation as a linguist
    Given I login as "admin" with password "password"
    And I create a new sample with text "abcdef"
    And I create a new tag with name "tagName1"
    And I create a new tag with name "tagName2"
    And I create a new annotation with start 0, end 4 and I associate a new tag with name "tagName1"
    When I change the tag of the annotation to "tagName2"
    Then The response code is 200
    And It has been created a new annotation with start 0, end 4, reviewed is false and tagName "tagName2"



