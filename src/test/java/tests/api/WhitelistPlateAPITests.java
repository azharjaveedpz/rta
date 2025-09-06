package tests.api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pages.api.WhitelistPlateAPI;
import tests.ui.TestLaunchBrowser;
import utils.ApiAssertions;
import utils.DataReader;
import utils.PayloadGenerator;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.databind.JsonNode;

import base.BaseTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.Assert;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import org.slf4j.LoggerFactory;
import com.aventstack.extentreports.ExtentTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

public class WhitelistPlateAPITests extends BaseTest {

	private static final Logger logger = LogManager.getLogger(WhitelistPlateAPITests.class);

	private WhitelistPlateAPI apiPage = new WhitelistPlateAPI();

	@Test
	public void TC01_shouldCreatePlateSuccessfully() {
		String payload = PayloadGenerator.generateDefaultPlatePayload();
		Response response = apiPage.sendAndLogRequest(payload, test, logger);

		ApiAssertions.assertStatusCode(response, 200, "Plate created successfully", "❌ Plate creation failed", test,
				logger);

		ApiAssertions.assertResponseContains(response, "plateNumber", "Response contains plateNumber",
				"plateNumber missing in response", test, logger);
	}

	@Test
	public void TC02_shouldCreatePlateWithByLawFalse() {
		String payload = PayloadGenerator.generateisByLawPlatePayload();
		Response response = apiPage.sendAndLogRequest(payload, test, logger);

		ApiAssertions.assertStatusCode(response, 200, "Plate created with ByLaw=false",
				"Failed to create plate with ByLaw=false", test, logger);
	}

	@Test
	public void TC03_shouldNotCreateDuplicatePlate() {
		String payload = PayloadGenerator.generateDuplicatePlatePayload();
		apiPage.sendAndLogRequest(payload, test, logger); // create first
		Response response = apiPage.sendAndLogRequest(payload, test, logger); // try duplicate

		ApiAssertions.assertStatusCode(response, 400, "Duplicate plate correctly rejected",
				"Duplicate plate should not be allowed", test, logger);
	}

	@Test

	public void TC04_shouldFetchPlateById() {
		int plateId = 123;
		String expectedPlateNumber = "CCC 1234 DBX"; // use dynamic value if created earlier

		// 🔹 Fetch plate by ID
		Response response = apiPage.getAndLogPlateById(plateId, test, logger);

		ApiAssertions.assertStatusCode(response, 200, "Plate fetched successfully by ID", "Failed to fetch plate by ID",
				test, logger);

		String actualPlateNumber = response.jsonPath().getString("plateNumber");

		Assert.assertNotNull(actualPlateNumber, "plateNumber is null in response");
		Assert.assertFalse(actualPlateNumber.isEmpty(), "plateNumber is empty in response");
		Assert.assertEquals(actualPlateNumber, expectedPlateNumber, "plateNumber does not match expected value");

		if (test != null) {
			test.pass("plateNumber matched successfully: " + actualPlateNumber);
		}
		logger.info("plateNumber matched successfully: {}", actualPlateNumber);
	}

	@Test
	public void TC05_shouldReturn404ForInvalidPlateId() {
		int invalidId = 99999;
		Response response = apiPage.getAndLogPlateById(invalidId, test, logger);
		ApiAssertions.assertStatusCode(response, 404, "Correctly returned 404 for invalid plateId",
				"Expected 404 but got different status", test, logger);
	}

	@Test
	public void TC06_shouldFetchPlatesWithPagination() {
		int pageNumberReq = 1;
		int pageSizeReq = 1;
		String query = "?pageNumber=" + pageNumberReq + "&pageSize=" + pageSizeReq;

		Response response = apiPage.getAndLogPlatesWithQuery(query, test, logger);
		ApiAssertions.assertStatusCode(response, 200, "Pagination request succeeded", "Pagination request failed",
				test, logger);
		// Pagination Metadata Validation
		int pageNumber = response.jsonPath().getInt("pageNumber");
		int pageSize = response.jsonPath().getInt("pageSize");
		int totalCount = response.jsonPath().getInt("totalCount");

		Assert.assertEquals(pageNumberReq, pageNumber, "pageNumber mismatch");
		Assert.assertEquals(pageSizeReq, pageSize, "pageSize mismatch");
		Assert.assertTrue(totalCount > 0, "totalCount should be greater than 0");

		List<Map<String, Object>> plates = response.jsonPath().getList("data");
		Assert.assertFalse(plates.isEmpty(), "Data array is empty");
		// Plate Number Validation
		String plateNumber = response.jsonPath().getString("data[0].plateNumber");
		Assert.assertNotNull(plateNumber, "plateNumber is missing in first record");
		test.pass("Pagination response validated successfully" + plateNumber);
	}

