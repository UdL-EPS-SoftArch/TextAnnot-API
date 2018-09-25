package cat.udl.eps.entsoftarch.textannot.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CreateTagHierarchyDefs {

    private final StepDefs stepDefs;

    public CreateTagHierarchyDefs(StepDefs stepDefs) {
        this.stepDefs = stepDefs;
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

    @When("^I create a new Tag Hierarchy \"([^\"]*)\" with the previous sample$")
    public void iCreateANewTagHierarchyWithThePreviousSample(String name) {
        assert (true);
    }

    @Then("^The Tag Hierarchy with name \"([^\"]*)\" have (\\d+) samples$")
    public void theTagHierarchyWithNameHaveSamples(String name, int numSamples) {
        assert(true);
    }
}
