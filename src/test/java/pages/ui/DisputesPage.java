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
import java.util.NoSuchElementException;
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

public class DisputesPage {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	private WebDriver driver;
	private Properties prop;
	protected ExtentReports extent;
	protected ExtentTest test;
	Faker faker = new Faker();
	private String lastEnteredFineNumber;
	private String lastGeneratedCRM;
	private String lastGeneratedEmail;
	private String lastGeneratedPhone;

	// Constructor with config passed from BaseTest
	public DisputesPage(WebDriver driver, Properties prop, ExtentTest test) {
		this.driver = driver;
		this.prop = prop;
		this.test = test;
		PageFactory.initElements(driver, this);
	}

	// ======== STEP WRAPPERS ========
	private <T> T step(String description, Supplier<T> action) {
		test.info("Step: " + description);
		try {
			T result = action.get();
			// test.pass("Step passed: " + description);
			return result;
		} catch (Exception e) {
			test.fail("Step failed: " + description + " → " + e.getMessage());
			throw e;
		}
	}

	private void step(String description, Runnable action) {
		step(description, () -> {
			action.run();
			return true;
		});
	}

	// WebElements using @FindBy

	@FindBy(xpath = "//button[.='Add New']")
	private WebElement addNewDisputeButton;

	@FindBy(xpath = "//div[contains(text(),'Add New Dispute')]")
	private WebElement addDisputePageMessage;

	@FindBy(xpath = "//input[@id='fineId']")
	private WebElement fineNumberField;

	@FindBy(xpath = "//input[@id='dispute_Reason']")
	private WebElement reasonField;

	@FindBy(xpath = "//input[@id='crM_Ref']")
	private WebElement crmField;

	@FindBy(xpath = "//input[@id='email']")
	private WebElement emailField;

	@FindBy(xpath = "//input[@id='phone']")
	private WebElement phoneField;

	@FindBy(xpath = "//textarea[@id='address']")
	private WebElement addressField;

	@FindBy(xpath = "//div[contains(@class,'ant-select-dropdown') and not(contains(@class,'hidden'))]//div[contains(@class,'ant-select-item-option-content')]")
	private List<WebElement> chooseList;

	@FindBy(xpath = "//input[@id='department']")
	private WebElement selectDepartment;

	@FindBy(xpath = "//input[@id='payment_Type']")
	private WebElement selectPayment;

	@FindBy(xpath = "//button[.='Submit']")
	private WebElement submit;

	// Locators for each column (index-based)
	@FindBy(xpath = "(//td[@class='ant-table-cell'])[1]")
	private WebElement departmentCell;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[2]")
	private WebElement paymentCell;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[3]")
	private WebElement phoneCell;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[4]")
	private WebElement crmCell;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[1]")
	private WebElement fineCell;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[6]")
	private WebElement statusCell;

	@FindBy(xpath = "//span[@aria-label='more']/ancestor::button")
	private WebElement threeDot;

	@FindBy(xpath = "//ul[contains(@class,'ant-dropdown-menu')]//li//span[@class='ant-dropdown-menu-title-content']")
	private List<WebElement> menuList;

	@FindBy(xpath = "//span[normalize-space()='View']")
	private WebElement viewButton;

	@FindBy(xpath = "//h4[contains(.,'Dispute Review')]")
	private WebElement viewDisputePageMessage;

	@FindBy(xpath = "//span[normalize-space()='Edit']")
	private WebElement editButton;

	@FindBy(xpath = "//div[contains(text(),'Edit Dispute')]")
	private WebElement editDisputePageMessage;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[6]")
	private List<WebElement> departmentSource;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[2]")
	private List<WebElement> paymentSource;

	@FindBy(xpath = "//div[@class='ant-statistic-title' and text()='Total Disputes']/following-sibling::div//span[@class='ant-statistic-content-value-int']")
	private WebElement totalDisputes;

	@FindBy(xpath = "//tbody[@class='ant-table-tbody']/tr[contains(@class, 'ant-table-row')]")
	private List<WebElement> rowList;

