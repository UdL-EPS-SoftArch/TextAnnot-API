package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import cat.udl.eps.entsoftarch.textannot.domain.Tag;
import cat.udl.eps.entsoftarch.textannot.repository.TagRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ListTagsStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private TagRepository tagRepository;

    @When("^I list tags$")
    public void iListTags() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tags")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        )
                .andDo(print());
    }

    @Given("^I create a tag with name \"([^\"]*)\"$")
    public void ICreateATag(String name) throws Throwable {
        Tag tag = new Tag(name);
        tagRepository.save(tag);
    }

    @And("^The tag with name \"([^\"]*)\" is in the response$")
    public void theTagWithNameIsInTheResponse(String name) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$._embedded.tags.*.name", hasItem(name)));
    }

    @And("^The tags' list is empty$")
    public void theTagsListIsEmpty() throws Throwable {
        stepDefs.result.andExpect(jsonPath("$._embedded.tags", hasSize(0)));
    }
}
