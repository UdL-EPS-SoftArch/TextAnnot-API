package cat.udl.eps.entsoftarch.textannot.steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CreateTagHierarchyInASingleShotDefs {

    private StepDefs stepDefs;

    public CreateTagHierarchyInASingleShotDefs(StepDefs stepDefs) {
        this.stepDefs = stepDefs;
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
        stepDefs.result.andExpect(jsonPath("$.name", is("tagHierarchy")));
        stepDefs.mockMvc.perform(
                get("tags/search/findByName?name=another_son_of_root_1")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.parent.name", is("root_1")))
                .andExpect(jsonPath("$.tagHierarchy.name", is("tagHierarchy")));
    }
}
