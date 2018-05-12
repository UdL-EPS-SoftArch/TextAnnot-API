package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataField;
import cat.udl.eps.entsoftarch.textannot.domain.MetadataValue;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataValueRepository;
import cat.udl.eps.entsoftarch.textannot.repository.XmlSampleRepository;
import cat.udl.eps.entsoftarch.textannot.service.XMLService;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class XmlSampleStepDefs {

    private String newResourceUri;

    @Autowired
    private StepDefs stepDefs;
    private MetadataValue metaValue;
    private MetadataField metaField;

    @Autowired
    XmlSampleRepository xmlSampleRepository;

    @Autowired
    private MetadataValueRepository metadataValueRepository;


    @When("^I upload a XmlSample with text \"([^\"]*)\" and content$")
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

        XMLService xml = new XMLService();
        xml.XMLParser(content);
    }


    @And("^It has been created a XmlSample with text \"([^\"]*)\" and content$")
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

    @And("^It has been created a new metadatafield with name \"([^\"]*)\" and type \"([^\"]*)\" and Id (\\d+)$")
    public void itHasBeenCreatedANewMetadatafieldWithNameAndTypeAndId(String name, String type, int id) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/metadataFields/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.type", is(type)))
                .andExpect(jsonPath("$.id", is(id)));
    }

    @And("^It has been created a new metadataValue with value \"([^\"]*)\" for metadataField with name \"([^\"]*)\"$")
    public void itHasBeenCreatedANewMetadatavauleWithValueForMetadatafieldWithName(String value, String name) throws Throwable {
        metaValue=metadataValueRepository.findByValue(value);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/metadataValues/{id}", metaValue.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.value", is(value)));

        stepDefs.mockMvc.perform(
                get("/metadataValues/"+metaValue.getId()+"/valued")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(metaField.getName())))
                .andExpect(status().is(200));
    }

}