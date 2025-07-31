package tests.mobile;


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
        driver.get("https://www.google.com");
        System.out.println("Page title is: " + driver.getTitle());
    }
}