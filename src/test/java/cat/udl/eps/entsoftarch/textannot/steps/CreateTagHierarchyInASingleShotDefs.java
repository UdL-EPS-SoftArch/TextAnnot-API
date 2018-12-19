package cat.udl.eps.entsoftarch.textannot.steps;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

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
        stepDefs.mockMvc.perform(
                get("/tagHierarchies/" + 1 + "/tags")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.roots[*].name",
                    containsInAnyOrder("root1", "root2")))
                .andExpect(jsonPath("$.roots[*].children[*].name",
                    containsInAnyOrder("son_of_root_1", "another_son_of_root_1", "son_of_root2")))
                .andExpect(jsonPath("$.roots[*].children[*].children[*].name",
                    containsInAnyOrder("grandchild_of_root1", "another_grandchild_of_root1")));
    }

    @When("^I send the following CSV to create tag hierarchy \"([^\"]*)\"$")
    public void iSendTheFollowingCSVToCreateTagHierarchy(String hierarchyName, String csvContent) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
            post("/quickTagHierarchyCreate?tagHierarchy={name}", hierarchyName)
                .contentType(MediaType.TEXT_PLAIN)
                .content(csvContent)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
            .andDo(print());
    }

    @When("^I send the CSV file \"([^\"]*)\" to create tag hierarchy \"([^\"]*)\"$")
    public void iSendTheCSVFileToCreateTagHierarchy(String filename, String hierarchyName) throws Throwable {
        Resource tagHierarchyCsv = stepDefs.wac.getResource("classpath:"+filename);
        byte[] content = Files.readAllBytes(tagHierarchyCsv.getFile().toPath());
        String csvContent = new String(content, StandardCharsets.UTF_8);

        stepDefs.result = stepDefs.mockMvc.perform(
            post("/quickTagHierarchyCreate?tagHierarchy={name}", hierarchyName)
                .contentType(MediaType.TEXT_PLAIN)
                .content(csvContent)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
            .andDo(print());
    }
}
