package cat.udl.eps.entsoftarch.textannot.steps;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import cat.udl.eps.entsoftarch.textannot.repository.SampleRepository;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

public class ListSamplesStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private SampleRepository sampleRepos;

    @When("^I list samples$")
    public void IListSamples() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/samples")
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate())
        )
        .andDo(print());
    }

    @Given("^I create a sample with text \"([^\"]*)\"$")
    public void ICreateASample(String text) throws Throwable {
        Sample sample = new Sample(text);
        sampleRepos.save(sample);
    }

    @And("The sample with text \"([^\"]*)\" is in the response$")
    public void theSampleIsInResponse(String text) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$._embedded.samples.*.text", hasItem(text)));
    }

    @And("^The list is empty$")
    public void TheListIsEmpty() throws Throwable {
        stepDefs.result.andExpect(jsonPath("$._embedded.samples", hasSize(0)));
    }


}