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

public class WhitelistPlatesPage {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	private WebDriver driver;
	private Properties prop;
	protected ExtentReports extent;
	protected ExtentTest test;
	Faker faker = new Faker();
	private String lastGeneratedPlate;

	// Constructor with config passed from BaseTest
	public WhitelistPlatesPage(WebDriver driver, Properties prop, ExtentTest test) {
		this.driver = driver;
		this.prop = prop;
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

	@FindBy(xpath = "//input[@id='plateSource']")
	private WebElement dropdownsPlateSource;

	@FindBy(xpath = "//div[contains(@class,'ant-select-dropdown') and contains(@class,'ant-select-dropdown-placement-bottomLeft')]//div[contains(@class,'ant-select-item-option-content')]")
	private List<WebElement> choosePlateSource;

	@FindBy(xpath = "//input[@id='plateType']")
	private WebElement dropdownsPlateType;

	@FindBy(xpath = "//input[@id='plateColor']")
	private WebElement dropdownsPlateClor;

	@FindBy(xpath = "//input[@id='exemptionReason_ID']")
	private WebElement dropdownsexemptionReason;

	@FindBy(xpath = "//input[@id='plateStatus']")
	private WebElement dropdownStatus;

	@FindBy(xpath = "//div[contains(@class,'ant-select-dropdown') and not(contains(@class,'hidden'))]//div[contains(@class,'ant-select-item-option-content')]")
	private List<WebElement> choosePlateType;

	@FindBy(xpath = "//div[@id='plateColor_list']//div[contains(@class,'ant-select-item-option-content')]")
	private List<WebElement> choosePlateColor;

	@FindBy(xpath = "//div[contains(@class,'ant-select-dropdown') and @aria-hidden='false']")
	private WebElement activeDropdown;

	@FindBy(xpath = "//div[contains(@class,'ant-select-dropdown') and not(contains(@class,'ant-select-dropdown-hidden'))]//div[contains(@class,'ant-select-item-option-content')]")
	private List<WebElement> chooseExemptionReason;

	@FindBy(xpath = "//div[@id='plateStatus_list']//div[contains(@class,'ant-select-item-option-content')]")
	private List<WebElement> choosePlateStatus;

	@FindBy(xpath = "//button[.='Submit']")
	private WebElement submit;

	@FindBy(xpath = "//input[@id='dateRange']")
	private WebElement startDate;

	@FindBy(xpath = "//td[contains(@class,'ant-picker-cell-in-view')]//div[text()='")
	private WebElement dateSelection;

	@FindBy(xpath = "//div[@class='ant-picker ant-picker-range ant-picker-large ant-picker-outlined ant-picker-status-error css-ch82n6']//input[@placeholder='End date']")
	private WebElement endtDate;

	@FindBy(xpath = "//td[@class='ant-table-cell']")
	private WebElement confirmAddedPlatesNumber;

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

	// Full login with data loaded from login.properties

	public boolean navigateToPlatePage() {
		clickPlates(); // Step 1: Click the plates link/button

		String sucMsg = getPlatesMessage(); // Step 2: Get the page message
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
		String plateNumber = faker.regexify("[A-Z]{2}[0-9]{2}[A-Z]{4}");
		lastGeneratedPlate = plateNumber; // save for later

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(inputPlateNumber));

		inputPlateNumber.sendKeys(plateNumber);

		logger.info("Entered Plate Number: " + plateNumber);
		test.log(Status.INFO, "Entered Plate Number: " + plateNumber);
	}

	public void selectPlateSource(int index) {
		dropdownsPlateSource.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElements(choosePlateType));

		choosePlateType.get(index).click();

		logger.info("Selected Plate Source by index: " + index);
		test.log(Status.INFO, "Selected Plate Source by index: " + index);
	}

	public void selectPlateType(int index) {
		dropdownsPlateType.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElements(choosePlateType));

		choosePlateType.get(index).click();

		logger.info("Selected Plate Source by index: " + index);
		test.log(Status.INFO, "Selected Plate Source by index: " + index);
	}

	public void selectPlateColor(int index) {
		dropdownsPlateClor.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElements(choosePlateType));

		choosePlateType.get(index).click();

		logger.info("Selected Plate Source by index: " + index);
		test.log(Status.INFO, "Selected Plate Source by index: " + index);
	}

	public void selectExemption(int index) {
		dropdownsexemptionReason.click(); // open dropdown

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Wait until visible exemption dropdown is present
		WebElement activeDropdown = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector(".ant-select-dropdown:not(.ant-select-dropdown-hidden)")));

		// Get all options
		List<WebElement> options = activeDropdown.findElements(By.cssSelector(".ant-select-item-option-content"));

		if (index >= options.size()) {
			throw new IllegalArgumentException(
					"Index " + index + " out of bounds. Available options: " + options.size());
		}

		String optionText = options.get(index).getText();

		// Use keyboard navigation on hidden input
		WebElement input = driver.findElement(By.id("exemptionReason_ID"));
		for (int i = 0; i <= index; i++) {
			input.sendKeys(Keys.ARROW_DOWN);
			try {
				Thread.sleep(200); // allow UI to highlight
			} catch (InterruptedException ignored) {
			}
		}
		input.sendKeys(Keys.ENTER);

		// Log success (without waiting for a specific selector)
		logger.info("Selected Exemption Reason by index: " + index + " → " + optionText);
		test.log(Status.INFO, "Selected Exemption Reason by index: " + index + " → " + optionText);
	}

	public void selectStatus(int index) {
		dropdownStatus.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElements(choosePlateType));

		choosePlateType.get(index).click();

		logger.info("Selected Plate Source by index: " + index);
		test.log(Status.INFO, "Selected Plate Source by index: " + index);
	}

	public void startDateRange(int day) {
		startDate.click();
		WebElement dateSelection = driver
				.findElement(By.xpath("//td[contains(@class,'ant-picker-cell-in-view')]//div[text()='" + day + "']"));
		dateSelection.click();
	}

	public void startEndRange(int day) {
		// endtDate.click();
		WebElement dateSelection = driver
				.findElement(By.xpath("//td[contains(@class,'ant-picker-cell-in-view')]//div[text()='" + day + "']"));
		dateSelection.click();
	}

	public void submitPlate() {
		submit.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(confirmAddedPlatesNumber));

		String actualPlate = confirmAddedPlatesNumber.getText().trim();

		if (actualPlate.equalsIgnoreCase(lastGeneratedPlate)) {
			logger.info("Plate number successfully added: " + actualPlate);
			test.log(Status.PASS, "Plate number successfully added: " + actualPlate);
		} else {
			logger.error("Plate number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualPlate);
			test.log(Status.FAIL,
					"Plate number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualPlate);
			throw new AssertionError(
					"Plate number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualPlate);

		}
	}

}
