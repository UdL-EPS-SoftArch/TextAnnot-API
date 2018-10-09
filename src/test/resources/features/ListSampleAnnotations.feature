Feature: List all the annotations associated to a sample



  Scenario: List 1 Annotation referencing a Sample
    Given I login as "user" with password "password"
    Given I create an annotation with start 0 and end 10
    Given I create a different sample with text "sampleExpectedText"
    When  I link the previous annotation with the previous sample
    Then  The annotation with the text "sampleExpectedText" has been linked to the sample

  Scenario: List 5 Annotations referencing a Sample
    Given I login as "user" with password "password"
    Given I create a different sample with text "5RelatedAnnotations" with 5 related Annotations
    When  I search by Annotated as the last sample
    Then  I get a List with the said number of annotations



