package cat.udl.eps.entsoftarch.textannot.steps;

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;


public class ListSampleAnnotationsStepDefs {

    private static String currentUsername;
    private static String currentPassword;

    @Before
    public void setup() {
        // Clear authentication credentials at the start of every test.
        ListSampleAnnotationsStepDefs.currentPassword = "";
        ListSampleAnnotationsStepDefs.currentUsername = "";
    }

    static RequestPostProcessor authenticate() {
        return currentUsername!=null ? httpBasic(currentUsername, currentPassword) : anonymous();
    }


    @Given("^I login in the system as \"([^\"]*)\" with password \"([^\"]*)\"$")
    public void iLoginInTheSystemAsWithPassword(String arg0, String arg1) throws Throwable {
        ListSampleAnnotationsStepDefs.currentUsername = arg0;
        ListSampleAnnotationsStepDefs.currentPassword = arg1;
    }

    @Given("^I create an annotation with start (\\d+) and end (\\d+)$")
    public void iCreateAnAnnotationWithStartAndEnd(int arg0, int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^I create an sample with text \"([^\"]*)\"$")
    public void iCreateAnSampleWithText(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I link the previous annotation with the previous sample$")
    public void iLinkThePreviousAnnotationWithThePreviousSample() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^The annotation has been linked to the sample$")
    public void theAnnotationHasBeenLinkedToTheSample() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
