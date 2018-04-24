package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataTemplate;
import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataTemplateRepository;
import cat.udl.eps.entsoftarch.textannot.repository.SampleRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CreateMetadataTemplateDefs {

    @Autowired
    private StepDefs stepDefs;
    @Autowired
    private SampleRepository sampleRepository;
    @Autowired
    private MetadataTemplateRepository mtR;

    protected ResultActions result;

    @When("^I create a new Metadata Template with name \"([^\"]*)\"$")
    public void iCreateANewMetadataTemplateWithName(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions


        JSONObject metadataTemplate = new JSONObject();
        metadataTemplate.put("name", arg0);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/metadataTemplates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(metadataTemplate.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^The metadata template name is \"([^\"]*)\"$")
    public void theObjectNameIs(String name) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/metadataTemplates/{name}", name)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(name)));
    }

    @Then("^The metadataTemplate with name \"([^\"]*)\" have (\\d+) samples$")
    public void theMetadataTemplateWithNameHaveSamples(String name, int size) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/metadataTemplates/{name}/describes", name)
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.samples", hasSize(size)));
    }

    @When("^There is a single Sample with text \"([^\"]*)\"$")
    public void thereIsASingleSampleWithText(String text) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Sample s = new Sample(text);
        sampleRepository.save(s);
    }


    @When("^I create a new metadata Template \"([^\"]*)\" with the previous sample$")
    public void iCreateANewMetadataTemplateThePreviousSample(String name) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        MetadataTemplate mt =  new MetadataTemplate();
        mt.setName(name);
        mt = mtR.save(mt);
        Sample s = sampleRepository.findByText("Test");
        s.setDescribedBy(mt);
        sampleRepository.save(s);

    }
}
