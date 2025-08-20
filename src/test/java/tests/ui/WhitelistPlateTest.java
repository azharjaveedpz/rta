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
		plate.validateStartDate();
		plate.validateEndDate();
	}
	
	@Test
	public void shouldCreatePlateWithTodaysDateSuccessfully() {
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
		plate.startDateRange(20);
		plate.startEndRange(20);
		plate.submitPlate();
		//plate.validatePlateAdded();
		plate.validateStartDate();
		plate.validateEndDate();
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
	public void shouldFindPlateWhenSearchingByValidPlateNumber()  {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		plate.searchWhitelistAndValidateResult("SJ1234", null);
		
	}
	
	@Test
	public void shouldNotFindPlateWhenSearchingByInvalidPlateNumber()  {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		plate.searchWhitelistAndValidateNoData("Commercial",  "No data");
		
	}
	
	@Test
	public void shouldFindPlatesWhenSearchingByPartialPlateNumber()  {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		plate.searchWhitelistWithPartialTextAndValidateResult("1234", null);
		
	}
	

	@Test
	public void shouldFindPlatesWhenSearchingByValidPlateSource()  {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		//plate.selectFilterByText("Plate Source");
		plate.selectFilterByIndex(1);
		plate.searchWhitelistAndValidateResult(null, "Abu Dhabi");
		
	}
	
	@Test
	public void shouldNotFindPlatesWhenSearchingByInvalidPlateSource()  {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		//plate.selectFilterByText("Plate Source");
		plate.selectFilterByIndex(1);
		plate.searchWhitelistAndValidateNoData("Commercial",  "No data");
		
	}
	
	@Test
	public void shouldFindPlatesWhenSearchingByPartialPlateSource()  {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		//plate.selectFilterByText("Plate Source");
		plate.selectFilterByIndex(1);
		plate.searchWhitelistWithPartialTextAndValidateResult(null, "Abu");
		
	}

	
	
	@Test
	public void shouldFindPlatesWithinValidDateRange()  {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		plate.searchStartDate(23);
		plate.searchEndDate(25);
		plate.validateStartDate();
		plate.validateEndDate();

	}
	
	@Test
	public void shouldFindPlatesWhenStartDateEqualsEndDate()  {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		plate.searchStartDate(24);
		plate.searchEndDate(24);
		plate.validateStartDate();
		plate.validateEndDate();

	}
	
	@Test
	public void shouldFindPlateWhenSearchingByPlateNumberAndSource()  {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		plate.searchWhitelistAndValidateResult("SJ1234", null);
		plate.selectFilterByIndex(1);
		plate.searchWhitelistAndValidateResult(null, "Abu Dhabi");
		
	}
	
	@Test
	public void shouldNotFindPlateWhenPlateNumberAndSourceDoNotMatch()   {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		plate.searchWhitelistAndValidateResult("SJ1234", null);
		plate.selectFilterByIndex(1);
		plate.searchWhitelistAndValidateNoData("Commercial",  "No data");
		
	}
	
	@Test
	public void shouldFindPlateWhenSearchingByPlateNumberAndDateRange()   {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		plate.searchWhitelistWithPartialTextAndValidateResult("1234", null);
		plate.searchStartDate(23);
		plate.searchEndDate(25);
		plate.validateStartDate();
		plate.validateEndDate();
		
	}
	
	@Test
	public void shouldFindPlatesWhenSearchingBySourceAndDateRange() throws InterruptedException   {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		plate.selectFilterByIndex(1);
		plate.searchWhitelistWithPartialTextAndValidateResult(null, "Abu");
		Thread.sleep(2000);
		plate.searchStartDate(23);
		plate.searchEndDate(25);
		plate.validateStartDate();
		plate.validateEndDate();
		
	}

	@Test
	public void shouldFindPlateWhenSearchingByPlateNumberSourceAndDateRange() throws InterruptedException  {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		plate.searchWhitelistWithPartialTextAndValidateResult("1234", null);
		plate.selectFilterByIndex(1);
		plate.searchWhitelistWithPartialTextAndValidateResult(null, "Abu");
		Thread.sleep(2000);
		plate.searchStartDate(23);
		plate.searchEndDate(25);
		plate.validateStartDate();
		plate.validateEndDate();
		
	}
	
	@Test
	public void validatePlateCount() {
		DashboardPage dashboard = new DashboardPage(driver, prop, test);
		WhitelistPlatesPage plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
		plate.printAndValidatePlateCounts();
		plate.printAndValidateTableCounts();
		plate.validateTableRowsAgainstTotalPlates();
		
		
	}
	@Test
	public void shouldEditPlate()  {
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
		plate.updatePlate();
		plate.validatePlateAdded();

	}

}
