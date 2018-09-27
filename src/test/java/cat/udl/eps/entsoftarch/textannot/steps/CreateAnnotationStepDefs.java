package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.Annotation;
import cat.udl.eps.entsoftarch.textannot.repository.AnnotationRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.anyOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class CreateAnnotationStepDefs {

    @Autowired
    private StepDefs stepDefs;

    private String newResourceUri;

    private long initialAnnotationCount;

    private AnnotationRepository annotationRepository;

    @Autowired
    public CreateAnnotationStepDefs(AnnotationRepository annotationRepository) {
        this.annotationRepository = annotationRepository;
        this.initialAnnotationCount = annotationRepository.count();
    }

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

    @When("^I create a new annotation with null start and end (\\d+)$")
    public void iCreateANewAnnotationWithNullStartAndEnd(int end) throws Throwable {

        JSONObject annotation = new JSONObject();
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

    @When("^I create a new annotation with (\\d+) start and null end$")
    public void iCreateANewAnnotationWithStartAndNullEnd(int start) throws Throwable {

        JSONObject annotation = new JSONObject();
        annotation.put("start", start);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/annotations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(annotation.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @When("^I create a new annotation with null start and null end$")
    public void iCreateANewAnnotationWithNullStartAndNullEnd() throws Throwable {
        JSONObject annotation = new JSONObject();

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/annotations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(annotation.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @And("^It has not been created a new annotation$")
    public void itHasNotBeenCreatedANewAnnotation() throws Throwable {
        Assert.assertEquals(initialAnnotationCount, annotationRepository.count());

    }
}

