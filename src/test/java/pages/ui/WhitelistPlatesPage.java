package pages.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.stream.Collectors;

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

public class WhitelistPlatesPage {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	private WebDriver driver;
	protected ExtentReports extent;
	protected ExtentTest test;
	Faker faker = new Faker();
	private String lastGeneratedPlate;
	private String selectedStartDate;
	private String selectedEndDate;

	
	public WhitelistPlatesPage(WebDriver driver, Properties prop, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		PageFactory.initElements(driver, this);
	}

	// WebElements using @FindBy
	@FindBy(xpath = "//a[normalize-space()='Plates']")
	private WebElement plates;

	@FindBy(xpath = "//h3[normalize-space()='Plate Whitelist']")
	private WebElement plateMessage;

	@FindBy(xpath = "//button[.='Add New']")
	private WebElement addNewPlateButton;

	@FindBy(xpath = "//div[contains(text(),'Add New Plate')]")
	private WebElement addPlatePageMessage;

	@FindBy(xpath = "//input[@id='plateNumber']")
	private WebElement inputPlateNumber;

	// Common locator for options (always use same XPath root for visible dropdowns)
	@FindBy(xpath = "//div[contains(@class,'ant-select-dropdown') and not(contains(@class,'hidden'))]"
			+ "//div[contains(@class,'ant-select-item-option-content')]")
	private List<WebElement> dropdownOptions;

	@FindBy(xpath = "//div[contains(@class,'ant-select-dropdown') and @aria-hidden='false']")
	private WebElement activeDropdown;

	@FindBy(xpath = "//button[.='Submit']")
	private WebElement submit;

	@FindBy(xpath = "//input[@id='dateRange']")
	private WebElement startDate;

	@FindBy(xpath = "//td[contains(@class,'ant-picker-cell-in-view')]//div[text()='")
	private WebElement dateSelection;

	@FindBy(xpath = "//div[@class='ant-picker ant-picker-range ant-picker-large ant-picker-outlined ant-picker-status-error css-ch82n6']//input[@placeholder='End date']")
	private WebElement endtDate;

	// Locators for each column (index-based)
	@FindBy(xpath = "(//td[@class='ant-table-cell'])[1]")
	private WebElement confirmAddedPlateNumber;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[2]")
	private WebElement confirmAddedPlateSource;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[3]")
	private WebElement confirmAddedPlateType;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[4]")
	private WebElement confirmAddedPlateColor;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[5]")
	private WebElement confirmAddedFromDate;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[6]")
	private WebElement confirmAddedToDate;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[7]")
	private WebElement confirmAddedStatus;

	@FindBy(xpath = "//div[@class='ant-notification-notice-message']")
	private WebElement dublicatedPlatesNumber;

	@FindBy(xpath = "//input[@placeholder='Search...']")
	private WebElement searchInput;

	@FindBy(xpath = "//td[@class='ant-table-cell']")
	private WebElement plateNameTable;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[2]")
	private WebElement plateSourceTable;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[3]")
	private WebElement plateTypeTable;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[4]")
	private WebElement plateColorTable;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[5]")
	private WebElement fromTable;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[6]")
	private WebElement toTable;

	@FindBy(xpath = "//input[@placeholder='Start date']")
	private WebElement startSearch;

	@FindBy(xpath = "//input[@placeholder='End date']")
	private WebElement endSearch;

	@FindBy(xpath = "//tbody/tr[2]/td[9]/button[1]")
	private WebElement threeDot;

	@FindBy(xpath = "//ul[contains(@class,'ant-dropdown-menu')]//li//span[@class='ant-dropdown-menu-title-content']")
	private List<WebElement> menuList;

	@FindBy(xpath = "//span[normalize-space()='Edit']")
	private WebElement editButton;

	@FindBy(xpath = "//span[normalize-space()='View']")
	private WebElement viewButton;

	@FindBy(xpath = "//span[normalize-space()='Delete']")
	private WebElement deleteButton;

	@FindBy(xpath = "//div[contains(text(),'Edit Plate')]")
	private WebElement editPlatePageMessage;

	@FindBy(xpath = "//button[.='Update']")
	private WebElement update;

	@FindBy(xpath = "//div[contains(text(),'Plate Details')]")
	private WebElement viewPlatePageMessage;

	@FindBy(xpath = "//div[contains(text(),'No data')]")
	private WebElement noData;

	@FindBy(xpath = "//tbody[@class='ant-table-tbody']//tr[not(contains(@class,'ant-table-measure-row'))]/td[2]")
	private List<WebElement> plateNameRowTable;

	@FindBy(xpath = "//tbody[@class='ant-table-tbody']//tr[not(contains(@class,'ant-table-measure-row'))]/td[3]")
	private List<WebElement> plateSourceRowTable;

	@FindBy(xpath = "//div[contains(@class,'ant-select-selector')]")
	private WebElement filter;

	@FindBy(xpath = "//div[contains(@class,'ant-select-item-option-content')]")
	private List<WebElement> filterOptions;

	@FindBy(xpath = "//span[@class='ant-select-selection-item']")
	private WebElement selectedValue;

	@FindBy(xpath = "//div[@class='ant-statistic-title' and text()='Total Plates']/following-sibling::div//span[@class='ant-statistic-content-value-int']")
	private WebElement totalPlates;

