package tests.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.ui.CommonCode;
import pages.ui.DashboardPage;
import pages.ui.LoginPage;
import pages.ui.WhitelistPage;

public class WhitelisTest extends BaseTest {

	private DashboardPage dashboard;
	private LoginPage login;
	private WhitelistPage plate;
	private CommonCode common;

	@BeforeMethod
	public void setUpPages() throws InterruptedException {
		dashboard = new DashboardPage(driver, prop, test);
		plate = new WhitelistPage(driver, prop, test);
		login = new LoginPage(driver, prop, test);
		common = new CommonCode(driver, prop, test);

		// login.login("azhar@gmail.com","Azhar@123");
		// dashboard.dashboardrMessageValidation();
		//Thread.sleep(1500);
		dashboard.clickConfiguration();
		dashboard.clickWhitelistManagement();

	}

	// ----------------- CREATE PLATE TESTS -----------------

	@Test
	public void TC_001_shouldCreatePlateSuccessfullyWithTrueByLaw() {
		plate.navigateToAddNewPlatePage();
		plate.enterPlateNumber();
		plate.selectPlateSourceByText("Ajman");
		plate.selectPlateTypeByText("Private");
		plate.selectPlateColorByText("White");
		plate.selectExemptionByText("Government Vehicle");
		plate.selectByLawByText("True");
		common.startDateRange(29);
		common.startEndRange(30);
		common.submit();
		common.validateToastMessage("Plate added successfully!");

		plate.validatePlateAdded();
		plate.validatePlateSource("Ajman");
		plate.validatePlateType("Private");
		plate.validatePlateColor("White");
		plate.validatePlateStatus("Active");
		plate.validateByLawStatus("True");

		plate.validateFromDate("2025-09-29");
		plate.validateToDate("2025-09-30");
		
	}

	@Test
	public void TC_002_shouldCreatePlateSuccessfullyWithFalseByLaw() {
		plate.navigateToAddNewPlatePage();
		plate.enterPlateNumber();
		plate.selectPlateSourceByText("Ajman");
		plate.selectPlateTypeByText("Private");
		plate.selectPlateColorByText("White");
		plate.selectExemptionByText("Government Vehicle");
		plate.selectByLawByText("False");
		common.startDateRange(29);
		common.startEndRange(30);
		common.submit();
		common.validateToastMessage("Plate added successfully!");

		plate.validatePlateAdded();
		plate.validatePlateSource("Ajman");
		plate.validatePlateType("Private");
		plate.validatePlateColor("White");
		plate.validatePlateStatus("Active");
		plate.validateByLawStatus("False");

		plate.validateFromDate("2025-09-29");
		plate.validateToDate("2025-09-30");
		
	}

	@Test
	public void TC_003_shouldCreatePlateWithTodaysDateSuccessfully() {
		plate.navigateToAddNewPlatePage();
		plate.enterPlateNumber();
		plate.selectPlateSourceByText("Ajman");
		plate.selectPlateTypeByText("Private");
		plate.selectPlateColorByText("White");
		plate.selectExemptionByText("Government Vehicle");
		plate.selectByLawByText("True");
		common.startDateRange(12);
		common.startEndRange(12);
		common.submit();
		common.validateToastMessage("Plate added successfully!");

		plate.validatePlateAdded();
		plate.validateFromDate("2025-09-12");
		plate.validateToDate("2025-09-12");
	}

