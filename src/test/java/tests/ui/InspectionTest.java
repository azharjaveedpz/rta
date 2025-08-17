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
import pages.ui.WhitelistPlatesPage;

public class InspectionTest extends BaseTest {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	@Test
	public void createInspectionObstacles()  {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		InspectionObstaclesPage inspection = new InspectionObstaclesPage(driver, prop, test);

		

		dashboard.dashboardrMessageValidation();
		dashboard.clickInspection();
		dashboard.inspectionPageMessageValidation();
		inspection.navigateToAddInspectionPage();
		inspection.enterObstacleNumber();
		inspection.enterDeviceAndReportedDetails();
		inspection.enterComments();
		inspection.uploadPhoto();
		inspection.selectZone(1);
		inspection.selectArea(1);
		inspection.selectObstacle(1);
		inspection.submitInspectionObstacle();
	
		

	}

}