	@FindBy(xpath = "//li[contains(@class,'ant-pagination-total-text')]")
	private WebElement totalItemPagination;

	@FindBy(xpath = "//li[contains(@class,'ant-pagination-prev')]/button")
	private WebElement previouBtn;

	@FindBy(xpath = "//li[contains(@class,'ant-pagination-next')]/button")
	private WebElement nestBtn;

	@FindBy(xpath = "//input[@id='sourceUser']")
	private WebElement source;

	@FindBy(xpath = "//div[.//strong[text()='Fine Number:']]/following-sibling::div")
	private WebElement viewFineNumber;

	@FindBy(xpath = "//div[.//strong[text()='Department:']]/following-sibling::div")
	private WebElement viewDepartment;

	@FindBy(xpath = "//div[.//strong[text()='Payment Type:']]/following-sibling::div")
	private WebElement viewPaymentType;

	@FindBy(xpath = "//div[.//strong[text()='Dispute Reason:']]/following-sibling::div")
	private WebElement viewDisputeReason;

	@FindBy(xpath = "//div[.//strong[text()='Email:']]/following-sibling::div")
	private WebElement viewEmail;

	@FindBy(xpath = "//div[.//strong[text()='Phone Number:']]/following-sibling::div")
	private WebElement viewPhoneNumber;

	@FindBy(xpath = "//div[.//strong[text()='CRM Reference:']]/following-sibling::div")
	private WebElement viewCRMReference;

	@FindBy(xpath = "//div[.//strong[text()='Address:']]/following-sibling::div")
	private WebElement viewAddress;

	@FindBy(xpath = "//div[.//strong[text()='Dispute Date:']]/following-sibling::div")
	private WebElement viewDisputeDate;

	// Actions

	// Full login with data loaded from login.properties

	public boolean navigateToAddDisputePage() {
		return step("Navigate to Add Dispute Page", () -> {
			addNewDisputeButton.click();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			String sucMsg = "";

			try {
				WebElement messageElement = wait.until(ExpectedConditions.visibilityOf(addDisputePageMessage));
				sucMsg = messageElement.getText().trim();
			} catch (TimeoutException e) {
				logger.error("Add New Dispute page message not found within timeout.");
				test.log(Status.FAIL, "Add New Dispute page message not found within timeout.");
				throw new AssertionError("Add New Dispute page message not found within timeout.", e);
			}

			if (sucMsg != null && !sucMsg.isEmpty()) {
				logger.info("Navigated to Add New Dispute page: " + sucMsg);
				test.log(Status.PASS, "Navigated to Add New Dispute page: " + sucMsg);
				return true;
			} else {
				logger.error("Failed to navigate to Add New Dispute page. Message was empty.");
				test.log(Status.FAIL, "Failed to navigate to Add New Dispute page. Message was empty.");
				throw new AssertionError("Failed to navigate to Add New Dispute page. Message was empty.");
			}
		});
	}

	private void enterValue(WebElement field, String value, String fieldName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(field));

