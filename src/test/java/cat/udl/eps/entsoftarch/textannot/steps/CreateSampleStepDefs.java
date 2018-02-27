package cat.udl.eps.entsoftarch.textannot.steps;

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

public class CreateSampleStepDefs {
    private static final Logger logger = LoggerFactory.getLogger(RegisterLinguistStepDef.class);

    @Autowired
    private StepDefs stepDefs;

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

    @And("^It has been created a sample with text\"([^\"]*)\"$")
    public void itHasBeenCreatedASample(String text) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri, text)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @And("^It has not been created a sample with text \"([^\"]*)\"$")
    public void itHasNotBeenCreatedASample(String text) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri, text)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
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
}
