package tests.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ui.CommonCode;
import pages.ui.DashboardPage;
import pages.ui.DisputesPage;
import pages.ui.LoginPage;

public class DisputesTest extends BaseTest {
	private DashboardPage dashboard;
	private LoginPage login;

	private CommonCode common;
	private DisputesPage dispute;

	@BeforeMethod
	public void setUpPages() {
		dashboard = new DashboardPage(driver, prop, test);

		login = new LoginPage(driver, prop, test);
		common = new CommonCode(driver, prop, test);
		dispute = new DisputesPage(driver, prop, test);

		// login.login("azhar@gmail.com", "Azhar@123");
		// login.dismissChromePasswordAlert();
		// dashboard.dashboardrMessageValidation();
		dashboard.clickDisputeManagement();
		dashboard.disputeManagementPageMessageValidation();

	}

	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	@Test
	public void TC_001_shouldCreateDisputeSucessfully() {

		dispute.navigateToAddDisputePage();
		dispute.enterFineNumber();
		dispute.enterReason();
		dispute.enterCRM();
		dispute.enterEmail();
		dispute.enterPhone();
		dispute.enterAddress();
		dispute.selectDepartment("Parking");
		dispute.selectPaymentType("Online");
		common.submit();
		common.validateToastMessage("Dispute added successfully!");
		dispute.validateDisputeAdded();
		dispute.validateDepartment("Parking");
		dispute.validatePaymentType("Online");
		dispute.validatePhoneNumber();

	}

	@Test
	public void TC_002_shouldGetDisputeDetailsFromViewPage() {
		dispute.clickThreeDotAndValidateMenuList();
		common.view();
		dispute.navigateToViewPlatePage();
		// plate.getPlateDetails();
	}

	@Test
	public void TC_003_shouldEditDisputeSuccessfully() {
		dispute.clickThreeDotAndValidateMenuList();
		common.edit();
		dispute.navigateToEditPlatePage();
		dispute.clearAllFields();
		dispute.enterCRM();
		// dispute.selectDepartment("Traffic");
		// dispute.selectPaymentType("Cash");
		common.update();
		common.validateToastMessage("Dispute updated successfully!");
		dispute.validateDisputeAdded();
		// dispute.validateDepartment("Traffic");
		// dispute.validatePaymentType("Cash");
	}

	// ----------------- SEARCH TESTS -----------------

	@Test
	public void TC_004_shouldSearchTradeLicenseWithValidData() {
		common.searchAndValidateResult("fine number", "12345");
	}

