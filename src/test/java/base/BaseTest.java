package base;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
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

        // Create test node with class + method name
        test = extent.createTest(method.getDeclaringClass().getSimpleName() + " :: " + method.getName());

        String testType = prop.getProperty("testType", "mobile");

        if (testType.equalsIgnoreCase("web")) {
            driver = DriverFactory.getDriver(prop);
            driver.get(prop.getProperty("base.url"));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
            URL appiumURL = new URL("http://127.0.0.1:4723");
            return new AndroidDriver(appiumURL, caps);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Appium driver init failed: " + e.getMessage(), e);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException {
        String testType = prop.getProperty("testType", "mobile");

        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail(result.getThrowable());
            if (!"api".equalsIgnoreCase(testType) && driver != null) {
                String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getName());
                if (screenshotPath != null) {
                    test.fail("Failure Screenshot:",
                            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                }
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test passed");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test skipped");
        }

        if (driver != null && !"api".equalsIgnoreCase(testType)) {
            driver.quit();
        }

        // flush immediately if only a single test is running
        if (result.getTestContext().getSuite().getAllMethods().size() == 1) {
            ExtentManager.flushReport();
        }
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        // flush report after all tests in the class (for IDE class run)
        ExtentManager.flushReport();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite(ITestContext context) {
        // log summary and flush after full suite run
        ExtentManager.logSummary(context);
        ExtentManager.flushReport();
    }
}
