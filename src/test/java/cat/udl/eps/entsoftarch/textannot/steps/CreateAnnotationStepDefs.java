package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.Annotation;
import cat.udl.eps.entsoftarch.textannot.repository.AnnotationRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class CreateAnnotationStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private AnnotationRepository annotationRepository;

    public CreateAnnotationStepDefs(StepDefs stepDefs) {
        this.stepDefs = stepDefs;
    }

    private String newResourceUri;

    @When("^I create a new annotation with start (\\d+|-\\d+) and end (\\d+|-\\d+)$")
    public void iCreateANewAnnotationWithStartAndEnd(int start, int end) throws Throwable {


        JSONObject annotation = new JSONObject();
        annotation.put("start", start);
        annotation.put("end", end);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/annotations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(annotation.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");

    }

    @And("^It has been created a new annotation with start (\\d+) and end (\\d+)$")
    public void itHasBeenCreatedANewAnnotationWithStartAndEndAndId(int start, int end) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.start", is(start)))
                .andExpect(jsonPath("$.end", is(end)));

    }
}
