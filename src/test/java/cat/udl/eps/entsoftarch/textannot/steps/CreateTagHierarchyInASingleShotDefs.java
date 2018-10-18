package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.Tag;
import cat.udl.eps.entsoftarch.textannot.repository.TagRepository;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CreateTagHierarchyInASingleShotDefs {

    private StepDefs stepDefs;
    private TagRepository tagRepository;

    public CreateTagHierarchyInASingleShotDefs(StepDefs stepDefs, TagRepository tagRepository) {
        this.stepDefs = stepDefs;
        this.tagRepository = tagRepository;
    }

    @When("^I send the \"([^\"]*)\" Tag Hierarchy structure$")
    public void theTagHierarchyTagsASampleWithText(String filename) throws Exception {

        Resource tagHierarchyJson = stepDefs.wac.getResource("classpath:"+filename);
        byte[] content = Files.readAllBytes(tagHierarchyJson.getFile().toPath());
        String body = new String(content, StandardCharsets.UTF_8);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/quickTagHierarchyCreate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }


    @Then("^Its tags have the correct parent/child relationship$")
    public void itsTagsHaveTheCorrectParentChildRelationship() throws Throwable {
        List<Tag> son = tagRepository.findByNameContaining("another_son_of_root_1");
        System.out.println("CONTENT!" + son.get(0).getId());
        stepDefs.mockMvc.perform(
                get("/tags/"+ son.get(0).getId() + "/parent")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(son.get(0).getParent().getName())));
    }
}
