package tests.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ui.CommonCode;
import pages.ui.DashboardPage;
import pages.ui.ObstaclesPage;
import pages.ui.LoginPage;

import pages.ui.WhitelistPage;

public class ObstaclesTest extends BaseTest {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);
	private DashboardPage dashboard;
	private LoginPage login;
	private CommonCode common;
	private ObstaclesPage obstacle;

	@BeforeMethod
	public void setUpPages() throws InterruptedException {
		dashboard = new DashboardPage(driver, prop, test);
		login = new LoginPage(driver, prop, test);
		common = new CommonCode(driver, prop, test);
		obstacle = new ObstaclesPage(driver, prop, test);

		// login.login("azhar@gmail.com", "Azhar@123");
		// login.dismissChromePasswordAlert();
		// dashboard.dashboardrMessageValidation();
		Thread.sleep(1000);
		dashboard.clickConfiguration();
		dashboard.clickObstacle();

	}

	// ----------------- CREATE & VIEW -----------------
	@Test
	public void TC_001_createInspectionObstacle_shouldSucceedWithValidInputs() throws InterruptedException {

		dashboard.inspectionPageMessageValidation();
		obstacle.navigateToAddObstaclePage();
		Thread.sleep(1000);
		obstacle.enterDeviceAndReportedDetails();
		obstacle.enterComments();
		obstacle.uploadPhoto();
		obstacle.selectZone("West"); // North,South,East,West
		obstacle.selectArea("Commercial"); // Residential ,Commercial,Industrial
		obstacle.selectObstacle("Parked Vehicle"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.submit();
		Thread.sleep(2200);
		common.validateToastMessage("Inspection Obstacle added successfully!");
		obstacle.validateZone("West");
		obstacle.validateArea("Commercial");
		obstacle.validateObstacle("Parked Vehicle");
		obstacle.validateStatus("Reported");

	}

	@Test
	public void TC_002_viewInspectionObstacle_shouldDisplayCorrectDetails() {
		obstacle.clickThreeDotAndValidateMenuList();
		common.view();
		obstacle.navigateToViewPage();
		obstacle.getObstacleDetails();
	}

	// ----------------- SEARCH VALIDATION (Positive) -----------------
	@Test
	public void TC_003_searchByZone_shouldReturnMatchingResults_caseSensitive() {
		common.searchAndValidateResult("zone", "West");
	}

	@Test
	public void TC_004_searchByZone_shouldReturnMatchingResults_lowerCase() {
		common.searchAndValidateResult("zone", "west");
	}

	@Test
	public void TC_005_searchByZone_shouldReturnMatchingResults_camelCase() {
		common.searchAndValidateResult("zone", "wEsT");
	}

	@Test
	public void TC_006_searchByArea_shouldReturnMatchingResults_caseSensitive() throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterByText("Area");
		common.searchAndValidateResult("Area", "Commercial");// Residential,Commercial,Industrial
	}

	@Test
	public void TC_007_searchByArea_shouldReturnMatchingResults_upperCase() throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterByText("Area");
		common.searchAndValidateResult("Area", "COMMERCIAL");// Residential,Commercial,Industrial
	}

	@Test
	public void TC_008_searchByArea_shouldReturnMatchingResults_camelCase() throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterByText("Area");
		common.searchAndValidateResult("Area", "cOMmerCiAL");// Residential,Commercial,Industrial
	}

	// ----------------- SEARCH VALIDATION (Negative) -----------------
	@Test
	public void TC_009_searchByZone_withInvalidInput_shouldShowNoData() {
		common.searchWhitelistAndValidateNoData("AZHAR", "No data");
	}

	@Test
	public void TC_010_searchByArea_withInvalidInput_shouldShowNoData() throws InterruptedException {
		Thread.sleep(2000);
		common.selectFilterByText("Area");
		common.searchWhitelistAndValidateNoData("AZHAR", "No data");
	}

	// ----------------- PAGINATION VALIDATION -----------------
	@Test
	public void TC_011_pagination_firstPage_shouldDisplayCorrectData() {
		common.checkFirstPagePagination();
		obstacle.validateTotalObstacleCount();
	}

	@Test
	public void TC_012_pagination_lastPage_shouldDisplayCorrectData() throws InterruptedException {
		Thread.sleep(2000);
		common.checkLastPagePagination();
		obstacle.validateTotalObstacleCount();
	}

	@Test
	public void TC_013_pagination_middlePage_shouldDisplayCorrectData() throws InterruptedException {
		Thread.sleep(2000);
		obstacle.checkMiddlePagesPagination();
	}

	// ----------------- FILTER VALIDATION ----------------

	@Test
	public void TC_014_filterByObstacleType_shouldReturnCorrectResults() throws InterruptedException {

		common.selectFilterButton("Parked Vehicle"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		obstacle.validateObstacleFilterList("Parked Vehicle");

	}

	@Test
	public void TC_015_filterByStatus_shouldReturnCorrectResults() throws InterruptedException {

		common.selectSecondFilterButton("Reported"); // Reported, Removed
		common.selectOK();
		obstacle.validateStatuseFilterList("Reported");
		obstacle.printAndValidateReportedAndRemovedObstacle();

	}

	// ----------------- SEARCH + FILTER VALIDATION (Single Filter)
	// -----------------

	@Test
	public void TC_016_searchZoneAndFilterByObstacle_shouldReturnMatchingResults() {
		common.searchAndValidateResult("zone", "West");
		common.selectFilterButton("Construction"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		obstacle.validateObstacleFilterList("Construction");
	}

	@Test
	public void TC_017_searchZoneAndFilterByStatus_shouldReturnMatchingResults() {
		common.searchAndValidateResult("zone", "West");
		common.selectSecondFilterButton("Reported"); // Reported, Removed
		common.selectOK();
		obstacle.validateStatuseFilterList("Reported");
	}

	@Test
	public void TC_018_searchAreaAndFilterByObstacle_shouldReturnMatchingResults() {
		common.selectFilterByText("Area");
		common.searchAndValidateResult("Area", "Commercial");// Residential,Commercial,Industrial
		common.selectFilterButton("Construction"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		obstacle.validateObstacleFilterList("Construction");
	}

	@Test
	public void TC_019_searchAreaAndFilterByStatus_shouldReturnMatchingResults() {
		common.selectFilterByText("Area");
		common.searchAndValidateResult("Area", "Commercial");
		common.selectSecondFilterButton("Reported"); // Reported, Removed
		common.selectFilterTwoOK();
		obstacle.validateStatuseFilterList("Reported");
	}

	// ----------------- SEARCH + FILTER VALIDATION (Multiple Filters)

	@Test
	public void TC_020_searchZoneAndApplyObstacleAndStatusFilters_shouldReturnMatchingResults()
			throws InterruptedException {
		common.searchAndValidateResult("zone", "West");
		common.selectFilterButton("Construction"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		obstacle.validateObstacleFilterList("Construction");
		common.selectSecondFilterButton("Removed"); // Reported, Removed
		common.selectFilterTwoOK();
		obstacle.validateStatuseFilterList("Removed");
	}

	@Test
	public void TC_021_searchAreaAndApplyObstacleAndStatusFilters_shouldReturnMatchingResults() {
		common.selectFilterByText("Area");
		common.searchAndValidateResult("Area", "Commercial");// Residential,Commercial,Industrial
		common.selectFilterButton("Construction"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		obstacle.validateObstacleFilterList("Construction");
		common.selectSecondFilterButton("Reported"); // Reported, Removed
		common.selectOK();
		obstacle.validateStatuseFilterList("Reported");
	}

	@Test
	public void TC_022_searchZoneAndApplyMultipleObstacleAndStatusFilters_shouldReturnMatchingResults() {
		common.searchAndValidateResult("zone", "West");
		common.selectFilterButton("Construction"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		obstacle.validateObstacleFilterList("Construction");
		common.selectFilterButton("Parked Vehicle"); // Construction,Parked Vehicle,Natural Obstacle,Road Work
		common.selectOK();
		obstacle.validateObstacleFilterList("Parked Vehicle");
		common.selectSecondFilterButton("Reported"); // Reported, Removed
		common.selectFilterTwoOK();
		obstacle.validateStatuseFilterList("Reported");
	}

	@Test
	public void TC_023_totalObstacleCount_shouldMatchDisplayedRows() {
		obstacle.validateTotalObstacleCount();
		obstacle.printAndValidateOgstacleCounts();
		obstacle.validateTableRowsAgainstTotalObstacle();
	}

	@Test
	public void TC_024_totalReportedAndRemovedCount_shouldMatchDisplayedRows() {
		obstacle.printAndValidateReportedAndRemovedObstacle();

	}
}
