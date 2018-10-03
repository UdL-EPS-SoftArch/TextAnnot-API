Feature: List all the annotations tagged with a certain Tag



  Scenario: List 1 Annotation tagged with a certain Tag
    Given I login as "admin" with password "password"
    Given I create one annotation with start 0 and end 10
    Given I create a certain Tag with the name "ExpectedTagName"
    When  I tag the annotation with the Tag
    Then  The annotation has been tagged with the Tag named "ExpectedTagName"

  Scenario: List 8 Annotations Tagged by a Tag
    Given I login as "user" with password "password"
    Given I create a certain Tag with text "8TaggedAnnotations" and Tag 8 Annotations
    When  I search the annotations by Tagged as the created Tag
    Then  I get a List with the said number of tagged annotations
