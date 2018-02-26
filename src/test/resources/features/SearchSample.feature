Feature: Search Sample
  In order to allow a user to search a Sample
  As a user
  I want to search a sample

  Scenario: Search a sample
    Given I login as "user" with "password"
    And There are some samples with text "sample1" "sample2" and "sample3"
    When I search a sample with the "word"
    Then The response code is 200
    And The samples are "sample"