	@Test
	public void TC_004_shouldNotAllowDuplicatePlateCreation() {
		plate.navigateToAddNewPlatePage();
		plate.enterDuplicatePlateNumber("FU 43758 WSE");
		plate.selectPlateSourceByText("Ajman");
		plate.selectPlateTypeByText("Private");
		plate.selectPlateColorByText("White");
		plate.selectExemptionByText("Government Vehicle");
		plate.selectByLawByText("True");
		common.startDateRange(29);
		common.startEndRange(30);
		common.submit();
		common.validateToastMessage("Plate number already exists for the specified period");
	}
	@Test
	public void TC_005_shouldEditExistingPlateSuccessfully() {
		plate.clickThreeDotAndValidateMenuList();
		common.edit();
		plate.navigateToEditPlatePage();
		plate.clearAllFields();
		plate.enterPlateNumber();
		common.startDateRange(28);
		common.startEndRange(30);
		plate.selectStatusByText("InActive");
		plate.selectByLawByText("False");

		common.update();
		common.validateToastMessage("Plate updated successfully!");
		plate.validatePlateAdded();
		plate.validatePlateStatus("InActive");
		plate.validateByLawStatus("False");
		plate.validateFromDate("2025-09-28");
		plate.validateToDate("2025-09-30");
	}
	
	
	// ----------------- SEARCH TESTS -----------------

	@Test
	public void TC_006_shouldFindPlateByValidPlateNumber()  {
		common.searchAndValidateResult("plate number", "KK 49389 NNB");
	}

	@Test
	public void TC_007_shouldFindPlateByLowerCasePlateNumber() {
		common.searchAndValidateResult("plate number", "kk 49389 nnb");
	}

	@Test
	public void TC_008_shouldFindPlateByCamelCasePlateNumber() {
		common.searchAndValidateResult("plate number", "kK 49389 nnB");
	}

	@Test
	public void TC_009_shouldNotFindPlateByInvalidPlateNumber() {
		common.searchWhitelistAndValidateNoData("AZHAR", "No data");
	}

	@Test
	public void TC_010_shouldFindPlatesWithinValidDateRange() {
		common.searchStartDate(24);
		common.searchEndDate(28);
		plate.validateFromDate("2025-09-24");
		plate.validateToDate("2025-09-28");
	}

	@Test
	public void TC_011_shouldFindPlatesWhenStartDateEqualsEndDate() {
		common.searchStartDate(24);
		common.searchEndDate(24);
		plate.validateFromDate("2025-09-24");
		plate.validateToDate("2025-09-28");
	}

	@Test
	public void TC_012_shouldFindPlateByPlateNumberAndDateRange() {
		common.searchAndValidateResult("plate number", "KK 49389 NNB");
		common.searchStartDate(24);
		common.searchEndDate(25);
		plate.validateFromDate("2025-09-24");
		plate.validateToDate("2025-09-25");
	}

	// ----------------- VALIDATION & EDIT -----------------

	@Test
	public void TC_013_shouldValidatePlateCountsAgainstTableRows() {
		plate.printAndValidatePlateCounts();
		plate.printAndValidateTableCounts();
		plate.validateTableRowsAgainstTotalPlates();
	}

	

	// ----------------- VALIDATION & VIEW -----------------

	@Test
	public void TC_014_shouldViewPlateDetailsSuccessfully() {
		plate.clickThreeDotAndValidateMenuList();
		common.view();
		plate.navigateToViewPlatePage();
		plate.getPlateDetails();
	}

	// ----------------- PAGINAATION VALIDATION -----------------

	@Test
	public void TC_015_shouldValidateFirstPagePagination() {
		common.checkFirstPagePagination();
		plate.validateTotalPlatesCount();
	}

	@Test
	public void TC_016_shouldValidateMiddlePagesPagination() throws InterruptedException {
		Thread.sleep(2000);
		plate.checkMiddlePagesPagination();
	}

	@Test
	public void TC_017_shouldValidateLastPagePagination() throws InterruptedException {
		Thread.sleep(2000);
		common.checkLastPagePagination();
		plate.validateTotalPlatesCount();
	}

	// ----------------- FILTER & SORT VALIDATION -----------------