	@FindBy(xpath = "//div[@class='ant-statistic-title' and text()='Active Plates']/following-sibling::div//span[@class='ant-statistic-content-value-int']")
	private WebElement activePlates;

	@FindBy(xpath = "//div[@class='ant-statistic-title' and text()='Inactive Plates']/following-sibling::div//span[@class='ant-statistic-content-value-int']")
	private WebElement inactivePlates;

	@FindBy(xpath = "//tbody[@class='ant-table-tbody']/tr[contains(@class, 'ant-table-row')]")
	private List<WebElement> rowList;

	@FindBy(xpath = "//tr[th/span[text()='Plate Number']]/td/span")
	private WebElement viewPlateName;

	@FindBy(xpath = "//tr[th/span[text()='Plate Source']]/td/span")
	private WebElement viewPlateSource;

	@FindBy(xpath = "//tr[th/span[text()='Plate Type']]/td/span")
	private WebElement viewPlateType;

	@FindBy(xpath = "//tr[th/span[text()='Plate Color']]/td/span")
	private WebElement viewPlateColor;

	@FindBy(xpath = "//tr[th/span[text()='From Date']]/td/span")
	private WebElement viewStartDate;

	@FindBy(xpath = "//tr[th/span[text()='To Date']]/td/span")
	private WebElement viewEndDate;

	@FindBy(xpath = "//tr[th/span[text()='Exemption Reason ID']]/td/span")
	private WebElement viewExemption;

	@FindBy(xpath = "//tr[th/span[text()='Status']]/td/span/span[contains(@class,'ant-tag')]")
	private WebElement viewStatus;

	@FindBy(xpath = "//li[contains(@class,'ant-pagination-prev')]/button")
	private WebElement previouBtn;

	@FindBy(xpath = "//li[contains(@class,'ant-pagination-next')]/button")
	private WebElement nestBtn;

	@FindBy(xpath = "//li[contains(@class,'ant-pagination-item-active')]/a")
	private WebElement currentPages;

	@FindBy(xpath = "//li[contains(@class,'ant-pagination-total-text')]")
	private WebElement totalItemPagination;

	@FindBy(xpath = "//th[@aria-label='Plate Source']//span[contains(@class,'ant-table-filter-trigger')]")
	private WebElement plateSourceFilterBtn;

	private By singleCheckBy = By
			.xpath("(//div[contains(@class,'ant-tree')]//span[contains(@class,'ant-tree-title')])[2]");

	@FindBy(xpath = "//span[text()='Plate Type']/following-sibling::span[contains(@class,'ant-table-filter-trigger')]")
	private WebElement plateTypeFilterBtn;

	@FindBy(xpath = "//span[text()='Status']/following-sibling::span[contains(@class,'ant-table-filter-trigger')]\n"
			+ "")
	private WebElement statusFilterBtn;

	@FindBy(xpath = "(//div[contains(@class,'ant-table-filter-dropdown')]//input[@placeholder='Search in filters'])[2]")
	private WebElement searchFilterText;

	@FindBy(xpath = "(//label[contains(@class,'ant-table-filter-dropdown-checkall')]//input[@type='checkbox'])[2]")
	private WebElement selectAllFilterCheckBox;

	@FindBy(xpath = "(//div[contains(@class,'ant-tree')]//span[contains(@class,'ant-tree-title')])[2]")
	private WebElement singleCheck;

	@FindBy(xpath = "//button[.='OK']")
	private WebElement ok;

	// Column 1: Checkbox
	@FindBy(xpath = "//table[contains(@class,'ant-table')]//tr[contains(@class,'ant-table-row')]//td[1]//input[@type='checkbox']")
	private WebElement plateCheckbox;

	// Column 2: Plate Number
	@FindBy(xpath = "//table[contains(@class,'ant-table')]//tr[contains(@class,'ant-table-row')]//td[2]")
	private WebElement plateNumber;

	// Column 3: Plate Source
	@FindBy(xpath = "(//td[@class='ant-table-cell'])[2]")
	private List<WebElement> plateSource;

	// Column 4: Plate Type
	@FindBy(xpath = "//table[contains(@class,'ant-table')]//tr[contains(@class,'ant-table-row')]//td[4]")
	private WebElement plateType;

	// Column 5: Plate Color
	@FindBy(xpath = "//table[contains(@class,'ant-table')]//tr[contains(@class,'ant-table-row')]//td[5]")
	private WebElement plateColor;

	// Column 6: Start Date
	@FindBy(xpath = "//table[contains(@class,'ant-table')]//tr[contains(@class,'ant-table-row')]//td[6]")
	private WebElement fromtDate;

	// Column 7: End Date
	@FindBy(xpath = "//table[contains(@class,'ant-table')]//tr[contains(@class,'ant-table-row')]//td[7]")
	private WebElement endDate;

	// Column 8: Status
	@FindBy(xpath = "//table[contains(@class,'ant-table')]//tr[contains(@class,'ant-table-row')]//td[8]")
	private WebElement plateStatus;

	// Column 9: Action Button
	@FindBy(xpath = "//table[contains(@class,'ant-table')]//tr[contains(@class,'ant-table-row')]//td[last()]//button")
	private WebElement actionButton;

