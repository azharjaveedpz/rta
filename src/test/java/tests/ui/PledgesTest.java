package tests.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ui.CommonCode;
import pages.ui.DashboardPage;
import pages.ui.LoginPage;
import pages.ui.PledgesPage;
import pages.ui.WhitelistPage;

public class PledgesTest extends BaseTest {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);
	private DashboardPage dashboard;
	private LoginPage login;
	private CommonCode common;
	private PledgesPage pledge;
	private WhitelistPage plate;

	@BeforeMethod
	public void setUpPages() {
		dashboard = new DashboardPage(driver, prop, test);
		login = new LoginPage(driver, prop, test);
		common = new CommonCode(driver, prop, test);
		pledge = new PledgesPage(driver, prop, test);
		plate = new WhitelistPage(driver, prop, test);

		// login.login("azhar@gmail.com", "Azhar@123");
		// login.dismissChromePasswordAlert();
		// dashboard.dashboardrMessageValidation();

		dashboard.clickConfiguration();
		dashboard.clickPledgeManagement();

	}

	@Test
	public void TC_001_createPledge_shouldBeSuccessful() throws InterruptedException {

		// dashboard.pledgeManagementPageMessageValidation();
		pledge.navigateToAddPledgePage();
		pledge.enterTradeLicenseNumber();
		pledge.enterBusinessName();
		pledge.enterRemark();
		pledge.selectPledgeType("Corporate");
		common.uploadPhoto();
		common.startDateRange(29);
		common.startEndRange(30);
		common.submit();
		Thread.sleep(10000); // 20 seconds
		common.validateToastMessage("Pledge added successfully!");
		pledge.validatePledgeAdded();
		pledge.validateBusiness("Corporate");
		pledge.validateFromDate("2025-09-29");
		pledge.validateToDate("2025-09-30");

	}

	@Test
	public void TC_002_viewPledgeDetails_shouldDisplayCorrectly() {
		pledge.clickThreeDotAndValidateMenuList();
		common.view();
		pledge.navigateToViewPage();
		pledge.getPledgeDetails();
	}

	// ----------------- SEARCH VALIDATION (Positive) -----------------
	@Test
	public void TC_003_searchByTradeLicense_withValidData_shouldReturnResults() {
		common.searchAndValidateResult("trade number", "OY");
	}

	@Test
	public void TC_004_shouldSearchTradeLicenseWithInvalidDataAndShowNoResults() {
		common.searchWhitelistAndValidateNoData("AZHAR", "No data");
	}

	@Test
	public void TC_005_shouldSearchBusinessNameWithValidDataAndDisplayResults() throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterByText("Business Name");
		common.searchAndValidateResult("business", "Import and Export");
	}

	@Test
	public void TC_006_shouldSearchBusinessNameWithCamelCaseAndDisplayResults() throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterByText("Business Name");
		common.searchAndValidateResult("business", "IMPORT");
	}

	// ----------------- FILTER VALIDATION ----------------

	@Test
	public void TC_007_shouldFilterPledgesByCorporateTypeAndShowOnlyCorporate() throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterButton("Corporate"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		pledge.validatePledgeFilterList("Corporate");

	}

	// ----------------- SEARCH + FILTER VALIDATION (Single Filter)
	// -----------------

	@Test
	public void TC_008_shouldSearchTradeLicenseThenFilterByCorporateTypeAndValidateResults() {
		common.searchAndValidateResult("trade number", "OE49WZJS");
		common.selectFilterButton("Corporate"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		pledge.validatePledgeFilterList("Corporate");
	}

	@Test
	public void TC_009_shouldSearchBusinessNameThenFilterByCorporateTypeAndValidateResults()
			throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterByText("Business Name");
		common.searchAndValidateResult("business", "IMPORT");
		common.selectFilterButton("Corporate"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		pledge.validatePledgeFilterList("Corporate");
	}

	@Test
	public void TC_010_shouldSearchUsingTradeLicenseAndBusinessNameTogetherAndValidateResults()
			throws InterruptedException {
		common.searchAndValidateResult("trade number", "OY");
		Thread.sleep(2000);
		common.selectFilterByText("Business Name");
		common.searchAndValidateResult("business", "Gambling");
	}

	@Test
	public void TC_011_shouldSearchUsingTradeLicenseAndBusinessNameWithCorporateFilterAndValidateResults()
			throws InterruptedException {
		common.searchAndValidateResult("trade number", "OY");
		common.selectFilterButton("Corporate"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		pledge.validatePledgeFilterList("Corporate");
		common.selectFilterByText("Business Name");
		common.searchAndValidateResult("business", "Gambling");
	}

	// ----------------- PAGINATION VALIDATION -----------------
	@Test
	public void TC_012_shouldValidateFirstPagePaginationShowsCorrectNumberOfRows() {
		common.checkFirstPagePagination();
		pledge.validateTotalPledgesCount();
		// pledge.validateCorporatePledgesCount();
	}

	@Test
	public void TC_013_shouldValidateLastPagePaginationShowsCorrectNumberOfRows() throws InterruptedException {
		Thread.sleep(2000);
		common.checkLastPagePagination();
		Thread.sleep(1500);
		pledge.validateTotalPledgesCount();
	}

	@Test
	public void TC_014_shouldValidateMiddlePagePaginationNavigatesAndDisplaysCorrectly() throws InterruptedException {
		Thread.sleep(2000);
		pledge.checkMiddlePagesPagination();
	}

	@Test
	public void TC_015_shouldValidateTotalPledgeCountAndCorporatePledgeCountMatch() {
		pledge.validateTotalPledgesCount();
		pledge.validateCorporatePledgesCount();
	}

	@Test
	public void TC_016_searchByDateRange_shouldReturnPledgesWithinRange() {
		common.searchStartDate(29);
		common.searchEndDate(30);
		pledge.validateFromDate("2025-09-29");
		pledge.validateToDate("2025-09-30");
	}

	@Test
	public void TC_017_searchByEqualStartAndEndDate_shouldReturnMatchingPledges() {
		common.searchStartDate(24);
		common.searchEndDate(24);
		pledge.validateFromDate("2025-09-24");
		pledge.validateToDate("2025-09-24");
	}

	@Test
	public void TC_018_searchByTradeLicense_andDateRange_shouldReturnMatchingPledges() {
		common.searchAndValidateResult("trade number", "OY");
		common.searchStartDate(24);
		common.searchEndDate(25);
		pledge.validateFromDate("2025-09-24");
		pledge.validateToDate("2025-09-25");
	}

	@Test
	public void TC_019_searchByBusinessName_andDateRange_shouldReturnMatchingPledges() throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterByText("Business Name");
		common.searchAndValidateResult("business", "Diary");
		common.searchStartDate(24);
		common.searchEndDate(30);
		pledge.validateFromDate("2025-09-24");
		pledge.validateToDate("2025-09-30");
	}

	@Test
	public void TC_020_searchByBusinessName_andTradeLicense_andDateRange_shouldReturnMatchingPledges()
			throws InterruptedException {
		common.searchAndValidateResult("trade number", "D");
		Thread.sleep(2000);
		common.selectFilterByText("Business Name");
		common.searchAndValidateResult("business", "Dairy");
		common.searchStartDate(24);
		common.searchEndDate(30);
		pledge.validateFromDate("2025-09-24");
		pledge.validateToDate("2025-09-30");
	}

	@Test
	public void TC_021_editPledge_shouldUpdateSuccessfully() {
		pledge.clickThreeDotAndValidateMenuList();
		common.edit();
		pledge.navigateToEditPlatePage();
		pledge.clearAllFields();
		pledge.enterTradeLicenseNumber();
		common.startDateRange(28);
		common.startEndRange(30);
		// common.uploadPhoto();

		common.update();
		common.validateToastMessage("Pledge updated successfully!");
		pledge.validatePledgeAdded();
		pledge.validateBusiness("Corporate");
		pledge.validateFromDate("2025-09-29");
		pledge.validateToDate("2025-09-30");
	}

	@Test
	public void TC_022_ValidateErrorMessage() {
		pledge.navigateToAddPledgePage();
		common.submit();
		common.validateErrorMessage("Please enter Pledge Type");
		common.validateErrorMessage("Please enter Trade License Number");
		common.validateErrorMessage("Please enter Business Name");
		common.validateErrorMessage("Please enter Document");
		common.validateErrorMessage("Please enter Date Range");

	}
}
