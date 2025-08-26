package base;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
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

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import utils.ConfigReader;
import utils.ExtentManager;
import utils.ScreenshotUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.logging.Level;

public class BaseTest {

	protected WebDriver driver;
	protected AppiumDriver mobileDriver;
	protected Properties prop;
	protected static ExtentReports extent;
	protected ExtentTest test;

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		extent = ExtentManager.getInstance();
	}

	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method) {
		prop = ConfigReader.initProperties();

		// Create a fresh node in ExtentReports for each test method
		test = extent.createTest(method.getDeclaringClass().getSimpleName() + " :: " + method.getName());

		String testType = prop.getProperty("testType", "mobile").toLowerCase();

		switch (testType) {
		case "web":
			setupWebDriver();
			break;
		case "mobile":
			setupMobileDriver();
			break;
		case "api":
			test.info("API Test - No browser needed");
			driver = null;
			mobileDriver = null;
			break;
		default:
			throw new RuntimeException("Invalid testType in config: " + testType);
		}
	}

	// ================= Web =================
	private void setupWebDriver() {
		driver = DriverFactory.getDriver(prop);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(prop.getProperty("base.url"));

		captureBrowserErrors(); // capture startup errors
		test.info("Web Test Started");
	}

	// ================= Mobile =================
	private void setupMobileDriver() {
		try {
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("platformName", prop.getProperty("platformName"));
			caps.setCapability("deviceName", prop.getProperty("deviceName"));
			caps.setCapability("platformVersion", prop.getProperty("platformVersion"));
			caps.setCapability("automationName", prop.getProperty("automationName"));
			caps.setCapability("browserName", prop.getProperty("browserName"));
			caps.setCapability("chromedriverExecutable", prop.getProperty("chromedriverExecutable"));

			mobileDriver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
			test.info("Mobile Test Started");
		} catch (Exception e) {
			throw new RuntimeException("Appium driver initialization failed: " + e.getMessage(), e);
		}
	}

	// ================= Capture Browser Console Errors =================
	private void captureBrowserErrors() {
		if (driver == null)
			return;

		try {
			LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
			for (LogEntry entry : logEntries) {
				String message = entry.getLevel() + " " + entry.getMessage();

				if (entry.getLevel().equals(Level.SEVERE)) {
					test.log(Status.FAIL, "Browser Console Error: " + message);
					System.err.println("Browser Console Error: " + message);
				} else {
					test.log(Status.WARNING, "Browser Console Log: " + message);
				}
			}
		} catch (Exception e) {
			test.warning("Unable to capture browser console logs: " + e.getMessage());
		}
	}

	// ================= TearDown =================
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) throws IOException {
		String testType = prop.getProperty("testType", "mobile").toLowerCase();

		// Always capture logs after test run
		if ("web".equals(testType)) {
			captureBrowserErrors();
		}

		if (result.getStatus() == ITestResult.FAILURE) {
			test.fail(result.getThrowable());
			if (!"api".equals(testType)) {
				WebDriver activeDriver = ("web".equals(testType) ? driver : mobileDriver);
				if (activeDriver != null) {
					String screenshotPath = ScreenshotUtils.captureScreenshot(activeDriver, result.getName());
					if (screenshotPath != null) {
						test.fail("Failure Screenshot:",
								MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
					}
				}
			}
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.pass("Test passed");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.skip("Test skipped");
		}

		// Quit drivers
		if ("web".equals(testType) && driver != null)
			driver.quit();
		if ("mobile".equals(testType) && mobileDriver != null)
			mobileDriver.quit();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite(ITestContext context) {
		ExtentManager.flushReport(); 
	}
}