	// Actions

	public void clickPlates() {
		plates.click();
	}

	public String getPlatesMessage() {
		try {
			return plateMessage.getText().trim();
		} catch (NoSuchElementException e) {
			return "";
		}
	}

	public boolean navigateToPlatePage() {
		clickPlates();

		String sucMsg = getPlatesMessage();
		if (sucMsg != null && !sucMsg.trim().isEmpty()) {
			logger.info("Navigated to plate page: " + sucMsg);
			test.log(Status.PASS, "Navigated to plate page: " + sucMsg);
			return true;
		} else {
			logger.error("Failed to navigate to plate page.");
			test.log(Status.FAIL, "Failed to navigate to plate page.");
			throw new AssertionError("Failed to navigate to plate page."); // causes test to fail

		}
	}

	public void selectFilterByIndex(int index) {
		filter.click(); // open dropdown

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElements(filterOptions));

		logger.info("Dropdown contains " + options.size() + " options.");
		test.log(Status.INFO, "Dropdown contains " + options.size() + " options.");

		if (options.isEmpty()) {
			throw new RuntimeException("No options found in dropdown!");
		}
		if (index < 0 || index >= options.size()) {
			throw new IllegalArgumentException(
					"Invalid index " + index + ", valid range: 0 to " + (options.size() - 1));
		}

		WebElement optionToSelect = options.get(index);
		String optionText = optionToSelect.getText().trim();
		optionToSelect.click();

		wait.until(ExpectedConditions.or(ExpectedConditions.textToBePresentInElement(selectedValue, optionText),
				ExpectedConditions.attributeToBe(selectedValue, "title", optionText)));

