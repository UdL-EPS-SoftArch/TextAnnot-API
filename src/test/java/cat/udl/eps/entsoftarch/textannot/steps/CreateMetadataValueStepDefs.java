package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataField;
import cat.udl.eps.entsoftarch.textannot.domain.MetadataValue;
import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataFieldRepository;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataValueRepository;
import cat.udl.eps.entsoftarch.textannot.repository.SampleRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateMetadataValueStepDefs {

    private static final Logger logger = LoggerFactory.getLogger(CreateMetadataValueStepDefs.class);

    @Autowired
    private StepDefs stepDefs;
    private Sample sample;
    private MetadataValue metaValue;
    private MetadataField metaField;

    @Autowired
    private SampleRepository sampleRepository;
    @Autowired
    private MetadataValueRepository metadataValueRepository;

    @Autowired
    private MetadataFieldRepository metadataFieldRepository;

    @When("^I register a new metadataValue with value \"([^\"]*)\"$")
    public void iRegisterANewMetadataValueWithValue(String testValue) throws Throwable {
        JSONObject metadatavalue = new JSONObject();
        metadatavalue.put("value", testValue);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/metadataValues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(metadatavalue.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }







    @And("^there is a created Sample with text \"([^\"]*)\"$")
    public void thereIsACreatedSampleWithText(String text) throws Throwable {
        JSONObject samplev = new JSONObject();
        samplev.put("text", text);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/samples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(samplev.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }






    @And("^there is a created metadataField with text \"([^\"]*)\" and type \"([^\"]*)\"$")
    public void thereIsACreatedMetadataFieldWithTextAndType(String name, String type) throws Throwable {
        JSONObject metadataField = new JSONObject();
        metadataField.put("name", name);
        metadataField.put("type", type);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/metadataFields")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(metadataField.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }




    @When("^I register a new metadataValue with value \"([^\"]*)\" for metadataField with text \"([^\"]*)\"$")
    public void iRegisterANewMetadataValueWithValueForMetadataFieldWithText(String value, String name) throws Throwable {
        JSONObject metadataValue = new JSONObject();
        metaField=metadataFieldRepository.findByName(name);
        metadataValue.put("value", value);
        metadataValue.put("values", "/metadataFields/" + metaField.getId());
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/metadataValues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(metadataValue.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^It has been created a new metadataValue with value \"([^\"]*)\" for metadataField with text \"([^\"]*)\"$")
    public void itHasBeenCreatedANewMetadataValueWithValueForMetadataFieldWithText(String value, String name) throws Throwable {
        metaValue = metadataValueRepository.findByValue(value);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/metadataValues/{id}", metaValue.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.value", is(value)));

        stepDefs.mockMvc.perform(
                get("/metadataValues/" + metaValue.getId() + "/values")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(metaField.getName())))
                .andExpect(status().is(200));
    }

    @When("^I register a new metadataValue with value \"([^\"]*)\" for Sample with text \"([^\"]*)\"$")
    public void iRegisterANewMetadataValueWithValueForSampleWithText(String value, String text) throws Throwable {
        JSONObject metadatavalue = new JSONObject();
        sample=sampleRepository.findByText(text);
        metadatavalue.put("value", value);
        metadatavalue.put("forA", "/samples/"+sample.getId()+"");
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/metadataValues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(metadatavalue.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^It has been created a new metadataValue with value \"([^\"]*)\"  for Sample with text \"([^\"]*)\"$")
    public void itHasBeenCreatedANewMetadataValueWithValueForSampleWithText(String value, String text) throws Throwable {
        metaValue=metadataValueRepository.findByValue(value);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/metadataValues/{id}", metaValue.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.value", is(value)));

        stepDefs.mockMvc.perform(
                get("/metadataValues/"+metaValue.getId()+"/forA")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.text", is(sample.getText())))
                .andExpect(status().is(200));
    }

    @And("^It has been created a new metadataValue with value \"([^\"]*)\"$")
    public void itHasBeenCreatedANewMetadataValueWithValue(String testValue) throws Throwable {
        metaValue=metadataValueRepository.findByValue(testValue);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/metadataValues/{id}", metaValue.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.value", is(testValue)))
                .andExpect(jsonPath("$.id", is(metaValue.getId())));
    }

    @And("^It has not been created a metadataValue with value \"([^\"]*)\"$")
    public void itHasNotBeenCreatedAMetadataValueWithValue(String testValue) throws Throwable {
        int id=0;
        metaValue=metadataValueRepository.findByValue(testValue);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/metadataValues/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
