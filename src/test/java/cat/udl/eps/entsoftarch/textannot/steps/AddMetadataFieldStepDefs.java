package cat.udl.eps.entsoftarch.textannot.steps;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataField;
import cat.udl.eps.entsoftarch.textannot.domain.MetadataValue;
import cat.udl.eps.entsoftarch.textannot.domain.MetadataTemplate;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataFieldRepository;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataTemplateRepository;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataValueRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

public class AddMetadataFieldStepDefs {


    private static final Logger logger = LoggerFactory.getLogger(RegisterLinguistStepDef.class);

    @Autowired
    private StepDefs stepDefs;
    private MetadataValue metaValue;
    private MetadataField metaField;
    private MetadataTemplate metaTemplate;


    @Autowired
    private MetadataTemplateRepository metadataTemplateRepository;

    @Autowired
    private MetadataFieldRepository metadataFieldRepository;

    private String newResourceUri;


    @When("^I create a new metadatafield with text \"([^\"]*)\" and type \"([^\"]*)\"$")
    public void iCreateANewMetadatafieldWithTextAndType(String name, String type) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
        JSONObject AddMetaDataField = new JSONObject();
        AddMetaDataField.put("name",name);
        AddMetaDataField.put("type",type);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/metadataFields")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(AddMetaDataField.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }


    @And("^It has been created a new metadatafield with text \"([^\"]*)\" and type \"([^\"]*)\" and Id (\\d+)$")
    public void itHasBeenCreatedANewMetadatafieldWithTextAndTypeAndId(String name, String type, Integer id) throws Throwable {
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




    @And("^It has not been created a metadatafield with text \"([^\"]*)\" and type \"([^\"]*)\" and Id (\\d+)$")
    public void itHasNotBeenCreatedAMetadatafieldWithTextAndTypeAndId(String name, String type, Integer id) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/metadataFields/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }





    @And("^there is a created metadataTemplate with name \"([^\"]*)\"$")
    public void thereIsACreatedMetadataTemplateWithName(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        JSONObject metadataTemplate = new JSONObject();
        metadataTemplate.put("name", arg0);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/metadataTemplates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(metadataTemplate.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("^I register a new metadataField with text \"([^\"]*)\" and type \"([^\"]*)\" for metadataTemplate with value \"([^\"]*)\"$")
    public void iRegisterANewMetadataFieldWithTextAndTypeForMetadataTemplateWithValue(String name, String type, String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        JSONObject metadataField = new JSONObject();
        metaTemplate=metadataTemplateRepository.findByName(arg0);
        metadataField.put("name", name);
        metadataField.put("type", type);
        metadataField.put("definedIn", "/metadataTemplates/"+metaTemplate.getId()+"");
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/metadataFields")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(metadataField.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @And("^It has been created a new metadataField with text \"([^\"]*)\"  for metadataTemplate with value \"([^\"]*)\"$")
    public void itHasBeenCreatedANewMetadataFieldWithTextForMetadataTemplateWithValue(String name, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        metaField=metadataFieldRepository.findByName(name);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/metadataFields/{id}", metaField.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(name)));

        stepDefs.mockMvc.perform(
                get("/metadataFields/"+metaField.getId()+"/definedIn")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(metaTemplate.getName())))
                .andExpect(status().is(200));
    }
}
