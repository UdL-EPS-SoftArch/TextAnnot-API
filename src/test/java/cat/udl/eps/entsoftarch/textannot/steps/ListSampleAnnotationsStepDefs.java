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
import static org.hamcrest.Matchers.is;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


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

    @Given("^I create a different sample with text \"([^\"]*)\"$")
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
        System.out.println(newSampleUri);
    }

    @When("^I link the previous annotation with the previous sample$")
    public void iLinkThePreviousAnnotationWithThePreviousSample() throws Throwable {

        stepDefs.mockMvc.perform(put("annotated",newSampleUri));

    }

    @Then("^The annotation has been linked to the sample$")
    public void theAnnotationHasBeenLinkedToTheSample() throws Throwable {
        System.out.println(newAnnotationUri);
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newAnnotationUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.annotated",is(newSampleUri)));
    }
}
