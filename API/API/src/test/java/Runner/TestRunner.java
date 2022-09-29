package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(tags = "", features = {"src/test/Features/ApiTest.feature"},
        glue = {"Steps"},
        plugin = {})

public class TestRunner extends AbstractTestNGCucumberTests{

}
