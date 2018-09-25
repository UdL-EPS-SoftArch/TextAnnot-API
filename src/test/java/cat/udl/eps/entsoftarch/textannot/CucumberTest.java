package cat.udl.eps.entsoftarch.textannot;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin={"pretty"}, tags = {"@Current"}, features="src/test/resources")
public class CucumberTest {
}
