package cat.udl.eps.entsoftarch.textannot.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataTemplate;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataTemplateRepository;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

public class ListMetadataTemplatesDefs {
    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private MetadataTemplateRepository mtR;

    @And("^There is a metadata template with name \"([^\"]*)\"$")
    public void thereIsASingleMetadataTemplateWithName(String mtName) {
        MetadataTemplate mt;
        mt = new MetadataTemplate();
        mt.setName(mtName);
        mtR.save(mt);
    }

    @And("^The respone contains only a MetadataTemplate with name \"([^\"]*)\"$")
    public void theResponeContainsAMetadataTemplateWithName(String mtName) {
        int i = 0;
        for(MetadataTemplate mt : mtR.findAll()){
            assert (mt.getName() == mtName);
            i ++;
        }
        assert (i == 1);
    }

    @When("^I get all MetadataTemplates$")
    public void iRetrieveAllMetadataTemplate() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/metadataTemplates")
                .with(AuthenticationStepDefs.authenticate()))
                .andDo(MockMvcResultHandlers.print());
    }

    @And("^There are (\\d+) MetadataTemplates$")
    public void thereAreMetadataTemplates(int numOfMetadataTemplates) throws Throwable {
        String mtname;
        for (int i=0; i<numOfMetadataTemplates; i++){
            mtname= "metadataTemplateExample"+i;
            thereIsASingleMetadataTemplateWithName(mtname);
        }
    }

    @And("^The response is a MetadataTemplatesList with (\\d+) items$")
    public void theResponseIsAMetadataTemplatesListWithItems(int numOfMT) {
        int i = 0;
        Set<String> mtNames = new LinkedHashSet<String>();
        for(int j=0; j<numOfMT; j++){
            mtNames.add("metadataTemplateExample"+j);
        }
        for(MetadataTemplate mt : mtR.findAll()){
            assert(mt.getClass() == MetadataTemplate.class);
            assert(mtNames.contains(mt.getName()));
            i ++;
        }
        assert (i == numOfMT);

    }
}
