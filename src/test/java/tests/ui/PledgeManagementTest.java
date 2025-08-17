package tests.ui;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import pages.ui.DashboardPage;
import pages.ui.InspectionObstaclesPage;
import pages.ui.LoginPage;
import pages.ui.PermitPage;
import pages.ui.PledgeManagementPage;
import pages.ui.WhitelistPlatesPage;

public class PledgeManagementTest extends BaseTest {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	@Test
	public void createPledgeManagement()  {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		PledgeManagementPage pledge = new PledgeManagementPage(driver, prop, test);


		

		dashboard.dashboardrMessageValidation();
		dashboard.clickPledgeManagement();
		dashboard.pledgeManagementPageMessageValidation();
		pledge.navigateToAddPledgePage();
		pledge.enterPledgeNumber();
		pledge.enterTradeLicenseNumber();
		pledge.enterBusinessName();
		pledge.enterRemark();
		pledge.uploadPhoto();
		pledge.selectPledgeType(1);
		pledge.submitPledge();

		
	
		

	}

}