		((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", field);
		field.sendKeys(value);

		logger.info("Entered " + fieldName + ": " + value);
		test.log(Status.PASS, "Entered " + fieldName + ": " + value);
	}

	public void enterFineNumber(String fineNumber) {
		step("Enter Fine Number", () -> {
			enterValue(fineNumberField, fineNumber, "Fine Number");
			lastEnteredFineNumber = fineNumber; // store for validation
		});
	}

	public void enterReason() {
		step("Enter Reason", () -> {
			String reason = faker.lorem().sentence();
			enterValue(reasonField, reason, "Reason");
		});
	}

	public void enterCRM() {
		step("Enter CRM", () -> {
			String crm = faker.regexify("[A-Z]{2}[0-9]{2}[A-Z]{4}");
			lastGeneratedCRM = crm;
			enterValue(crmField, crm, "CRM");
		});
	}

	public void enterEmail() {
		step("Enter Email", () -> {
			String email = faker.internet().emailAddress();
			lastGeneratedEmail = email;
			enterValue(emailField, email, "Email");
		});
	}

	public void enterPhone() {
		step("Enter Phone", () -> {
			String ph = faker.regexify("[0-9]{10}");
			lastGeneratedPhone = ph;
			enterValue(phoneField, ph, "Phone");
		});
	}

	public void enterAddress() {
		step("Enter Address", () -> {
			String add = faker.address().fullAddress();
			enterValue(addressField, add, "Address");
		});
	}

	public void enterSourceUser() {
		step("Enter Source Details", () -> {
			String[] sources = { "Self Complaint", "Internal Complaint", "Customer Care Complaint",
					"Third-Party Complaint" };
			String source1 = sources[new java.util.Random().nextInt(sources.length)];
			sendKeysWithWait(source, source1, "Source Name");
		});
	}

	private void sendKeysWithWait(WebElement element, String value, String fieldName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(element));
		element.sendKeys(value);
		logger.info("Entered " + fieldName + ": " + value);
		test.pass("Entered " + fieldName + ": " + value);
	}

