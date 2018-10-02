package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.Tag;
import cat.udl.eps.entsoftarch.textannot.domain.TagHierarchy;
import cat.udl.eps.entsoftarch.textannot.repository.TagHierarchyRepository;
import cat.udl.eps.entsoftarch.textannot.repository.TagRepository;
import cucumber.api.PendingException;
import cucumber.api.java.bs.A;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.print.attribute.standard.Media;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TagStepDefs {

    @Autowired
    private StepDefs stepDefs;
    @Autowired
    private TagHierarchyRepository tagHierarchyRepository;
    @Autowired
    private TagRepository tagRepository;

    private Tag tag;
    private TagHierarchy tagHierarchy;

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

    @And("^It hasn't been created a tag with name \"([^\"]*)\" and Id (\\d+)$")
    public void itHasnTBeenCreatedATagWithNameAndId(String arg0, Integer id) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tags/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(status().isNotFound());
    }

    @And("^It has not been created a tag with name \"([^\"]*)\" and Id (\\d+)$")
    public void itHasNotBeenCreatedATagWithNameAndId(String name, Integer id) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tags/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(status().isUnauthorized());
    }

    @And("^Exists a TagHierarchy with name \"([^\"]*)\"$")
    public void existsATagHierarchyWithName(String name) throws Throwable {
        tagHierarchy = new TagHierarchy();
        tagHierarchy.setName(name);
        tagHierarchyRepository.save(tagHierarchy);
    }

    @And("^Exists a Tag with name \"([^\"]*)\"$")
    public void existsATagWithName(String name) throws Throwable {
        tag = new Tag(name);
        tagRepository.save(tag);
    }

    @When("^I create a new tag with name \"([^\"]*)\" defined in the tag hierarchy \"([^\"]*)\"$")
    public void iCreateANewTagWithNameDefinedInTheTagHierarchy(String name, String tagHierName) throws Throwable {
        List<Tag> tags = tagRepository.findByNameContaining(name);
        Optional<TagHierarchy> tagHier= tagHierarchyRepository.findByName(tagHierName);
        if(!tags.isEmpty())
            tag.setName(tags.get(0).getName());
        tagHier.ifPresent(tagHierarchy1 -> tag.setDefinedIn(tagHierarchy1));
        stepDefs.result = stepDefs.mockMvc.perform(
                put("/tags/" + tag.getId() + "/definedIn")
                    .contentType("text/uri-list")
                    .content(tag.getUri())
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @Then("^The tag hierarchy \"([^\"]*)\" defines a tag with the text \"([^\"]*)\"$")
    public void theTagHierarchyDefinesATagWithTheText(String tagHierName, String name) throws Throwable {
        stepDefs.mockMvc.perform(
                get("/tagHierarchies/" + tagHierarchy.getId() + "/defines")
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.tags[0].id", is(tag.getId()))
        );
    }
}
