Feature: Create XML Sample
    In order to allow linguists to upload their XML samples
    As a linguist
    I want to create a new xml sample

  Scenario: Upload a XML file as linguist
    Given I login as "user" with password "password"
    And There is a metadata template with name "TemplateA"
    And The metadata template "TemplateA" has fields
      | datos_generales | número_muestra      | String                |
      | datos_generales | código_informante   | String                |
      | datos_generales | transliteración     | String                |
      | datos_generales | revisión_primera    | String                |
      | datos_generales | revisión_segunda    | String                |
      | datos_generales | etiquetado          | String                |
      | datos_muestra   | fecha_recogida      | String                |
      | datos_muestra   | palabras            | String                |
      | datos_muestra   | género_discursivo   | String                |
      | datos_muestra   | observaciones       | String                |
    When I upload the XML file with filename "0607_A1h1b0n20170523.xml" described by "TemplateA"
    Then The response code is 201
    And It has been created a XmlSample containing the text "¿Cómo es mi ciudad?"
    And It has been created a XmlSample with the following 7 values
      | datos_generales | número_muestra    | 0607                  |
      | datos_generales | código_informante | 0607_A1h1b0n20170523  |
      | datos_generales | transliteración   | David Benioff         |
      | datos_muestra   | fecha_recogida    | 01-06-2017            |
      | datos_muestra   | palabras          | 13                    |
      | datos_muestra   | género_discursivo | descripción           |
      | datos_muestra   | observaciones     | --                    |