package tests.ui;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import pages.ui.DashboardPage;
import pages.ui.LoginPage;
import pages.ui.PermitPage;
import pages.ui.WhitelistPlatesPage;

public class PermitTest extends BaseTest {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	@Test
	public void PlateNumberSearch() {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		PermitPage permit = new PermitPage(driver, prop, test);

		String plateNumber = "HW588X7LA";

		dashboard.dashboardrMessageValidation();
		dashboard.clickPermits();
		dashboard.permitPageMessageValidation();
		permit.enterPlateNumber(plateNumber);
		permit.searchPermit();
		permit.validateSearchedPlateNumber(plateNumber);

	}

}
