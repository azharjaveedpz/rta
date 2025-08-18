package tests.ui;

import org.testng.annotations.Test;
import base.BaseTest;
import pages.ui.DashboardPage;
import pages.ui.WhitelistPlatesPage;

public class WhitelistPlateTest extends BaseTest {
	@Test
	public void shouldCreatePlateSuccessfully() {
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
		plate.validatePlateAdded();
	}

	@Test
	public void shouldNotAllowDuplicatePlate() {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		plate.navigateToAddNewPlatePage();
		plate.enterDuplicatePlateNumber("TN223A4BCD");
		plate.selectPlateSource(1);
		plate.selectPlateType(1);
		plate.selectPlateColor(1);
		plate.selectExemption(2);
		plate.selectStatus(0);
		plate.startDateRange(24);
		plate.startEndRange(26);
		plate.submitPlate();
		plate.validatePlateError("Plate number already exists for the specified period");
	}

	@Test
	public void shouldSearchPlateByNumber() {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		plate.searchWhitelist("ST33ZPFY", null, null, null, null, null);

	}

	@Test
	public void shouldSearchPlateBySource() {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		plate.searchWhitelist(null, "Abu Dhabi", null, null, null, null);

	}

	@Test
	public void shouldSearchPlateByType() {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		plate.searchWhitelist(null, null, "Commercial", null, null, null);

	}

	@Test
	public void shouldSearchPlateByColor() {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		plate.searchWhitelist(null, null, null, "Red", null, null);

	}

	@Test
	public void shouldSearchPlateByDate() throws InterruptedException {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		plate.searchStartDate(24);
		plate.searchEndDate(26);
		plate.validateStartDate();
		plate.validateEndDate();

	}

	@Test
	public void shouldEditPlate() throws InterruptedException {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		plate.clickThreeDotAndValidateMenuList();
		plate.navigateToEditPlatePage();
		plate.clearAllFields();
		plate.enterPlateNumber();
		Thread.sleep(1000);
		plate.updatePlate();
		plate.validatePlateAdded();

	}

}