	@Test
	public void TC_018_shouldFilterPlatesBySource() throws InterruptedException {

		common.selectFilterButton("Sharjah"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		plate.validateFilterList("Sharjah");

	}

	@Test
	public void TC_019_shouldFilterPlatesByType() throws InterruptedException {

		common.selectSecondFilterButton("Private"); // Reported, Removed
		common.selectOK();
		plate.validatePlateTypeFilterList("Private");

	}

	@Test
	public void TC_020_shouldFilterPlatesByStatus() throws InterruptedException {

		common.selectFourthFilterButton("InActive"); // Reported, Removed
		common.selectOK();
		plate.validateStatuseFilterList("InActive");
		plate.printAndValidatePlateCounts();
		plate.printAndValidateTableCounts();
		plate.validateTableRowsAgainstTotalPlates();
		plate.printAndValidateActiveAndInActiveCounts();

	}

	// ----------------- SEARCH + FILTER VALIDATION (Single Filter)
	@Test
	public void TC_021_shouldSearchByPlateNumberAndFilterBySource() {
		common.searchAndValidateResult("plate number", "KK 49389 NNB");
		common.selectFilterButton("Ajman"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		plate.validateFilterList("Ajman");

	}

	@Test
	public void TC_022_shouldSearchByPlateNumberAndFilterByType() {
		common.searchAndValidateResult("plate number", "KK 49389 NNB");
		common.selectSecondFilterButton("Taxi"); // Reported, Removed
		common.selectOK();
		plate.validatePlateTypeFilterList("Taxi");

	}

	@Test
	public void TC_023_shouldSearchPllateNumberAndFilterByStatus() {
		common.searchAndValidateResult("plate number", "KK 49389 NNB");
		common.selectFourthFilterButton("InActive"); // Reported, Removed
		common.selectOK();
		plate.validateStatuseFilterList("InActive");
		plate.printAndValidatePlateCounts();
		plate.printAndValidateTableCounts();
		plate.validateTableRowsAgainstTotalPlates();

	}

	// ----------------- SEARCH + FILTER VALIDATION (Multiple Filters)

	@Test
	public void TC_024_shouldSearchPlateNumberAndFilterBySourceAndStatus() {
		common.searchAndValidateResult("plate number", "KK 49389 NNB");
		common.selectSecondFilterButton("Taxi"); // Reported, Removed
		common.selectOK();
		plate.validatePlateTypeFilterList("Taxi");
		common.selectFourthFilterButton("InActive"); // Reported, Removed
		common.selectFilterTwoOK();
		plate.validateStatuseFilterList("InActive");
	}

	@Test
	public void TC_025_shouldSearchPlateNumberAndFilterByPlateTypeANDSourceAndStatus() {
		common.searchAndValidateResult("plate number", "KK 49389 NNB");
		common.selectFilterButton("Ajman"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();

		common.selectSecondFilterButton("Taxi"); // Reported, Removed
		common.selectFilterTwoOK();

		common.selectFourthFilterButton("InActive"); // Reported, Removed
		common.selectFilterThreeOK();

		plate.validateFilterList("Ajman");
		plate.validatePlateTypeFilterList("Taxi");
		plate.validateStatuseFilterList("InActive");
	}

	@Test
	public void TC_026_shouldSearchPlateNumberAndFilterByTypeAndStatus() {
		common.searchAndValidateResult("plate number", "KK 49389 NNB");
		common.selectFilterButton("Ajman"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		plate.validateFilterList("Ajman");
		common.selectFourthFilterButton("InActive"); // Reported, Removed
		common.selectFilterTwoOK();
		plate.validateStatuseFilterList("InActive");
	}

	@Test
	public void TC_027_shouldFindPlatesWhenSearchingByPartialPlateNumber() {
		plate.searchWhitelistWithPartialTextAndValidateResult("49389", null);
	}
	
	@Test
	public void TC_028_shouldValidateActiveAndInactivePlateCounts() throws InterruptedException {
		Thread.sleep(3000);
		plate.printAndValidateActiveAndInActiveCounts();
		
	}
}