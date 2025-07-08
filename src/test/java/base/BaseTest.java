package base;

import java.lang.reflect.Method;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utils.ConfigReader;
import utils.ExtentManager;

public class BaseTest {
    protected WebDriver driver;
    protected Properties prop;
    protected ExtentReports extent;
    protected ExtentTest test;

    @BeforeMethod
    public void setUp(Method method) {
        // Load properties
        prop = ConfigReader.initProperties();

        // Initialize Extent Report
        extent = ExtentManager.getInstance();
        test = extent.createTest(method.getName()); // Dynamic test name

        // Decide which test type to run
        String testType = prop.getProperty("testType", "web");

        if (testType.equalsIgnoreCase("web")) {
            driver = DriverFactory.getDriver(prop);
            driver.get(prop.getProperty("base.url"));
            test.info("Launching browser: " + prop.getProperty("base.url"));
            test.log(Status.INFO, "Browser started");
        } else if (testType.equalsIgnoreCase("mobile")) {
            test.info("Mobile setup to be added.");
        } else if (testType.equalsIgnoreCase("api")) {
            test.info("API test, no browser launch.");
        } else {
            throw new RuntimeException("Invalid testType: " + testType);
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Log result in Extent Report
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test passed");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test skipped");
        }

        // Close browser if applicable
        if (driver != null) {
            driver.quit();
        }

        // Flush Extent report
        extent.flush();
    }
}
