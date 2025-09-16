package pages.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import utils.TestDataReader;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.function.Supplier;

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

public class InspectionManagementPage {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	private WebDriver driver;
	private Properties prop;
	protected ExtentReports extent;
	protected ExtentTest test;
	Faker faker = new Faker();
	private String lastGeneratedPlate;

	// Constructor with config passed from BaseTest
	public InspectionManagementPage(WebDriver driver, Properties prop, ExtentTest test) {
		this.driver = driver;
		this.prop = prop;
		this.test = test;
		PageFactory.initElements(driver, this);
	}

	// WebElements using @FindBy

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[2]")
	private List<WebElement> inspectionCell;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[3]")
	private List<WebElement> fineCell;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[6]")
	private List<WebElement> statusCell;

	@FindBy(xpath = "//li[contains(@class,'ant-pagination-total-text')]")
	private WebElement totalItemPagination;

	@FindBy(xpath = "//li[contains(@class,'ant-pagination-prev')]/button")
	private WebElement previouBtn;

	@FindBy(xpath = "//li[contains(@class,'ant-pagination-next')]/button")
	private WebElement nestBtn;

	@FindBy(xpath = "//tbody[@class='ant-table-tbody']/tr[contains(@class, 'ant-table-row')]")
	private List<WebElement> rowList;

	@FindBy(xpath = "//div[@class='ant-statistic-title' and text()='Total Fines']/following-sibling::div//span[@class='ant-statistic-content-value-int']")
	private WebElement totalFines;

	@FindBy(xpath = "//div[@class='ant-statistic-title' and text()='Paid Fines']/following-sibling::div//span[@class='ant-statistic-content-value-int']")
	private WebElement paid;

	@FindBy(xpath = "//div[@class='ant-statistic-title' and text()='Unpaid Fines']/following-sibling::div//span[@class='ant-statistic-content-value-int']")
	private WebElement unpaid;

	@FindBy(xpath = "//tbody/tr[2]/td[8]/button[1]")
	private WebElement threeDot;

	@FindBy(xpath = "//ul[contains(@class,'ant-dropdown-menu')]//li//span[@class='ant-dropdown-menu-title-content']")
	private List<WebElement> menuList;

	@FindBy(xpath = "//div[contains(text(),'Fine Details')]")
	private WebElement viewInspectionPageMessage;

	@FindBy(xpath = "//tr[th/span[text()='Plate Number']]/td/span")
	private WebElement viewPlateName;

	@FindBy(xpath = "//tr[th/span[text()='Fine Type']]/td/span")
	private WebElement viewFineType;

	@FindBy(xpath = "//tr[th/span[text()='Fine Number']]/td/span")
	private WebElement viewFineNumber;

	@FindBy(xpath = "//tr[th/span[text()='Inspection Type']]/td/span")
	private WebElement viewInspectionType;

	@FindBy(xpath = "//tr[th/span[text()='Inspection Date']]/td/span")
	private WebElement viewInspectionDate;

	@FindBy(xpath = "//tr[th/span[text()='Amount']]/td/span")
	private WebElement viewAmount;

	@FindBy(xpath = "//tr[th/span[text()='Payment Type']]/td/span")
	private WebElement viewPaymentType;

	@FindBy(xpath = "//tr[th/span[text()='Inspection Status']]/td/span")
	private WebElement viewStatus;

	@FindBy(xpath = "//tr[th/span[text()='Black Points']]/td/span")
	private WebElement viewBlackpoint;

	@FindBy(xpath = "//body[1]/div[4]/div[1]/div[3]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]")
	private WebElement viewLicense;

	@FindBy(xpath = "//tr[th/span[text()='Vehicle Color']]/td/span")
	private WebElement viewVehicleColor;

	@FindBy(xpath = "//tr[th/span[text()='Vehicle Type']]/td/span")
	private WebElement viewVehicleType;

	@FindBy(xpath = "//tr[th/span[text()='Vehicle Brand']]/td/span")
	private WebElement viewVehicleBrand;

	@FindBy(xpath = "//tr[th/span[text()='Manufacturer Year']]/td/span")
	private WebElement viewManufacturYear;

	@FindBy(xpath = "//tr[th/span[text()='Vehicle Owner Name']]/td/span")
	private WebElement viewOwnerName;

	@FindBy(xpath = "//tr[th/span[text()='Vehicle Owner Email']]/td/span")
	private WebElement viewOwnerEmail;

	@FindBy(xpath = "//tr[th/span[text()='Vehicle Owner Mobile']]/td/span")
	private WebElement viewOwnerMobille;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[5]")
	private WebElement fineDateCell;

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

	// Full login with data loaded from login.properties

