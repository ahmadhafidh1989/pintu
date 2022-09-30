package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/features/LoginTest.feature", "src/test/features/RegisterTest.feature"},
        glue = {"Steps"}
)

public class Runner extends AbstractTestNGCucumberTests{

}
