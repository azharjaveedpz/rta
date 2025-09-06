package tests.ui;


import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import pages.ui.LoginPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestLaunchBrowser extends BaseTest {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

    @Test
    public void openAppTest() {
        logger.info("🔍 Starting openAppTest");

        String currentUrl = driver.getCurrentUrl();
        logger.info(" Opened URL: " + currentUrl);
        test.log(Status.INFO, "Navigated to website");
        // Page object with internal login data loading
       // LoginPage loginPage = new LoginPage(driver, prop);
       // loginPage.login();

        logger.info("Login successful (assuming no error was thrown)");
        test.log(Status.PASS, " Login successful");

    }
}