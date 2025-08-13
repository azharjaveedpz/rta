package tests.ui;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import pages.ui.DashboardPage;
import pages.ui.LoginPage;

public class WhitelistPlateTest extends BaseTest {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	@Test
	public void testPlateCreation() {
		DashboardTest dashboard = new DashboardTest();
		
		
		 dashboard.validateDashboardMessage();;
		 dashboard.validateWhitelistManagementSubmenu();

	}
	

}

	


