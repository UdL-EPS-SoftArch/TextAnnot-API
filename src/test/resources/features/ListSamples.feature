Feature: List samples
  //TODO explain

  Scenario: Try to list samples without authenticating
    Given: I’m not logged in
    When: I list samples
    Then: The response code is 401

  Scenario: Having 2 samples, list samples authenticated as admin
    Given: I login as "admin" with password "password"
    And: I create a sample with text "a"
    And: I create a sample with text “b"
    When: I list samples
    Then: The response code is 200
    And: The sample with text "a" is in the response
    And: The sample with text "b" is in the response

  Scenario: Having 2 samples, list samples authenticated as a linguist
    Given: I login as "user" with password "password"
    And: I create a sample with text “a"
    And: I create a sample with text “b"
    When: I list samples
    Then: The response code is 200
    And: The sample with text "a" is in the response
    And: The sample with text "b" is in the response

  Scenario: Having 0 samples, list samples authenticated
    Given: I login as "user" with password "password"
    When: I list samples.
    Then: The response code is 200
    And: The list is empty.
