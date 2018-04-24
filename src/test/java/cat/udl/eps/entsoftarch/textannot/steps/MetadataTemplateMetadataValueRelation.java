package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataField;
import cat.udl.eps.entsoftarch.textannot.domain.MetadataTemplate;
import cat.udl.eps.entsoftarch.textannot.domain.MetadataValue;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataFieldRepository;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataTemplateRepository;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataValueRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class MetadataTemplateMetadataValueRelation {
    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private MetadataTemplateRepository metadataTemplateRepository;

    @Autowired
    private MetadataFieldRepository metadataFieldRepository;

    @Autowired
    private MetadataValueRepository metadataValueRepository;

    @Given("^A MetadataTemplate with name \"([^\"]*)\" defines a MetadataField with name \"([^\"]*)\" and type \"([^\"]*)\" that values a MetadataValue with value \"([^\"]*)\"$")
    public void aMetadataTemplateWithNameDefinesAMetadataFieldWithNameAndTypeThatValuesAMetadataValueWithValue(String name, String FName, String FType, String VValue) throws Throwable {
        MetadataTemplate mt = new MetadataTemplate();
        mt.setName(name);
        MetadataField mf = new MetadataField(FName, FType);
        MetadataValue mv = new MetadataValue(VValue);

        mt = metadataTemplateRepository.save(mt);
        mf.setDefinedIn(mt);
        metadataFieldRepository.save(mf);
        mv.setValued(mf);
        metadataValueRepository.save(mv);
    }

    @When("^I find MetadataTemplates by MetadataValue with value \"([^\"]*)\"$")
    public void iFindMetadataTemplatesByMetadataValueWithValue(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I get a list with a MetadataTemplate with name \"([^\"]*)\"$")
    public void iGetAListWithAMetadataTemplateWithName(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
