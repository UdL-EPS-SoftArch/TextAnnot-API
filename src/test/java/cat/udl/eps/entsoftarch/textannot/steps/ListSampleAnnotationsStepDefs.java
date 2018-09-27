package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.repository.AnnotationRepository;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


public class ListSampleAnnotationsStepDefs {

    private static String currentUsername;
    private static String currentPassword;

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private AnnotationRepository annotationRepository;

    public ListSampleAnnotationsStepDefs(StepDefs stepDefs) {
        this.stepDefs = stepDefs;
    }

    private String newSampleUri;
    private String newAnnotationUri;

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
        JSONObject annotation = new JSONObject();
        annotation.put("start", arg0);
        annotation.put("end", arg1);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/annotations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(annotation.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newAnnotationUri = stepDefs.result.andReturn().getResponse().getHeader("Location");

    }

    @Given("^I create a sample with text \"([^\"]*)\"$")
    public void iCreateASampleWithText(String arg0) throws Throwable {
        JSONObject sample = new JSONObject();
        sample.put("text", arg0);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/samples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sample.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newSampleUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @When("^I link the previous annotation with the previous sample$")
    public void iLinkThePreviousAnnotationWithThePreviousSample() throws Throwable {


    }

    @Then("^The annotation has been linked to the sample$")
    public void theAnnotationHasBeenLinkedToTheSample() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
