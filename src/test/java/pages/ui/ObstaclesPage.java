package pages.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import tests.ui.TestLaunchBrowser;
import com.github.javafaker.Faker;

public class ObstaclesPage {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	private WebDriver driver;
	private Properties prop;
	protected ExtentReports extent;
	protected ExtentTest test;
	Faker faker = new Faker();
	private String lastGeneratedPlate;

	// Constructor with config passed from BaseTest
	public ObstaclesPage(WebDriver driver, Properties prop, ExtentTest test) {
		this.driver = driver;
		this.prop = prop;
		this.test = test;
		PageFactory.initElements(driver, this);

	}

	// WebElements using @FindBy

	@FindBy(xpath = "//button[.='Add New']")
	private WebElement addNewInspectionButton;

	@FindBy(xpath = "//div[contains(text(),'Add New Inspection Obstacle')]")
	private WebElement addInspectionPageMessage;

	@FindBy(xpath = "//input[@id='ObstacleNumber']")
	private WebElement ObstacleNumber;

	@FindBy(xpath = "//input[@id='Zone']")
	private WebElement zoneType;

	@FindBy(xpath = "//input[@id='Area']")
	private WebElement dropdownsArea;

	@FindBy(xpath = "//input[@id='SourceOfObstacle']")
	private WebElement dropdowObstacle;

	@FindBy(xpath = "//input[@id='ClosestPaymentDevice']")
	private WebElement paymentDevice;

	@FindBy(xpath = "//input[@id='ReportedBy']")
	private WebElement reported;

	@FindBy(xpath = "//div[contains(@class,'ant-select-dropdown') and not(contains(@class,'hidden'))]//div[contains(@class,'ant-select-item-option-content')]")
	private List<WebElement> chooseList;

	@FindBy(xpath = "//textarea[@id='Comments']")
	private WebElement comments;

	@FindBy(id = "Photo")
	private WebElement imageUpload;

	@FindBy(xpath = "//button[.='Submit']")
	private WebElement submit;

	@FindBy(xpath = "//td[@class='ant-table-cell']")
	private WebElement confirmAddedInspectionObstacleNumber;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[1]")
	private WebElement zoneCell;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[2]")
	private WebElement areaCell;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[3]")
	private WebElement obstacleCell;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[4]")
	private WebElement statusCell;

	@FindBy(xpath = "//span[@aria-label='more']/ancestor::button")
	private WebElement threeDot;

	@FindBy(xpath = "//ul[contains(@class,'ant-dropdown-menu')]//li")
	private List<WebElement> menuList;

	@FindBy(xpath = "//div[contains(text(),'Inspection Obstacle Details')]")
	private WebElement viewObstaclePageMessage;

	@FindBy(xpath = "//tr[th/span[text()='Zone']]/td/span")
	private WebElement viewZoneType;

	@FindBy(xpath = "//tr[th/span[text()='Area']]/td/span")
	private WebElement viewArea;

	@FindBy(xpath = "//tr[th/span[text()='Source Of Obstacle']]/td/span")
	private WebElement viewSourcebstaclle;

	@FindBy(xpath = "//tr[th/span[text()='Closest Payment Device']]/td/span")
	private WebElement viewDevice;

	@FindBy(xpath = "//tr[th/span[text()='Comments']]/td/span")
	private WebElement viewComments;

	@FindBy(xpath = "//tr[th/span[text()='Status']]/td/span")
	private WebElement viewStatus;

	@FindBy(xpath = "//div[contains(@class,'ant-space')]//div[contains(@class,'ant-image')]//img")
	private List<WebElement> viewDocuments;

	@FindBy(xpath = "//div[@class='ant-statistic-title' and text()='Total Obstacles']/following-sibling::div//span[@class='ant-statistic-content-value-int']")
	private WebElement totalObstacle;

	@FindBy(xpath = "//div[@class='ant-statistic-title' and text()='Reported Obstacles']/following-sibling::div//span[@class='ant-statistic-content-value-int']")
	private WebElement reportedObstacle;

