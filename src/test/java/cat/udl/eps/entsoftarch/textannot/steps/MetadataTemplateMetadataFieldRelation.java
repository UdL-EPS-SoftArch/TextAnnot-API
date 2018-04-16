package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataField;
import cat.udl.eps.entsoftarch.textannot.domain.MetadataTemplate;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataFieldRepository;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataTemplateRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MetadataTemplateMetadataFieldRelation {
    @Autowired
    private MetadataTemplateRepository mtr;

    @Autowired
    private MetadataFieldRepository mtfr;

    private List<MetadataTemplate> MetadataTemplatesList;

    @Given("^A MetadataTemplate with name \"([^\"]*)\" defines a MetadataField with name \"([^\"]*)\" and type \"([^\"]*)\"$")
    public void aMetadataTemplateWithNameDefinesAMetadataFieldWithNameAndType(String name, String FName, String FType) throws Throwable {
        MetadataTemplate mt = new MetadataTemplate();
        mt.setName(name);
        MetadataField mf = new MetadataField(FName, FType);

        mt = mtr.save(mt);
        mf.setDefinedIn(mt);
        mtfr.save(mf);
    }

    @When("^I find MetadataTemplates by MetadataField name \"([^\"]*)\" and type \"([^\"]*)\"$")
    public void iFindMetadataTemplateByMetadataFieldNameAndType(String FName, String FType) throws Throwable {
        this.MetadataTemplatesList = mtr.findByDefinesNameAndDefinesType(FName, FType);
    }

    @Then("^I get the list with a MetadataTemplate with name \"([^\"]*)\"$")
    public void iGetTheListWithAMetadataTemplateWithName(String name) throws Throwable {

        Assert.assertTrue(MetadataTemplatesList.size() == 1);
        MetadataTemplate metadataTemplate = MetadataTemplatesList.get(0);
        Assert.assertTrue(metadataTemplate.getName().equals(name));
    }


    @Given("^A MetadataTemplate with name \"([^\"]*)\" which defines a MetadataFields with name \"([^\"]*)\"$")
    public void aMetadataTemplateWithNameWhichDefinesAMetadataFieldsWithName(String name, String FName) throws Throwable {
        MetadataTemplate m = new MetadataTemplate();
        m.setName(name);
        MetadataField f = new MetadataField(FName,"NTp"); //no type

        m = mtr.save(m);
        f.setDefinedIn(m);
        mtfr.save(f);

    }

    @When("^I find MetadataTemplate by MetadataField name \"([^\"]*)\"$")
    public void iFindMetadataTemplateByMetadataFieldName(String FName) throws Throwable {
        this.MetadataTemplatesList = mtr.findByDefinesName(FName);
    }

    @Given("^A MetadataTemplate with name \"([^\"]*)\" defines a MetadataField with type \"([^\"]*)\"$")
    public void aMetadataTemplateWithNameDefinesAMedatataFieldWithType(String name, String FType) throws Throwable {
        MetadataTemplate m = new MetadataTemplate();
        m.setName(name);
        MetadataField f = new MetadataField("NName",FType); //no name

        m = mtr.save(m);
        f.setDefinedIn(m);
        mtfr.save(f);
    }

    @When("^I find MetadataTemplate by MetadataField type \"([^\"]*)\"$")
    public void iFindMetadataTemplateByMetadataFieldType(String FType) throws Throwable {
        this.MetadataTemplatesList = mtr.findByDefinesType(FType);
    }
}
