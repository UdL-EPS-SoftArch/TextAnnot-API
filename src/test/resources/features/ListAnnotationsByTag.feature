Feature: List all the annotations tagged with a certain Tag

  Scenario: List 1 Annotations Tagged by a Tag
    Given I login as "user" with password "password"
    Given I create a certain Tag with text "1TaggedAnnotation" and Tag 1 Annotations
    When  I search the annotations by Tagged as the created Tag
    Then  I get a List with the said number of tagged annotations

  Scenario: List 8 Annotations Tagged by a Tag
    Given I login as "user" with password "password"
    Given I create a certain Tag with text "8TaggedAnnotations" and Tag 8 Annotations
    When  I search the annotations by Tagged as the created Tag
    Then  I get a List with the said number of tagged annotations