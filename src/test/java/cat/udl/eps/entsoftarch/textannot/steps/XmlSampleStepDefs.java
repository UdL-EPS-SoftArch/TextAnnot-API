package cat.udl.eps.entsoftarch.textannot.steps;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import cat.udl.eps.entsoftarch.textannot.repository.XmlSampleRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

public class XmlSampleStepDefs {

    private String newResourceUri;

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private XmlSampleRepository xmlSampleRepos;

    @When("^I upload a XmlSample with content \"([^\"]*)\"$")
    public void iUploadAXmlSampleWithContent(String content) throws Throwable {
        org.json.JSONObject sample = new org.json.JSONObject();
        sample.put("text", content);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/xmlSamples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sample.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @And("^It has been created a XmlSample with content \"([^\"]*)\"$")
    public void itHasBeenCreatedAXmlSampleWithContent(String content) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.content", is(content)));
    }
}