	@Test
	public void TC07_shouldHandleInvalidPagination() {
		String query = "?pageNumber=-1&pageSize=0";
		Response response = apiPage.getAndLogPlatesWithQuery(query, test, logger);
		ApiAssertions.assertStatusCode(response, 400, "Invalid pagination correctly rejected",
				"Invalid pagination should return 400", test, logger);
	}

	@Test
	public void TC08_shouldFetchPlatesWithFiltering() {
		String query = "?plateNumber=AP03BL0101";
		Response response = apiPage.getAndLogPlatesWithQuery(query, test, logger);
		ApiAssertions.assertStatusCode(response, 200, "Filter request succeeded", "Filter request failed", test,
				logger);
		ApiAssertions.assertResponseContains(response, "AP03BL0101", "Response contains filtered plate",
				"Filtered plate missing in response", test, logger);
	}

	@Test
	public void TC09_shouldFetchPlatesWithPaginationAndSorting() {
		int pageNumberReq = 1;
		int pageSizeReq = 1;
		String sortField = "pageNumber";
		String sortOrder = "asc"; // or "desc"

		// Add sort parameters
		String query = "?pageNumber=" + pageNumberReq + "&pageSize=" + pageSizeReq + "&sortField=" + sortField
				+ "&sortOrder=" + sortOrder;
		Response response = apiPage.getAndLogPlatesWithQuery(query, test, logger);
		ApiAssertions.assertStatusCode(response, 200, "Pagination+Sorting request succeeded",
				"Pagination+Sorting request failed", test, logger);
		int pageNumber = response.jsonPath().getInt("pageNumber");
		int pageSize = response.jsonPath().getInt("pageSize");
		Assert.assertEquals(pageNumberReq, pageNumber, "pageNumber mismatch");
		Assert.assertEquals(pageSizeReq, pageSize, "pageSize mismatch");
		List<Integer> ids = response.jsonPath().getList("data.id");
		Assert.assertFalse(ids.isEmpty(), "No records found in data");
		// Check Sorting (ascending or descending)
		List<Integer> sorted = new ArrayList<>(ids);
		if ("asc".equalsIgnoreCase(sortOrder)) {
			Collections.sort(sorted);
		} else {
			Collections.sort(sorted, Collections.reverseOrder());
		}
		Assert.assertEquals(sorted, ids, "Records are not sorted by " + sortField + " in " + sortOrder + " order");
		test.pass("Pagination and sorting validated successfully");
	}

	@Test
	public void TC10_shouldFetchPlatesWithSortingAscending() {
		String query = "?sortBy=plateNumber&sortOrder=asc";

		Response response = apiPage.getAndLogPlatesWithQuery(query, test, logger);
		ApiAssertions.assertStatusCode(response, 200, "Sorting request succeeded", "Sorting request failed", test,
				logger);
		List<String> plateNumbers = response.jsonPath().getList("data.plateNumber");
		Assert.assertFalse(plateNumbers.isEmpty(), "Plate list should not be empty");
		logger.info("Fetched plate numbers: {}", plateNumbers);
		test.info("Fetched plate numbers: " + plateNumbers);
		List<String> expectedSorted = new ArrayList<>(plateNumbers);
		expectedSorted.sort((p1, p2) -> {
			return p1.compareToIgnoreCase(p2);
		});
		logger.info("Expected sorted list (ascending): {}", expectedSorted);
		test.info("Expected sorted list (ascending): " + expectedSorted);
		Assert.assertEquals(plateNumbers, expectedSorted, "Plate numbers are not sorted correctly (ascending)");
		test.pass("Plate numbers are sorted correctly in ascending order");
	}

	@Test
	public void TC11_shouldEditPlateSuccessfully() {

		int plateId = 42;
		String payload = PayloadGenerator.generateEditPlatePayload(plateId);
		Response response = apiPage.sendAndLogRequest(payload, test, logger);
		ApiAssertions.assertStatusCode(response, 200, " Plate edited successfully", "Plate edit failed", test,
				logger);
		ApiAssertions.assertResponseContains(response, "plateNumber", "Response contains updated plateNumber",
				"plateNumber missing in response", test, logger);
		ApiAssertions.assertResponseContains(response, "editBy", "Response contains editBy",
				"editBy missing in response", test, logger);
	}
	
}
