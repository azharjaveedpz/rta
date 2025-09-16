package tests.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ui.CommonCode;
import pages.ui.DashboardPage;
import pages.ui.DisputesPage;
import pages.ui.InspectionManagementPage;
import pages.ui.LoginPage;

public class DisputesTest extends BaseTest {
	private DashboardPage dashboard;
	private LoginPage login;

	private CommonCode common;
	private DisputesPage dispute;
	private InspectionManagementPage inspection;

	@BeforeMethod
	public void setUpPages() {
		dashboard = new DashboardPage(driver, prop, test);

		login = new LoginPage(driver, prop, test);
		common = new CommonCode(driver, prop, test);
		dispute = new DisputesPage(driver, prop, test);
		inspection = new InspectionManagementPage(driver, prop, test);

		// login.login("azhar@gmail.com", "Azhar@123");
		// login.dismissChromePasswordAlert();
		// dashboard.dashboardrMessageValidation();
		dashboard.clickDisputeManagement();
		dashboard.disputeManagementPageMessageValidation();

	}

	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	@Test
	public void TC_001_shouldCreateNewDisputeWhenValidDetailsProvided() {

		dispute.navigateToAddDisputePage();
		dispute.enterFineNumber("1544250911163122");
		dispute.enterReason();
		dispute.enterCRM();
		dispute.enterEmail();
		dispute.enterPhone();
		dispute.enterAddress();
		dispute.selectDepartment("Parking");
		dispute.selectPaymentType("Online");
		dispute.enterSourceUser();
		common.disputesDate(15);
		common.submit();
		common.validateToastMessage("Dispute added successfully!");
		dispute.validateDisputeAdded();
		dispute.validateFineNumber();
		dispute.validatePaymentType("Online");
		dispute.validatePhoneNumber();
		dispute.validateDisputeStatus();
		/*
		 * dashboard.clickInspections(); dashboard.clickInspectionManagement();
		 * common.searchAndValidateResult("plates number", "37646");
		 * common.selectThirdFilterButton("Dispute"); // Dispute ,Active,Paid,Cancel
		 * Requested,Cancel Request Approved,Dispute Approved common.selectOK();
		 * inspection.validateStatusFilterList("Dispute");
		 */

	}

	@Test
	public void TC_002_shouldDisplayDisputeDetailsInViewPage() {
		dispute.clickThreeDotAndValidateMenuList();
		common.view();
		dispute.navigateToViewDisputePage();
		dispute.getDisputeDetails();
	}

	@Test
	public void TC_003_shouldUpdateDisputeDetailsWhenEdited() throws InterruptedException {
		dispute.clickThreeDotAndValidateMenuList();
		common.edit();
		dispute.navigateToEditDisputePage();
		dispute.clearAllFields();
		dispute.enterCRM();
		dispute.enterSourceUser();
		common.disputesDate(15);
		Thread.sleep(2000);
		// dispute.selectDepartment("Traffic");
		// dispute.selectPaymentType("Cash");
		common.update();
		common.validateToastMessage("Dispute updated successfully!");
		dispute.validateDisputeAdded();
		// dispute.validateDepartment("Traffic");
		// dispute.validatePaymentType("Cash");
	}

	@Test
	public void TC_004_shouldRejectDisputeCreationWhenFineNumberAlreadyExists() {

		dispute.navigateToAddDisputePage();
		dispute.enterFineNumber("1544250904225348");
		dispute.enterReason();
		dispute.enterCRM();
		dispute.enterEmail();
		dispute.enterPhone();
		dispute.enterAddress();
		dispute.selectDepartment("Parking");
		dispute.selectPaymentType("Online");
		dispute.enterSourceUser();
		common.disputesDate(15);
		common.submit();
		common.validateToastMessage("Dispute for Fine Id 1544250904225348 already exists");

	}

