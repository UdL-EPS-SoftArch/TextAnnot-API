package cat.udl.eps.entsoftarch.textannot.steps;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataField;
import cat.udl.eps.entsoftarch.textannot.domain.MetadataValue;
import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import cat.udl.eps.entsoftarch.textannot.domain.TagHierarchy;
import cat.udl.eps.entsoftarch.textannot.repository.SampleRepository;
import cat.udl.eps.entsoftarch.textannot.repository.TagHierarchyRepository;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.Optional;
import org.junit.Assert;
import org.springframework.http.MediaType;

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
        TagHierarchy tagHierarchy = new TagHierarchy();
        tagHierarchy.setName(name);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/tagHierarchies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(tagHierarchy))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^The Tag Hierarchy name is \"([^\"]*)\"$")
    public void theTagHierarchyNameIs(String name) throws Exception {
        String location = stepDefs.result.andReturn().getResponse().getHeader("Location");
        Assert.assertTrue("location is not null", location != null);
        stepDefs.result = stepDefs.mockMvc.perform(
                get(location)
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(name)));
    }

    @Given("^Exists a Sample with text \"([^\"]*)\"$")
    public void thereIsASingleSampleWithText(String text) {
        sample = new Sample(text);
        sampleRepository.save(sample);
    }

    @And("^Exists a Tag Hierarchy with name \"([^\"]*)\"$")
    public void existsATagHierarchyWithName(String name) {
        tagHierarchy = new TagHierarchy();
        tagHierarchy.setName(name);
        tagHierarchyRepository.save(tagHierarchy);
    }

    @When("^I set the previous Sample tagged by the previous Tag Hierarchy$")
    public void iSetThePreviousSampleTaggedByThePreviousTagHierarchy() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                put("/samples/"+ sample.getId() +"/taggedBy")
                        .contentType("text/uri-list")
                        .content(tagHierarchy.getUri())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()));
    }

    @Then("^The Tag Hierarchy have (\\d+) samples$")
    public void theTagHierarchyWithNameHaveSamples(String name, int numSamples) throws Exception {
        stepDefs.mockMvc.perform(
                get("/tagHierarchies/" + tagHierarchy.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @Then("^The Sample is taged by the Tag Hierarchy$")
    public void theSampleIsTagedByTheTagHierarchy() throws Throwable {
        stepDefs.mockMvc.perform(
                get("/samples/" + sample.getId() + "/taggedBy")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.id", is(tagHierarchy.getId())));
    }

    @When("^I create a new sample with text \"([^\"]*)\" tagged by the tag hierarchy \"([^\"]*)\"$")
    public void iCreateANewSampleWithTextTaggedByTheTagHierarchy(String text, String tagHierarchyName) throws Throwable {
        Optional<TagHierarchy> tagHierarchy = tagHierarchyRepository.findByName(tagHierarchyName);
        Assert.assertTrue("Tag hierarchy must exist", tagHierarchy.isPresent());
        Sample sample = new Sample();
        sample.setText(text);
        sample.setTaggedBy(tagHierarchy.get());
        stepDefs.mockMvc.perform(post("/samples")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(stepDefs.mapper.writeValueAsString(sample))
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .with(AuthenticationStepDefs.authenticate()))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Then("^The tag hierarchy \"([^\"]*)\" tags a sample with text \"([^\"]*)\"$")
    public void theTagHierarchyTagsASampleWithText(String tagHierarchyName, String text) throws Throwable {
        Optional<TagHierarchy> tagHierarchy = tagHierarchyRepository.findByName(tagHierarchyName);
        Assert.assertTrue("Tag hiearchy must exist", tagHierarchy.isPresent());
        stepDefs.mockMvc.perform(
            get("/samples/search/findByTaggedBy?taggedBy=" + tagHierarchy.get().getUri())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(AuthenticationStepDefs.authenticate()))
            .andDo(print())
            .andExpect(jsonPath("$._embedded.samples[0].text", is(text)));
    }
}