	@Test
	public void TC_005_shouldSearchCRMReferenceWithValidData() throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterByText("CRM Reference");
		common.searchAndValidateResult("crm reference", "12345");
	}

	@Test
	public void TC_006_shouldSearchPhoneNumberWithValidData() throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterByText("Phone Number");
		common.searchAndValidateResult("phone number", "1234567890");
	}

	@Test
	public void TC_007_shouldSearchEmailWithValidData() throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterByText("Email");
		common.searchAndValidateResult("email", "azhar@gmail.com");
	}

	@Test
	public void TC_008_shouldNotFindDataWhenSearchingWithInvalidData() {
		common.searchWhitelistAndValidateNoData("AZHAR", "No data");
	}

	@Test
	public void TC_009_shouldNotFindDataWhenSearchingWithInvalidEmail() throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterByText("Email");
		common.searchWhitelistAndValidateNoData("azhaez@", "No data");

	}

	// ----------------- FILTER VALIDATION ----------------

	@Test
	public void TC_010_shouldFilterDisputesByPaymentTypeSuccessfully() throws InterruptedException {

		common.selectFilterButton("Online"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Online");

	}

	@Test
	public void TC_011_shouldFilterDisputesByDepartmentSuccessfully() throws InterruptedException {

		common.selectSecondFilterButton("ECE"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validatePaymentFilterList("ECE");

	}
	// ----------------- SEARCH + FILTER VALIDATION (Single Filter)
	// -----------------

	@Test
	public void TC_012_shouldSearchFineNumberAndFilterByDeparment() {
		common.searchAndValidateResult("fine number", "12345");
		common.selectFilterButton("Online"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Online");
	}

	@Test
	public void TC_013_shouldSearchFineNumberAndFilterByPaymentType() {
		common.searchAndValidateResult("fine number", "12345");
		common.selectSecondFilterButton("ECE"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validatePaymentFilterList("ECE");
	}

	@Test
	public void TC_014_shouldSearchFineNumberAndFilterByPaymentTypeAndDepartment() {
		common.searchAndValidateResult("fine number", "12345");
		common.selectSecondFilterButton("ECE"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validatePaymentFilterList("ECE");
		common.selectFilterButton("Online"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Online");
	}

	@Test
	public void TC_015_shouldSearchCRMReferenceAndFilterByDeparment() {

		common.selectFilterByText("CRM Reference");
		common.searchAndValidateResult("crm reference", "12345");
		common.selectFilterButton("Online"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Online");
	}

	@Test
	public void TC_016_shouldSearchCRMReferenceAndFilterByPaymentType() {
		common.selectFilterByText("CRM Reference");
		common.searchAndValidateResult("crm reference", "12345");
		common.selectSecondFilterButton("ECE"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validatePaymentFilterList("ECE");
	}

	@Test
	public void TC_017_shouldSearchCRMReferenceAndFilterByPaymentTypeAndDepartment() {
		common.selectFilterByText("CRM Reference");
		common.searchAndValidateResult("crm reference", "12345");
		common.selectSecondFilterButton("ECE"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validatePaymentFilterList("ECE");
		common.selectFilterButton("Online"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Online");
	}

	@Test
	public void TC_018_shouldSearchPhoneNumberAndFilterByDeparment() {

		common.selectFilterByText("Phone Number");
		common.searchAndValidateResult("phone number", "1234567890");
		common.selectFilterButton("Online"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Online");
	}

	@Test
	public void TC_019_shouldSearchPhoneNumberAndFilterByPaymentType() {
		common.selectFilterByText("Phone Number");
		common.searchAndValidateResult("phone number", "1234567890");
		common.selectSecondFilterButton("ECE"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validatePaymentFilterList("ECE");
	}

	@Test
	public void TC_020_shouldSearchCRMPhoneNumberAndFilterByPaymentTypeAndDepartment() {
		common.selectFilterByText("Phone Number");
		common.searchAndValidateResult("phone number", "1234567890");
		common.selectSecondFilterButton("ECE"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validatePaymentFilterList("ECE");
		common.selectFilterButton("Online"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Online");
	}

	@Test
	public void TC_021_shouldSearchEmailAndFilterByDeparment() {

		common.selectFilterByText("Email");
		common.searchAndValidateResult("email", "azhar@gmail.com");
		common.selectFilterButton("Online"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Online");
	}

	@Test
	public void TC_022_shouldSearchEmailAndFilterByPaymentType() {
		common.selectFilterByText("Email");
		common.searchAndValidateResult("email", "azhar@gmail.com");
		common.selectSecondFilterButton("ECE"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validatePaymentFilterList("ECE");
	}

	@Test
	public void TC_023_shouldSearchEmailAndFilterByPaymentTypeAndDepartment() {
		common.selectFilterByText("Email");
		common.searchAndValidateResult("email", "azhar@gmail.com");
		common.selectSecondFilterButton("ECE"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validatePaymentFilterList("ECE");
		common.selectFilterButton("Online"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Online");
	}

	// ----------------- SEARCH + FILTER VALIDATION (Multiple Filters)

	@Test
	public void TC_024_shouldSearchFinePhoneEmailAndFilterByPayment() {
		common.searchAndValidateResult("fine number", "12345");
		common.selectFilterByText("Phone Number");
		common.searchAndValidateResult("phone number", "1234567890");
		common.selectFilterByText("Email");
		common.searchAndValidateResult("email", "azhar@gmail.com");
		common.selectSecondFilterButton("ECE"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validatePaymentFilterList("ECE");

	}

	@Test
	public void TC_025_shouldSearchFinePhoneAndFilterByPaymentAndDepartment() {
		common.searchAndValidateResult("fine number", "12345");
		common.selectFilterByText("Phone Number");
		common.searchAndValidateResult("phone number", "1234567890");

		common.selectSecondFilterButton("ECE"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validatePaymentFilterList("ECE");
		common.selectFilterButton("Online"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Online");
	}

	// ----------------- PAGINATION VALIDATION -----------------
	@Test
	public void TC_026_shouldValidateFirstPagePaginationSuccessfully() {
		common.checkFirstPagePagination();
		dispute.validateTotalDisputesCount();
	}

	@Test
	public void TC_027_shouldValidateLastPagePaginationSuccessfully() throws InterruptedException {
		Thread.sleep(2000);
		common.checkLastPagePagination();
		dispute.validateTotalDisputesCount();
	}

	@Test
	public void TC_028_shouldValidateMiddlePagePaginationSuccessfully() throws InterruptedException {
		Thread.sleep(2000);
		dispute.checkMiddlePagesPagination();
	}

	@Test
	public void TC_029_validateObstacleCount() {
		// plate.printAndValidatePlateCounts();
		// plate.printAndValidateTableCounts();
		// plate.validateTableRowsAgainstTotalPlates();
	}

}
