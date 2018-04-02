package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataTemplate;
import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataTemplateRepository;
import cat.udl.eps.entsoftarch.textannot.repository.SampleRepository;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class FindByMetadataTemplateStepDefs {

    private static Map<String, MetadataTemplate> map;
    private static List<Sample> sampleList;
    private static List<Sample> result;

    @Autowired
    private StepDefs stepDefs;


    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private MetadataTemplateRepository metadataTemplateRepository;

    @And("^There are some Metadata Templates with text \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\"$")
    public void saveTheMetadataTemplate(String md1, String md2, String md3) throws Throwable{
        FindByMetadataTemplateStepDefs.map = new HashMap<String, MetadataTemplate>();
        MetadataTemplate metadata1 = new MetadataTemplate(md1);
        MetadataTemplate metadata2 = new MetadataTemplate(md2);
        MetadataTemplate metadata3 = new MetadataTemplate(md3);

        FindByMetadataTemplateStepDefs.map.put(md1, metadata1);
        FindByMetadataTemplateStepDefs.map.put(md2, metadata2);
        FindByMetadataTemplateStepDefs.map.put(md1, metadata3);

        metadataTemplateRepository.save(metadata1);
        metadataTemplateRepository.save(metadata2);
        metadataTemplateRepository.save(metadata3);

    }

    @When("^I search a sample which is defined by the Metadata Template \"([^\"]*)\"$")
    public void searchMetadataTemplate(String word) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/samples/search/findByTextContains?word={word}", word)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }


    @And("There is a sample with text \"([^\"]*)\" defined by \"([^\"]*)\"$")
    public void addSamples(String sam, String metadata) throws Throwable{
        Sample sample = new Sample(sam);
        sample.setDescribedBy(this.map.get(metadata));
        sampleRepository.save(sample);
    }

    @And("The samples are empty$")
    public void theSamplesAreEmpty() throws Throwable{
        stepDefs.result.andExpect(jsonPath("$._embedded.samples", hasSize(0)));
    }

}
