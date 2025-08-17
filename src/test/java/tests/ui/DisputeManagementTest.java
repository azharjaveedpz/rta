package tests.ui;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import pages.ui.DashboardPage;
import pages.ui.DisputeManagementPage;
import pages.ui.InspectionObstaclesPage;
import pages.ui.LoginPage;
import pages.ui.PermitPage;
import pages.ui.WhitelistPlatesPage;

public class DisputeManagementTest extends BaseTest {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	@Test
	public void createDisputeManagement()  {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		DisputeManagementPage dispute = new DisputeManagementPage(driver, prop, test);

		

		dashboard.dashboardrMessageValidation();
		dashboard.clickDisputeManagement();
		dashboard.disputeManagementPageMessageValidation();
		dispute.navigateToAddDisputePage();
		dispute.enterFineNumber();
		dispute.enterReason();
		dispute.enterCRM();
		dispute.enterEmail();
		dispute.enterPhone();
		dispute.enterAddress();
		dispute.selectDepartment(1);
		dispute.selectPaymentType(1);
		dispute.submitDisputeManagement();
		
		

	}

}
