Feature: Create XML Sample
    In order to allow linguists to upload their XML samples
    As a linguist
    I want to create a new xml sample

  Scenario: Upload a xml sample as linguist
    Given I login as "user" with password "password"
    When I upload a XmlSample with content "content"
    Then The response code is 201
    And It has been created a XmlSample with content "content"