	@Test
	public void TC_005_shouldShowErrorWhenFineNumberIsInvalid() {

		dispute.navigateToAddDisputePage();
		dispute.enterFineNumber("15442");
		dispute.enterReason();
		dispute.enterCRM();
		dispute.enterEmail();
		dispute.enterPhone();
		dispute.enterAddress();
		dispute.selectDepartment("Parking");
		dispute.selectPaymentType("Online");
		dispute.enterSourceUser();
		common.disputesDate();
		common.submit();
		common.validateToastMessage("Fine id not found");

	}

	@Test
	public void TC_006_shouldNotAllowDisputeCreationWithFutureDate() {

		dispute.navigateToAddDisputePage();
		dispute.enterFineNumber("1544250908170621");
		dispute.enterReason();
		dispute.enterCRM();
		dispute.enterEmail();
		dispute.enterPhone();
		dispute.enterAddress();
		dispute.selectDepartment("Parking");
		dispute.selectPaymentType("Online");
		dispute.enterSourceUser();
		common.disputesDate(20);
		common.validateDisputeCalendar();

	}

	@Test
	public void TC_007_shouldNotAllowDisputeCreationWithPastDate() {

		dispute.navigateToAddDisputePage();
		dispute.enterFineNumber("1544250908170621");
		dispute.enterReason();
		dispute.enterCRM();
		dispute.enterEmail();
		dispute.enterPhone();
		dispute.enterAddress();
		dispute.selectDepartment("Parking");
		dispute.selectPaymentType("Online");
		dispute.enterSourceUser();
		common.disputesDate(12);
		common.validateDisputeCalendar();

	}
	// ----------------- SEARCH TESTS -----------------

	@Test
	public void TC_008_shouldReturnResultsWhenSearchingWithValidFineNumber() {
		common.searchAndValidateResult("fine number", "15442");
	}

