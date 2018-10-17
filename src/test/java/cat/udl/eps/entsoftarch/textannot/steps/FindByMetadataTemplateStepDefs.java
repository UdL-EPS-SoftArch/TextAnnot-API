package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataTemplate;
import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataTemplateRepository;
import cat.udl.eps.entsoftarch.textannot.repository.SampleRepository;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.*;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class FindByMetadataTemplateStepDefs {


    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private MetadataTemplateRepository metadataTemplateRepository;

    @And("^There are some Metadata Templates with text \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\"$")
    public void saveTheMetadataTemplate(String md1, String md2, String md3) {
        MetadataTemplate metadata1 = new MetadataTemplate();
        metadata1.setName(md1);
        MetadataTemplate metadata2 = new MetadataTemplate();
        metadata2.setName(md2);
        MetadataTemplate metadata3 = new MetadataTemplate();
        metadata3.setName(md3);
        metadataTemplateRepository.save(metadata1);
        metadataTemplateRepository.save(metadata2);
        metadataTemplateRepository.save(metadata3);

    }

    @When("^I search a sample which is defined by the Metadata Template \"([^\"]*)\"$")
    public void searchMetadataTemplate(String word) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/samples/search/findByDescribedByName?text={text}", word)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("^I search a sample with an inexistent Metadata Template named \"([^\"]*)\"$")
    public void inexistentMetadataTemplate(String word) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/samples/search/findByDescribedByName?text={text}", word)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("There is a sample with text \"([^\"]*)\" defined by \"([^\"]*)\"$")
    public void addSamples(String sam, String metadata) {
        Sample sample = new Sample(sam);
        Optional<MetadataTemplate> metadataTemplateOptional = metadataTemplateRepository.findByName(metadata);
        Assert.assertTrue("metadataTemplate is present", metadataTemplateOptional.isPresent());
        sample.setDescribedBy(metadataTemplateOptional.get());
        sampleRepository.save(sample);
    }

    @And("The sample is \"([^\"]*)\"$")
    public void theSampleIs(String s) throws Throwable{
        stepDefs.result.andExpect(jsonPath("$._embedded.samples.*.text", hasItem(s)));
    }

}

