package tests.ui;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ui.CommonCode;
import pages.ui.DashboardPage;
import pages.ui.LoginPage;
import pages.ui.PledgesPage;

public class PledgesTest extends BaseTest {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);
	private DashboardPage dashboard;
	private LoginPage login;
	private CommonCode common;
	private PledgesPage pledge;

	@BeforeMethod
	public void setUpPages() throws InterruptedException {
		dashboard = new DashboardPage(driver, prop, test);
		login = new LoginPage(driver, prop, test);
		common = new CommonCode(driver, prop, test);
		pledge = new PledgesPage(driver, prop, test);

		// login.login("azhar@gmail.com", "Azhar@123");
		// login.dismissChromePasswordAlert();
		// dashboard.dashboardrMessageValidation();
		Thread.sleep(1000);
		dashboard.clickConfiguration();
		dashboard.clickPledgeManagement();

	}

	@Test
	public void TC_001_createPledgeSuccessfully() throws InterruptedException {

		// dashboard.pledgeManagementPageMessageValidation();
		pledge.navigateToAddPledgePage();
		Thread.sleep(1000);
		pledge.enterTradeLicenseNumber();
		pledge.enterBusinessName();
		pledge.enterRemark();
		pledge.selectPledgeType("Corporate");
		common.uploadPhoto();
		common.submit();
		Thread.sleep(21000); // 20 seconds
		common.validateToastMessage("Pledge added successfully!");
		pledge.validatePledgeAdded();
		pledge.validateBusiness("Corporate");

	}

	@Test
	public void TC_002_shouldOpenViewPageAndValidatePledgeDetails() {
		pledge.clickThreeDotAndValidateMenuList();
		common.view();
		pledge.navigateToViewPage();
		pledge.getPledgeDetails();
	}

	// ----------------- SEARCH VALIDATION (Positive) -----------------
	@Test
	public void TC_003_shouldSearchTradeLicenseWithValidDataAndDisplayResults() {
		common.searchAndValidateResult("trade number", "O");
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
		common.searchAndValidateResult("trade number", "O");
		Thread.sleep(2000);
		common.selectFilterByText("Business Name");
		common.searchAndValidateResult("business", "IMPORT");
	}

	@Test
	public void TC_011_shouldSearchUsingTradeLicenseAndBusinessNameWithCorporateFilterAndValidateResults()
			throws InterruptedException {
		common.searchAndValidateResult("trade number", "O");
		common.selectFilterButton("Corporate"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		pledge.validatePledgeFilterList("Corporate");
		common.selectFilterByText("Business Name");
		common.searchAndValidateResult("business", "IMPORT");
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
}