	@FindBy(xpath = "//div[@class='ant-statistic-title' and text()='Removed Obstacles']/following-sibling::div//span[@class='ant-statistic-content-value-int']")
	private WebElement removedObstacle;

	@FindBy(xpath = "//tbody[@class='ant-table-tbody']/tr[contains(@class, 'ant-table-row')]")
	private List<WebElement> rowList;

	@FindBy(xpath = "//li[contains(@class,'ant-pagination-prev')]/button")
	private WebElement previouBtn;

	@FindBy(xpath = "//li[contains(@class,'ant-pagination-next')]/button")
	private WebElement nestBtn;

	@FindBy(xpath = "//li[contains(@class,'ant-pagination-total-text')]")
	private WebElement totalItemPagination;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[3]")
	private List<WebElement> obstacleSource;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[4]")
	private List<WebElement> statusSource;
	// Actions

	public boolean navigateToAddObstaclePage() {
		addNewInspectionButton.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String sucMsg = "";

		try {

			WebElement messageElement = wait.until(ExpectedConditions.visibilityOf(addInspectionPageMessage));
			sucMsg = messageElement.getText().trim();
		} catch (TimeoutException e) {
			logger.error("Add New Inspection page message not found within timeout.");
			test.log(Status.FAIL, "Add New Inspection page message not found within timeout.");
			throw new AssertionError("Add New Inspection page message not found within timeout.", e);
		}

		if (sucMsg != null && !sucMsg.isEmpty()) {
			logger.info("Navigated to Add New Inspection page: " + sucMsg);
			test.log(Status.PASS, "Navigated to Add New Inspection page: " + sucMsg);
			return true;
		} else {
			logger.error("Failed to navigate to Add New Inspection page. Message was empty.");
			test.log(Status.FAIL, "Failed to navigate to Add New Inspection page. Message was empty.");
			throw new AssertionError("Failed to navigate to Add New Inspection page. Message was empty.");
		}
	}

	public void selectZone(String zoneName) {
		zoneType.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElements(chooseList));

		for (WebElement option : chooseList) {
			if (option.getText().equalsIgnoreCase(zoneName)) {
				option.click();
				logger.info("Selected Zone by text: " + zoneName);
				test.log(Status.INFO, "Selected Zone by text: " + zoneName);
				return;
			}
		}

