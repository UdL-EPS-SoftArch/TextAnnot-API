package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.TagHierarchy;
import cat.udl.eps.entsoftarch.textannot.repository.TagHierarchyRepository;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateTagHierarchyStepDefs {

    @Autowired
    private StepDefs stepDefs;
    private TagHierarchy tagHierarchy;

    @Autowired
    private TagHierarchyRepository tagHierarchyRepository;

    @And("^It has been created a new tagHierarchy with name \"([^\"]*)\"$")
    public void itHasBeenCreatedANewTagHierarchyWithName(String nameTagH) throws Throwable {
        tagHierarchy = tagHierarchyRepository.findByName(nameTagH);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tagHierarchies/{id}", tagHierarchy.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(nameTagH)))
                .andExpect(jsonPath("$.id", is(tagHierarchy.getId())));
    }

    @When("^I register a new tagHierarchy with name \"([^\"]*)\"$")
    public void iRegisterANewTagHierarchyWithName(String nameTagH) throws Throwable {

        JSONObject tagH = new JSONObject();
        tagH.put("name", nameTagH);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/tagHierarchies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagH.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^It has not been created a tagHierarchy with name \"([^\"]*)\"$")
    public void itHasNotBeenCreatedATagHierarchyWithName(String nameTagH) throws Throwable {
        int id=0;
        tagHierarchy=tagHierarchyRepository.findByName(nameTagH);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tagHierarchies/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }
}
