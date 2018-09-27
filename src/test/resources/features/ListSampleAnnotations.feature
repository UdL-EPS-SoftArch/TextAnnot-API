Feature: In order to see how a sample is annotated
  As a Linguist
  I want to list all the annotations associated to a sample



  Scenario: List 1 Annotation referencing a Sample
    Given I login in the system as "linguist" with password "password"
    Given I create an annotation with start 0 and end 4
    Given I create a sample with text "abcd"
    When  I link the previous annotation with the previous sample
    Then  The annotation has been linked to the sample