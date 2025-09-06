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
		Thread.sleep(1500);
		dashboard.clickConfiguration();
		dashboard.clickWhitelistManagement();

	}

	// ----------------- CREATE PLATE TESTS -----------------

	@Test
	public void TC_001_shouldCreatePlateSuccessfully() {
		plate.navigateToAddNewPlatePage();
		plate.enterPlateNumber();
		plate.selectPlateSourceByText("Ajman");
		plate.selectPlateTypeByText("Private");
		plate.selectPlateColorByText("White");
		plate.selectStatusByText("Active");
		plate.selectExemptionByText("Government Vehicle");
		plate.startDateRange(29);
		plate.startEndRange(30);
		common.submit();
		common.validateToastMessage("Plate added successfully!");

		plate.validatePlateAdded();
		plate.validatePlateSource("Ajman");
		plate.validatePlateType("Private");
		plate.validatePlateColor("White");
		plate.validatePlateStatus("Active");
		plate.validateFromDate("2025-09-29");
		plate.validateToDate("2025-09-30");
		// plate.validateStartDate();
		// plate.validateEndDate();
	}

	@Test
	public void TC_002_shouldCreatePlateWithInactiveStatusSuccessfully() {
		plate.navigateToAddNewPlatePage();
		plate.enterPlateNumber();
		plate.selectPlateSourceByText("Sharjah"); // Dubai , Ajman ,Sharjah,Ras Al Khaimah,Fujairah,Umm Al Quwain
		plate.selectPlateTypeByText("Taxi"); // Commercial , Motorcycle ,Taxi,Private
		plate.selectPlateColorByText("Black"); // White,Black,Red,Blue,Orange,Green,Yellow,Purple
		plate.selectStatusByText("Inactive"); // Inactive , Active
		plate.selectExemptionByText("Diplomatic Vehicle"); // Diplomatic Vehicle ,Government Vehicle ,Emergency Vehicle
		plate.startDateRange(29);
		plate.startEndRange(30);
		common.submit();
		common.validateToastMessage("Plate added successfully!");

		plate.validatePlateAdded();
		plate.validatePlateSource("Sharjah");
		plate.validatePlateType("Taxi");
		plate.validatePlateColor("Black");
		plate.validatePlateStatus("Inactive");
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
		plate.selectStatusByText("Active");
		plate.selectExemptionByText("Government Vehicle");
		plate.startDateRange(28);
		plate.startEndRange(28);
		common.submit();
		common.validateToastMessage("Plate added successfully!");

		plate.validatePlateAdded();
		plate.validateFromDate("2025-09-29");
		plate.validateToDate("2025-09-30");
	}

	@Test
	public void TC_004_shouldNotAllowDuplicatePlate() {
		plate.navigateToAddNewPlatePage();
		plate.enterDuplicatePlateNumber("IU 53478 JZI");
		plate.selectPlateSourceByText("Ajman");
		plate.selectPlateTypeByText("Private");
		plate.selectPlateColorByText("White");
		plate.selectStatusByText("Active");
		plate.selectExemptionByText("Government Vehicle");
		plate.startDateRange(29);
		plate.startEndRange(30);
		common.submit();
		common.validateToastMessage("Plate number already exists for the specified period");
	}

	@Test
	public void TC_001_shouldCreatePlateSuccessfullyWithTrueByLaw() {
		plate.navigateToAddNewPlatePage();
		plate.enterPlateNumber();
		plate.selectPlateSourceByText("Ajman");
		plate.selectPlateTypeByText("Private");
		plate.selectPlateColorByText("White");
		plate.selectStatusByText("Active");
		plate.selectExemptionByText("Government Vehicle");
		plate.selectByLawByText("True");
		plate.startDateRange(29);
		plate.startEndRange(30);
		common.submit();
		common.validateToastMessage("Plate added successfully!");

		// plate.validatePlateAdded();
		plate.validateByLawStatus("True");

	}

	@Test
	public void TC_001_shouldCreatePlateSuccessfullyWithFalseByLaw() {
		plate.navigateToAddNewPlatePage();
		plate.enterPlateNumber();
		plate.selectPlateSourceByText("Ajman");
		plate.selectPlateTypeByText("Private");
		plate.selectPlateColorByText("White");
		plate.selectStatusByText("Active");
		plate.selectExemptionByText("Government Vehicle");
		plate.selectByLawByText("False");
		plate.startDateRange(29);
		plate.startEndRange(30);
		common.submit();
		common.validateToastMessage("Plate added successfully!");

		// plate.validatePlateAdded();
		plate.validateByLawStatus("False");

	}
	// ----------------- SEARCH TESTS -----------------

	@Test
	public void TC_005_shouldFindPlateWhenSearchingByValidPlateNumber() {
		common.searchAndValidateResult("plate number", "KK 49389 NNB");
	}

	@Test
	public void TC_006_shouldFindPlateWithLowerCaseData() {
		common.searchAndValidateResult("plate number", "kk 49389 nnb");
	}

	@Test
	public void TC_007_shouldFindPlateeWithCamelCase() {
		common.searchAndValidateResult("plate number", "kK 49389 nnB");
	}

	@Test
	public void TC_008_shouldNotFindPlateWhenSearchingByInvalidPlateNumber() {
		common.searchWhitelistAndValidateNoData("AZHAR", "No data");
	}

	@Test
	public void TC_009_shouldFindPlatesWithinValidDateRange() {
		plate.searchStartDate(24);
		plate.searchEndDate(28);
		plate.validateFromDate("2025-09-24");
		plate.validateToDate("2025-09-28");
	}

	@Test
	public void TC_010_shouldFindPlatesWhenStartDateEqualsEndDate() {
		plate.searchStartDate(24);
		plate.searchEndDate(24);
		plate.validateFromDate("2025-09-24");
		plate.validateToDate("2025-09-28");
	}

	@Test
	public void TC_011_shouldFindPlateWhenSearchingByPlateNumberAndDateRange() {
		common.searchAndValidateResult("plate number", "KK 49389 NNB");
		plate.searchStartDate(24);
		plate.searchEndDate(25);
		plate.validateFromDate("2025-09-24");
		plate.validateToDate("2025-09-25");
	}

	// ----------------- VALIDATION & EDIT -----------------

	@Test
	public void TC_012_validatePlateCount() {
		plate.printAndValidatePlateCounts();
		plate.printAndValidateTableCounts();
		plate.validateTableRowsAgainstTotalPlates();
	}

	@Test
	public void TC_013_shouldEditPlateSuccessfully() {
		plate.clickThreeDotAndValidateMenuList();
		common.edit();
		plate.navigateToEditPlatePage();
		plate.clearAllFields();
		plate.enterPlateNumber();
		plate.startDateRange(29);
		plate.startEndRange(30);
		common.update();
		common.validateToastMessage("Plate updated successfully!");
		plate.validatePlateAdded();
		plate.validateFromDate("2025-09-29");
		plate.validateToDate("2025-09-30");
	}

	// ----------------- VALIDATION & VIEW -----------------

	@Test
	public void TC_014_TC_viewPlateDetails() {
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
	public void TC_016_shouldValidateMiddlePagesPagination() {
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
	public void TC_018_shouldFilterPlatesBySourceSuccessfully() throws InterruptedException {

		common.selectFilterButton("Sharjah"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		plate.validateFilterList("Sharjah");

	}

	@Test
	public void TC_019_shouldFilterPlatesByTypeSuccessfully() throws InterruptedException {

		common.selectSecondFilterButton("Private"); // Reported, Removed
		common.selectOK();
		plate.validatePlateTypeFilterList("Private");

	}

	@Test
	public void TC_020_shouldFilterPlatesByStatusSuccessfully() throws InterruptedException {

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
	public void TC_021_shouldSearchPllateNumberAndFilterByPlateSource() {
		common.searchAndValidateResult("plate number", "KK 49389 NNB");
		common.selectFilterButton("Ajman"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		plate.validateFilterList("Ajman");

	}

	@Test
	public void TC_022_shouldSearchPllateNumberAndFilterByPlateType() {
		common.searchAndValidateResult("plate number", "KK 49389 NNB");
		common.selectSecondFilterButton("Private"); // Reported, Removed
		common.selectOK();
		plate.validatePlateTypeFilterList("Private");

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
		common.selectSecondFilterButton("Private"); // Reported, Removed
		common.selectOK();
		plate.validatePlateTypeFilterList("Private");
		common.selectFourthFilterButton("ACTIVE"); // Reported, Removed
		common.selectFilterTwoOK();
		plate.validateStatuseFilterList("ACTIVE");
	}

	@Test
	public void TC_025_shouldSearchPlateNumberAndFilterByPlateTypeANDSourceAndStatus() {
		common.searchAndValidateResult("plate number", "KK 49389 NNB");
		common.selectFilterButton("Ajman"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();

		common.selectSecondFilterButton("Private"); // Reported, Removed
		common.selectFilterTwoOK();

		common.selectFourthFilterButton("ACTIVE"); // Reported, Removed
		common.selectFilterThreeOK();

		plate.validateFilterList("Ajman");
		plate.validatePlateTypeFilterList("Private");
		plate.validateStatuseFilterList("ACTIVE");
	}

	@Test
	public void TC_026_shouldSearchPlateNumberAndFilterByTypeAndStatus() {
		common.searchAndValidateResult("plate number", "KK 49389 NNB");
		common.selectFilterButton("Ajman"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		plate.validateFilterList("Ajman");
		common.selectFourthFilterButton("ACTIVE"); // Reported, Removed
		common.selectFilterTwoOK();
		plate.validateStatuseFilterList("ACTIVE");
	}

	@Test
	public void TC_027_shouldFindPlatesWhenSearchingByPartialPlateNumber() {
		plate.searchWhitelistWithPartialTextAndValidateResult("49389", null);
	}
	
	@Test
	public void TC_028_validateActiveAndInActivePlateCount() {
		plate.printAndValidateActiveAndInActiveCounts();
		
	}
}