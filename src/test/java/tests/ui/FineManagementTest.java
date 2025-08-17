package tests.ui;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import pages.ui.DashboardPage;
import pages.ui.FineManagementPage;
import pages.ui.LoginPage;
import pages.ui.PermitPage;
import pages.ui.WhitelistPlatesPage;

public class FineManagementTest extends BaseTest {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	@Test
	public void fineNumberSearch() {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		FineManagementPage fine = new FineManagementPage(driver, prop, test);


		String fineNumber = "SM17DQPD";

		dashboard.dashboardrMessageValidation();
		dashboard.clickFineManagement();
		dashboard.fineManagementPageMessageValidation();
		fine.enterFineNumber(fineNumber);
		fine.searchFine();
		fine.validateSearchedFineNumber(fineNumber);
		

	}

}