	@Test
	public void TC_009_shouldReturnResultsWhenSearchingWithValidCRMReference() throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterByText("CRM Reference");
		common.searchAndValidateResult("crm reference", "12");
	}

	@Test
	public void TC_010_shouldReturnResultsWhenSearchingWithValidPhoneNumber() throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterByText("Phone Number");
		common.searchAndValidateResult("phone number", "123");
	}

	@Test
	public void TC_011_shouldReturnResultsWhenSearchingWithValidEmail() throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterByText("Email");
		common.searchAndValidateResult("email", "azhar");
	}

	@Test
	public void TC_012_shouldShowNoResultsMessageWhenSearchDataIsInvalid() {
		common.searchWhitelistAndValidateNoData("AZHAR", "No data");
	}

	// ----------------- FILTER VALIDATION ----------------

	@Test
	public void TC_013_shouldFilterDisputesByPaymentType() throws InterruptedException {

		common.selectFilterButton("Online"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validatePaymentFilterList("Online");

	}

	@Test
	public void TC_014_shouldFilterDisputesByPendingStatus() throws InterruptedException {

		common.selectSecondFilterButton("Pending"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Pending");

	}

	@Test
	public void TC_015_shouldFilterDisputesByApprovedStatus() throws InterruptedException {

		common.selectSecondFilterButton("Approved"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Approved");

	}

	@Test
	public void TC_016_shouldFilterDisputesByRejectedStatus() throws InterruptedException {

		common.selectSecondFilterButton("Rejected"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Rejected");

	}

	// ----------------- SEARCH + FILTER VALIDATION (Single Filter)

	@Test
	public void TC_017_shouldSearchByFineNumberAndFilterByStatus() {
		common.searchAndValidateResult("fine number", "12345");
		common.selectSecondFilterButton("Approved"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Approved");
	}

	@Test
	public void TC_018_shouldSearchByFineNumberAndFilterByPaymentType() {
		common.searchAndValidateResult("fine number", "12345");
		common.selectFilterButton("Online"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validatePaymentFilterList("Online");
	}

	@Test
	public void TC_019_shouldSearchByFineNumberAndApplyMultipleFilters() {
		common.searchAndValidateResult("fine number", "12345");
		common.selectFilterButton("Online"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validatePaymentFilterList("Online");
		common.selectSecondFilterButton("Approved"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Approved");
	}

	@Test
	public void TC_020_shouldSearchByCRMReferenceAndFilterByStatus() {

		common.selectFilterByText("CRM Reference");
		common.searchAndValidateResult("crm reference", "12");
		common.selectSecondFilterButton("Pending"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Pending");
	}

	@Test
	public void TC_021_shouldSearchByCRMReferenceAndFilterByPaymentType() {
		common.selectFilterByText("CRM Reference");
		common.searchAndValidateResult("crm reference", "12");
		common.selectFilterButton("Cash"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validatePaymentFilterList("Cash");
	}

	@Test
	public void TC_022_shouldSearchByCRMReferenceAndApplyMultipleFilters() {
		common.selectFilterByText("CRM Reference");
		common.searchAndValidateResult("crm reference", "12");
		common.selectSecondFilterButton("Pending"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Pending");
		common.selectFilterButton("Cash"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectFilterTwoOK();
		dispute.validatePaymentFilterList("Cash");
	}

	@Test
	public void TC_023shouldSearchByPhoneNumberAndFilterByPaymentType() {

		common.selectFilterByText("Phone Number");
		common.searchAndValidateResult("phone number", "12");
		common.selectFilterButton("Online"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validatePaymentFilterList("Online");
	}

	@Test
	public void TC_024_shouldSearchByPhoneNumberAndFilterByStatus() {

		common.selectFilterByText("Phone Number");
		common.searchAndValidateResult("phone number", "12");
		common.selectSecondFilterButton("Pending"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Pending");
	}

	@Test
	public void TC_025_shouldSearchByPhoneNumberAndApplyMultipleFilters() {
		common.selectFilterByText("Phone Number");
		common.searchAndValidateResult("phone number", "12");
		common.selectSecondFilterButton("Pending"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Pending");
		common.selectFilterButton("Online"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectFilterTwoOK();
		dispute.validatePaymentFilterList("Online");
	}

	// ----------------- SEARCH + FILTER VALIDATION (Multiple Filters)

	@Test
	public void TC_026_shouldSearchByFineAndPhoneAndApplyMultipleFilters() {
		common.searchAndValidateResult("fine number", "12345");
		common.selectFilterByText("Phone Number");
		common.searchAndValidateResult("phone number", "12");
		common.selectSecondFilterButton("Pending"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		dispute.validateDepartmentFilterList("Pending");
		common.selectFilterButton("Cash"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectFilterTwoOK();
		dispute.validatePaymentFilterList("Cash");
	}

	// ----------------- PAGINATION VALIDATION -----------------
	@Test
	public void TC_027_shouldDisplayCorrectResultsOnFirstPage() {
		common.checkFirstPagePagination();
		dispute.validateTotalDisputesCount();
	}

	@Test
	public void TC_028_shouldDisplayCorrectResultsOnLastPage() throws InterruptedException {
		Thread.sleep(2000);
		common.checkLastPagePagination();
		dispute.validateTotalDisputesCount();
	}

	@Test
	public void TC_029_shouldDisplayCorrectResultsOnMiddlePage() throws InterruptedException {
		Thread.sleep(2000);
		dispute.checkMiddlePagesPagination();
	}

	@Test
	public void TC_030_shouldDisplayTotalDisputeCountCorrectly() {
		dispute.validateTotalDisputesCount();
	}

	@Test
	public void TC_031_ValidateErrorMessage() {
		dispute.navigateToAddDisputePage();
		common.submit();
		common.validateErrorMessage("Enter the fine number");
		common.validateErrorMessage("Select the department");
		common.validateErrorMessage("Select the payment type");
		common.validateErrorMessage("Enter the dispute reason");
		common.validateErrorMessage("Enter the CRM reference");
		common.validateErrorMessage("Enter your email address");
		common.validateErrorMessage("Enter your phone number");
		common.validateErrorMessage("Enter source user");
		common.validateErrorMessage("Select date");
		common.validateErrorMessage("Enter your address");

	}

}
