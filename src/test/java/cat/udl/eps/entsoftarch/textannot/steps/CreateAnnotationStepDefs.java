package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.repository.AnnotationRepository;
import cat.udl.eps.entsoftarch.textannot.repository.SampleRepository;
import cat.udl.eps.entsoftarch.textannot.repository.TagRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    private SampleRepository sampleRepository;

    @Autowired
    public CreateAnnotationStepDefs(AnnotationRepository annotationRepository) {
        this.annotationRepository = annotationRepository;
        this.initialAnnotationCount = annotationRepository.count();
    }

    @When("^I create a new annotation with start (\\d+|-\\d+) and end (\\d+|-\\d+) without a sample$")
    public void iCreateANewAnnotationWithStartAndEndWithoutASample(int start, int end) throws Throwable {

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


    @And("^It has not been created a new annotation$")
    public void itHasNotBeenCreatedANewAnnotation() throws Throwable {
        Assert.assertEquals(initialAnnotationCount, annotationRepository.count());

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

        JSONObject links = (JSONObject) jsonObject.get("_links");
        JSONObject tag = (JSONObject) links.get("tag");
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
        annotation.put("tag",tagUri);

        stepDefs.result = stepDefs.mockMvc.perform(
                patch(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(annotation.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }


    @And("^It has been created a new annotation with start (\\d+), end (\\d+), reviewed is false and username of the linguist is \"([^\"]*)\"$")
    public void itHasBeenCreatedANewAnnotationWithStartEndReviewedIsFalseAndUsernameOfTheLinguistIs(int start, int end, String username) throws Throwable {

        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(jsonPath("$.start", is(start)))
                .andExpect(jsonPath("$.end", is(end)))
                .andExpect(jsonPath("$.reviewed", is(false)))
                .andDo(print());

        JSONObject jsonObject = new JSONObject(stepDefs.result.andReturn().getResponse().getContentAsString());
        JSONObject links = (JSONObject) jsonObject.get("_links");
        JSONObject linguist = (JSONObject) links.get("linguist");
        String linguistUri = linguist.getString("href");

        stepDefs.result = stepDefs.mockMvc.perform(
                get(linguistUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(jsonPath("$.username", is(username)))
                .andDo(print());
    }

    @When("^I create a new annotation with start (\\d+|-\\d+), end (\\d+|-\\d+) and sample \"([^\"]*)\"$")
    public void iCreateANewAnnotationWithStartAndEndAndSample(int start, int end, String sample) throws Throwable {
        JSONObject annotation = new JSONObject();
        annotation.put("start", start);
        annotation.put("end", end);
        String sampleUri = sampleRepository.findByTextContaining(sample).get(0).getUri();
        annotation.put("sample", sampleUri);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/annotations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(annotation.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @And("^It has been created a new annotation with start (\\d+), end (\\d+) and sample is \"([^\"]*)\"$")
    public void itHasBeenCreatedANewAnnotationWithStartEndAndSampleIs(int start, int end, String sample) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(jsonPath("$.start", is(start)))
                .andExpect(jsonPath("$.end", is(end)))
                .andDo(print());

        JSONObject jsonObject = new JSONObject(stepDefs.result.andReturn().getResponse().getContentAsString());
        JSONObject links = (JSONObject) jsonObject.get("_links");
        JSONObject sampleJSON = (JSONObject) links.get("sample");
        String sampletUri = sampleJSON.getString("href");

        stepDefs.result = stepDefs.mockMvc.perform(
                get(sampletUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(jsonPath("$.text", is(sample)))
                .andDo(print());
    }

    @When("^I create a new annotation with null start, end (\\d+) and sample \"([^\"]*)\"$")
    public void iCreateANewAnnotationWithNullStartAndEndAndSample(int end, String sample) throws Throwable {
        JSONObject annotation = new JSONObject();
        annotation.put("end", end);
        String sampleUri = sampleRepository.findByTextContaining(sample).get(0).getUri();
        annotation.put("sample", sampleUri);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/annotations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(annotation.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }


    @When("^I create a new annotation with start (\\d+), null end and sample \"([^\"]*)\"$")
    public void iCreateANewAnnotationWithStartAndNullEndAndSample(int start, String sample) throws Throwable {
        JSONObject annotation = new JSONObject();
        annotation.put("start", start);
        String sampleUri = sampleRepository.findByTextContaining(sample).get(0).getUri();
        annotation.put("sample", sampleUri);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/annotations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(annotation.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @When("^I create a new annotation with null start, null end and sample \"([^\"]*)\"$")
    public void iCreateANewAnnotationWithNullStartAndNullEndAndSample(String sample) throws Throwable {
        JSONObject annotation = new JSONObject();
        String sampleUri = sampleRepository.findByTextContaining(sample).get(0).getUri();
        annotation.put("sample", sampleUri);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/annotations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(annotation.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @When("^I create a new annotation with start (\\d+), end (\\d+), sample \"([^\"]*)\" and I associate a new tag with name \"([^\"]*)\"$")
    public void iCreateANewAnnotationWithStartEndSampleAndIAssociateANewTagWithName(int start, int end, String sample, String tagName) throws Throwable {

        JSONObject annotation = new JSONObject();
        annotation.put("start", start);
        annotation.put("end", end);
        String tagUri = tagRepository.findByNameContaining(tagName).get(0).getUri();
        annotation.put("tag", tagUri);
        String sampleUri = sampleRepository.findByTextContaining(sample).get(0).getUri();
        annotation.put("sample", sampleUri);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/annotations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(annotation.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }
}

