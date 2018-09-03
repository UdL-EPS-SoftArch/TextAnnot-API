Feature: Create XML Sample
    In order to allow linguists to upload their XML samples
    As a linguist
    I want to create a new xml sample

  Scenario: Upload a XML file as linguist
    Given I login as "user" with password "password"
    And There is a metadata template with name "TemplateA"
    And The metadata template "TemplateA" has fields
      | número_muestra      | String                |
      | código_informante   | String                |
      | transliteración     | String                |
      | revisión_primera    | String                |
      | revisión_segunda    | String                |
      | etiquetado          | String                |
      | fecha_recogida      | String                |
      | palabras            | String                |
      | género_discursivo   | String                |
      | observaciones       | String                |
    When I upload the XML file with filename "0607_A1h1b0n20170523.xml" described by "TemplateA"
    Then The response code is 201
    And It has been created a XmlSample containing the text "¿Cómo es mi ciudad?"
    And It has been created a XmlSample with the following 7 values
      | número_muestra      | 0607                  |
      | código_informante   | 0607_A1h1b0n20170523  |
      | transliteración     | David Benioff         |
      | fecha_recogida      | 01-06-2017            |
      | palabras            | 13                    |
      | género_discursivo   | descripción           |
      | observaciones       | --                    |