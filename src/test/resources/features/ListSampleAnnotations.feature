Feature: In order to see how a sample is annotated
  As a Linguist
  I want to list all the annotations associated to a sample



  Scenario: List 1 Annotation referencing a Sample
    Given I login as "user" with password "password"
    Given I create an annotation with start 0 and end 10
    Given I create a different sample with text "abcdfdfdfdfdfdfdfdfdfd"
    When  I link the previous annotation with the previous sample
    Then  The annotation has been linked to the sample