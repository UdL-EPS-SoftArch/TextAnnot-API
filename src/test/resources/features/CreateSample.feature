Feature: Create Sample
  In order to allow linguists to upload their samples
  As a linguist
  I want to create a new sample

  Scenario: Create a new sample as linguist
    Given I login as "linguist" with password "password"
    When I create a new sample with text "Lorem ipsum text"
    Then The response code is 201
    And It has been created a sample with text "Lorem ipsum text"

  Scenario: Attempting to create a new sample without authenticating
    Given I'm not logged in
    When I create a new sample with text "Lorem ipsum text"
    Then The response code is 401
    And It has not been created a sample with text "Lorem ipsum text"

  Scenario: Attempting to create a sample with empty text
    Given I login as "linguist" with password "password"
    When I create a new sample with text ""
    Then The response code is 400
    And It has not been created a sample with text ""
