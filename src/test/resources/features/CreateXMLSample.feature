Feature: Create XML Sample
    In order to allow linguists to upload their XML samples
    As a linguist
    I want to create a new xml sample

  Scenario: Create a new xml sample as linguist
    Given I login as "linguist" with password "password"
    When I create a new xml sample with content "<xml>"
    Then The response code is 201
    And It has been created a sample with content "<xml>"

  Scenario: Attempting to create a new xml sample without authenticating
    Given I'm not logged in
    When I create a new xml sample with content "Lorem ipsum text"
    Then The response code is 401
    And It has not been created a xml sample with content "Lorem ipsum text"

  Scenario: Attempting to create a xml sample with empty content
    Given I login as "linguist" with password "password"
    When I create a new xml sample without content
    Then The response code is 400
    And It has not been created a sample with empty text