Feature: Create Annotation
  In order to allow a linguist to annotate a text sample
  As a linguist
  I want to create a new annotation of a sample with a tag

  Scenario: Create a new annotation as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with start 0 and end 4
    Then The response code is 201
    And It has been created a new annotation with start 0 and end 4

  Scenario: Create a new annotation with a wrong start as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with start -1 and end 4
    Then The response code is 400
    And The error message is "Invalid range"


  Scenario: Create a new annotation with a wrong end as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with start 3 and end 0
    Then The response code is 400
    And The error message is "Invalid range"


  Scenario: Create a new annotation with a wrong start and end as a linguist
    Given I login as "user" with password "password"
    And I create a new sample with text "abcdef"
    When I create a new annotation with start -1 and end -5
    Then The response code is 400
    And The error message is "Invalid range"



