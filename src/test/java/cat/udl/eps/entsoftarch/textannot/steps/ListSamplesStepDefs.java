package cat.udl.eps.entsoftarch.textannot.steps;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

public class ListSamplesStepDefs {

  @Autowired
  private StepDefs stepDefs;

  @Autowired
  private SampleRepository sampleRepos;

  @When("^I list samples$")
  public IListSamples() throws Throwable {
    stepDefs.result = stepDefs.mockMvc.perform(
        get("/samples", samples).accept(MediaType.APPLICATION_JSON))
    .andDo(print());
  }

  @Given("^I create a sample with text \"([^\"]*)\"$")
  public ICreateASample(String text) throws Throwable {
    Sample sample = new Sample(text);
    sampleRepos.save(sample);
  }




}