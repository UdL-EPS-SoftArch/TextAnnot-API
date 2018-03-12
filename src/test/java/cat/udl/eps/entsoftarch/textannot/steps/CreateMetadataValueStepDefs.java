package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataField;
import cat.udl.eps.entsoftarch.textannot.domain.MetadataValue;
import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateMetadataValueStepDefs {

    private static final Logger logger = LoggerFactory.getLogger(CreateMetadataValueStepDefs.class);

    @Autowired
    StepDefs stepDefs;
    Sample sample;
    MetadataValue metaValue = new MetadataValue();
    MetadataField metaField;

    @When("^I register a new metadataValue with value \"([^\"]*)\"$")
    public void iRegisterANewMetadataValueWithValue(String testValue) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        JSONObject metadatavalue = new JSONObject();
        metadatavalue.put("value", testValue);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/metadataValues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(metadatavalue.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^It has been created a new metadataValue with value \"([^\"]*)\"$ and Id (\\d+)$")
    public void itHasBeenCreatedANewMetadataValue(String testValue, int testId) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/metadataValues/{id}", testId)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.value", is(testValue)))
                .andExpect(jsonPath("$.id", is(testId)));
    }

    @And("^It has not been created a metadataValue with value \"([^\"]*)\" and Id (\\d+)$")
    public void itHasNotBeenCreatedAMetadataValueWithValue(String testValue, int testId) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/metadataValues/{id}", testId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @And("^It has been created a new metadataValue with value \"([^\"]*)\" and Id (\\d+)$")
    public void itHasBeenCreatedANewMetadataValueWithValueAndId(String testValue, int testId) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/metadataValues/{id}", testId)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.value", is(testValue)))
                .andExpect(jsonPath("$.id", is(testId)));
    }

    @And("^there is a created Sample with text \"([^\"]*)\"$")
    public void thereIsACreatedSampleWithText(String text) throws Throwable {
            sample = new Sample(text);
    }


    @When("^I register a new metadataValue with value \"([^\"]*)\" for Sample \"([^\"]*)\"$")
    public void iRegisterANewMetadataValueWithValueForSample(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        JSONObject metadatavalue = new JSONObject();
        metadatavalue.put("value", arg0);
        metadatavalue.put("has", sample);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/metadataValues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(metadatavalue.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^It has been created a new metadataValue with value \"([^\"]*)\" and Id (\\d+) for Sample with text \"([^\"]*)\"$")
    public void itHasBeenCreatedANewMetadataValueWithValueAndIdForSampleWithText(String arg0, int arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^It has been created a new metadataValue with value \"([^\"]*)\" and Id (\\d+) for metadataField with text \"([^\"]*)\" and type \"([^\"]*)\"$")
    public void itHasBeenCreatedANewMetadataValueWithValueAndIdForMetadataFieldWithTextAndType(String arg0, int arg1, String arg2, String arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I register a new metadataValue with value \"([^\"]*)\" for metadataField \"([^\"]*)\"$")
    public void iRegisterANewMetadataValueWithValueForMetadataField(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^there is a created metadataField with text \"([^\"]*)\" and type \"([^\"]*)\"$")
    public void thereIsACreatedMetadataFieldWithTextAndType(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
