Feature: Create XML Sample
    In order to allow linguists to upload their XML samples
    As a linguist
    I want to create a new xml sample

  Scenario: Upload a xml sample as linguist
    Given I login as "user" with password "password"
    When I upload a XmlSample with text "text" and content
    """
    <book>
       <author>Gambardella, Matthew</author>
       <title>XML Developer Guide</title>
       <genre>Computer</genre>
       <price>44.95</price>
       <date>2000-11-01</date>
       <description>An in-depth look at creating applications with XML.</description>
    </book>
    """
    Then The response code is 201
    And It has been created a XmlSample with text "text" and content
    """
    <book>
       <author>Gambardella, Matthew</author>
       <title>XML Developer Guide</title>
       <genre>Computer</genre>
       <price>44.95</price>
       <date>2000-11-01</date>
       <description>An in-depth look at creating applications with XML.</description>
    </book>
    """

  Scenario: Upload a xml sample as linguist
    Given  I login as "user" with password "password"
    When I upload a XmlSample with text "text" and content
    """
    <book>
       <author>Gambardella, Matthew</author>
       <title>XML Developer Guide</title>
       <genre>Computer</genre>
       <price>44.95</price>
       <date>2000-11-01</date>
       <description>An in-depth look at creating applications with XML.</description>
    </book>
    """
    Then The response code is 201

  Scenario: Upload a xml sample as linguist
    Given I login as "user" with password "password"
    When I upload a XmlSample with text "text" and content
    """
    <book>
       <author>Gambardella, Matthew</author>
    </book>
    """
    Then The response code is 201
    And It has been created a new metadataValue with value "Gambardella, Matthew" for metadataField with name "author"
    And It has been created a new metadatafield with name "author" and type "string" and Id 1
