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

