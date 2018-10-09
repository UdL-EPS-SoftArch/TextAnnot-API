package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.Annotation;
import cat.udl.eps.entsoftarch.textannot.domain.Tag;
import cat.udl.eps.entsoftarch.textannot.repository.AnnotationRepository;
import cat.udl.eps.entsoftarch.textannot.repository.TagRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import gherkin.deps.com.google.gson.JsonObject;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.anyOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private TagRepository tagRepository;

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

    @And("^It has been created a new annotation with start (\\d+), end (\\d+) and reviewed is false$")
    public void itHasBeenCreatedANewAnnotationWithStartAndEndAndId(int start, int end) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.start", is(start)))
                .andExpect(jsonPath("$.end", is(end)))
                .andExpect(jsonPath("$.reviewed", is(false)));
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

    @When("^I create a new annotation with start (\\d+), end (\\d+) and I associate a new tag with name \"([^\"]*)\"$")
    public void iCreateANewAnnotationWithStartEndAndIAssociateANewTagWithName(int start, int end, String tagName) throws Throwable {

        JSONObject annotation = new JSONObject();
        annotation.put("start", start);
        annotation.put("end", end);
        String tagUri = tagRepository.findByNameContaining(tagName).get(0).getUri();
        annotation.put ("tag",tagUri);


        stepDefs.result = stepDefs.mockMvc.perform(
                post("/annotations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(annotation.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @And("^It has been created a new annotation with start (\\d+), end (\\d+), reviewed is false and tagName \"([^\"]*)\"$")
    public void itHasBeenCreatedANewAnnotationWithStartEndReviewedIsFalseAndTagName(int start, int end, String tagName) throws Throwable {

        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(jsonPath("$.start", is(start)))
                .andExpect(jsonPath("$.end", is(end)))
                .andExpect(jsonPath("$.reviewed", is(false)))
                .andDo(print());

        JSONObject jsonObject = new JSONObject(stepDefs.result.andReturn().getResponse().getContentAsString());

        JSONObject links = (JSONObject)jsonObject.get("_links");
        JSONObject tag = (JSONObject)links.get("tag");
        String tagUri = tag.getString("href");

        stepDefs.result = stepDefs.mockMvc.perform(
                get(tagUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(tagName)));
    }

    @When("^I change the tag of the annotation to \"([^\"]*)\"$")
    public void iChangeTheTagOfTheAnnotationTo(String tagName2) throws Throwable {

        JSONObject annotation = new JSONObject();
        String tagUri = tagRepository.findByNameContaining(tagName2).get(0).getUri();
        annotation.put ("tag",tagUri);

        stepDefs.result = stepDefs.mockMvc.perform(
                patch(newResourceUri)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(annotation.toString())
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }
}

