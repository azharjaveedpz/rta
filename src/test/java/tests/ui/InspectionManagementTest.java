package tests.ui;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ui.CommonCode;
import pages.ui.DashboardPage;
import pages.ui.InspectionManagementPage;
import pages.ui.LoginPage;
import pages.ui.PledgesPage;

public class InspectionManagementTest extends BaseTest {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	private DashboardPage dashboard;
	private LoginPage login;
	private CommonCode common;
	private PledgesPage pledge;
	private InspectionManagementPage inspection;

	@BeforeMethod
	public void setUpPages() {
		dashboard = new DashboardPage(driver, prop, test);
		login = new LoginPage(driver, prop, test);
		common = new CommonCode(driver, prop, test);
		pledge = new PledgesPage(driver, prop, test);
		inspection = new InspectionManagementPage(driver, prop, test);

		// login.login("azhar@gmail.com", "Azhar@123");
		// login.dismissChromePasswordAlert();
		// dashboard.dashboardrMessageValidation();

		dashboard.clickInspections();
		dashboard.clickInspectionManagement();

	}

	@Test
	public void TC_001_shouldFindPlateByValidPlateNumber() {
		common.searchAndValidateResult("plates number", "46");
	}

	@Test
	public void TC_002_shouldSearchPlateWithInvalidDataAndShowNoResults() {
		common.searchWhitelistAndValidateNoData("AZHAR", "No data");
	}

	@Test
	public void TC_003_shouldSearchTradeLicenseWithValidDataAndDisplayResults() throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterByText("tradeLicenseNumber");
		common.searchAndValidateResult("license number", "ysh");
	}

	@Test
	public void TC_004_shouldSearchTradeLicenseWithCaseSensitiveDataAndDisplayResults() throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterByText("tradeLicenseNumber");
		common.searchAndValidateResult("license number", "YsH");
	}

	@Test
	public void TC_005_shouldFilterByInspectionType() throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterButton("Illegal Parking"); // Illegal Parking ,Parking Inspection,Reserved
														// Parking,Residential Area,Towing,Prohibited Parking
		common.selectOK();
		inspection.validateInspectionFilterList("Illegal Parking");

	}

	@Test
	public void TC_006_shouldFilterByFineType() throws InterruptedException {
		Thread.sleep(2000);
		common.selectSecondFilterButton("Fine"); // Fine ,Warning,Routine,
		common.selectOK();
		inspection.validateFineFilterList("Fine");

	}

	@Test
	public void TC_007_shouldFilterByInspectionStatus() throws InterruptedException {
		Thread.sleep(2000);
		common.selectThirdFilterButton("Dispute"); // Dispute ,Active,Paid,Cancel Requested,Cancel Request
													// Approved,Dispute Approved
		common.selectOK();
		inspection.validateStatusFilterList("Dispute");

	}

	@Test
	public void TC_008_shouldFilterByInspectionActiveStatus() throws InterruptedException {
		Thread.sleep(2000);
		common.selectThirdFilterButton("Active"); // Dispute ,Active,Paid,Cancel Requested,Cancel Request
													// Approved,Dispute Approved
		common.selectOK();
		inspection.validateStatusFilterList("Active");

	}

	@Test
	public void TC_009_shouldFindPlateByValidPlateNumberWithInspectionTypeAndFineTypeAndInspectionStatus()
			throws InterruptedException {
		common.searchAndValidateResult("plates number", "4");
		Thread.sleep(2000);
		common.selectFilterButton("Illegal Parking"); // Illegal Parking ,Parking Inspection,Reserved
														// Parking,Residential Area,Towing,Prohibited Parking
		common.selectOK();
		inspection.validateInspectionFilterList("Illegal Parking");
		common.selectSecondFilterButton("Fine"); // Fine ,Warning,Routine,
		common.selectFilterTwoOK();
		inspection.validateFineFilterList("Fine");
		common.selectThirdFilterButton("Active"); // Dispute ,Active,Paid,Cancel Requested,Cancel Request
													// Approved,Dispute Approved
		common.selectFilterThreeOK();
		inspection.validateStatusFilterList("Active");

	}

	@Test
	public void TC_011_shouldSearchTradeLicenseWithWithInspectionTypeAndFineTypeAndInspectionStatus()
			throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterByText("tradeLicenseNumber");
		common.searchAndValidateResult("license number", "ysh");
		Thread.sleep(2000);
		common.selectFilterButton("Parking Inspection"); // Illegal Parking ,Parking Inspection,Reserved
															// Parking,Residential Area,Towing,Prohibited Parking
		common.selectOK();
		inspection.validateInspectionFilterList("Parking Inspection");
		common.selectSecondFilterButton("Fine"); // Fine ,Warning,Routine,
		common.selectFilterTwoOK();
		inspection.validateFineFilterList("Fine");
		common.selectThirdFilterButton("Dispute"); // Dispute ,Active,Paid,Cancel Requested,Cancel Request
													// Approved,Dispute Approved
		common.selectFilterThreeOK();
		inspection.validateStatusFilterList("Dispute");
	}

	@Test
	public void TC_012_shouldValidateFirstPagePagination() {
		common.checkFirstPagePagination();
		inspection.validateTotalFineCount();

	}

	@Test
	public void TC_013_shouldValidateLastPagePagination() throws InterruptedException {
		Thread.sleep(2000);
		common.checkLastPagePagination();
		inspection.validateTotalFineCount();

	}

	@Test
	public void TC_014_shouldValidateMiddlePagesPagination() throws InterruptedException {
		Thread.sleep(2000);
		inspection.checkMiddlePagesPagination();
	}

	@Test
	public void TC_015_shouldValidateFineCountsAgainstTableRows() {
		inspection.printAndValidateFineCounts();
		inspection.printAndValidateTableCounts();
		inspection.validateTableRowsAgainstTotalFines();
	}

	@Test
	public void TC_016_shouldViewTradeLicenseDetailsSuccessfully() throws InterruptedException {
		inspection.clickThreeDotAndValidateMenuList();
		common.view();
		inspection.navigateToViewInspectionPage();
		inspection.getFineDetails();
	}

	@Test
	public void TC_017_shouldViewPlateDetailsSuccessfully() throws InterruptedException {
		common.searchAndValidateResult("plates number", "46");
		inspection.clickThreeDotAndValidateMenuList();
		common.view();
		inspection.navigateToViewInspectionPage();
		inspection.getFineDetails();
		inspection.getFineDetailsOfPlateDetails();
	}

	@Test
	public void TC_018_shouldFindInspectionWithinValidDateRange() {
		common.searchStartDate(10);
		common.searchEndDate(10);
		inspection.validateFromDate("2025-09-10");
		inspection.validateToDate("2025-09-10");
	}
}
