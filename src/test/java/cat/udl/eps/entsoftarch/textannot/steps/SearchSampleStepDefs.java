package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import cat.udl.eps.entsoftarch.textannot.repository.SampleRepository;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class SearchSampleStepDefs {

    private static String word;
    private static List<Sample> sampleList;
    private static List<Sample> result;

    @Autowired
    private StepDefs stepDefs;


    @Autowired
    private SampleRepository sampleRepository;

    @And("^There are some samples with text \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\"$")
    public void listTheSamples(String sample1, String sample2, String sample3) throws Throwable{
        List<Sample> sampleList = new ArrayList<>();
        sampleList.add(new Sample(sample1));
        sampleList.add(new Sample(sample2));
        sampleList.add(new Sample(sample3));
        sampleRepository.saveAll(sampleList);

    }

    @When("^I search a sample with the word \"([^\"]*)\"$")
    public void searchASample(String word) throws Throwable {
        SearchSampleStepDefs.word = word;
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/samples/search/findByTextContains?word={word}", word)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }


    @And("The samples are \"([^\"]*)\" and \"([^\"]*)\"$")
    public void theSamplesAre(String sample1, String sample2) throws Throwable{
        stepDefs.result.andExpect(jsonPath("$._embedded.samples.*.text", hasItem(sample1)));
        stepDefs.result.andExpect(jsonPath("$._embedded.samples.*.text", hasItem(sample2)));
        stepDefs.result.andExpect(jsonPath("$._embedded.samples", hasSize(2)));
    }

    @And("The samples are empty$")
    public void theSamplesAreEmpty() throws Throwable{
       stepDefs.result.andExpect(jsonPath("$._embedded.samples", hasSize(0)));
    }
}
