package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.repository.TagRepository;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TagStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private TagRepository tagRepository;

    @When("^I create a new tag with name \"([^\"]*)\"$")
    public void iCreateANewTagWithName(String name) throws Throwable {
        JSONObject AddTag = new JSONObject();
        AddTag.put("name", name);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(AddTag.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^It has been created a new tag with name \"([^\"]*)\" and Id (\\d+)$")
    public void itHasBeenCreatedANewTagWithNameAndId(String name, Integer id) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tags/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.id", is(id)));
    }

    @And("^It has not been created a tag with name \"([^\"]*)\" and Id (\\d+)$")
    public void itHasNotBeenCreatedATagWithNameAndId(String name, Integer id) throws Throwable {
        Assert.assertEquals(0,tagRepository.count());
        Assert.assertEquals(0,tagRepository.findByNameContaining(name).size());
    }
}
