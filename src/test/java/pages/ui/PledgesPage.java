package pages.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import tests.ui.TestLaunchBrowser;
import com.github.javafaker.Faker;

public class PledgesPage {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	private WebDriver driver;
	private Properties prop;
	protected ExtentReports extent;
	protected ExtentTest test;
	Faker faker = new Faker();
	private String lastGeneratedPlate;

	// Constructor with config passed from BaseTest
	public PledgesPage(WebDriver driver, Properties prop, ExtentTest test) {
		this.driver = driver;
		this.prop = prop;
		this.test = test;
		PageFactory.initElements(driver, this);
	}

	// WebElements using @FindBy

	@FindBy(xpath = "//button[.='Add New']")
	private WebElement addNewPledgeButton;

	@FindBy(xpath = "//div[contains(text(),'Add New Pledge')]")
	private WebElement addPledgePageMessage;

	@FindBy(xpath = "//input[@id='pledgeNumber']")
	private WebElement pledgeNumbers;

	@FindBy(xpath = "//input[@id='tradeLicenseNumber']")
	private WebElement tradeLicense;

	@FindBy(xpath = "//input[@id='businessName']")
	private WebElement businessName;

	@FindBy(xpath = "//textarea[@id='remarks']")
	private WebElement remarks;

	@FindBy(xpath = "//input[@id='pledgeType']")
	private WebElement pledgeType;

	@FindBy(xpath = "//div[contains(@class,'ant-select-dropdown') and not(contains(@class,'hidden'))]//div[contains(@class,'ant-select-item-option-content')]")
	private List<WebElement> chooseList;

	@FindBy(id = "documentPath")
	private WebElement imageUpload;

	@FindBy(xpath = "//button[.='Submit']")
	private WebElement submit;

	@FindBy(xpath = "//td[@class='ant-table-cell']")
	private WebElement confirmAddedInspectionObstacleNumber;

	@FindBy(xpath = "//span[@aria-label='more']/ancestor::button")
	private WebElement threeDot;

	@FindBy(xpath = "//ul[contains(@class,'ant-dropdown-menu')]//li")
	private List<WebElement> menuList;

	@FindBy(xpath = "//tr[th/span[text()='Pledge Type']]/td/span")
	private WebElement viewPledgeType;

	@FindBy(xpath = "//tr[th/span[text()='Trade License Number']]/td/span")
	private WebElement viewTrade;

	@FindBy(xpath = "//tr[th/span[text()='Business Name']]/td/span")
	private WebElement viewBuisness;

	@FindBy(xpath = "//tr[th/span[text()='Remarks']]/td/span")
	private WebElement viewRemarks;

	@FindBy(xpath = "//tr[th/span[text()='Added On']]/td/span")
	private WebElement viewAddedOn;

	@FindBy(xpath = "//div[contains(@class,'ant-space')]//div[contains(@class,'ant-image')]//img")
	private List<WebElement> viewDocuments;

	@FindBy(xpath = "//div[contains(text(),'Pledge Details')]")
	private WebElement viewPledgePageMessage;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[1]")
	private WebElement zoneCell;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[2]")
	private WebElement areaCell;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[3]")
	private WebElement obstaclCell;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[4]")
	private WebElement statusell;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[3]")
	private List<WebElement> pledgeSource;

	@FindBy(xpath = "//div[@class='ant-statistic-title' and text()='Total Pledges']    /following-sibling::div//span[@class='ant-statistic-content-value-int']")
	private WebElement totalPledges;

	@FindBy(xpath = "//div[@class='ant-statistic-title' and text()='Corporate Pledges']    /following-sibling::div//span[@class='ant-statistic-content-value-int']")
	private WebElement coprPledges;

	@FindBy(xpath = "//tbody[@class='ant-table-tbody']/tr[contains(@class, 'ant-table-row')]")
	private List<WebElement> rowList;

	@FindBy(xpath = "//tr[contains(@class,'ant-table-row')]//td[4]")
	private List<WebElement> corList;

	@FindBy(xpath = "//li[contains(@class,'ant-pagination-total-text')]")
	private WebElement totalItemPagination;

	@FindBy(xpath = "//li[contains(@class,'ant-pagination-prev')]/button")
	private WebElement previouBtn;

	@FindBy(xpath = "//li[contains(@class,'ant-pagination-next')]/button")
	private WebElement nestBtn;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[1]")
	private WebElement confirmAddedLicenseNumber;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[3]")
	private WebElement pledgeTypeCell;

	@FindBy(xpath = "//tbody/tr[8]/td[5]")
	private WebElement validateFromDate;

	@FindBy(xpath = "//tbody/tr[8]/td[6]")
	private WebElement validateToDate;

	@FindBy(xpath = "//div[contains(text(),'Edit Pledge')]")
	private WebElement editPlatePageMessage;

