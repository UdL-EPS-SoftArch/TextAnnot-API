package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataField;
import cat.udl.eps.entsoftarch.textannot.domain.MetadataTemplate;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataFieldRepository;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataTemplateRepository;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.*;

public class MetadataTemplateMetadataFieldRelation {
    @Autowired
    private MetadataTemplateRepository mtr;

    @Autowired
    private MetadataFieldRepository mtfr;

    @Autowired
    private StepDefs stepDefs;

    private List<MetadataTemplate> MetadataTemplatesList;

    @Given("^A MetadataTemplate with name \"([^\"]*)\" defines a MetadataField with name \"([^\"]*)\" and type \"([^\"]*)\"$")
    public void aMetadataTemplateWithNameDefinesAMetadataFieldWithNameAndType(String name, String FName, String FType) {
        MetadataTemplate mt = new MetadataTemplate();
        mt.setName(name);
        MetadataField mf = new MetadataField(FName, FType);

        mt = mtr.save(mt);
        mf.setDefinedAt(mt);
        mtfr.save(mf);
    }

    @When("^I find MetadataTemplates by MetadataField name \"([^\"]*)\" and type \"([^\"]*)\"$")
    public void iFindMetadataTemplateByMetadataFieldNameAndType(String FName, String FType) throws Throwable {

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/metadataFields/search/findAllMetadataTemplatesByNameAndType?name={FName}&type={FType}", FName, FType)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Then("^I get the list with a MetadataTemplate with name \"([^\"]*)\"$")
    public void iGetTheListWithAMetadataTemplateWithName(String name) throws Throwable {

        stepDefs.result.andExpect(jsonPath("$._embedded.metadataTemplates.*.name", hasItem(name)));

    }


    @Given("^A MetadataTemplate with name \"([^\"]*)\" which defines a MetadataFields with name \"([^\"]*)\"$")
    public void aMetadataTemplateWithNameWhichDefinesAMetadataFieldsWithName(String name, String FName) {
        MetadataTemplate m = new MetadataTemplate();
        m.setName(name);
        MetadataField f = new MetadataField(FName,"NTp"); //no type

        m = mtr.save(m);
        f.setDefinedAt(m);
        mtfr.save(f);

    }

    @When("^I find MetadataTemplate by MetadataField name \"([^\"]*)\"$")
    public void iFindMetadataTemplateByMetadataFieldName(String FName) throws Throwable {

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/metadataFields/search/findAllMetadataTemplatesByName?name={FName}", FName)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Given("^A MetadataTemplate with name \"([^\"]*)\" defines a MetadataField with type \"([^\"]*)\"$")
    public void aMetadataTemplateWithNameDefinesAMedatataFieldWithType(String name, String FType) {
        MetadataTemplate m = new MetadataTemplate();
        m.setName(name);
        MetadataField f = new MetadataField("NName",FType); //no name

        m = mtr.save(m);
        f.setDefinedAt(m);
        mtfr.save(f);
    }

    @When("^I find MetadataTemplate by MetadataField type \"([^\"]*)\"$")
    public void iFindMetadataTemplateByMetadataFieldType(String FType) throws Throwable {

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/metadataFields/search/findAllMetadataTemplatesByType?type={FType}",FType)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
                .andDo(MockMvcResultHandlers.print());
    }
}
