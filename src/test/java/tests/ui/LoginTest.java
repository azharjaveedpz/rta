package tests.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import pages.LoginPage;

public class LoginTest extends BaseTest {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	@Test
	/*public void testLoginSuccess() {
		 LoginPage loginPage = new LoginPage(driver, prop);
		 loginPage.login();
	}*/

	
	public void testLoginFailure() {
	    LoginPage loginPage = new LoginPage(driver, prop);
	    loginPage.login();

	    if (loginPage.loginErrMessageValidation()) {
	        String errorMsg = loginPage.getLoginErrorMessage();
	        logger.info("Login failed with error: " + errorMsg);
	        test.log(Status.PASS, "Login failed with error: " + errorMsg);
	    } else {
	        logger.info("Login success");
	        test.log(Status.FAIL, "Login succeeded unexpectedly"); 
	    }
	}


}
