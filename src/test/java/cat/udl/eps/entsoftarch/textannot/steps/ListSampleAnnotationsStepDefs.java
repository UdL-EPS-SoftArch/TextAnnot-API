package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.Annotation;
import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import cat.udl.eps.entsoftarch.textannot.repository.AnnotationRepository;

import cat.udl.eps.entsoftarch.textannot.repository.SampleRepository;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class ListSampleAnnotationsStepDefs {

    private static String currentUsername;
    private static String currentPassword;

    private Sample expected;
    List<Annotation> result;

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private AnnotationRepository annotationRepository;

    @Autowired
    private SampleRepository sampleRepository;

    public ListSampleAnnotationsStepDefs(StepDefs stepDefs) {
        this.stepDefs = stepDefs;
    }

    private String newSampleUri;
    private String newAnnotationUri;


    @Given("^I create an annotation with start (\\d+) and end (\\d+)$")
    public void iCreateAnAnnotationWithStartAndEnd(int arg0, int arg1) throws Throwable {
        JSONObject annotation = new JSONObject();
        annotation.put("start", arg0);
        annotation.put("end", arg1);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/annotations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(annotation.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newAnnotationUri = stepDefs.result.andReturn().getResponse().getHeader("Location");


    }

    @Given("^I create a different sample with text \"([^\"]*)\"$")
    public void iCreateASampleWithText(String arg0) throws Throwable {
        JSONObject sample = new JSONObject();
        sample.put("text", arg0);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/samples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sample.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newSampleUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @When("^I link the previous annotation with the previous sample$")
    public void iLinkThePreviousAnnotationWithThePreviousSample() throws Throwable {

        JSONObject annotated = new JSONObject();
        annotated.put("sample",newSampleUri);

        stepDefs.mockMvc.perform(patch(newAnnotationUri).content(annotated.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());



    }


    @Then("^The annotation with the text \"([^\"]*)\" has been linked to the sample$")
    public void theAnnotationWithTheTextHasBeenLinkedToTheSample(String arg0) throws Throwable {
        stepDefs.mockMvc.perform(get(newAnnotationUri + "/sample")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
                .andExpect(jsonPath("@.text").value(arg0))
                .andDo(print());
    }

    @Given("^I create a different sample with text \"([^\"]*)\" with (\\d+) related Annotations$")
    public void iCreateADifferentSampleWithTextWithRelatedAnnotations(String arg0, int arg1) {

        Sample annotated = new Sample();
        this.expected = annotated;
        annotated.setText(arg0);
        sampleRepository.save(annotated);


        for(int i = 0; i < arg1; i++){
            Annotation toAdd = new Annotation();
            toAdd.setStart(1 + i);
            toAdd.setEnd(2 + i);
            toAdd.setSample(annotated);
            annotationRepository.save(toAdd);
        }


    }

    @When("^I search by Annotated as the last sample$")
    public void iSearchByAnnotatedAsTheLastSample() {

        this.result = annotationRepository.findBySample(expected);


    }



    @Then("^I get a List with the said number of annotations$")
    public void iGetAListWithTheSaidNumberOfAnnotations() throws Throwable {
        stepDefs.mockMvc.perform(get("/annotations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate())).andExpect(jsonPath("@.page.totalElements").value(this.result.size()))
                .andDo(print());
    }



}