	// ================= Step Logging =================
	protected void step(String description, Runnable action) {
		test.info("Step: " + description);
		try {
			action.run();
			// test.pass("Step passed: " + description);
		} catch (Exception e) {
			test.fail("Step failed: " + description + " → " + e.getMessage());
			throw e;
		}
	}

	protected <T> T step(String description, Supplier<T> action) {
		test.info("Step: " + description);
		try {
			T result = action.get();
			// test.pass("Step passed: " + description + " → Result: " + result);
			return result;
		} catch (Exception e) {
			test.fail("Step failed: " + description + " → " + e.getMessage());
			throw e;
		}
	}

	// Actions

	public boolean navigateToAddPledgePage() {
		return step("Navigate to Add New Pledge Page", () -> {
			addNewPledgeButton.click();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			String sucMsg = "";

			try {

				WebElement messageElement = wait.until(ExpectedConditions.visibilityOf(addPledgePageMessage));
				sucMsg = messageElement.getText().trim();
			} catch (TimeoutException e) {
				logger.error("Add New Pledge page message not found within timeout.");
				test.log(Status.FAIL, "Add New Pledge page message not found within timeout.");
				throw new AssertionError("Add New Pledge page message not found within timeout.", e);
			}

			if (sucMsg != null && !sucMsg.isEmpty()) {
				logger.info("Navigated to Add New Pledge page: " + sucMsg);
				test.pass("Navigated to Add New Pledge page → " + sucMsg);
				return true;
			} else {
				logger.error("Failed to navigate to Add New Pledge page. Message was empty.");
				test.log(Status.FAIL, "Failed to navigate to Add New Pledge page. Message was empty.");
				throw new AssertionError("Failed to navigate to Add New Pledge page. Message was empty.");
			}
		});
	}

	public void enterPledgeNumber() {
		String pledgeNumber = faker.regexify("[A-Z]{2}[0-9]{2}[A-Z]{4}");
		lastGeneratedPlate = pledgeNumber; // save for later

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(pledgeNumbers));

		pledgeNumbers.sendKeys(pledgeNumber);

