package Steps;

import Model.LoginDto;
import Pages.Pages;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import Helper.Helper;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import static Helper.Helper.pause;


public class LoginTestSteps {
    public static AndroidDriver androidDriver;
    public static String LoginData = "LoginInfo.json";
    public LoginDto loginInfo;
    public String email ;
    public String password;

    public LoginTestSteps() throws IOException, InterruptedException {
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

    @After
    public void TearDown(){
        androidDriver.closeApp();
    }

    @When("I Log in")
    public void i_log_in() throws InterruptedException {
        //androidDriver.findElementById(Pages.TXT_EMAIL).click();
        androidDriver.findElementById(Pages.TXT_EMAIL).sendKeys(email);
        //androidDriver.findElementById(Pages.TXT_PASSWORD).click();
        androidDriver.findElementById(Pages.TXT_PASSWORD).sendKeys(password);
        androidDriver.findElementById(Pages.BTN_LOGIN).click();
        pause(120);
    }

    @Given("I want to login with invalid email format")
    public void iWantToLoginWithInvalidEmailFormat() {
        email = "hafidh@hafidh";
        password = "test123";
    }

    @Then("I should get login error invalid email format message")
    public void iShouldGetLoginErrorInvalidEmailFormatMessage() {
        String errorMessage = "Enter Valid Email";
        List<WebElement> webElements = androidDriver.findElementsByClassName("android.widget.TextView");
        for(WebElement we : webElements){
            if(we.getText().contains(errorMessage)){
                Assert.assertEquals(errorMessage, we.getText());
                break;
            }
        }
    }

    @Given("I want to login without providing password")
    public void iWantToLoginWithoutProvidingPassword() {
        email = "hafidh@hafidh.com";
        password = "";
    }

    @Given("I want to login with invalid password or email")
    public void iWantToLoginWithInvalidPasswordOrEmail() {
        email = "hafidh@hafidh.com";
        password = "test@123456";
    }

    @Then("I should get login error invalid error or password")
    public void iShouldGetLoginErrorInvalidErrorOrPassword() {
        System.out.println("still no clue");
        Assert.assertTrue(false);
    }

    @Given("I want to login with valid email and password")
    public void iWantToLoginWithValidEmailAndPassword() throws InterruptedException {
        email = loginInfo.email;
        password = loginInfo.password;
        androidDriver.findElementById(Pages.LINK_SIGNUP).click();
        pause(500);
        Helper.Register(androidDriver,loginInfo);
    }

    @Then("I should redirect to home page")
    public void iShouldRedirectToHomePage() {
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
