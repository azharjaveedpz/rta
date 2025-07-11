package base;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import utils.ConfigReader;
import utils.ExtentManager;
import utils.ScreenshotUtils;

public class BaseTest {
	 protected WebDriver driver; 
	    protected Properties prop;
	    protected ExtentReports extent;
	    protected ExtentTest test;

	    @BeforeMethod
	    public void setUp(Method method) {
	        prop = ConfigReader.initProperties();
	        extent = ExtentManager.getInstance();
	        test = extent.createTest(method.getName());

	        String testType = prop.getProperty("testType", "mobile");

	        if (testType.equalsIgnoreCase("web")) {
	            driver = DriverFactory.getDriver(prop);
	            driver.get(prop.getProperty("base.url"));
	            test.info("Web Test Started");

	        } else if (testType.equalsIgnoreCase("mobile")) {
	            driver = setupMobileDriver();
	            test.info("Mobile Test Started");

	        } else if (testType.equalsIgnoreCase("api")) {
	        	  driver = null;
	        	    test.info("API Test - No browser");
	        } else {
	            throw new RuntimeException("Invalid test type in config: " + testType);
	        }
	    }

	    private AppiumDriver setupMobileDriver() {
	        DesiredCapabilities caps = new DesiredCapabilities();

	        caps.setCapability("platformName", prop.getProperty("platformName"));
	        caps.setCapability("deviceName", prop.getProperty("deviceName"));
	        caps.setCapability("platformVersion", prop.getProperty("platformVersion"));
	        caps.setCapability("automationName", prop.getProperty("automationName"));
	        caps.setCapability("browserName", prop.getProperty("browserName"));
	        caps.setCapability("chromedriverExecutable", prop.getProperty("chromedriverExecutable"));

	        try {
	            System.out.println("Launching Appium session with capabilities: " + caps);
	            URL appiumURL = new URL("http://127.0.0.1:4723/wd/hub");
	            return new AndroidDriver(appiumURL, caps);
	        } catch (Exception e) {
	            e.printStackTrace(); 
	            throw new RuntimeException("Appium driver init failed: " + e.getMessage(), e);
	        }

	    }



    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail(result.getThrowable());

            // Use the util method
            String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getName());
            test.addScreenCaptureFromPath(screenshotPath);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test passed");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test skipped");
        }

        if (driver != null) {
            driver.quit();
        }

        extent.flush();
    }

}
