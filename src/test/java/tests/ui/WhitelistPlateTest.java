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
		plate.startDateRange(28);
		plate.startEndRange(30);
		plate.submitPlate();
		plate.validatePlateError("Plate number created sucessfully");

		plate.validatePlateAdded();
		plate.validatePlateSource("Ajman");
		plate.validatePlateType("Private");
		plate.validatePlateColor("White");
		plate.validatePlateStatus("Active");
	//	plate.validateFromDate("2025-08-27");
	//	plate.validateToDate("2025-08-29");
		plate.validateStartDate();
		plate.validateEndDate();
	}
	
	@Test
	public void TC_002_shouldCreatePlateWithInactiveStatusSuccessfully() {
		plate.navigateToAddNewPlatePage();
		plate.enterPlateNumber();
		plate.selectPlateSourceByText("Sharjah");  // Dubai , Ajman ,Sharjah,Ras Al Khaimah,Fujairah,Umm Al Quwain
		plate.selectPlateTypeByText("Taxi");  //Commercial , Motorcycle ,Taxi,Private
		plate.selectPlateColorByText("Black");  //White,Black,Red,Blue,Orange,Green,Yellow,Purple
		plate.selectStatusByText("Inactive");   //Inactive ,  Active
		plate.selectExemptionByText("Diplomatic Vehicle"); //Diplomatic Vehicle  ,Government Vehicle ,Emergency Vehicle
		plate.startDateRange(28);
		plate.startEndRange(30);
		plate.submitPlate();
		plate.validatePlateError("Plate number created sucessfully");


		plate.validatePlateAdded();
		plate.validateStartDate();
		plate.validateEndDate();
	}

	@Test
	public void TC_003_shouldCreatePlateWithTodaysDateSuccessfully() {
		plate.navigateToAddNewPlatePage();
		plate.enterPlateNumber();
		plate.selectPlateSourceByText("Online");
		plate.selectPlateTypeByText("Private Car");
		plate.selectPlateColorByText("White");
		plate.selectStatusByText("Active");
		plate.selectExemptionByText("Government Vehicle");
		plate.startDateRange(20);
		plate.startEndRange(20);
		plate.submitPlate();
		plate.validatePlateError("Plate number created sucessfully");

		plate.validatePlateAdded();
		plate.validateStartDate();
		plate.validateEndDate();
	}

	@Test
	public void TC_004_shouldNotAllowDuplicatePlate() {
		plate.navigateToAddNewPlatePage();
		plate.enterDuplicatePlateNumber("TN223A4BCD");
		plate.selectPlateSourceByText("Online");
		plate.selectPlateTypeByText("Private Car");
		plate.selectPlateColorByText("White");
		plate.selectStatusByText("Active");
		plate.selectExemptionByText("Government Vehicle");
		plate.startDateRange(24);
		plate.startEndRange(26);
		plate.submitPlate();

		plate.validatePlateError("Plate number already exists for the specified period");
	}

	// ----------------- SEARCH TESTS -----------------

	@Test
	public void TC_005_shouldFindPlateWhenSearchingByValidPlateNumber() {
		plate.searchWhitelistAndValidateResult("SJ1234", null);
	}

	@Test
	public void TC_006_shouldNotFindPlateWhenSearchingByInvalidPlateNumber() {
		plate.searchWhitelistAndValidateNoData("Commercial", "No data");
	}

	@Test
	public void TC_007_shouldFindPlatesWhenSearchingByPartialPlateNumber() {
		plate.searchWhitelistWithPartialTextAndValidateResult("1234", null);
	}

	@Test
	public void TC_008_shouldFindPlatesWhenSearchingByValidPlateSource() {
		plate.selectFilterByIndex(1);
		plate.searchWhitelistAndValidateResult(null, "Abu Dhabi");
	}

	@Test
	public void TC_009_shouldNotFindPlatesWhenSearchingByInvalidPlateSource() {
		plate.selectFilterByIndex(1);
		plate.searchWhitelistAndValidateNoData("Commercial", "No data");
	}

	@Test
	public void TC_010_shouldFindPlatesWhenSearchingByPartialPlateSource() {
		plate.selectFilterByIndex(1);
		plate.searchWhitelistWithPartialTextAndValidateResult(null, "Abu");
	}

	@Test
	public void TC_011_shouldFindPlatesWithinValidDateRange() {
		plate.searchStartDate(25);
		plate.searchEndDate(25);
		plate.validateStartDate();
		plate.validateEndDate();
	}

	@Test
	public void TC_012_shouldFindPlatesWhenStartDateEqualsEndDate() {
		plate.searchStartDate(24);
		plate.searchEndDate(24);
		plate.validateStartDate();
		plate.validateEndDate();
	}

	@Test
	public void TC_013_shouldFindPlateWhenSearchingByPlateNumberAndSource() {
		plate.searchWhitelistAndValidateResult("SJ1234", null);
		plate.selectFilterByIndex(1);
		plate.searchWhitelistAndValidateResult(null, "Abu Dhabi");
	}

	@Test
	public void TC_014_shouldNotFindPlateWhenPlateNumberAndSourceDoNotMatch() {
		plate.searchWhitelistAndValidateResult("SJ1234", null);
		plate.selectFilterByIndex(1);
		plate.searchWhitelistAndValidateNoData("Commercial", "No data");
	}

	@Test
	public void TC_015_shouldFindPlateWhenSearchingByPlateNumberAndDateRange() {
		plate.searchWhitelistWithPartialTextAndValidateResult("1234", null);
		plate.searchStartDate(23);
		plate.searchEndDate(25);
		plate.validateStartDate();
		plate.validateEndDate();
	}

	@Test
	public void TC_016_shouldFindPlatesWhenSearchingBySourceAndDateRange() {
		plate.selectFilterByIndex(1);
		plate.searchWhitelistWithPartialTextAndValidateResult(null, "Abu");
		plate.searchStartDate(23);
		plate.searchEndDate(25);
		plate.validateStartDate();
		plate.validateEndDate();
	}

	@Test
	public void TC_017_shouldFindPlateWhenSearchingByPlateNumberSourceAndDateRange() {
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
	public void TC_018_validatePlateCount() {
		plate.printAndValidatePlateCounts();
		plate.printAndValidateTableCounts();
		plate.validateTableRowsAgainstTotalPlates();
	}

	@Test
	public void TC_019_shouldEditPlateSuccessfully() {
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
	public void TC_020_TC_viewPlateDetails() {
		plate.clickThreeDotAndValidateMenuList();
		plate.navigateToViewPlatePage();
		plate.getPlateDetails();
	}

	// ----------------- PAGINAATION VALIDATION -----------------

	@Test
	public void TC_021_shouldValidateFirstPagePagination() {
		plate.checkFirstPagePagination();
	}

	@Test
	public void TC_022_shouldValidateMiddlePagesPagination() {
		plate.checkMiddlePagesPagination();
	}

	@Test
	public void TC_023_shouldValidateLastPagePagination() {
		plate.checkLastPagePagination();
	}

	// ----------------- FILTER & SORT VALIDATION -----------------

	@Test
	public void TC_024_shouldFilterPlatesBySourceSuccessfully()  {
		plate.selectPlateSource("PLATE_SOURCE 2");
		plate.selectFilter();
		plate.validateFilterList("PLATE_SOURCE 2");
		
	}
}