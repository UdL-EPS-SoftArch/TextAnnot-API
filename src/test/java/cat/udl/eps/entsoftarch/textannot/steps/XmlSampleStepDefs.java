package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.repository.XmlSampleRepository;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class XmlSampleStepDefs {

    private String newResourceUri;

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    XmlSampleRepository xmlSampleRepository;

    /*

    @When("^I upload a XmlSample with content \"([^\"]*)\"$")
    public void iUploadAXmlSampleWithContent(String content, String text) throws Throwable {
        org.json.JSONObject XmlSample = new org.json.JSONObject();
        XmlSample.put("content",content);
        XmlSample.put("text",text);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/xmlSamples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(XmlSample.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());


        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    */




    @When("^I upload a XmlSample with text \"([^\"]*)\" and content \"([^\"]*)\"$")
    public void iUploadAXmlSampleWithTextAndContent(String text, String content) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        org.json.JSONObject xmlSamples = new org.json.JSONObject();
        xmlSamples.put("text", text);
        xmlSamples.put("content", content);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/xmlSamples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(xmlSamples.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }






    @And("^It has been created a XmlSample with text \"([^\"]*)\" and content \"([^\"]*)\"$")
    public void itHasBeenCreatedAXmlSampleWithTextAndContent(String text, String content) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.text", is(text)))
                .andExpect(jsonPath("$.content", is(content)));
    }
}