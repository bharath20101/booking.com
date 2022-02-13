package TestRunners;

import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(tags = "@Regression", features = "src\\main\\java\\Features", glue = "StepDefinitions")

@Test
public class Runner extends AbstractTestNGCucumberTests {

}