		logger.info("Entered pledge Number: " + pledgeNumber);
		test.log(Status.INFO, "Entered pledge Number: " + pledgeNumber);
	}

	public void enterTradeLicenseNumber() {
		step("Enter Trade License Number", () -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			String licNumber = faker.regexify("[A-Z]{2}[0-9]{2}[A-Z]{4}");
			lastGeneratedPlate = licNumber;

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(tradeLicense));

			tradeLicense.sendKeys(licNumber);

			logger.info("Entered license Number: " + licNumber);
			test.pass("Entered license Number: " + licNumber);
		});
	}

	public void clearText(WebElement element) {
		try {

			element.click();

			element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			element.sendKeys(Keys.DELETE);

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].value=''; " + "arguments[0].dispatchEvent(new Event('input', { bubbles: true })); "
							+ "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
					element);

			element.sendKeys(" ");
			element.sendKeys(Keys.BACK_SPACE);

			String value = element.getAttribute("value");
			if (value != null && !value.isEmpty()) {
				throw new RuntimeException("Field not cleared, current value: " + value);
			}

			logger.info("Cleared text from element: " + element);
			test.log(Status.INFO, "Cleared text from element: " + element);

		} catch (Exception e) {
			String message = "Unable to clear text from element: " + element;
			logger.error(message, e);
			test.log(Status.FAIL, message);
			throw new RuntimeException(message, e);
		}
	}

	public void clearAllFields() {
		clearText(tradeLicense);

	}

	public void enterBusinessName() {
		step("Enter Business Name", () -> {
			String bus = faker.company().industry();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(businessName));

			businessName.sendKeys(bus);

			logger.info("Entered business name: " + bus);
			test.pass("Entered business name: " + bus);
		});
	}

	public void enterRemark() {
		step("Enter Remark", () -> {
			String bus = faker.lorem().sentence();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(remarks));

			remarks.sendKeys(bus);

			logger.info("Entered remarks : " + bus);
			test.pass("Entered remarks : " + bus);
		});
	}

	public void selectPledgeType(String pledgeName) {
		step("Select Pledge type: " + pledgeName, () -> {
			pledgeType.click();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfAllElements(chooseList));

			for (WebElement option : chooseList) {
				if (option.getText().equalsIgnoreCase(pledgeName)) {
					option.click();
					logger.info("Selected Pledge by text: " + pledgeName);
					test.log(Status.PASS, "Selected Pledge by text: " + pledgeName);
					return;
				}
			}

			throw new RuntimeException("Pledge not found: " + pledgeName);
		});
	}

	public void uploadPhoto() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("documentPath")));

		String filePath = System.getProperty("user.dir") + "/src/test/java/utils/sample.png";

		imageUpload.sendKeys(filePath);

		logger.info("Uploaded photo from: " + filePath);
		test.log(Status.INFO, "Uploaded photo from: " + filePath);
	}

	public void clickThreeDotAndValidateMenuList() {
		step("Click three-dot menu and validate items", () -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException("Thread sleep interrupted", e);
			}

			threeDot.click();
			logger.info("Clicked on three-dot menu");
			test.info("Clicked on three-dot menu");

			if (menuList == null || menuList.isEmpty()) {
				String message = "Menu list not found after clicking three-dot!";
				logger.error(message);
				test.fail(message);
				throw new AssertionError(message);
			} else {
				logger.info("Menu items found: " + menuList.size());
				test.pass("Menu items found: " + menuList.size());

				for (WebElement item : menuList) {
					String menuText = item.getText().trim();
					logger.info("Menu Item: " + menuText);
					test.info("Menu Item: " + menuText);
				}
			}
		});
	}

	public void getPledgeDetails() {
		step("Validate and Print Plate Type", () -> validateAndPrint("Plate Type", viewPledgeType.getText()));
		step("Validate and Print Trade License Number",
				() -> validateAndPrint("Trade License Number", viewTrade.getText()));
		step("Validate and Print Business Name", () -> validateAndPrint("Business Name", viewBuisness.getText()));
		step("Validate and Print Remarks", () -> validateAndPrint("Remarks", viewRemarks.getText()));
		// step("Validate and Print Added On", () -> validateAndPrint("Added On",
		// viewAddedOn.getText()));

		step("Validate Uploaded Documents", () -> {
			int docCount = viewDocuments.size();
			if (docCount == 0) {
				logger.error("Document is empty!");
				test.fail("Document is empty!");
			} else {
				logger.info("Documents Uploaded: " + docCount);
				test.pass("Documents Uploaded: " + docCount);

				for (WebElement doc : viewDocuments) {
					String imgPath = doc.getAttribute("src");
					logger.info("Document Path: " + imgPath);
					test.info("Document Path: " + imgPath);
				}
			}
		});
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

	public boolean navigateToViewPage() {

		return step("Navigate to View Page", () -> {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			String sucMsg = "";

			try {

				WebElement messageElement = wait.until(ExpectedConditions.visibilityOf(viewPledgePageMessage));
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
		});
	}

	public boolean navigateToEditPlatePage() {

		return step("Navigate to Edit Plate Page", () -> {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			String sucMsg = "";

			try {

				WebElement messageElement = wait.until(ExpectedConditions.visibilityOf(editPlatePageMessage));
				sucMsg = messageElement.getText().trim();
			} catch (TimeoutException e) {
				logger.error("Edit Plate page message not found within timeout.");
				test.log(Status.FAIL, "Edit Plate page message not found within timeout.");
				throw new AssertionError("Edit Plate page message not found within timeout.", e);
			}

			if (sucMsg != null && !sucMsg.isEmpty()) {
				logger.info("Navigated to Edit Plate page: " + sucMsg);
				test.log(Status.PASS, "Navigated to Edit Plate page: " + sucMsg);
				return true;
			} else {
				logger.error("Failed to navigate to Edit Plate page. Message was empty.");
				test.log(Status.FAIL, "Failed to navigate to Edit Plate page. Message was empty.");
				throw new AssertionError("Failed to navigate to Edit Plate page. Message was empty.");
			}
		});
	}

	public void validatePledgeFilterList(String expectedPlateSource) {
		step("Validate Pledge Filter List for: " + expectedPlateSource, () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {

				wait.until(ExpectedConditions.visibilityOfAllElements(pledgeSource));

				boolean allMatched = true;

				for (int i = 0; i < pledgeSource.size(); i++) {
					String actualValue = pledgeSource.get(i).getText().trim();

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
							"One or more rows did not match the expected pledged Source: " + expectedPlateSource);
				}

				logger.info("All rows matched the expected pledged Source: " + expectedPlateSource);
				test.log(Status.PASS, "All rows matched the expected pledged Source: " + expectedPlateSource);

			} catch (Exception e) {
				logger.error("Validation failed for pledged Source filter: " + expectedPlateSource, e);
				test.log(Status.FAIL,
						"Validation failed for pledged Source filter: " + expectedPlateSource + " - " + e.getMessage());
				throw new AssertionError("Validation failed for pledged Source filter", e);
			}
		});

	}

	public void validateTotalPledgesCount() {
		step("Validate Total Pledges Count", () -> {
			try {
				int actualCount = Integer.parseInt(totalPledges.getText().trim()); // UI total
				int expectedCount = rowList.size(); // rows count

				if (expectedCount == actualCount) {
					String message = "Row count (" + expectedCount + ") matches total pledge (" + actualCount + ")";
					logger.info(message);
					test.log(Status.PASS, message);
				} else {
					String message = "Row count (" + expectedCount + ") does NOT match total pledge (" + actualCount
							+ ")";
					logger.error(message);
					test.log(Status.FAIL, message);
					throw new AssertionError(message);
				}

			} catch (Exception e) {
				logger.error("Error validating total pledge: " + e.getMessage());
				test.log(Status.FAIL, "Error validating total pledge: " + e.getMessage());
				throw new RuntimeException(e);
			}
		});
	}

	public void validateCorporatePledgesCount() {
		step("Validate Corporate Pledges Count", () -> {
			try {
				// Get corporate count from statistic card
				int corporateTotal = Integer.parseInt(coprPledges.getText().trim());

				// Count how many rows in the table are "Corporate"
				long corporateRows = corList.stream().map(row -> row.getText().trim())
						.filter(text -> text.equalsIgnoreCase("Corporate")).count();

				if (corporateRows == corporateTotal) {
					String message = "Corporate count matches: UI shows (" + corporateTotal + "), Table has ("
							+ corporateRows + ")";
					logger.info(message);
					test.log(Status.PASS, message);
				} else {
					String message = "Corporate count mismatch! UI shows (" + corporateTotal + "), but Table has ("
							+ corporateRows + ")";
					logger.error(message);
					test.log(Status.FAIL, message);
					throw new AssertionError(message); // fail the test
				}

			} catch (Exception e) {
				logger.error("Error validating corporate pledges: " + e.getMessage());
				test.log(Status.FAIL, "Error validating corporate pledges: " + e.getMessage());
				throw new RuntimeException(e);
			}
		});
	}

	public void checkMiddlePagesPagination() {
		step("Validate Middle Page Pagination", () -> {
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
				int total = Integer.parseInt(totalPledges.getText().trim());

				logger.info("Total pages: " + totalPages);
				test.log(Status.INFO, "Total pages: " + totalPages);

				for (int page = 2; page < totalPages; page++) {
					driver.findElement(
							By.xpath("//li[contains(@class,'ant-pagination-item') and @title='" + page + "']")).click();
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
		});
	}

	public void validateBusiness(String expectedSource) {
		step("Validate Business Name displayed on listing page", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(pledgeTypeCell));

			String actualSource = pledgeTypeCell.getText().trim();

			if (actualSource.equalsIgnoreCase(expectedSource)) {
				logger.info("Business Name successfully validated: " + actualSource);
				test.log(Status.PASS, "Business Name successfully validated:  " + actualSource);
			} else {
				logger.error("Business Name  mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
				test.log(Status.FAIL,
						"Business Name  mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
				throw new AssertionError(
						"Business Name  mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
			}
		});
	}

	public void validatePledgeAdded() {
		step("Validate license number is displayed on listing page after pledge creation ", () -> {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(confirmAddedLicenseNumber));

			String actualPlate = confirmAddedLicenseNumber.getText().trim();

			if (actualPlate.equalsIgnoreCase(lastGeneratedPlate)) {
				logger.info("License number successfully added: " + actualPlate);
				test.log(Status.PASS, "License number successfully added: " + actualPlate);
			} else {

				logger.error(
						"License number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualPlate);
				test.log(Status.FAIL,
						"License number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualPlate);

				throw new AssertionError(
						"License number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualPlate);
			}
		});
	}

	public void validateFromDate(String expectedDate) {
		step("Validate From Date on listing page", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(validateFromDate));

			String actualDate = validateFromDate.getText().trim();

			if (actualDate.equalsIgnoreCase(expectedDate)) {
				logger.info("From Date successfully validated: " + actualDate);
				test.pass("From Date successfully validated: " + actualDate);
			} else {
				logger.error("From Date mismatch! Expected: " + expectedDate + ", but found: " + actualDate);
				test.fail("From Date mismatch! Expected: " + expectedDate + ", but found: " + actualDate);
				throw new AssertionError(
						"From Date mismatch! Expected: " + expectedDate + ", but found: " + actualDate);
			}
		});
	}

	// ====== To Date ======

	public void validateToDate(String expectedDate) {
		step("Validate To Date on listing page", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(validateToDate));

			String actualDate = validateToDate.getText().trim();

			if (actualDate.equalsIgnoreCase(expectedDate)) {
				logger.info("To Date successfully validated: " + actualDate);
				test.pass("To Date successfully validated: " + actualDate);
			} else {
				logger.error("To Date mismatch! Expected: " + expectedDate + ", but found: " + actualDate);
				test.fail("To Date mismatch! Expected: " + expectedDate + ", but found: " + actualDate);
				throw new AssertionError("To Date mismatch! Expected: " + expectedDate + ", but found: " + actualDate);
			}
		});
	}

}
