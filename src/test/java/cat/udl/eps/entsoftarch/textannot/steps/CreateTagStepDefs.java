package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.Tag;
import cat.udl.eps.entsoftarch.textannot.repository.TagRepository;
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

public class CreateTagStepDefs {

    private static final Logger logger = LoggerFactory.getLogger(cat.udl.eps.entsoftarch.textannot.steps.CreateTagStepDefs.class);

    @Autowired
    private StepDefs stepDefs;
    private Tag tag;
    private Tag parent;


    @Autowired
    private TagRepository tagRepository;

    @When("^I register a new tag with name \"([^\"]*)\"$")
    public void iRegisterANewTagWithName(String testName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        JSONObject tag = new JSONObject();
        tag.put("name", testName);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tag.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^It has been created a new tag with name \"([^\"]*)\"$")
    public void itHasBeenCreatedANewTagWithName(String testName) throws Throwable {
        tag=tagRepository.findByName(testName);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tags/{id}", tag.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(testName)))
                .andExpect(jsonPath("$.id", is(tag.getId())));
    }

    @And("^It has not been created a tag with name \"([^\"]*)\"$")
    public void itHasNotBeenCreatedATagWithValue(String testName) throws Throwable {
        int id=0;
        tag=tagRepository.findByName(testName);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tags/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @And("^there is a created tag with name \"([^\"]*)\"$")
    public void thereIsACreatedTagWithName(String parentName) throws Throwable {
        JSONObject parent = new JSONObject();
        parent.put("name", parentName);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(parent.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }


    @When("^I register a new tag with name \"([^\"]*)\" for tag with name \"([^\"]*)\"$")
    public void iRegisterANewTagWithNameForTagWithName(String childName, String parentName) throws Throwable {
        JSONObject child = new JSONObject();
        parent=tagRepository.findByName(parentName);
        child.put("name", childName);
        child.put("parent", "/tags/"+parent.getId()+"");
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(child.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^It has been created a new tag with name \"([^\"]*)\" for tag with text \"([^\"]*)\"$")
    public void itHasBeenCreatedANewTagWithNameForTagWithText(String chidlName, String parentName) throws Throwable {
        tag=tagRepository.findByName(chidlName);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tags/{id}", tag.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(chidlName)));

        stepDefs.mockMvc.perform(
                get("/tags/"+tag.getId()+"/parent")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(parentName)))
                .andExpect(status().is(200));
    }
}