	public void validateInspectionFilterList(String expectedPlateSource) {
		step("Validate Inspection Filter List for: " + expectedPlateSource, () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {

				wait.until(ExpectedConditions.visibilityOfAllElements(inspectionCell));

				boolean allMatched = true;

				for (int i = 0; i < inspectionCell.size(); i++) {
					String actualValue = inspectionCell.get(i).getText().trim();

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
							"One or more rows did not match the expected inspection Source: " + expectedPlateSource);
				}

				logger.info("All rows matched the expected inspection Source: " + expectedPlateSource);
				test.log(Status.PASS, "All rows matched the expected inspection Source: " + expectedPlateSource);

			} catch (Exception e) {
				logger.error("Validation failed for inspection Source filter: " + expectedPlateSource, e);
				test.log(Status.FAIL, "Validation failed for inspection Source filter: " + expectedPlateSource + " - "
						+ e.getMessage());
				throw new AssertionError("Validation failed for inspection Source filter", e);
			}
		});

	}

	public void validateFineFilterList(String expectedPlateSource) {
		step("Validate Fine Filter List for: " + expectedPlateSource, () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {

				wait.until(ExpectedConditions.visibilityOfAllElements(fineCell));

				boolean allMatched = true;

				for (int i = 0; i < fineCell.size(); i++) {
					String actualValue = fineCell.get(i).getText().trim();

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
							"One or more rows did not match the expected Fine Source: " + expectedPlateSource);
				}

				logger.info("All rows matched the expected Fine Source: " + expectedPlateSource);
				test.log(Status.PASS, "All rows matched the expected Fine Source: " + expectedPlateSource);

			} catch (Exception e) {
				logger.error("Validation failed for Fine Source filter: " + expectedPlateSource, e);
				test.log(Status.FAIL,
						"Validation failed for Fine Source filter: " + expectedPlateSource + " - " + e.getMessage());
				throw new AssertionError("Validation failed for Fine Source filter", e);
			}
		});

	}

	public void validateStatusFilterList(String expectedPlateSource) {
		step("Validate Inspection Status Filter List for: " + expectedPlateSource, () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {

				wait.until(ExpectedConditions.visibilityOfAllElements(statusCell));

				boolean allMatched = true;

				for (int i = 0; i < statusCell.size(); i++) {
					String actualValue = statusCell.get(i).getText().trim();

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
					throw new AssertionError("One or more rows did not match the expected Inspection Status Source: "
							+ expectedPlateSource);
				}

				logger.info("All rows matched the expected Inspection Status Source: " + expectedPlateSource);
				test.log(Status.PASS, "All rows matched the expected Fine Source: " + expectedPlateSource);

			} catch (Exception e) {
				logger.error("Validation failed for Inspection Status Source filter: " + expectedPlateSource, e);
				test.log(Status.FAIL, "Validation failed for Inspection Status Source filter: " + expectedPlateSource
						+ " - " + e.getMessage());
				throw new AssertionError("Validation failed for Inspection Status Source filter", e);
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
				int total = Integer.parseInt(totalFines.getText().trim());

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

	public void printAndValidateFineCounts() {
		step("Print and validate total, active, and inactive plate counts", () -> {

			int total = Integer.parseInt(totalFines.getText().trim());
			int active = Integer.parseInt(paid.getText().trim());
			int inactive = Integer.parseInt(unpaid.getText().trim());

			logger.info("Total Fines: " + total);
			test.log(Status.INFO, "Total Fines: " + total);

			logger.info("Paid Fines: " + active);
			test.log(Status.INFO, "Paid Finess: " + active);

			logger.info("UnPaid Fines: " + inactive);
			test.log(Status.INFO, "UnPaid Fines: " + inactive);

			if (total == active + inactive) {
				logger.info("Fine count validation passed: Total = Paid + Unpaid");
				test.log(Status.PASS, "Fine count validation passed: Total = Paid + Unpaid");
			} else {
				String errorMsg = "Fine count validation FAILED! Total: " + total + ", Paid + Unpaide: "
						+ (active + inactive);
				logger.error(errorMsg);
				test.log(Status.FAIL, errorMsg);
				throw new AssertionError("Fine count mismatch: Total != Active + Inactive");
			}

		});
	}

	public void printAndValidateTableCounts() {
		step("Print total fines available in the table", () -> {
			int total = rowList.size();

			logger.info("Total Fines available: " + total);
			test.log(Status.INFO, "Total Fines available: " + total);
		});
	}

	public void validateTableRowsAgainstTotalFines() {
		step("Validate total fines label matches table row count", () -> {

			int totalFromLabel = Integer.parseInt(totalFines.getText().trim());

			List<WebElement> rowList = driver
					.findElements(By.xpath("//tbody[@class='ant-table-tbody']/tr[contains(@class, 'ant-table-row')]"));
			int totalFromTable = rowList.size();

			logger.info("Total Fines from label: " + totalFromLabel);
			test.log(Status.INFO, "Total Fines from label: " + totalFromLabel);
			logger.info("Total Fines from table rows: " + totalFromTable);
			test.log(Status.INFO, "Total Fines from table rows: " + totalFromTable);

			if (totalFromLabel == totalFromTable) {
				logger.info("Validation passed: Total Fines label matches table row count.");
				test.log(Status.PASS, "Validation passed: Total Fines label matches table row count.");
			} else {
				String errorMsg = "Validation FAILED! Total Fines label: " + totalFromLabel + ", Table rows: "
						+ totalFromTable;
				logger.error(errorMsg);
				test.log(Status.FAIL, errorMsg);
				throw new AssertionError("Total Fines mismatch: Label != Table row count");
			}
		});
	}

	public void validateTotalFineCount() {
		step("Validate total fine count against table rows", () -> {
			try {
				int total = Integer.parseInt(totalFines.getText().trim());
				int rowsOnPage = rowList.size();

				if (rowsOnPage <= total) {
					logger.info("Row count (" + rowsOnPage + ") matches or is less than total fines (" + total + ")");
					test.log(Status.PASS,
							"Row count (" + rowsOnPage + ") matches or is less than total fines (" + total + ")");
				} else {
					logger.error("Row count (" + rowsOnPage + ") exceeds total fines (" + total + ")");
					test.log(Status.FAIL, "Row count (" + rowsOnPage + ") exceeds total fines (" + total + ")");
					throw new AssertionError("Row count (" + rowsOnPage + ") exceeds total fines (" + total + ")");
				}

			} catch (Exception e) {
				logger.error("Error validating total fines: " + e.getMessage());
				test.log(Status.FAIL, "Error validating total fines: " + e.getMessage());
				throw new RuntimeException(e);
			}
		});
	}

	public void clickThreeDotAndValidateMenuList() {
		step("Click three-dot menu and validate menu list", () -> {
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
		});
	}

	public boolean navigateToViewInspectionPage() {
		return step("Navigate to View Inspection page", () -> {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			String sucMsg = "";

			try {
				WebElement messageElement = wait.until(ExpectedConditions.visibilityOf(viewInspectionPageMessage));
				sucMsg = messageElement.getText().trim();
			} catch (TimeoutException e) {
				logger.error("View Inspection page message not found within timeout.");
				test.log(Status.FAIL, "View Inspection page message not found within timeout.");
				throw new AssertionError("View Inspection page message not found within timeout.", e);
			}

			if (sucMsg != null && !sucMsg.isEmpty()) {
				logger.info("Navigated to View Inspection page: " + sucMsg);
				test.log(Status.PASS, "Navigated to View Inspection page: " + sucMsg);
				return true;
			} else {
				logger.error("Failed to navigate to View Inspection page. Message was empty.");
				test.log(Status.FAIL, "Failed to navigate to View Inspection page. Message was empty.");
				throw new AssertionError("Failed to navigate to View Inspection page. Message was empty.");
			}
		});
	}

	public void getFineDetails() {
		step("Validate and print Fine Type	", () -> validateAndPrint("Fine Type	", viewFineType.getText()));
		step("Validate and print Fine Number", () -> validateAndPrint("Fine Number", viewFineNumber.getText()));
		step("Validate and print Inspection Type",
				() -> validateAndPrint("Inspection Type", viewInspectionType.getText()));
		step("Validate and print Inspection Date",
				() -> validateAndPrint("Inspection Date", viewInspectionDate.getText()));
		step("Validate and print Amount", () -> validateAndPrint("Amount", viewAmount.getText()));
		step("Validate and print Payment Type", () -> validateAndPrint("Payment Type", viewPaymentType.getText()));
		step("Validate and print Inspection Status", () -> validateAndPrint("Inspection Status", viewStatus.getText()));
		step("Validate and print Black Points", () -> validateAndPrint("Black Points", viewBlackpoint.getText()));
		step("Validate and print Trade License Details",
				() -> validateAndPrint("Trade License Details", viewLicense.getText()));

	}

	public void getFineDetailsOfPlateDetails() {
		step("Validate and print Vehicle Color		",
				() -> validateAndPrint("Vehicle Color", viewVehicleColor.getText()));
		step("Validate and print Vehicle Type", () -> validateAndPrint("Vehicle Type", viewVehicleType.getText()));
		step("Validate and print Vehicle Brand", () -> validateAndPrint("Vehicle Brand", viewVehicleBrand.getText()));
		step("Validate and print Manufacturer Year",
				() -> validateAndPrint("Manufacturer Year", viewManufacturYear.getText()));
		step("Validate and print Vehicle Owner Name	",
				() -> validateAndPrint("Vehicle Owner Name	", viewOwnerName.getText()));
		step("Validate and print Vehicle Owner Email	",
				() -> validateAndPrint("Vehicle Owner Email	", viewOwnerEmail.getText()));
		step("Validate and print Vehicle Owner Mobile	",
				() -> validateAndPrint("Vehicle Owner Mobile	", viewOwnerMobille.getText()));

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

	public void validateFromDate(String expectedDate) {
		step("Validate From Date: " + expectedDate, () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(fineDateCell));
			String actualDate = fineDateCell.getText().trim();

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

	public void validateToDate(String expectedDate) {
		step("Validate To Date: " + expectedDate, () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(fineDateCell));
			String actualDate = fineDateCell.getText().trim();

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
