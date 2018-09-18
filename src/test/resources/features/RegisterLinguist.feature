Feature: Register Linguist
  In order to allow a new linguist to use the app
  As an admin
  I want to register a new linguist account

  Scenario: Register new linguist as admin
    Given I login as "admin" with password "password"
    When I register a new linguist with username "linguist", email "linguist@textannot.org" and password "password"
    Then The response code is 201
    And It has been created a linguist with username "linguist" and email "linguist@textannot.org", the password is not returned

  Scenario: Try to register new linguist without authenticating
    Given I'm not logged in
    When I register a new linguist with username "linguist", email "linguist@textannot.org" and password "password"
    Then The response code is 401
    And It has not been created a linguist with username "linguist"

  Scenario: Register new linguist with empty password
    Given I login as "admin" with password "password"
    When I register a new linguist with username "linguist", email "linguist@textannot.org" and password ""
    Then The response code is 400
    And The error message is "must not be blank"
    And It has not been created a linguist with username "linguist"

  Scenario: Register new linguist with empty email
    Given I login as "admin" with password "password"
    When I register a new linguist with username "linguist", email "" and password "password"
    Then The response code is 400
    And The error message is "must not be blank"
    And It has not been created a linguist with username "linguist"

  Scenario: Register new linguist with invalid email
    Given I login as "admin" with password "password"
    When I register a new linguist with username "linguist", email "linguistatextannot.org" and password "password"
    Then The response code is 400
    And The error message is "must be a well-formed email address"
    And It has not been created a linguist with username "linguist"

  Scenario: Register new linguist with password shorter than 8 characters
    Given I login as "admin" with password "password"
    When I register a new linguist with username "linguist", email "linguist@textannot.org" and password "pass"
    Then The response code is 400
    And The error message is "length must be between 8 and 256"
    And It has not been created a linguist with username "linguist"