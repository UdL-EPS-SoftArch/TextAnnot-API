package cat.udl.eps.entsoftarch.textannot.steps;

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
    private StepDefs stepDefs;

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
}
