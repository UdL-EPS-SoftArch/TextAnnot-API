package cat.udl.eps.entsoftarch.textannot.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import cat.udl.eps.entsoftarch.textannot.repository.XMLSampleRepository;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

public class XMLSampleStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private XMLSampleRepository xmlSampleRepos;

    @When("^I list XML samples$")
    public void IListXMLSamples() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/xmlsamples").accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

}