		logger.info("Selected filter: " + optionText);
		test.log(Status.INFO, "Selected filter: " + optionText);
	}

	public void selectFilterByText(String text) {
		filter.click(); // open dropdown

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElements(filterOptions));

		for (WebElement option : filterOptions) {
			if (option.getText().trim().equalsIgnoreCase(text)) {
				option.click();
				wait.until(ExpectedConditions.textToBePresentInElement(selectedValue, text));
				logger.info("Selected filter: " + text);
				test.log(Status.INFO, "Selected filter: " + text);
				return;
			}
		}
		throw new NoSuchElementException("No option found with text: " + text);
	}

	public void addNewPlates() {
		addNewPlateButton.click();
	}

	public String getNewPlatesMessage() {
		try {
			return addPlatePageMessage.getText().trim();
		} catch (NoSuchElementException e) {
			return "";
		}
	}

	public boolean navigateToAddNewPlatePage() {

		addNewPlateButton.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String sucMsg = "";

		try {

			WebElement messageElement = wait.until(ExpectedConditions.visibilityOf(addPlatePageMessage));
			sucMsg = messageElement.getText().trim();
		} catch (TimeoutException e) {
			logger.error("Add New Plate page message not found within timeout.");
			test.log(Status.FAIL, "Add New Plate page message not found within timeout.");
			throw new AssertionError("Add New Plate page message not found within timeout.", e);
		}

		if (sucMsg != null && !sucMsg.isEmpty()) {
			logger.info("Navigated to Add New Plate page: " + sucMsg);
			test.log(Status.PASS, "Navigated to Add New Plate page: " + sucMsg);
			return true;
		} else {
			logger.error("Failed to navigate to Add New Plate page. Message was empty.");
			test.log(Status.FAIL, "Failed to navigate to Add New Plate page. Message was empty.");
			throw new AssertionError("Failed to navigate to Add New Plate page. Message was empty.");
		}
	}

	public void enterPlateNumber() {
		// String plateNumber = faker.regexify("[A-Z]{2}[0-9]{2}[A-Z]{4}");
		String plateNumber = faker.regexify("[A-Z]{2} [0-9]{5} [A-Z]{3}");
		lastGeneratedPlate = plateNumber; // save for later

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(inputPlateNumber));
		inputPlateNumber.clear();
		inputPlateNumber.sendKeys(plateNumber);

		logger.info("Entered Plate Number: " + plateNumber);
		test.log(Status.INFO, "Entered Plate Number: " + plateNumber);
	}

	public void enterDuplicatePlateNumber(String plateNo) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(inputPlateNumber));

		inputPlateNumber.sendKeys(plateNo);

		logger.info("Entered Plate Number: " + plateNo);
		test.log(Status.INFO, "Entered Plate Number: " + plateNo);
	}

	// Plate Source
	public void selectPlateSourceByText(String text) {
		selectDropdownByText("plateSource_Id", text, "Plate Source");
	}

	public void selectPlateTypeByText(String text) {
		selectDropdownByText("plateType_Id", text, "Plate Type");
	}

	public void selectPlateColorByText(String text) {
		selectDropdownByText("plateColor_Id", text, "Plate Color");
	}

	public void selectStatusByText(String text) {
		selectDropdownByText("plateStatus_Id", text, "Status");
	}

	public void selectExemptionByText(String text) {
		selectDropdownByText("exemptionReason_ID", text, "Exemption");
	}

	public void selectDropdownByText(String dropdownId, String text, String dropdownName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Dynamic dropdown locator
		By dropdownLocator = By.xpath("//*[@id='" + dropdownId
				+ "']/ancestor::div[contains(@class,'ant-select')]//div[contains(@class,'ant-select-selector')]");

		int attempts = 0;
		boolean success = false;

		while (attempts < 3 && !success) {
			try {
				// Click dropdown
				WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
				dropdown.click();

				// Wait for options to load
				wait.until(ExpectedConditions.visibilityOfAllElements(dropdownOptions));

				// Select matching option
				boolean found = false;
				for (WebElement option : dropdownOptions) {
					if (option.getText().trim().equalsIgnoreCase(text.trim())) {
						option.click();
						found = true;
						break;
					}
				}

				if (!found) {
					throw new RuntimeException("Option '" + text + "' not found for " + dropdownName);
				}

				logger.info(" Selected " + dropdownName + " by text: " + text);
				test.log(Status.INFO, " Selected " + dropdownName + " by text: " + text);

				success = true;
			} catch (Exception e) {
				attempts++;
				logger.warn("Attempt " + attempts + " failed for " + dropdownName + " with text: " + text);
				if (attempts == 3) {
					throw new RuntimeException(" Failed to select " + dropdownName + " by text after 3 retries", e);
				}
			}
		}
	}

	public void startDateRange(int day) {

		safeClick(startDate);

		WebElement dateSelection = driver
				.findElement(By.xpath("//td[contains(@class,'ant-picker-cell-in-view')]//div[text()='" + day + "']"));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateSelection);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateSelection);

		selectedStartDate = String.valueOf(day);
		logger.info("Selected Start Date: " + selectedStartDate);
		test.log(Status.INFO, "Selected Start Date: " + selectedStartDate);
	}

	public void startEndRange(int day) {

		WebElement dateSelection = driver
				.findElement(By.xpath("//td[contains(@class,'ant-picker-cell-in-view')]//div[text()='" + day + "']"));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateSelection);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateSelection);

		selectedEndDate = String.valueOf(day);
		logger.info("Selected End Date: " + selectedEndDate);
		test.log(Status.INFO, "Selected End Date: " + selectedEndDate);
	}

	public void submitPlate() {
		submit.click();
	}

	public void validatePlateAdded() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(confirmAddedPlateNumber));

		String actualPlate = confirmAddedPlateNumber.getText().trim();

		if (actualPlate.equalsIgnoreCase(lastGeneratedPlate)) {
			logger.info("Plate number successfully added: " + actualPlate);
			test.log(Status.PASS, "Plate number successfully added: " + actualPlate);
		} else {

			logger.error("Plate number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualPlate);
			test.log(Status.FAIL,
					"Plate number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualPlate);

			// Capture browser console logs for debugging
			try {
				LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
				for (LogEntry entry : logs) {
					logger.error("Browser Log: " + entry.getLevel() + " " + entry.getMessage());
					test.log(Status.INFO, "Browser Log: " + entry.getLevel() + " " + entry.getMessage());
				}
			} catch (Exception e) {
				logger.warn("Could not capture browser logs: " + e.getMessage());
			}

			throw new AssertionError(
					"Plate number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualPlate);
		}
	}
	// ====== Plate Source ======

	public void validatePlateSource(String expectedSource) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(confirmAddedPlateSource));

		String actualSource = confirmAddedPlateSource.getText().trim();

		if (actualSource.equalsIgnoreCase(expectedSource)) {
			logger.info("Plate source successfully validated: " + actualSource);
			test.log(Status.PASS, "Plate source successfully validated: " + actualSource);
		} else {
			logger.error("Plate source mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
			test.log(Status.FAIL,
					"Plate source mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
			throw new AssertionError(
					"Plate source mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
		}
	}

	// ====== Plate Type ======

	public void validatePlateType(String expectedType) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(confirmAddedPlateType));

		String actualType = confirmAddedPlateType.getText().trim();

		if (actualType.equalsIgnoreCase(expectedType)) {
			logger.info("Plate type successfully validated: " + actualType);
			test.log(Status.PASS, "Plate type successfully validated: " + actualType);
		} else {
			logger.error("Plate type mismatch! Expected: " + expectedType + ", but found: " + actualType);
			test.log(Status.FAIL, "Plate type mismatch! Expected: " + expectedType + ", but found: " + actualType);
			throw new AssertionError("Plate type mismatch! Expected: " + expectedType + ", but found: " + actualType);
		}
	}

	// ====== Plate Color ======

	public void validatePlateColor(String expectedColor) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(confirmAddedPlateColor));

		String actualColor = confirmAddedPlateColor.getText().trim();

		if (actualColor.equalsIgnoreCase(expectedColor)) {
			logger.info("Plate color successfully validated: " + actualColor);
			test.log(Status.PASS, "Plate color successfully validated: " + actualColor);
		} else {
			logger.error("Plate color mismatch! Expected: " + expectedColor + ", but found: " + actualColor);
			test.log(Status.FAIL, "Plate color mismatch! Expected: " + expectedColor + ", but found: " + actualColor);
			throw new AssertionError(
					"Plate color mismatch! Expected: " + expectedColor + ", but found: " + actualColor);
		}
	}

	// ====== Status ======
	@FindBy(xpath = "(//td[@class='ant-table-cell'])[5]")
	private WebElement confirmAddedPlateStatus;

	public void validatePlateStatus(String expectedStatus) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(confirmAddedPlateStatus));

		String actualStatus = confirmAddedPlateStatus.getText().trim();

		if (actualStatus.equalsIgnoreCase(expectedStatus)) {
			logger.info("Plate status successfully validated: " + actualStatus);
			test.log(Status.PASS, "Plate status successfully validated: " + actualStatus);
		} else {
			logger.error("Plate status mismatch! Expected: " + expectedStatus + ", but found: " + actualStatus);
			test.log(Status.FAIL,
					"Plate status mismatch! Expected: " + expectedStatus + ", but found: " + actualStatus);
			throw new AssertionError(
					"Plate status mismatch! Expected: " + expectedStatus + ", but found: " + actualStatus);
		}
	}

	// ====== From Date ======

	public void validateFromDate(String expectedDate) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(confirmAddedFromDate));

		String actualDate = confirmAddedFromDate.getText().trim();

		if (actualDate.equalsIgnoreCase(expectedDate)) {
			logger.info("From Date successfully validated: " + actualDate);
			test.log(Status.PASS, "From Date successfully validated: " + actualDate);
		} else {
			logger.error("From Date mismatch! Expected: " + expectedDate + ", but found: " + actualDate);
			test.log(Status.FAIL, "From Date mismatch! Expected: " + expectedDate + ", but found: " + actualDate);
			throw new AssertionError("From Date mismatch! Expected: " + expectedDate + ", but found: " + actualDate);
		}
	}

	// ====== To Date ======

	public void validateToDate(String expectedDate) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(confirmAddedToDate));

		String actualDate = confirmAddedToDate.getText().trim();

		if (actualDate.equalsIgnoreCase(expectedDate)) {
			logger.info("To Date successfully validated: " + actualDate);
			test.log(Status.PASS, "To Date successfully validated: " + actualDate);
		} else {
			logger.error("To Date mismatch! Expected: " + expectedDate + ", but found: " + actualDate);
			test.log(Status.FAIL, "To Date mismatch! Expected: " + expectedDate + ", but found: " + actualDate);
			throw new AssertionError("To Date mismatch! Expected: " + expectedDate + ", but found: " + actualDate);
		}
	}

	public void validatePlateError(String expectedErrorMessage) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(dublicatedPlatesNumber)); // <-- define locator for error message

		String actualError = dublicatedPlatesNumber.getText().trim();

		if (actualError.equalsIgnoreCase(expectedErrorMessage)) {
			logger.info("Validation error correctly displayed: " + actualError);
			test.log(Status.PASS, "Validation error correctly displayed: " + actualError);
		} else {
			logger.error("Error message mismatch! Expected: " + expectedErrorMessage + ", but found: " + actualError);
			test.log(Status.FAIL,
					"Error message mismatch! Expected: " + expectedErrorMessage + ", but found: " + actualError);
			throw new AssertionError(
					"Error message mismatch! Expected: " + expectedErrorMessage + ", but found: " + actualError);
		}

	}

	public void searchWhitelistAndValidateResult(String expectedPlateNumber, String expectedPlateSource) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(searchInput));

		searchInput.clear();
		if (expectedPlateNumber != null) {
			searchInput.sendKeys(expectedPlateNumber);
		} else if (expectedPlateSource != null) {
			searchInput.sendKeys(expectedPlateSource);
		} else {
			throw new IllegalArgumentException("At least one search parameter must be provided!");
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		wait.until(ExpectedConditions.visibilityOf(plateNameTable));

		String actualPlateNumber = plateNameTable.getText().trim();
		String actualPlateSource = plateSourceTable.getText().trim();

		if (expectedPlateNumber != null) {
			validateField("Plate Number", expectedPlateNumber, actualPlateNumber);
		}
		if (expectedPlateSource != null) {
			validateField("Plate Source", expectedPlateSource, actualPlateSource);
		}
	}

	public void searchWhitelistAndValidateNoData(String searchValue, String expectedNoDataMessage) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(searchInput));
		searchInput.clear();
		searchInput.sendKeys(searchValue);

		wait.until(ExpectedConditions.visibilityOf(noData));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String actualNoData = noData.getText().trim();
		validateField("No Data Message", expectedNoDataMessage, actualNoData);
	}

	public void searchWhitelistWithPartialTextAndValidateResult(String expectedPlateNumber,
			String expectedPlateSource) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(searchInput));

		searchInput.clear();
		if (expectedPlateNumber != null) {
			searchInput.sendKeys(expectedPlateNumber);
			logger.info("Searching with Plate Number: " + expectedPlateNumber);
			test.log(Status.INFO, "Searching with Plate Number: " + expectedPlateNumber);
		} else if (expectedPlateSource != null) {
			searchInput.sendKeys(expectedPlateSource);
			logger.info("Searching with Plate Source: " + expectedPlateSource);
			test.log(Status.INFO, "Searching with Plate Source: " + expectedPlateSource);
		} else {
			throw new IllegalArgumentException("At least one search parameter must be provided!");
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			logger.error("Interrupted during sleep", e);
			test.log(Status.FAIL, "Interrupted during sleep: " + e.getMessage());
		}

		wait.until(ExpectedConditions.visibilityOfAllElements(plateNameRowTable));

		List<String> actualPlateNumbers = new ArrayList<>();
		for (WebElement element : plateNameRowTable) {
			actualPlateNumbers.add(element.getText().trim());
		}
		logger.info("Fetched Plate Numbers: " + actualPlateNumbers);
		test.log(Status.INFO, "Fetched Plate Numbers: " + actualPlateNumbers);

		List<String> actualPlateSources = new ArrayList<>();
		for (WebElement element : plateSourceRowTable) {
			actualPlateSources.add(element.getText().trim());
		}
		logger.info("Fetched Plate Sources: " + actualPlateSources);
		test.log(Status.INFO, "Fetched Plate Sources: " + actualPlateSources);

		if (expectedPlateNumber != null) {
			if (actualPlateNumbers.contains(expectedPlateNumber)) {
				logger.info(" Plate Number found: " + expectedPlateNumber);
				test.log(Status.PASS, "Plate Number found: " + expectedPlateNumber);
			} else {
				logger.error(" Plate Number not found! Expected: " + expectedPlateNumber + " | Actual: "
						+ actualPlateNumbers);
				test.log(Status.FAIL, "Plate Number not found! Expected: " + expectedPlateNumber + " | Actual: "
						+ actualPlateNumbers);
				throw new AssertionError("Plate Number mismatch!");
			}
		}

		boolean sourceMatched = false;

		if (expectedPlateSource != null) {
			for (String actualSource : actualPlateSources) {
				if (actualSource != null && actualSource.length() >= 3 && expectedPlateSource.length() >= 3
						&& actualSource.substring(0, 3).equalsIgnoreCase(expectedPlateSource.substring(0, 3))) {
					sourceMatched = true;
					break;
				}
			}

			if (sourceMatched) {
				logger.info("Plate Source found (partial match): " + expectedPlateSource);
				test.log(Status.PASS, "Plate Source found (partial match): " + expectedPlateSource);
			} else {
				logger.error("Plate Source not found! Expected (partial): " + expectedPlateSource + " | Actual: "
						+ actualPlateSources);
				test.log(Status.FAIL, "Plate Source not found! Expected (partial): " + expectedPlateSource
						+ " | Actual: " + actualPlateSources);
				throw new AssertionError("Plate Source mismatch!");
			}
		}

	}

	private void validateField(String fieldName, String expected, String actual) {
		if (actual.equalsIgnoreCase(expected)) {
			logger.info(fieldName + " matched: " + actual);
			test.log(Status.PASS, fieldName + " matched: " + actual);
		} else {
			String message = String.format("%s mismatch! Expected: %s, but found: %s", fieldName, expected, actual);
			logger.error(message);
			test.log(Status.FAIL, message);
			throw new AssertionError(message);
		}
	}

	private WebElement waitForClickable(WebElement element) {
		return new WebDriverWait(driver, Duration.ofSeconds(15))
				.until(ExpectedConditions.elementToBeClickable(element));
	}

	private WebElement waitForVisible(WebElement element) {
		return new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOf(element));
	}

	private void safeClick(WebElement element) {
		int attempts = 0;
		while (attempts < 3) {
			try {
				waitForClickable(element).click();
				return;
			} catch (Exception e) {
				attempts++;
				if (attempts == 3)
					throw e;
			}
		}
	}

	public String searchStartDate(int day) {
		safeClick(startSearch); // click the calendar field safely

		By dateLocator = By.xpath("//td[contains(@class,'ant-picker-cell-in-view')]//div[text()='" + day + "']");

		WebElement dateSelection = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(dateLocator));

		safeClick(dateSelection); // retry logic for date cell

		selectedStartDate = String.valueOf(day);
		return selectedStartDate;
	}

	public String searchEndDate(int day) {
		By dateLocator = By.xpath("//td[contains(@class,'ant-picker-cell-in-view')]//div[text()='" + day + "']");

		WebElement dateSelection = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(dateLocator));

		safeClick(dateSelection); // retry logic for date cell

		selectedEndDate = String.valueOf(day);
		return selectedEndDate;
	}

	public void validateStartDate() {
		String actualFull = fromTable.getText().trim();
		String actualDay = actualFull.split("-")[2];
		validateField("Start Date", selectedStartDate, actualDay);
	}

	public void validateEndDate() {
		String actualFull = toTable.getText().trim();
		String actualDay = actualFull.split("-")[2];
		validateField("End Date", selectedEndDate, actualDay);
	}

	public void clickThreeDotAndValidateMenuList() {

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
				System.out.println("Menu Item: " + menuText);
			}
		}
	}

	public void editPlates() {
		editButton.click();
	}

	public boolean navigateToEditPlatePage() {

		editButton.click();

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
	}

	public void updatePlate() {
		update.click();
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
		clearText(inputPlateNumber);

	}

	public void printAndValidatePlateCounts() {

		int total = Integer.parseInt(totalPlates.getText().trim());
		int active = Integer.parseInt(activePlates.getText().trim());
		int inactive = Integer.parseInt(inactivePlates.getText().trim());

		logger.info("Total Plates: " + total);
		test.log(Status.INFO, "Total Plates: " + total);

		logger.info("Active Plates: " + active);
		test.log(Status.INFO, "Active Plates: " + active);

		logger.info("Inactive Plates: " + inactive);
		test.log(Status.INFO, "Inactive Plates: " + inactive);

		if (total == active + inactive) {
			logger.info("Plate count validation passed: Total = Active + Inactive");
			test.log(Status.PASS, "Plate count validation passed: Total = Active + Inactive");
		} else {
			logger.error(
					"Plate count validation FAILED! Total: " + total + ", Active + Inactive: " + (active + inactive));
			test.log(Status.FAIL,
					"Plate count validation FAILED! Total: " + total + ", Active + Inactive: " + (active + inactive));
			throw new AssertionError("Plate count mismatch: Total != Active + Inactive");
		}
	}

	public void printAndValidateTableCounts() {

		int total = rowList.size();

		logger.info("Total Plates available: " + total);
		test.log(Status.INFO, "Total Plates available: " + total);
		System.out.println("Total Plates available: " + total);
	}

	public void validateTableRowsAgainstTotalPlates() {

		int totalFromLabel = Integer.parseInt(totalPlates.getText().trim());

		List<WebElement> rowList = driver
				.findElements(By.xpath("//tbody[@class='ant-table-tbody']/tr[contains(@class, 'ant-table-row')]"));
		int totalFromTable = rowList.size();

		logger.info("Total Plates from label: " + totalFromLabel);
		test.log(Status.INFO, "Total Plates from label: " + totalFromLabel);
		logger.info("Total Plates from table rows: " + totalFromTable);
		test.log(Status.INFO, "Total Plates from table rows: " + totalFromTable);

		if (totalFromLabel == totalFromTable) {
			logger.info("Validation passed: Total plates label matches table row count.");
			test.log(Status.PASS, "Validation passed: Total plates label matches table row count.");
		} else {
			logger.error(
					"Validation FAILED! Total plates label: " + totalFromLabel + ", Table rows: " + totalFromTable);
			test.log(Status.FAIL,
					"Validation FAILED! Total plates label: " + totalFromLabel + ", Table rows: " + totalFromTable);
			throw new AssertionError("Total plates mismatch: Label != Table row count");
		}
	}

	public boolean navigateToViewPlatePage() {

		viewButton.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String sucMsg = "";

		try {

			WebElement messageElement = wait.until(ExpectedConditions.visibilityOf(viewPlatePageMessage));
			sucMsg = messageElement.getText().trim();
		} catch (TimeoutException e) {
			logger.error("View Plate page message not found within timeout.");
			test.log(Status.FAIL, "View Plate page message not found within timeout.");
			throw new AssertionError("View Plate page message not found within timeout.", e);
		}

		if (sucMsg != null && !sucMsg.isEmpty()) {
			logger.info("Navigated to View Plate page: " + sucMsg);
			test.log(Status.PASS, "Navigated to View Plate page: " + sucMsg);
			return true;
		} else {
			logger.error("Failed to navigate to View Plate page. Message was empty.");
			test.log(Status.FAIL, "Failed to navigate to View Plate page. Message was empty.");
			throw new AssertionError("Failed to navigate to View Plate page. Message was empty.");
		}
	}

	public void getPlateDetails() {
		validateAndPrint("Plate Name", viewPlateName.getText());
		validateAndPrint("Plate Source", viewPlateSource.getText());
		validateAndPrint("Plate Type", viewPlateType.getText());
		validateAndPrint("Plate Color", viewPlateColor.getText());
		validateAndPrint("Start Date", viewStartDate.getText());
		validateAndPrint("End Date", viewEndDate.getText());
		validateAndPrint("Status", viewStatus.getText());
		validateAndPrint("Exemption", viewExemption.getText());
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

	public void checkFirstPagePagination() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			int total = Integer.parseInt(totalPlates.getText().trim());
			int rowsOnPage = rowList.size();

			logger.info("Checking first page pagination");
			test.log(Status.INFO, "Checking first page pagination");

			WebElement prevBtn = previouBtn;
			WebElement nextBtn = nestBtn;

			if (!prevBtn.isEnabled()) {
				logger.info("First page: Previous disabled");
				test.log(Status.PASS, "First page: Previous disabled");
			} else {
				logger.error("First page: Previous should be disabled");
				test.log(Status.FAIL, "First page: Previous should be disabled");
			}

			if (nextBtn.isEnabled()) {
				logger.info("First page: Next enabled");
				test.log(Status.PASS, "First page: Next enabled");
			} else {
				logger.error("First page: Next should be enabled");
				test.log(Status.FAIL, "First page: Next should be enabled");
			}

			if (rowsOnPage == total || rowsOnPage <= total) {
				logger.info("Rows on first page match or less than Total Plates: " + rowsOnPage);
				test.log(Status.PASS, "Rows on first page match or less than Total Plates: " + rowsOnPage);
			} else {
				logger.error("Rows on first page (" + rowsOnPage + ") exceed Total Plates (" + total + ")");
				test.log(Status.FAIL, "Rows on first page (" + rowsOnPage + ") exceed Total Plates (" + total + ")");
			}

		} catch (Exception e) {
			logger.error("Error in first page validation: " + e.getMessage());
			test.log(Status.FAIL, "Error in first page validation: " + e.getMessage());
			throw new RuntimeException(e);
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
			int total = Integer.parseInt(totalPlates.getText().trim());

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

	public void checkLastPagePagination() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			// Navigate to last page
			String paginationText = totalItemPagination.getText();
			String[] parts = paginationText.split(" of ");
			int itemsPerPage = Integer.parseInt(parts[0].split("-")[1].trim());
			int totalItems = Integer.parseInt(parts[1].replaceAll("[^0-9]", "").trim());
			int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
			int total = Integer.parseInt(totalPlates.getText().trim());

			driver.findElement(By.xpath("//li[contains(@class,'ant-pagination-item') and @title='" + totalPages + "']"))
					.click();

			new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.textToBePresentInElement(
					driver.findElement(By.xpath("//li[contains(@class,'ant-pagination-item-active')]/a")),
					String.valueOf(totalPages)));

			int expectedRowsOnLastPage = totalItems - itemsPerPage * (totalPages - 1);
			new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.numberOfElementsToBe(
					By.xpath("//tbody[@class='ant-table-tbody']/tr[contains(@class, 'ant-table-row')]"),
					expectedRowsOnLastPage));

			int rowsOnPage = rowList.size();
			WebElement prevBtn = previouBtn;
			WebElement nextBtn = nestBtn;

			logger.info("Checking last page pagination");
			test.log(Status.INFO, "Checking last page pagination");

			if (prevBtn.isEnabled()) {
				logger.info("Last page: Previous enabled");
				test.log(Status.PASS, "Last page: Previous enabled");
			} else {
				logger.error("Last page: Previous should be enabled");
				test.log(Status.FAIL, "Last page: Previous should be enabled");
			}

			if (!nextBtn.isEnabled()) {
				logger.info("Last page: Next disabled");
				test.log(Status.PASS, "Last page: Next disabled");
			} else {
				logger.error("Last page: Next should be disabled");
				test.log(Status.FAIL, "Last page: Next should be disabled");
			}

			if (rowsOnPage == expectedRowsOnLastPage) {
				logger.info("Rows on last page match expected: " + rowsOnPage);
				test.log(Status.PASS, "Rows on last page match expected: " + rowsOnPage);
			} else {
				logger.error("Rows on last page (" + rowsOnPage + ") do NOT match expected (" + expectedRowsOnLastPage
						+ ")");
				test.log(Status.FAIL, "Rows on last page (" + rowsOnPage + ") do NOT match expected ("
						+ expectedRowsOnLastPage + ")");
			}

		} catch (Exception e) {
			logger.error("Error in last page validation: " + e.getMessage());
			test.log(Status.FAIL, "Error in last page validation: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public void selectPlateSource(String plateSourceName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			// Click filter button
			wait.until(ExpectedConditions.elementToBeClickable(plateSourceFilterBtn));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", plateSourceFilterBtn);

			// Wait for dropdown
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[contains(@class,'ant-dropdown') and not(contains(@class,'hidden'))]")));

			// Build dynamic xpath using the given plateSourceName
			String dynamicXpath = String.format(
					"//span[@class='ant-tree-checkbox' and contains(@aria-label,'Select %s')]", plateSourceName);

			WebElement checkBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dynamicXpath)));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkBox);

			logger.info("Checkbox for Plate Source '" + plateSourceName + "' clicked successfully");
			test.log(Status.PASS, "Checkbox for Plate Source '" + plateSourceName + "' clicked successfully");

		} catch (Exception e) {
			logger.error("Failed to select Plate Source: " + plateSourceName, e);
			test.log(Status.FAIL, "Failed to select Plate Source: " + plateSourceName + " - " + e.getMessage());
			throw new AssertionError("Plate Source selection failed", e);
		}
	}

	public void selectFilter() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			// Wait until the OK button is clickable
			WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(ok));
			okBtn.click();

			logger.info("OK button clicked successfully");
			test.log(Status.PASS, "OK button clicked successfully");

		} catch (Exception e) {
			logger.error("Failed to click OK button", e);
			test.log(Status.FAIL, "Failed to click OK button - " + e.getMessage());
			throw new AssertionError("Failed to click OK button", e);
		}
	}

	public void validateFilterList(String expectedPlateSource) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			// Wait until table is refreshed with filtered rows
			wait.until(ExpectedConditions.visibilityOfAllElements(plateSource));

			boolean allMatched = true;

			for (int i = 0; i < plateSource.size(); i++) {
				String actualValue = plateSource.get(i).getText().trim();

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
						"One or more rows did not match the expected Plate Source: " + expectedPlateSource);
			}

			logger.info("All rows matched the expected Plate Source: " + expectedPlateSource);
			test.log(Status.PASS, "All rows matched the expected Plate Source: " + expectedPlateSource);

		} catch (Exception e) {
			logger.error("Validation failed for Plate Source filter: " + expectedPlateSource, e);
			test.log(Status.FAIL,
					"Validation failed for Plate Source filter: " + expectedPlateSource + " - " + e.getMessage());
			throw new AssertionError("Validation failed for Plate Source filter", e);
		}
	}
}
