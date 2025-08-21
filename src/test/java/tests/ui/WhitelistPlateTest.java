package tests.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.ui.DashboardPage;
import pages.ui.WhitelistPlatesPage;

public class WhitelistPlateTest extends BaseTest {

	private DashboardPage dashboard;
	private WhitelistPlatesPage plate;

	@BeforeMethod
	public void setUpPages() {
		dashboard = new DashboardPage(driver, prop, test);
		plate = new WhitelistPlatesPage(driver, prop, test);

		dashboard.dashboardrMessageValidation();
		dashboard.openWhitelistManagementMenu();
		dashboard.validateWhitelistManagementSubmenuItems();
		plate.navigateToPlatePage();
	}

	// ----------------- CREATE PLATE TESTS -----------------

	@Test
	public void TC_001_shouldCreatePlateSuccessfully() {
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
	public void TC_002_shouldCreatePlateWithTodaysDateSuccessfully() {
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

		plate.validateStartDate();
		plate.validateEndDate();
	}

	@Test
	public void TC_003_shouldNotAllowDuplicatePlate() {
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

	// ----------------- SEARCH TESTS -----------------

	@Test
	public void TC_004_shouldFindPlateWhenSearchingByValidPlateNumber() {
		plate.searchWhitelistAndValidateResult("SJ1234", null);
	}

	@Test
	public void TC_005_shouldNotFindPlateWhenSearchingByInvalidPlateNumber() {
		plate.searchWhitelistAndValidateNoData("Commercial", "No data");
	}

	@Test
	public void TC_006_shouldFindPlatesWhenSearchingByPartialPlateNumber() {
		plate.searchWhitelistWithPartialTextAndValidateResult("1234", null);
	}

	@Test
	public void TC_007_shouldFindPlatesWhenSearchingByValidPlateSource() {
		plate.selectFilterByIndex(1);
		plate.searchWhitelistAndValidateResult(null, "Abu Dhabi");
	}

	@Test
	public void TC_008_shouldNotFindPlatesWhenSearchingByInvalidPlateSource() {
		plate.selectFilterByIndex(1);
		plate.searchWhitelistAndValidateNoData("Commercial", "No data");
	}

	@Test
	public void TC_009_shouldFindPlatesWhenSearchingByPartialPlateSource() {
		plate.selectFilterByIndex(1);
		plate.searchWhitelistWithPartialTextAndValidateResult(null, "Abu");
	}

	@Test
	public void TC_010_shouldFindPlatesWithinValidDateRange() {
		plate.searchStartDate(25);
		plate.searchEndDate(25);
		plate.validateStartDate();
		plate.validateEndDate();
	}

	@Test
	public void TC_011_shouldFindPlatesWhenStartDateEqualsEndDate() {
		plate.searchStartDate(24);
		plate.searchEndDate(24);
		plate.validateStartDate();
		plate.validateEndDate();
	}

	@Test
	public void TC_012_shouldFindPlateWhenSearchingByPlateNumberAndSource() {
		plate.searchWhitelistAndValidateResult("SJ1234", null);
		plate.selectFilterByIndex(1);
		plate.searchWhitelistAndValidateResult(null, "Abu Dhabi");
	}

	@Test
	public void TC_013_shouldNotFindPlateWhenPlateNumberAndSourceDoNotMatch() {
		plate.searchWhitelistAndValidateResult("SJ1234", null);
		plate.selectFilterByIndex(1);
		plate.searchWhitelistAndValidateNoData("Commercial", "No data");
	}

	@Test
	public void TC_014_shouldFindPlateWhenSearchingByPlateNumberAndDateRange() {
		plate.searchWhitelistWithPartialTextAndValidateResult("1234", null);
		plate.searchStartDate(23);
		plate.searchEndDate(25);
		plate.validateStartDate();
		plate.validateEndDate();
	}

	@Test
	public void TC_015_shouldFindPlatesWhenSearchingBySourceAndDateRange() {
		plate.selectFilterByIndex(1);
		plate.searchWhitelistWithPartialTextAndValidateResult(null, "Abu");
		plate.searchStartDate(23);
		plate.searchEndDate(25);
		plate.validateStartDate();
		plate.validateEndDate();
	}

	@Test
	public void TC_016_shouldFindPlateWhenSearchingByPlateNumberSourceAndDateRange() {
		plate.searchWhitelistWithPartialTextAndValidateResult("1234", null);
		plate.selectFilterByIndex(1);
		plate.searchWhitelistWithPartialTextAndValidateResult(null, "Abu");
		plate.searchStartDate(23);
		plate.searchEndDate(25);
		plate.validateStartDate();
		plate.validateEndDate();
	}

	// ----------------- VALIDATION & EDIT -----------------

	@Test
	public void TC_017_validatePlateCount() {
		plate.printAndValidatePlateCounts();
		plate.printAndValidateTableCounts();
		plate.validateTableRowsAgainstTotalPlates();
	}

	@Test
	public void TC_018_shouldEditPlateSuccessfully() {
		plate.clickThreeDotAndValidateMenuList();
		plate.navigateToEditPlatePage();
		plate.clearAllFields();
		plate.enterPlateNumber();
		plate.startDateRange(24);
		plate.startEndRange(26);
		plate.updatePlate();
		plate.validatePlateAdded();
	}

	// ----------------- VALIDATION & VIEW -----------------

	@Test
	public void TC_019_TC_viewPlateDetails() {
		plate.clickThreeDotAndValidateMenuList();
		plate.navigateToViewPlatePage();
		plate.getPlateDetails();
	}

	// ----------------- PAGINAATION VALIDATION -----------------

	@Test
	public void TC_020_shouldValidateFirstPagePagination() {
		plate.checkFirstPagePagination();
	}

	@Test
	public void TC_021_shouldValidateMiddlePagesPagination() {
		plate.checkMiddlePagesPagination();
	}

	@Test
	public void TC_022_shouldValidateLastPagePagination() {
		plate.checkLastPagePagination();
	}
}