	public void selectDepartment(String departmentName) {
		step("Select Department: " + departmentName, () -> {
			selectDepartment.click();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfAllElements(chooseList));

			for (WebElement option : chooseList) {
				if (option.getText().equalsIgnoreCase(departmentName)) {
					option.click();
					logger.info("Selected Department by text: " + departmentName);
					test.log(Status.PASS, "Selected department by text: " + departmentName);
					return;
				}
			}

			throw new RuntimeException("Department not found: " + departmentName);
		});
	}

	public void selectPaymentType(String paymentType) {
		step("Select Payment Type: " + paymentType, () -> {
			selectPayment.click();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfAllElements(chooseList));

			for (WebElement option : chooseList) {
				if (option.getText().equalsIgnoreCase(paymentType)) {
					option.click();
					logger.info("Selected Payment Type by text: " + paymentType);
					test.log(Status.PASS, "Selected payment type by text: " + paymentType);
					return;
				}
			}

			throw new RuntimeException("Payment Type not found: " + paymentType);
		});
	}

	public void validateDisputeAdded() {
		step("Validate Dispute Added", () -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(crmCell));

			String actualPlate = crmCell.getText().trim();

			if (actualPlate.equalsIgnoreCase(lastGeneratedCRM)) {
				logger.info("CRM number successfully added: " + actualPlate);
				test.log(Status.PASS, "CRM number successfully added: " + actualPlate);
			} else {
				logger.error("CRM number mismatch! Expected: " + lastGeneratedCRM + ", but found: " + actualPlate);
				test.log(Status.FAIL,
						"CRM number mismatch! Expected: " + lastGeneratedCRM + ", but found: " + actualPlate);

				captureBrowserLogs();
				throw new AssertionError(
						"CRM number mismatch! Expected: " + lastGeneratedCRM + ", but found: " + actualPlate);
			}
		});
	}

	public void validateFineNumber() {
		step("Validate Fine Number", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(fineCell));

			String actualFine = fineCell.getText().trim();

			if (actualFine.equalsIgnoreCase(lastEnteredFineNumber)) {
				logger.info("Fine number successfully added: " + actualFine);
				test.log(Status.PASS, "Fine number successfully added: " + actualFine);
			} else {
				logger.error("Fine number mismatch! Expected: " + lastEnteredFineNumber + ", but found: " + actualFine);
				test.log(Status.FAIL,
						"Fine number mismatch! Expected: " + lastEnteredFineNumber + ", but found: " + actualFine);
				throw new AssertionError(
						"Fine number mismatch! Expected: " + lastEnteredFineNumber + ", but found: " + actualFine);
			}
		});
	}

	public void validateDepartment(String expectedSource) {
		step("Validate Department", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(departmentCell));

			String actualSource = departmentCell.getText().trim();

			if (actualSource.equalsIgnoreCase(expectedSource)) {
				logger.info("Department successfully validated: " + actualSource);
				test.log(Status.PASS, "Department successfully validated: " + actualSource);
			} else {
				logger.error("Department mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
				test.log(Status.FAIL,
						"Department mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
				throw new AssertionError(
						"Department mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
			}
		});
	}

	public void validatePaymentType(String expectedSource) {
		step("Validate Payment Type", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(paymentCell));

			String actualSource = paymentCell.getText().trim();

			if (actualSource.equalsIgnoreCase(expectedSource)) {
				logger.info("Payment type successfully validated: " + actualSource);
				test.log(Status.PASS, "Payment type successfully validated: " + actualSource);
			} else {
				logger.error("Payment type mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
				test.log(Status.FAIL,
						"Payment type mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
				throw new AssertionError(
						"Payment type mismatch! Expected: " + expectedSource + ", but found: " + actualSource);
			}
		});
	}

	public void validatePhoneNumber() {
		step("Validate Phone Number", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(phoneCell));

			String actualPlate = phoneCell.getText().trim();

			if (actualPlate.equalsIgnoreCase(lastGeneratedPhone)) {
				logger.info("Phone number successfully added: " + actualPlate);
				test.log(Status.PASS, "Phone number successfully added: " + actualPlate);
			} else {
				logger.error("Phone number mismatch! Expected: " + lastGeneratedPhone + ", but found: " + actualPlate);
				test.log(Status.FAIL,
						"Phone number mismatch! Expected: " + lastGeneratedPhone + ", but found: " + actualPlate);

				captureBrowserLogs();
				throw new AssertionError(
						"Phone number mismatch! Expected: " + lastGeneratedPhone + ", but found: " + actualPlate);
			}
		});
	}

	public void validateDisputeStatus() {
		step("Validate Dispute Status", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(statusCell));

			String actualStatus = statusCell.getText().trim();

			if (actualStatus.equalsIgnoreCase("Pending")) {
				logger.info("Dispute Status found as: " + actualStatus);
				test.log(Status.PASS, "Dispute Status found as: " + actualStatus);
			} else {
				logger.error("Dispute Status mismatch! Expected: Pending, but found: " + actualStatus);
				test.log(Status.FAIL, "Dispute Status mismatch! Expected: Pending, but found: " + actualStatus);
				throw new AssertionError("Dispute Status mismatch! Expected: Pending, but found: " + actualStatus);
			}
		});
	}

	// Reusable helper for capturing console logs
	private void captureBrowserLogs() {
		try {
			LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
			for (LogEntry entry : logs) {
				logger.error("Browser Log: " + entry.getLevel() + " " + entry.getMessage());
				test.log(Status.INFO, "Browser Log: " + entry.getLevel() + " " + entry.getMessage());
			}
		} catch (Exception e) {
			logger.warn("Could not capture browser logs: " + e.getMessage());
		}
	}

	public void clickThreeDotAndValidateMenuList() {
		step("Click three-dot menu and validate menu items", () -> {
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

	public boolean navigateToViewDisputePage() {
		return step("Navigate to View Plate Page", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			String sucMsg = "";

			try {
				WebElement messageElement = wait.until(ExpectedConditions.visibilityOf(viewDisputePageMessage));
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
		});
	}

	public boolean navigateToEditDisputePage() {
		return step("Navigate to Edit Plate Page", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			String sucMsg = "";

			try {
				WebElement messageElement = wait.until(ExpectedConditions.visibilityOf(editDisputePageMessage));
				sucMsg = messageElement.getText().trim();
			} catch (TimeoutException e) {
				logger.error("Edit Dispute page message not found within timeout.");
				test.log(Status.FAIL, "Edit Dispute page message not found within timeout.");
				throw new AssertionError("Edit Plate page message not found within timeout.", e);
			}

			if (sucMsg != null && !sucMsg.isEmpty()) {
				logger.info("Navigated to Edit Dispute page: " + sucMsg);
				test.log(Status.PASS, "Navigated to Edit Dispute page: " + sucMsg);
				return true;
			} else {
				logger.error("Failed to navigate to Edit Dispute page. Message was empty.");
				test.log(Status.FAIL, "Failed to navigate to Edit Dispute page. Message was empty.");
				throw new AssertionError("Failed to navigate to Edit Dispute page. Message was empty.");
			}
		});
	}

	public void clearText(WebElement element) {
		step("Clear text from field", () -> {
			try {
				element.click();
				element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
				element.sendKeys(Keys.DELETE);

				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].value=''; "
						+ "arguments[0].dispatchEvent(new Event('input', { bubbles: true })); "
						+ "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", element);

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
		});
	}

	public void clearAllFields() {
		step("Clear all input fields", () -> {
			clearText(crmField);
		});
	}

	public void validateDepartmentFilterList(String expectedPlateSource) {
		step("Validate Department Filter List for: " + expectedPlateSource, () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			wait.until(ExpectedConditions.visibilityOfAllElements(departmentSource));
			boolean allMatched = true;

			for (int i = 0; i < departmentSource.size(); i++) {
				String actualValue = departmentSource.get(i).getText().trim();

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
						"One or more rows did not match the expected department Source: " + expectedPlateSource);
			}
		});
	}

	public void validatePaymentFilterList(String expectedPlateSource) {
		step("Validate Payment Filter List for: " + expectedPlateSource, () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			wait.until(ExpectedConditions.visibilityOfAllElements(paymentSource));
			boolean allMatched = true;

			for (int i = 0; i < paymentSource.size(); i++) {
				String actualValue = paymentSource.get(i).getText().trim();

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
						"One or more rows did not match the expected payment Source: " + expectedPlateSource);
			}
		});
	}

	public void validateTotalDisputesCount() {
		step("Validate Total Disputes Count", () -> {
			try {
				int total = Integer.parseInt(totalDisputes.getText().trim());
				int rowsOnPage = rowList.size();

				if (rowsOnPage <= total) {
					logger.info("Row count (" + rowsOnPage + ") matches or is less than total dispute (" + total + ")");
					test.log(Status.PASS,
							"Row count (" + rowsOnPage + ") matches or is less than total dispute (" + total + ")");
				} else {
					logger.error("Row count (" + rowsOnPage + ") exceeds total dispute (" + total + ")");
					test.log(Status.FAIL, "Row count (" + rowsOnPage + ") exceeds total dispute (" + total + ")");
				}
			} catch (Exception e) {
				logger.error("Error validating total dispute: " + e.getMessage());
				test.log(Status.FAIL, "Error validating total dispute: " + e.getMessage());
				throw new RuntimeException(e);
			}
		});
	}

	public void checkMiddlePagesPagination() {
		step("Validate middle pages pagination", () -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String paginationText = totalItemPagination.getText();
			String[] parts = paginationText.split(" of ");
			int itemsPerPage = Integer.parseInt(parts[0].split("-")[1].trim());
			int totalItems = Integer.parseInt(parts[1].replaceAll("[^0-9]", "").trim());
			int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
			int total = Integer.parseInt(totalDisputes.getText().trim());

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
		});
	}

	public void getDisputeDetails() {
		step("Validate and print Fine Number", () -> validateAndPrint("Fine Number", viewFineNumber.getText()));
		step("Validate and print Department", () -> validateAndPrint("Department", viewDepartment.getText()));
		step("Validate and print Payment Type", () -> validateAndPrint("Payment Type", viewPaymentType.getText()));
		step("Validate and print Dispute Reason",
				() -> validateAndPrint("Dispute Reason", viewDisputeReason.getText()));
		step("Validate and print Email", () -> validateAndPrint("Emaile", viewEmail.getText()));
		step("Validate and print Phone Number", () -> validateAndPrint("Phone Number", viewPhoneNumber.getText()));
		step("Validate and print CRM Reference", () -> validateAndPrint("CRM Reference", viewCRMReference.getText()));
		step("Validate and print Address", () -> validateAndPrint("Address", viewAddress.getText()));
		step("Validate and print Dispute Date", () -> validateAndPrint("Dispute Date", viewDisputeDate.getText()));

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

}
