package tests.ui;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import pages.ui.DashboardPage;
import pages.ui.LoginPage;
import pages.ui.WhitelistPlatesPage;

public class WhitelistPlateTest extends BaseTest {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	@Test
	public void PlateCreation() {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		plate.navigateToAddNewPlatePage();
		plate.enterPlateNumber();
		plate.selectPlateSource(1);
		plate.selectPlateType(1);
		plate.selectPlateColor(1);
		plate.selectExemption(2);
		plate.selectStatus(0);
		plate.startDateRange(24);
		plate.startEndRange(26);
		plate.submitPlate();

	}

}
