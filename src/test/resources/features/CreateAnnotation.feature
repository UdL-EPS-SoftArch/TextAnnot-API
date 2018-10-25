Feature: Create Annotation
  In order to allow a linguist to annotate a text sample
  As a linguist
  I want to create a new annotation of a sample with a tag

  Scenario: Create a new annotation as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with start 0, end 4 and sample "abcdef"
    Then The response code is 201
    And It has been created a new annotation with start 0, end 4, reviewed is false and username of the linguist is "user"

  Scenario: Create a new annotation with the same end as sample text length as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with start 0, end 6 and sample "abcdef"
    Then The response code is 201
    And It has been created a new annotation with start 0, end 6, reviewed is false and username of the linguist is "user"

  Scenario: Create a new annotation with a sample as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with start 0, end 4 and sample "abcdef"
    Then The response code is 201
    And It has been created a new annotation with start 0, end 4 and sample is "abcdef"

  Scenario: Create a new annotation without a sample as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with start 0 and end 4 without a sample
    Then The response code is 400
    And It has not been created a new annotation

  Scenario: Create a new annotation with a wrong start as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with start -1, end 4 and sample "abcdef"
    Then The response code is 400
    And The error message is "Invalid range"
    And It has not been created a new annotation

  Scenario: Create a new annotation with a wrong end as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with start 3, end 0 and sample "abcdef"
    Then The response code is 400
    And The error message is "Invalid range"
    And It has not been created a new annotation

  Scenario: Create a new annotation with a greater end than sample text length as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with start 3, end 7 and sample "abcdef"
    Then The response code is 400
    And The error message is "Invalid range"
    And It has not been created a new annotation

  Scenario: Create a new annotation with a wrong start and end as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with start -1, end -5 and sample "abcdef"
    Then The response code is 400
    And The error message is "Invalid range"
    And It has not been created a new annotation

  Scenario: Create a new annotation with null start as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with null start, end 5 and sample "abcdef"
    Then The response code is 400
    And The error message is "Invalid range"
    And It has not been created a new annotation

  Scenario: Create a new annotation with null end as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with start 0, null end and sample "abcdef"
    Then The response code is 400
    And The error message is "Invalid range"
    And It has not been created a new annotation

  Scenario: Create a new annotation with null start and null end as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with null start, null end and sample "abcdef"
    Then The response code is 400
    And The error message is "Invalid range"
    And It has not been created a new annotation

  Scenario: Attempting to create a new annotation without authenticating
    Given I'm not logged in
    And Exists a Sample with text "abcdef"
    When I create a new annotation with start 0, end 3 and sample "abcdef"
    Then The response code is 401
    And It has not been created a new annotation

  Scenario: Create a new annotation as a linguist with a tag associated
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    And Exists a Tag with name "tagName"
    When I create a new annotation with start 0, end 4, sample "abcdef" and I associate a new tag with name "tagName"
    Then The response code is 201
    And It has been created a new annotation with start 0, end 4, reviewed is false and tagName "tagName"

  Scenario: Create two new annotations as a linguist with the same tag associated
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    And Exists a Tag with name "tagName"
    And I create a new annotation with start 0, end 2, sample "abcdef" and I associate a new tag with name "tagName"
    When I create a new annotation with start 3, end 4, sample "abcdef" and I associate a new tag with name "tagName"
    Then The response code is 201
    And It has been created a new annotation with start 3, end 4, reviewed is false and tagName "tagName"

  Scenario: Change the tag of an annotation as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    And Exists a Tag with name "tagName1"
    And Exists a Tag with name "tagName2"
    And I create a new annotation with start 0, end 4, sample "abcdef" and I associate a new tag with name "tagName1"
    When I change the tag of the annotation to "tagName2"
    Then The response code is 200
    And It has been created a new annotation with start 0, end 4, reviewed is false and tagName "tagName2"