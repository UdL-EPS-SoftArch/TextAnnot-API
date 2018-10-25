package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.Annotation;
import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import cat.udl.eps.entsoftarch.textannot.domain.Tag;
import cat.udl.eps.entsoftarch.textannot.repository.AnnotationRepository;
import cat.udl.eps.entsoftarch.textannot.repository.SampleRepository;
import cat.udl.eps.entsoftarch.textannot.repository.TagRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ListAnnotationsByTagStepDefs {


    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private AnnotationRepository annotationRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private SampleRepository sampleRepository;

    private String taggedAnnotation;
    private String taggedBy;
    private List<Annotation> taggedAnnotations;
    private Tag expectedTag;

    @Given("^I create a certain Tag with the name \"([^\"]*)\"$")
    public void iCreateACertainTagWithTheName(String tagName) throws Throwable {
        JSONObject tag = new JSONObject();
        tag.put("name", tagName);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tag.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        taggedBy = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @When("^I tag the annotation with the Tag$")
    public void iTagTheAnnotationWithTheTag() throws Throwable {

        JSONObject tagged = new JSONObject();
        tagged.put("tag",taggedBy);

        stepDefs.mockMvc.perform(patch(taggedAnnotation).content(tagged.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @Then("^The annotation has been tagged with the Tag named \"([^\"]*)\"$")
    public void theAnnotationHasBeenTaggedWithTheTagNamed(String tagName) throws Throwable {
        stepDefs.mockMvc.perform(get(taggedAnnotation + "/tag")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
                .andExpect(jsonPath("@.name").value(tagName))
                .andDo(print());
    }

    @Given("^I create a certain Tag with text \"([^\"]*)\" and Tag (\\d+) Annotations$")
    public void iCreateACertainTagWithTextAndTagAnnotations(String tagName, int numOfTags) {

        Tag taggedBy = new Tag(tagName);
        expectedTag = taggedBy;
        tagRepository.save(taggedBy);

        for(int i = 0; i < numOfTags; i++){
            Annotation toAdd = new Annotation();
            Sample sample = new Sample("Lorem ipsum dolor sit amet");
            sampleRepository.save(sample);
            toAdd.setSample(sample);
            toAdd.setStart(1 + i);
            toAdd.setEnd(2 + i);
            toAdd.setTag(taggedBy);

            annotationRepository.save(toAdd);
        }
    }

    @When("^I search the annotations by Tagged as the created Tag$")
    public void iSearchTheAnnotationsByTaggedAsTheCreatedTag() {
        taggedAnnotations = annotationRepository.findByTag(expectedTag);
    }

    @Then("^I get a List with the said number of tagged annotations$")
    public void iGetAListWithTheSaidNumberOfTaggedAnnotations() throws Throwable {
        stepDefs.mockMvc.perform(get("/annotations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate())).andExpect(jsonPath("@.page.totalElements").value(this.taggedAnnotations.size()))
                .andDo(print());
    }
}
