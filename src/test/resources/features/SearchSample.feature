Feature: Search Sample
  In order to allow a user to search a Sample
  As a user
  I want to search a sample

  Scenario: Search a sample
    Given I login as "user" with password "password"
    And There are some samples with text "Hola que tal" "Yo bien y tu?" and "Mas o menos"
    When I search a sample with the word "a"
    Then The response code is 200
    And The samples are "Hola que tal" and "Mas o menos"



