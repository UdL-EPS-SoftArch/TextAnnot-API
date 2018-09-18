Feature: Create Sample
  In order to allow linguists to upload their samples
  As a linguist
  I want to create a new sample

  Scenario: Create a new sample as linguist
    Given I login as "user" with password "password"
    When I create a new sample with text "Lorem ipsum text"
    Then The response code is 201
    And It has been created a sample with text "Lorem ipsum text"

  Scenario: Attempting to create a new sample without authenticating
    Given I'm not logged in
    When I create a new sample with text "Lorem ipsum text"
    Then The response code is 401
    And It has not been created a sample with text "Lorem ipsum text"

  Scenario: Attempting to create a sample without text field
    Given I login as "user" with password "password"
    When I create a new sample with no text field
    Then The response code is 400
    And The error message is "must not be null"

  Scenario: Create a new sample with a metadata template
    Given I login as "user" with password "password"
    And There is a metadata template with name "mtTest"
    When I create a new sample with text "Lorem ipsum text" with metadata template "mtTest"
    Then The response code is 201
    And It has been created a sample with text "Lorem ipsum text"
    And The sample with text "Lorem ipsum text" has a metadata template "mtTest"
