package cat.udl.eps.entsoftarch.textannot.steps;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

public class RegisterLinguistStepDef {

  private static final Logger logger = LoggerFactory.getLogger(RegisterLinguistStepDef.class);

  @Autowired
  private StepDefs stepDefs;

  @When("^I register a new linguist with username \"([^\"]*)\", email \"([^\"]*)\" and password \"([^\"]*)\"$")
  public void iRegisterANewLinguist(String username, String email, String password) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    JSONObject linguist = new JSONObject();
    linguist.put("username", username);
    linguist.put("email", email);
    linguist.put("password", password);
    stepDefs.result = stepDefs.mockMvc.perform(
        post("/linguists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(linguist.toString())
            .accept(MediaType.APPLICATION_JSON)
            .with(AuthenticationStepDefs.authenticate()))
        .andDo(print());
  }

  @And("^It has been created a linguist with username \"([^\"]*)\" and email \"([^\"]*)\", the password is not returned$")
  public void itHasBeenCreatedALinguist(String username, String email) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    stepDefs.result = stepDefs.mockMvc.perform(
        get("/linguists/{username}", username)
            .accept(MediaType.APPLICATION_JSON)
            .with(AuthenticationStepDefs.authenticate()))
        .andDo(print())
        .andExpect(jsonPath("$.email", is(email)))
        .andExpect(jsonPath("$.password").doesNotExist());
  }

  @And("^It has not been created a linguist with username \"([^\"]*)\"$")
  public void itHasNotBeenCreatedAPlayerWithUsername(String username) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    stepDefs.result = stepDefs.mockMvc.perform(
        get("/linguists/{username}", username)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}
