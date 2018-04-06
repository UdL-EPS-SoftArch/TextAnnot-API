Feature: Find by Metadata Template

  Scenario: Search a Sample by Metadata Template
    Given I login as "user" with password "password"
    And There are some Metadata Templates with text "Hola que tal" "Yo bien y tu?" and "Mas o menos"
    And There is a sample with text "patata" defined by "Hola que tal"
    And There is a sample with text "col" defined by "Yo bien y tu?"
    And There is a sample with text "zanahoria" defined by "Mas o menos"
    When I search a sample which is defined by the Metadata Template "Hola que tal"
    Then The response code is 200
    And The sample is "patata"

  Scenario: Search a Sample by Metadata Template
    Given I login as "user" with password "password"
    And There are some Metadata Templates with text "uno" "dos" and "tres"
    And There is a sample with text "pareja" defined by "dos"
    And There is a sample with text "solo" defined by "uno"
    When I search a sample which is defined by the Metadata Template "tres"
    Then The response code is 200
    And The samples are empty

  Scenario: Search a Sample by Metadata Template
    Given I login as "user" with password "password"
    And There are some Metadata Templates with text "uno" "dos" and "tres"
    And There is a sample with text "pareja" defined by "dos"
    And There is a sample with text "solo" defined by "uno"
    And There is a sample with text "trio" defined by "tres"
    When I search a sample with an inexistent Metadata Template named "sjfsj"
    Then The response code is 200
    And The samples are empty