		throw new RuntimeException("Zone not found: " + zoneName);
	}

	public void selectArea(String areaName) {
		dropdownsArea.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElements(chooseList));

		for (WebElement option : chooseList) {
			if (option.getText().equalsIgnoreCase(areaName)) {
				option.click();
				logger.info("Selected are by text: " + areaName);
				test.log(Status.INFO, "Selected Area by text: " + areaName);
				return;
			}
		}

		throw new RuntimeException("Area not found: " + areaName);
	}

	public void selectObstacle(String obsName) {
		dropdowObstacle.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElements(chooseList));

		for (WebElement option : chooseList) {
			if (option.getText().equalsIgnoreCase(obsName)) {
				option.click();
				logger.info("Selected Obstacle by text: " + obsName);
				test.log(Status.INFO, "Selected Obstacle by text: " + obsName);
				return;
			}
		}

		throw new RuntimeException("Obstacle not found: " + obsName);
	}

	public void enterDeviceAndReportedDetails() {

		String[] devices = { "Laptop", "Mobile", "Pager", "Tab", "Palm Tab" };

		String device = devices[new java.util.Random().nextInt(devices.length)];

		// Enter values
		sendKeysWithWait(paymentDevice, device, "device name");

	}

	private void sendKeysWithWait(WebElement element, String value, String fieldName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(element));
		// element.clear();
		element.sendKeys(value);

		logger.info("Entered " + fieldName + ": " + value);
		test.log(Status.INFO, "Entered " + fieldName + ": " + value);
	}

	public void enterComments() {

		String com = faker.lorem().sentence();

		sendKeysWithWait(comments, com, "comment as");
	}

	public void uploadPhoto() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Photo")));

		String filePath = System.getProperty("user.dir") + "/src/test/java/utils/sample.png";
		imageUpload.sendKeys(filePath);

		logger.info("Uploaded photo from: " + filePath);
		test.log(Status.INFO, "Uploaded photo from: " + filePath);
	}

	public void submitInspectionObstacle() {
		submit.click();
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(confirmAddedInspectionObstacleNumber));

		String actualInspection = confirmAddedInspectionObstacleNumber.getText().trim();

		if (actualInspection.equalsIgnoreCase(lastGeneratedPlate)) {
			logger.info("Obstacle number successfully added: " + actualInspection);
			test.log(Status.PASS, "Obstacle number successfully added: " + actualInspection);
		} else {
			logger.error(
					"Obstacle number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualInspection);
			test.log(Status.FAIL,
					"Obstacle number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualInspection);
			throw new AssertionError(
					"Obstacle number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualInspection);

		}
	}

	public void validateZone(String expectedSource) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(zoneCell));

		String actualSource = zoneCell.getText().trim();

		if (actualSource.equalsIgnoreCase(expectedSource)) {
			logger.info("Zone successfully validated: " + actualSource);
			test.log(Status.PASS, "Zone successfully validated: " + actualSource);
		} else {
			logger.error("Zone  mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
			test.log(Status.FAIL, "Zone  mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
			throw new AssertionError("Zone  mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
		}
	}

	public void validateArea(String expectedSource) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(areaCell));

		String actualSource = areaCell.getText().trim();

		if (actualSource.equalsIgnoreCase(expectedSource)) {
			logger.info("Area type successfully validated: " + actualSource);
			test.log(Status.PASS, "Area type successfully validated: " + actualSource);
		} else {
			logger.error("Area type  mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
			test.log(Status.FAIL, "Area type  mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
			throw new AssertionError(
					"Area type  mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
		}
	}

	public void validateObstacle(String expectedSource) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(obstacleCell));

		String actualSource = obstacleCell.getText().trim();

		if (actualSource.equalsIgnoreCase(expectedSource)) {
			logger.info("Obstacle type successfully validated: " + actualSource);
			test.log(Status.PASS, "Obstacle type successfully validated: " + actualSource);
		} else {
			logger.error("Obstacle type  mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
			test.log(Status.FAIL,
					"Obstacle type  mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
			throw new AssertionError(
					"Obstacle type  mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
		}
	}

	public void validateStatus(String expectedSource) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(statusCell));

		String actualSource = statusCell.getText().trim();

		if (actualSource.equalsIgnoreCase(expectedSource)) {
			logger.info("Status type successfully validated: " + actualSource);
			test.log(Status.PASS, "Status type successfully validated: " + actualSource);
		} else {
			logger.error("Status type  mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
			test.log(Status.FAIL,
					"Status type  mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
			throw new AssertionError(
					"Status type  mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
		}
	}

	public void clickThreeDotAndValidateMenuList() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		threeDot.click();
		logger.info("Clicked on three-dot menu");
		test.log(Status.INFO, "Clicked on three-dot menu");

		if (menuList == null || menuList.isEmpty()) {
			String message = "Menu list not found after clicking three-dot!";
			logger.error(message);
			test.log(Status.FAIL, message);
			throw new AssertionError(message);
		} else {
			logger.info("Menu items found: " + menuList.size());
			test.log(Status.PASS, "Menu items found: " + menuList.size());

			for (WebElement item : menuList) {
				String menuText = item.getText().trim();
				logger.info("Menu Item: " + menuText);
				test.log(Status.INFO, "Menu Item: " + menuText);

			}
		}
	}

	public boolean navigateToViewPage() {

		// viewButton.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String sucMsg = "";

		try {

			WebElement messageElement = wait.until(ExpectedConditions.visibilityOf(viewObstaclePageMessage));
			sucMsg = messageElement.getText().trim();
		} catch (TimeoutException e) {
			logger.error("View  page message not found within timeout.");
			test.log(Status.FAIL, "View  page message not found within timeout.");
			throw new AssertionError("View  page message not found within timeout.", e);
		}

		if (sucMsg != null && !sucMsg.isEmpty()) {
			logger.info("Navigated to View  page: " + sucMsg);
			test.log(Status.PASS, "Navigated to View  page: " + sucMsg);
			return true;
		} else {
			logger.error("Failed to navigate to View  page. Message was empty.");
			test.log(Status.FAIL, "Failed to navigate to View  page. Message was empty.");
			throw new AssertionError("Failed to navigate to View Plate page. Message was empty.");
		}
	}

	public void getObstacleDetails() {
		validateAndPrint("Zone Type", viewZoneType.getText());
		validateAndPrint("Area Type", viewArea.getText());
		validateAndPrint("Source Of Obstacle", viewSourcebstaclle.getText());
		validateAndPrint("Device Used", viewDevice.getText());
		validateAndPrint("Comments", viewComments.getText());
		validateAndPrint("Status", viewStatus.getText());

		int docCount = viewDocuments.size();
		if (docCount == 0) {
			logger.error("Document is empty!");
			test.log(Status.FAIL, "Document is empty!");
		} else {
			logger.info("Documents Uploaded: " + docCount);
			test.log(Status.PASS, "Documents Uploaded: " + docCount);

			for (WebElement doc : viewDocuments) {
				String imgPath = doc.getAttribute("src");
				logger.info("Document Path: " + imgPath);
				test.log(Status.INFO, "Document Path: " + imgPath);
			}
		}
	}

	private void validateAndPrint(String fieldName, String value) {
		value = value != null ? value.trim() : "";

		if (value.isEmpty()) {
			logger.error(fieldName + " is empty!");
			test.log(Status.FAIL, fieldName + " is empty!");
			throw new AssertionError(fieldName + " should not be empty!");
		} else {
			logger.info(fieldName + ": " + value);
			test.log(Status.PASS, fieldName + ": " + value);
		}
	}

	public void validateTotalObstacleCount() {
		try {
			int total = Integer.parseInt(totalObstacle.getText().trim());
			int rowsOnPage = rowList.size();

			if (rowsOnPage <= total) {
				logger.info("Row count (" + rowsOnPage + ") matches or is less than total obstacle (" + total + ")");
				test.log(Status.PASS,
						"Row count (" + rowsOnPage + ") matches or is less than total obstacle (" + total + ")");
			} else {
				logger.error("Row count (" + rowsOnPage + ") exceeds total obstacle (" + total + ")");
				test.log(Status.FAIL, "Row count (" + rowsOnPage + ") exceeds total obstacle (" + total + ")");
			}

		} catch (Exception e) {
			logger.error("Error validating total obstacle: " + e.getMessage());
			test.log(Status.FAIL, "Error validating total obstacle: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public void printAndValidateOgstacleCounts() {

		int total = Integer.parseInt(totalObstacle.getText().trim());
		int report = Integer.parseInt(reportedObstacle.getText().trim());
		int remove = Integer.parseInt(removedObstacle.getText().trim());

		logger.info("Total obstacle: " + total);
		test.log(Status.INFO, "Total obstacle: " + total);

		logger.info("reported obstacle: " + report);
		test.log(Status.INFO, "reported obstacle: " + report);

		logger.info("removed obstacle: " + remove);
		test.log(Status.INFO, "removds obstacle: " + remove);

		if (total == report + remove) {
			logger.info("obstacle count validation passed: Total = report + remove");
			test.log(Status.PASS, "obstacle count validation passed: Total = report + remove");
		} else {
			logger.error(
					"obstacle count validation FAILED! Total: " + total + ", Active + Inactive: " + (report + remove));
			test.log(Status.FAIL,
					"obstacle count validation FAILED! Total: " + total + ", Active + Inactive: " + (report + remove));
			throw new AssertionError("Plate count mismatch: Total != Active + Inactive");
		}
	}

	public void validateTableRowsAgainstTotalObstacle() {

		int totalFromLabel = Integer.parseInt(totalObstacle.getText().trim());

		List<WebElement> rowList = driver
				.findElements(By.xpath("//tbody[@class='ant-table-tbody']/tr[contains(@class, 'ant-table-row')]"));
		int totalFromTable = rowList.size();

		logger.info("Total Obstacle from label: " + totalFromLabel);
		test.log(Status.INFO, "Total Obstacle from label: " + totalFromLabel);
		logger.info("Total Obstacle from table rows: " + totalFromTable);
		test.log(Status.INFO, "Total Obstacle from table rows: " + totalFromTable);

		if (totalFromLabel == totalFromTable) {
			logger.info("Validation passed: Total Obstacle label matches table row count.");
			test.log(Status.PASS, "Validation passed: Total Obstacle label matches table row count.");
		} else {
			logger.error(
					"Validation FAILED! Total Obstacle label: " + totalFromLabel + ", Table rows: " + totalFromTable);
			test.log(Status.FAIL,
					"Validation FAILED! Total Obstacle label: " + totalFromLabel + ", Table rows: " + totalFromTable);
			throw new AssertionError("Total Obstacle mismatch: Label != Table row count");
		}
	}

	public void checkMiddlePagesPagination() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String paginationText = totalItemPagination.getText();
			String[] parts = paginationText.split(" of ");
			int itemsPerPage = Integer.parseInt(parts[0].split("-")[1].trim());
			int totalItems = Integer.parseInt(parts[1].replaceAll("[^0-9]", "").trim());
			int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
			int total = Integer.parseInt(totalObstacle.getText().trim());

			logger.info("Total pages: " + totalPages);
			test.log(Status.INFO, "Total pages: " + totalPages);

			for (int page = 2; page < totalPages; page++) {
				driver.findElement(By.xpath("//li[contains(@class,'ant-pagination-item') and @title='" + page + "']"))
						.click();
				new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.textToBePresentInElement(
						driver.findElement(By.xpath("//li[contains(@class,'ant-pagination-item-active')]/a")),
						String.valueOf(page)));

				WebElement prevBtn = previouBtn;
				WebElement nextBtn = nestBtn;
				int rowsOnPage = rowList.size();

				logger.info("Checking middle page: " + page);
				test.log(Status.INFO, "Checking middle page: " + page);

				if (prevBtn.isEnabled() && nextBtn.isEnabled()) {
					logger.info("Middle page: Previous and Next enabled");
					test.log(Status.PASS, "Middle page: Previous and Next enabled");
				} else {
					logger.error("Middle page: Previous and Next should be enabled");
					test.log(Status.FAIL, "Middle page: Previous and Next should be enabled");
				}

				if (rowsOnPage == total || rowsOnPage <= total) {
					logger.info("Rows on middle page match or less than Total Plates: " + rowsOnPage);
					test.log(Status.PASS, "Rows on middle page match or less than Total Plates: " + rowsOnPage);
				} else {
					logger.error("Rows on middle page (" + rowsOnPage + ") exceed Total Plates (" + total + ")");
					test.log(Status.FAIL,
							"Rows on middle page (" + rowsOnPage + ") exceed Total Plates (" + total + ")");
				}
			}

		} catch (Exception e) {
			logger.error("Error in middle pages validation: " + e.getMessage());
			test.log(Status.FAIL, "Error in middle pages validation: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public void validateObstacleFilterList(String expectedPlateSource) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {

			wait.until(ExpectedConditions.visibilityOfAllElements(obstacleSource));

			boolean allMatched = true;

			for (int i = 0; i < obstacleSource.size(); i++) {
				String actualValue = obstacleSource.get(i).getText().trim();

				if (!actualValue.equalsIgnoreCase(expectedPlateSource)) {
					logger.error("Row " + (i + 1) + " mismatch: expected = " + expectedPlateSource + ", actual = "
							+ actualValue);
					test.log(Status.FAIL, "Row " + (i + 1) + " mismatch: expected = " + expectedPlateSource
							+ ", actual = " + actualValue);
					allMatched = false;
				} else {
					logger.info("Row " + (i + 1) + " matched: " + actualValue);
					test.log(Status.PASS, "Row " + (i + 1) + " matched: " + actualValue);
				}
			}

			if (!allMatched) {
				throw new AssertionError(
						"One or more rows did not match the expected Obstacle Source: " + expectedPlateSource);
			}

			logger.info("All rows matched the expected Obstacle Source: " + expectedPlateSource);
			test.log(Status.PASS, "All rows matched the expected Obstacle Source: " + expectedPlateSource);

		} catch (Exception e) {
			logger.error("Validation failed for Obstacle Source filter: " + expectedPlateSource, e);
			test.log(Status.FAIL,
					"Validation failed for Obstacle Source filter: " + expectedPlateSource + " - " + e.getMessage());
			throw new AssertionError("Validation failed for Obstacle Source filter", e);
		}
	}

	public void validateStatuseFilterList(String expectedPlateSource) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {

			wait.until(ExpectedConditions.visibilityOfAllElements(statusSource));

			boolean allMatched = true;

			for (int i = 0; i < statusSource.size(); i++) {
				String actualValue = statusSource.get(i).getText().trim();

				if (!actualValue.equalsIgnoreCase(expectedPlateSource)) {
					logger.error("Row " + (i + 1) + " mismatch: expected = " + expectedPlateSource + ", actual = "
							+ actualValue);
					test.log(Status.FAIL, "Row " + (i + 1) + " mismatch: expected = " + expectedPlateSource
							+ ", actual = " + actualValue);
					allMatched = false;
				} else {
					logger.info("Row " + (i + 1) + " matched: " + actualValue);
					test.log(Status.PASS, "Row " + (i + 1) + " matched: " + actualValue);
				}
			}

			if (!allMatched) {
				throw new AssertionError(
						"One or more rows did not match the expected Obstacle Source: " + expectedPlateSource);
			}

			logger.info("All rows matched the expected Status Source: " + expectedPlateSource);
			test.log(Status.PASS, "All rows matched the expected Status Source: " + expectedPlateSource);

		} catch (Exception e) {
			logger.error("Validation failed for Status Source filter: " + expectedPlateSource, e);
			test.log(Status.FAIL,
					"Validation failed for Status Source filter: " + expectedPlateSource + " - " + e.getMessage());
			throw new AssertionError("Validation failed for Status Source filter", e);
		}
	}
	public void printAndValidateReportedAndRemovedObstacle() {

		// UI
		int reported = Integer.parseInt(reportedObstacle.getText().trim());
		int removed = Integer.parseInt(removedObstacle.getText().trim());

		// Collect all statuses from the table
		List<WebElement> statusList = driver
				.findElements(By.xpath("//tbody[@class='ant-table-tbody']/tr[contains(@class,'ant-table-row')]/td[5]"));

		long activeCount = statusList.stream().map(WebElement::getText).map(String::trim)
				.filter(status -> status.equalsIgnoreCase("Reported")).count();

		long inactiveCount = statusList.stream().map(WebElement::getText).map(String::trim)
				.filter(status -> status.equalsIgnoreCase("Removed")).count();

		logger.info("Reported (UI): " + reported + " | Reported (Table): " + activeCount);
		test.log(Status.INFO, "Reported (UI): " + reported + " | Active (Table): " + activeCount);

		logger.info("Removed (UI): " + removed + " | Removed (Table): " + inactiveCount);
		test.log(Status.INFO, "Inactive (UI): " + removed + " | Inactive (Table): " + inactiveCount);

		if (reported == activeCount && removed == inactiveCount) {
			logger.info("Validation passed: UI counts match table counts");
			test.log(Status.PASS, "Validation passed: UI counts match table counts");
		} else {
			logger.error("Validation FAILED! Reported(UI/Table): " + reported + "/" + activeCount
					+ " | Inactive(UI/Table): " + removed + "/" + inactiveCount);
			test.log(Status.FAIL, "Validation FAILED! Reported(UI/Table): " + reported + "/" + activeCount
					+ " | Inactive(UI/Table): " + removed + "/" + inactiveCount);
			throw new AssertionError("Mismatch between UI and table counts");
		}
	}
}
