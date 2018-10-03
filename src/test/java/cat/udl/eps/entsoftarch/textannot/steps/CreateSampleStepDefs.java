package cat.udl.eps.entsoftarch.textannot.steps;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataTemplate;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataTemplateRepository;
import cat.udl.eps.entsoftarch.textannot.repository.SampleRepository;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Optional;

public class CreateSampleStepDefs {
    private static final Logger logger = LoggerFactory.getLogger(RegisterLinguistStepDef.class);

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private MetadataTemplateRepository metadataTemplateRepository;

    private String newResourceUri;

    @When("^I create a new sample with text \"([^\"]*)\"$")
    public void iCreateANewSample(String text) throws Throwable{
        JSONObject sample = new JSONObject();
        sample.put("text", text);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/samples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sample.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @And("^It has been created a sample with text \"([^\"]*)\"$")
    public void itHasBeenCreatedASample(String text) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
        .andExpect(jsonPath("$.text", is(text)));
    }

    @And("^It has not been created a sample with text \"([^\"]*)\"$")
    public void itHasNotBeenCreatedASample(String text) {
        Assert.assertEquals(0,sampleRepository.count());
        Assert.assertEquals(0,sampleRepository.findByTextContaining(text).size());
    }

    @When("^I create a new sample with no text field$")
    public void itHasNotBeenCreatedASampleGivenNoTextField () throws Throwable {
        JSONObject sample = new JSONObject();
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/samples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sample.toString())
                       .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @When("^I create a new sample with text \"([^\"]*)\" with metadata template \"([^\"]+)\"$")
    public void iCreateANewSampleWithMetadataTemplate(String text, String mtName) throws Throwable{
        JSONObject sample = new JSONObject();
        sample.put("text", text);
        Optional<MetadataTemplate> metadataTemplateOptional = metadataTemplateRepository.findByName(mtName);
        Assert.assertTrue("metadataTemplate is present", metadataTemplateOptional.isPresent());
        sample.put("describedBy", metadataTemplateOptional.get().getUri());
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/samples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sample.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @And("^The sample with text \"([^\"]+)\" has a metadata template \"([^\"]+)\"$")
    public void theSampleWithTextHasMetadataTemplate(String text, String mtName) throws Throwable {

        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        JSONObject jsonObject = new JSONObject(stepDefs.result.andReturn().getResponse().getContentAsString());
        JSONObject links = (JSONObject)jsonObject.get("_links");
        JSONObject descBy = (JSONObject)links.get("describedBy");
        String mtUrl = descBy.getString("href");
        stepDefs.result = stepDefs.mockMvc.perform(
                get(mtUrl)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(mtName)));


    }
}
