package Helper;

import Model.LoginDto;
import Pages.Pages;
import com.google.gson.Gson;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class Helper {

    public static void pause(int milli) throws InterruptedException {
        pause(milli, TimeUnit.MILLISECONDS);
    }

    public static void pause(int time, TimeUnit timeunit) throws InterruptedException {
        timeunit.sleep(time);
    }

    public static LoginDto LoginInfo(String jsonFile) throws IOException {
        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
        Path filePath = Paths.get(root.toString(),"src", "main", "resources", jsonFile);

        String json = readFileAsString(filePath.toString());
        Gson gjson = new Gson();
        LoginDto login = gjson.fromJson(json, LoginDto.class);
        return login;
    }

    public static String readFileAsString(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    public static void Register(AndroidDriver androidDriver, LoginDto login) throws InterruptedException
    {
        androidDriver.findElementById(Pages.REG_NAME).sendKeys(login.name);
        androidDriver.findElementById(Pages.TXT_EMAIL).sendKeys(login.email);
        androidDriver.findElementById(Pages.TXT_PASSWORD).sendKeys(login.password);
        androidDriver.findElementById(Pages.REG_PASS_CONFIRM).sendKeys(login.password);
        pause(500);
        androidDriver.findElementById(Pages.BTN_REGISTER).click();
        androidDriver.navigate().back();
    }

    public static void Login(AndroidDriver androidDriver, LoginDto login) throws InterruptedException {
        androidDriver.findElementById(Pages.TXT_EMAIL).sendKeys(login.email);
        androidDriver.findElementById(Pages.TXT_PASSWORD).sendKeys(login.password);
        androidDriver.findElementById(Pages.BTN_LOGIN).click();
        pause(120);
    }

    public static void waitUntilVisible(AndroidDriver driver, int timeLimitInSeconds, String targetResourceId) throws InterruptedException {
        MobileElement mobileElement;
        boolean isElementPresent;
        do{
            isElementPresent = false;
            try{
                mobileElement = (MobileElement) driver.findElementById(targetResourceId);
                WebDriverWait wait = new WebDriverWait(driver, timeLimitInSeconds);
                wait.until(ExpectedConditions.visibilityOf(mobileElement));
                driver.findElementById(targetResourceId).isDisplayed();
                isElementPresent = true;
            }catch (Exception e)
            {
                isElementPresent = false;
            }

        }while(!isElementPresent);
    }
}
