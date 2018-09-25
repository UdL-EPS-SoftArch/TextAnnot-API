package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import cat.udl.eps.entsoftarch.textannot.domain.TagHierarchy;
import cat.udl.eps.entsoftarch.textannot.repository.SampleRepository;
import cat.udl.eps.entsoftarch.textannot.repository.TagHierarchyRepository;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateTagHierarchyDefs {

    private final StepDefs stepDefs;
    private final SampleRepository sampleRepository;
    private final TagHierarchyRepository tagHierarchyRepository;

    private Sample sample;
    private TagHierarchy tagHierarchy;

    public CreateTagHierarchyDefs(StepDefs stepDefs,
                                  SampleRepository sampleRepository,
                                  TagHierarchyRepository tagHierarchyRepository) {
        this.stepDefs = stepDefs;
        this.sampleRepository = sampleRepository;
        this.tagHierarchyRepository = tagHierarchyRepository;
    }

    @When("^I create a new Tag Hierarchy with name \"([^\"]*)\"$")
    public void iCreateANewTagHierarchyWithName(String name) throws Exception {
        JSONObject tagHierarchy = new JSONObject();
        tagHierarchy.put("name", name);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/tagHierarchies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagHierarchy.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^The Tag Hierarchy name is \"([^\"]*)\"$")
    public void theTagHierarchyNameIs(String name) throws Exception {
        stepDefs.mockMvc.perform(
                get("/tagHierarchies/1")
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(name)));
    }

    @Given("^Exists a Sample with text \"([^\"]*)\"$")
    public void thereIsASingleSampleWithText(String text) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        sample = new Sample(text);
        sampleRepository.save(sample);
    }

    @And("^Exists a Tag Hierarchy with name \"([^\"]*)\"$")
    public void existsATagHierarchyWithName(String name) throws Throwable {
        tagHierarchy = new TagHierarchy();
        tagHierarchy.setName(name);
        tagHierarchyRepository.save(tagHierarchy);
    }

    @When("^I set the previous Sample tagged by the previous Tag Hierarchy$")
    public void iSetThePreviousSampleTaggedByThePreviousTagHierarchy() throws Throwable {
        stepDefs.mockMvc.perform(
                put("/samples/"+ sample.getId() +"/taggedBy")
                        .contentType("text/uri-list")
                        .content(tagHierarchy.getUri())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(status().is(204));
    }

    @Then("^The Tag Hierarchy has the sample associated$")
    public void theTagHierarchyHasTheSampleAssociated() throws Throwable {
        stepDefs.mockMvc.perform(
                get("/tagHierarchies/" + tagHierarchy.getId() + "/tags")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.samples[1]", is(1)));
    }

    @Then("^The Tag Hierarchy have (\\d+) samples$")
    public void theTagHierarchyWithNameHaveSamples(String name, int numSamples) throws Exception {
        stepDefs.mockMvc.perform(
                get("/tagHierarchies/" + tagHierarchy.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
