package Steps;

import Helper.Helper;
import Model.LoginDto;
import Pages.Pages;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static Helper.Helper.pause;
import static Helper.Helper.waitUntilVisible;

public class RegisterTestSteps {
    public static AndroidDriver androidDriver;
    public static String LoginData = "LoginInfo.json";
    public LoginDto loginInfo;
    public String email ;
    public String password;
    public String name;

    public RegisterTestSteps() throws IOException, InterruptedException {
        loginInfo = Helper.LoginInfo(LoginData);

        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
        Path filePath = Paths.get(root.toString(),"src", "main", "resources", "Login.apk");
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel");
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        caps.setCapability(MobileCapabilityType.UDID, "emulator-5554");
        caps.setCapability("ignoreHiddenApiPolicyError", true);
        caps.setCapability("avd","Pixel");
        caps.setCapability(MobileCapabilityType.NO_RESET,"false");
        caps.setCapability("avdLaunchTimeOut", 10980000);
        caps.setCapability("avdReadyTimeout", 10980000);
        caps.setCapability(MobileCapabilityType.APP, filePath.toString());

        URL url = new URL("http://0.0.0.0:4723/wd/hub");
        androidDriver = new AndroidDriver(url, caps);
        pause(800);
    }

    @Given("I want to register with invalid email address")
    public void i_want_to_register_with_invalid_email_address() throws InterruptedException {
        loginInfo.email = "ahk@ahk";
    }

    @Given("I want to register with out providing name")
    public void iWantToRegisterWithOutProvidingName() throws InterruptedException {
        loginInfo.name = "";
    }

    @Given("I want to register with out providing password")
    public void iWantToRegisterWithOutProvidingPassword() throws InterruptedException {
        loginInfo.password = "";
    }

    @Given("I want to register With invalid confirm password")
    public void iWantToRegisterWithInvalidConfirmPassword() throws InterruptedException {
        loginInfo.confirmPassword = "321";
    }

    @When("I register")
    public void i_register_in() throws InterruptedException {
        androidDriver.findElementById(Pages.LINK_SIGNUP).click();
        pause(1000);
        androidDriver.findElementById(Pages.REG_NAME).isDisplayed();
        androidDriver.findElementById(Pages.REG_NAME).sendKeys(loginInfo.name);
        androidDriver.findElementById(Pages.TXT_EMAIL).sendKeys(loginInfo.email);
        androidDriver.findElementById(Pages.TXT_PASSWORD).sendKeys(loginInfo.password);
        androidDriver.findElementById(Pages.REG_PASS_CONFIRM).sendKeys(loginInfo.confirmPassword);
        pause(500);
        androidDriver.findElementById(Pages.BTN_REGISTER).click();
    }

    @Then("I should get registration error message saying {string}")
    public void iShouldGetRegistrationErrorMessageSayingEnterFullName(String errorMessage) {
        List<WebElement> webElements = androidDriver.findElementsByClassName("android.widget.TextView");
        for(WebElement we : webElements){
            if(we.getText().contains(errorMessage)){
                Assert.assertEquals(errorMessage, we.getText());
                break;
            }
        }
    }

    @Then("I should be able to login")
    public void iShouldBeAbleToLogin() throws InterruptedException {
        androidDriver.navigate().back();
        Helper.Login(androidDriver,loginInfo);
    }

    @And("I should logged in")
    public void iShouldLoggedIn() {
        String welcomeString = "Android NewLine Learning";
        List<WebElement> webElements = androidDriver.findElementsByClassName("android.widget.TextView");
        for(WebElement we : webElements){
            if(we.getText().contains(welcomeString)){
                Assert.assertEquals(we.getText(), welcomeString);
            }
            if(we.getText().contains(loginInfo.email)){
                Assert.assertEquals(we.getText(),loginInfo.email);
            }
        }
    }
}
