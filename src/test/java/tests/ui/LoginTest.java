package tests.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import pages.ui.DashboardPage;
import pages.ui.LoginPage;

public class LoginTest extends BaseTest {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	@Test
	/*public void testLoginSuccess() {
		 LoginPage loginPage = new LoginPage(driver, prop);
		 loginPage.login();
	}*/

	
	public void testLoginFailure() {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);		dashboard.dashboardrMessageValidation();

	    if (dashboard.dashboardrMessageValidation()) {
	        String sucMsg = dashboard.getDashboardMessage();
	        logger.info("Navigated  to dashboard: " + sucMsg);
	        test.log(Status.PASS, "Navigated  to dashboard: " + sucMsg);
	    } else {
	        logger.info("Login failed");
	        test.log(Status.FAIL, "Login failed"); 
	    }
	}


}
