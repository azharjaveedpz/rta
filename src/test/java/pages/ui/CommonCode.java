package pages.ui;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.github.javafaker.Faker;

import tests.ui.TestLaunchBrowser;

public class CommonCode {

	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);
	private String selectedStartDate;
	private String selectedEndDate;
	private WebDriver driver;
	protected ExtentReports extent;
	protected ExtentTest test;
	Faker faker = new Faker();

	@FindBy(xpath = "//button[.//span[text()='Submit']]")
	private WebElement submit;

	@FindBy(xpath = "//div[@class='ant-notification-notice-message']")
	private WebElement toastMessage;

	@FindBy(xpath = "//button[.='Update']")
	private WebElement update;

	@FindBy(xpath = "//span[normalize-space()='Edit']")
	private WebElement editButton;

	@FindBy(xpath = "//span[normalize-space()='View']")
	private WebElement viewButton;

	@FindBy(id = "document")
	private WebElement imageUpload;

	@FindBy(xpath = "//input[@placeholder='Search...']")
	private WebElement searchInput;

	@FindBy(xpath = "//tr[contains(@class,'ant-table-row')]//td[3]")
	private List<WebElement> plateNameTable;

	@FindBy(xpath = "//tr[contains(@class,'ant-table-row')]//td[4]")
	private List<WebElement> plateSourceTable;

	@FindBy(xpath = "//div[contains(@class,'ant-select') and contains(@class,'ant-select-single')]//div[@class='ant-select-selector']")
	private WebElement filter;

	@FindBy(xpath = "//div[contains(@class,'ant-select-item-option-content')]")
	private List<WebElement> filterOptions;

	@FindBy(xpath = "//span[@class='ant-select-selection-item']")
	private WebElement selectedValue;

	@FindBy(xpath = "//tbody[@class='ant-table-tbody']/tr[contains(@class, 'ant-table-row')]")
	private List<WebElement> rowList;

	@FindBy(xpath = "//li[contains(@class,'ant-pagination-prev')]/button")
	private WebElement previouBtn;

	@FindBy(xpath = "//li[contains(@class,'ant-pagination-next')]/button")
	private WebElement nestBtn;

	@FindBy(xpath = "//li[contains(@class,'ant-pagination-total-text')]")
	private WebElement totalItemPagination;

	@FindBy(xpath = "(//span[@role='button' and contains(@class,'ant-table-filter-trigger')])[1]")
	private WebElement firstFilterBtn;

	@FindBy(xpath = "(//span[@role='button' and contains(@class,'ant-table-filter-trigger')])[2]")
	private WebElement secondFilterBtn;

	@FindBy(xpath = "(//span[@role='button' and contains(@class,'ant-table-filter-trigger')])[3]")
	private WebElement thirdFilterBtn;

	@FindBy(xpath = "(//span[@role='button' and contains(@class,'ant-table-filter-trigger')])[4]")
	private WebElement fourthFilterBtn;

	@FindBy(xpath = "//button[normalize-space()='OK']")
	private WebElement ok;

	@FindBy(xpath = "(//button[.='OK'])[2]")
	private WebElement Twook;

	@FindBy(xpath = "(//button[.='OK'])[3]")
	private WebElement Threeok;
	@FindBy(xpath = "//div[contains(text(),'No data')]")
	private WebElement noData;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[1]")
	private List<WebElement> licenseNameTable;

	@FindBy(xpath = "(//td[@class='ant-table-cell'])[2]")
	private List<WebElement> businessTable;

	@FindBy(xpath = "//input[@id='dateRange']")
	private WebElement startDate;

	@FindBy(xpath = "//input[@id='actualDisputeDate']")
	private WebElement disputeDate;

	@FindBy(xpath = "//input[@placeholder='Start date']")
	private WebElement startSearch;

	@FindBy(xpath = "//tr[contains(@class,'ant-table-row')]//td[2]")
	private List<WebElement> fineNumberTable;

	@FindBy(xpath = "//tr[contains(@class,'ant-table-row')]//td[4]")
	private List<WebElement> phoneNumberTable;

	@FindBy(xpath = "//tr[contains(@class,'ant-table-row')]//td[5]")
	private List<WebElement> crmReferenceTable;

	@FindBy(xpath = "//tr[contains(@class,'ant-table-row')]//td[6]")
	private List<WebElement> emailTable;

	@FindBy(xpath = "//tr[contains(@class,'ant-table-row')]//td[2]")
	private List<WebElement> tradeLicenseTable;

	public CommonCode(WebDriver driver, Properties prop, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		PageFactory.initElements(driver, this);
	}

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

	public void submit() {
		step("Click Submit button", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(submit)).click();

			logger.info("Clicked on Submit  button");
			test.log(Status.PASS, "Clicked on Submit  button");
		});
	}

	public void validateToastMessage(String expectedMessage) {
		step("Validate toast message", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(toastMessage));

			String actualMessage = toastMessage.getText().trim();

			if (actualMessage.equalsIgnoreCase(expectedMessage)) {
				logger.info("Toast message correctly displayed: " + actualMessage);
				test.pass("Toast message correctly displayed: " + actualMessage);
			} else {
				logger.error("Toast message mismatch! Expected: " + expectedMessage + ", but found: " + actualMessage);
				test.fail("Toast message mismatch! Expected: " + expectedMessage + ", but found: " + actualMessage);
				throw new AssertionError(
						"Toast message mismatch! Expected: " + expectedMessage + ", but found: " + actualMessage);
			}
		});
	}

	public void update() {
		step("Click Update button", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(update)).click();

			logger.info("Clicked on Update button");
			test.pass("Clicked on Update button");
		});
	}

	public void edit() {
		step("Click Edit button", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();

			logger.info("Clicked on Edit button");
			test.pass("Clicked on Edit button");
		});
	}

	public void view() {
		step("Click View button", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(viewButton)).click();

			logger.info("Clicked on View button");
			test.pass("Clicked on View button");
		});
	}

	public void uploadPhoto() {
		step("Upload docs", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("document")));

			String filePath = System.getProperty("user.dir") + "/src/test/java/utils/sample.png"; // use jpg/jpeg
			imageUpload.sendKeys(filePath);

			logger.info("Uploaded photo from: " + filePath);
			test.pass("Uploaded photo from: " + filePath);
		});
	}

	public void searchAndValidateResult(String searchType, String expectedValue) {
		step("Enter search value: " + expectedValue, () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(searchInput));

			if (expectedValue == null || expectedValue.trim().isEmpty()) {
				throw new IllegalArgumentException("Search value cannot be null or empty!");
			}

			searchInput.clear();
			searchInput.sendKeys(expectedValue);

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

			wait.until(ExpectedConditions.visibilityOfAllElements(plateNameTable));
		});

		step("Fetch search results", () -> {

			List<String> plateNumbers = plateNameTable.stream().map(e -> e.getText().trim()).toList();
			List<String> plateSources = plateSourceTable.stream().map(e -> e.getText().trim()).toList();
			List<String> tradeLicenses = tradeLicenseTable.stream().map(e -> e.getText().trim()).toList();
			List<String> fineNumbers = fineNumberTable.stream().map(e -> e.getText().trim()).toList();
			List<String> phoneNumbers = phoneNumberTable.stream().map(e -> e.getText().trim()).toList();
			List<String> crmReferences = crmReferenceTable.stream().map(e -> e.getText().trim()).toList();
			List<String> emails = emailTable.stream().map(e -> e.getText().trim()).toList();

			List<String> platesNumber = tradeLicenseTable.stream().map(e -> e.getText().trim()).toList();
			List<String> licenseNumbers = tradeLicenseTable.stream().map(e -> e.getText().trim()).toList();

			List<String> licenceNumbers = new ArrayList<>();
			List<String> businesses = new ArrayList<>();

			if (searchType.equalsIgnoreCase("trade number") || searchType.equalsIgnoreCase("business")
					|| searchType.equalsIgnoreCase("zone") || searchType.equalsIgnoreCase("area")) {
				licenceNumbers = licenseNameTable.stream().map(e -> e.getText().trim()).toList();
				businesses = businessTable.stream().map(e -> e.getText().trim()).toList();
			}

			switch (searchType.toLowerCase()) {
			case "plate number" -> logAndValidate(searchType, expectedValue, plateNumbers);
			case "plates number" -> logAndValidate(searchType, expectedValue, platesNumber);
			case "license number" -> logAndValidate(searchType, expectedValue, licenseNumbers);

			case "fine number" -> logAndValidate(searchType, expectedValue, fineNumbers);
			case "crm reference" -> logAndValidate(searchType, expectedValue, crmReferences);
			case "phone number" -> logAndValidate(searchType, expectedValue, phoneNumbers);
			case "email" -> logAndValidate(searchType, expectedValue, emails);

			case "trade number", "business", "zone", "area" -> {
				logger.info("Validating Licence & Business columns | Total: " + licenceNumbers.size());
				test.info("Validating Licence & Business columns | Total: " + licenceNumbers.size());

				for (int i = 0; i < licenceNumbers.size(); i++) {
					String rowData = String.format("Row %d => Licence: %s | Business: %s", i + 1, licenceNumbers.get(i),
							businesses.size() > i ? businesses.get(i) : "N/A");
					logger.info(rowData);
					test.info(rowData);
				}

				if (!licenceNumbers.isEmpty() || !businesses.isEmpty()) {
					validateField(searchType, expectedValue, new String[] { licenceNumbers.get(0) },
							new String[] { businesses.get(0) });
				} else {
					String errorMsg = "No rows found for validation for searchType: " + searchType;
					logger.error(errorMsg);
					test.fail(errorMsg);
					throw new AssertionError(errorMsg);
				}
			}

			default -> {
				String warnMsg = "Unknown search type: " + searchType;
				logger.warn(warnMsg);
				test.warning(warnMsg);
			}
			}
		});
	}

	private void logAndValidate(String searchType, String expectedValue, List<String> values) {
		logger.info("Validating " + searchType + " | Total: " + values.size());
		test.info("Validating " + searchType + " | Total: " + values.size());

		boolean isPartialMatch = searchType.equalsIgnoreCase("plates number")
				|| searchType.equalsIgnoreCase("license number");

		boolean found = false;
		String expectedLower = expectedValue.toLowerCase();

		for (int i = 0; i < values.size(); i++) {
			String rowValue = values.get(i);
			String rowData = "Row " + (i + 1) + " => " + searchType + ": " + rowValue;
			logger.info(rowData);
			test.info(rowData);

			if (isPartialMatch) {
				if (rowValue.toLowerCase().contains(expectedLower)) {
					found = true;
					logger.info("Match found: " + expectedValue + " in " + rowValue);
					test.pass("Match found: " + expectedValue + " in " + rowValue);
					break;
				}
			}
		}

		if (!isPartialMatch) {

			validateField(searchType, expectedValue, values.toArray(new String[0]));
		} else if (!found) {
			String errorMsg = searchType + " mismatch! Expected to contain '" + expectedValue + "' but found none in: "
					+ values;
			logger.error(errorMsg);
			test.fail(errorMsg);
			throw new AssertionError(errorMsg);
		}
	}

	/**
	 * For single-column validations (partial + first-letter strictness)
	 */
	private void validateField(String fieldName, String expected, String... actualValues) {
		String expectedLower = expected.toLowerCase();

		for (String actual : actualValues) {
			if (actual != null) {
				String actualLower = actual.toLowerCase();
				boolean startsWithSameFirstLetter = actualLower.startsWith(expectedLower.substring(0, 1));
				boolean containsPartial = actualLower.contains(expectedLower);

				if (startsWithSameFirstLetter && containsPartial) {
					logger.info(fieldName + " matches: starts with '" + expected.charAt(0)
							+ "' and contains expected value: " + expected + " | Found in: " + actual);
					test.log(Status.PASS, fieldName + " matches: starts with '" + expected.charAt(0)
							+ "' and contains expected value: " + expected + " | Found in: " + actual);
					return;
				}
			}
		}

		String message = String.format(
				"%s mismatch! Expected to start with '%s' and contain '%s', but found none in: %s", fieldName,
				expected.charAt(0), expected, Arrays.toString(actualValues));
		logger.error(message);
		test.log(Status.FAIL, message);
		throw new AssertionError(message);
	}

	/**
	 * For two-column validations (Trade Number + Business, Zone + Area)
	 */
	private void validateField(String fieldName, String expected, String[] actualList1, String[] actualList2) {
		String expectedLower = expected.toLowerCase();

		for (int i = 0; i < Math.max(actualList1.length, actualList2.length); i++) {
			String actual1 = i < actualList1.length ? actualList1[i] : null;
			String actual2 = i < actualList2.length ? actualList2[i] : null;

			boolean match1 = actual1 != null && actual1.toLowerCase().startsWith(expectedLower.substring(0, 1))
					&& actual1.toLowerCase().contains(expectedLower);

			boolean match2 = actual2 != null && actual2.toLowerCase().startsWith(expectedLower.substring(0, 1))
					&& actual2.toLowerCase().contains(expectedLower);

			if (match1 || match2) {
				String found = match1 ? actual1 : actual2;
				logger.info(fieldName + " matches: starts with '" + expected.charAt(0)
						+ "' and contains expected value: " + expected + " | Found in: " + found);
				test.log(Status.PASS, fieldName + " matches: starts with '" + expected.charAt(0)
						+ "' and contains expected value: " + expected + " | Found in: " + found);
				return;
			}
		}

		String message = String.format(
				"%s mismatch! Expected to start with '%s' and contain '%s', but found none in lists => %s / %s",
				fieldName, expected.charAt(0), expected, Arrays.toString(actualList1), Arrays.toString(actualList2));
		logger.error(message);
		test.log(Status.FAIL, message);
		throw new AssertionError(message);
	}

	public void selectFilterByText(String text) {
		step("Select filter: " + text, () -> {
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
		});
	}

	public void checkFirstPagePagination() {
		step("Validate First Page Pagination", () -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			try {
				logger.info("Checking first page pagination");
				test.log(Status.INFO, "Checking first page pagination");

				WebElement prevBtn = previouBtn;
				WebElement nextBtn = nestBtn;
				int rowsOnPage = rowList.size();

				if (!prevBtn.isEnabled()) {
					logger.info("First page: Previous disabled");
					test.log(Status.PASS, "First page: Previous disabled");
				} else {
					logger.error("First page: Previous should be disabled");
					test.log(Status.FAIL, "First page: Previous should be disabled");
					throw new AssertionError("First page: Previous should be disabled");
				}

				if (nextBtn.isEnabled()) {
					logger.info("First page: Next enabled");
					test.log(Status.PASS, "First page: Next enabled");
				} else {
					logger.error("First page: Next should be enabled");
					test.log(Status.FAIL, "First page: Next should be enabled");
					throw new AssertionError("First page: Previous should be enabled");
				}

				if (rowsOnPage > 0) {
					logger.info("Rows are displayed on first page: " + rowsOnPage);
					test.log(Status.PASS, "Rows are displayed on first page: " + rowsOnPage);
				} else {
					logger.error("No rows displayed on first page!");
					test.log(Status.FAIL, "No rows displayed on first page!");
					throw new AssertionError("No rows displayed on first page!");
				}

			} catch (Exception e) {
				logger.error("Error in first page pagination validation: " + e.getMessage());
				test.log(Status.FAIL, "Error in first page pagination validation: " + e.getMessage());
				throw new RuntimeException(e);
			}
		});
	}

	public void checkLastPagePagination() {
		step("Validate Last Page Pagination", () -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			try {
				logger.info("Checking last page pagination");
				test.log(Status.INFO, "Checking last page pagination");

				String paginationText = totalItemPagination.getText(); // e.g. "1-10 of 35"
				String[] parts = paginationText.split(" of ");
				int itemsPerPage = Integer.parseInt(parts[0].split("-")[1].trim());
				int totalItems = Integer.parseInt(parts[1].replaceAll("[^0-9]", "").trim());
				int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

				WebElement lastPageBtn = driver.findElement(
						By.xpath("//li[contains(@class,'ant-pagination-item') and @title='" + totalPages + "']"));
				lastPageBtn.click();

				new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.textToBePresentInElement(
						driver.findElement(By.xpath("//li[contains(@class,'ant-pagination-item-active')]/a")),
						String.valueOf(totalPages)));

				int rowsOnPage = rowList.size();
				WebElement prevBtn = previouBtn;
				WebElement nextBtn = nestBtn;

				if (prevBtn.isEnabled()) {
					logger.info("Last page: Previous enabled");
					test.log(Status.PASS, "Last page: Previous enabled");
				} else {
					logger.error("Last page: Previous should be enabled");
					test.log(Status.FAIL, "Last page: Previous should be enabled");
					throw new AssertionError("Last page: Previous should be enabled");
				}

				if (!nextBtn.isEnabled()) {
					logger.info("Last page: Next disabled");
					test.log(Status.PASS, "Last page: Next disabled");
				} else {
					logger.error("Last page: Next should be disabled");
					test.log(Status.FAIL, "Last page: Next should be disabled");
					throw new AssertionError("Last page: Next should be disabled");

				}

				logger.info("Rows displayed on last page: " + rowsOnPage);
				test.log(Status.PASS, "Rows displayed on last page: " + rowsOnPage);

			} catch (Exception e) {
				logger.error("Error in last page validation: " + e.getMessage());
				test.log(Status.FAIL, "Error in last page validation: " + e.getMessage());
				throw new RuntimeException(e);
			}
		});
	}

	public void selectFilterButton(String optioneName) {
		step("Select filter checkbox: " + optioneName, () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			try {

				wait.until(ExpectedConditions.elementToBeClickable(firstFilterBtn));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstFilterBtn);

				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//div[contains(@class,'ant-dropdown') and not(contains(@class,'hidden'))]")));

				String dynamicXpath = String.format(
						"//span[@class='ant-tree-checkbox' and contains(@aria-label,'Select %s')]", optioneName);

				WebElement checkBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dynamicXpath)));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkBox);

				logger.info("Checkbox for filter Source '" + optioneName + "' clicked successfully");
				test.log(Status.PASS, "Checkbox for filter Source '" + optioneName + "' clicked successfully");

			} catch (Exception e) {
				logger.error("Failed to select filter Source: " + optioneName, e);
				test.log(Status.FAIL, "Failed to select filter Source: " + optioneName + " - " + e.getMessage());
				throw new AssertionError("filter Source selection failed", e);
			}
		});
	}

	public void selectSecondFilterButton(String optioneName) {
		step("Select filter checkbox: " + optioneName, () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			try {

				wait.until(ExpectedConditions.elementToBeClickable(secondFilterBtn));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", secondFilterBtn);

				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//div[contains(@class,'ant-dropdown') and not(contains(@class,'hidden'))]")));

				String dynamicXpath = String.format(
						"//span[@class='ant-tree-checkbox' and contains(@aria-label,'Select %s')]", optioneName);

				WebElement checkBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dynamicXpath)));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkBox);

				logger.info("Checkbox for filter Source '" + optioneName + "' clicked successfully");
				test.log(Status.PASS, "Checkbox for filter Source '" + optioneName + "' clicked successfully");

			} catch (Exception e) {
				logger.error("Failed to select filter Source: " + optioneName, e);
				test.log(Status.FAIL, "Failed to select filter Source: " + optioneName + " - " + e.getMessage());
				throw new AssertionError("filter Source selection failed", e);
			}
		});
	}

	public void selectThirdFilterButton(String optioneName) {
		step("Select filter checkbox: " + optioneName, () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			try {

				wait.until(ExpectedConditions.elementToBeClickable(thirdFilterBtn));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", thirdFilterBtn);

				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//div[contains(@class,'ant-dropdown') and not(contains(@class,'hidden'))]")));

				String dynamicXpath = String.format(
						"//span[@class='ant-tree-checkbox' and contains(@aria-label,'Select %s')]", optioneName);

				WebElement checkBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dynamicXpath)));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkBox);

				logger.info("Checkbox for filter Source '" + optioneName + "' clicked successfully");
				test.log(Status.PASS, "Checkbox for filter Source '" + optioneName + "' clicked successfully");

			} catch (Exception e) {
				logger.error("Failed to select filter Source: " + optioneName, e);
				test.log(Status.FAIL, "Failed to select filter Source: " + optioneName + " - " + e.getMessage());
				throw new AssertionError("filter Source selection failed", e);
			}
		});
	}

	public void selectFourthFilterButton(String optioneName) {
		step("Select filter checkbox: " + optioneName, () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			try {

				wait.until(ExpectedConditions.elementToBeClickable(fourthFilterBtn));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", fourthFilterBtn);

				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//div[contains(@class,'ant-dropdown') and not(contains(@class,'hidden'))]")));

				String dynamicXpath = String.format(
						"//span[@class='ant-tree-checkbox' and contains(@aria-label,'Select %s')]", optioneName);

				WebElement checkBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dynamicXpath)));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkBox);

				logger.info("Checkbox for filter Source '" + optioneName + "' clicked successfully");
				test.log(Status.PASS, "Checkbox for filter Source '" + optioneName + "' clicked successfully");

			} catch (Exception e) {
				logger.error("Failed to select filter Source: " + optioneName, e);
				test.log(Status.FAIL, "Failed to select filter Source: " + optioneName + " - " + e.getMessage());
				throw new AssertionError("filter Source selection failed", e);
			}
		});
	}

	public void selectOK() {
		step("Click OK button", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

			try {

				WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(ok));
				okBtn.click();

				logger.info("OK button clicked successfully");
				test.log(Status.PASS, "OK button clicked successfully");

			} catch (Exception e) {
				logger.error("Failed to click OK button", e);
				test.log(Status.FAIL, "Failed to click OK button - " + e.getMessage());
				throw new AssertionError("Failed to click OK button", e);
			}
		});
	}

	public void selectFilterTwoOK() {
		step("Click OK button", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

			try {

				WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(Twook));
				okBtn.click();

				logger.info("OK button clicked successfully");
				test.log(Status.PASS, "OK button clicked successfully");

			} catch (Exception e) {
				logger.error("Failed to click OK button", e);
				test.log(Status.FAIL, "Failed to click OK button - " + e.getMessage());
				throw new AssertionError("Failed to click OK button", e);
			}
		});
	}

	public void selectFilterThreeOK() {
		step("Click OK button", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

			try {

				WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(Threeok));
				okBtn.click();

				logger.info("OK button clicked successfully");
				test.log(Status.PASS, "OK button clicked successfully");

			} catch (Exception e) {
				logger.error("Failed to click OK button", e);
				test.log(Status.FAIL, "Failed to click OK button - " + e.getMessage());
				throw new AssertionError("Failed to click OK button", e);
			}
		});
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

	public void startDateRange(int day) {
		step("Select Start Date", () -> {
			safeClick(startDate);

			WebElement dateSelection = driver.findElement(
					By.xpath("//td[contains(@class,'ant-picker-cell-in-view')]//div[text()='" + day + "']"));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateSelection);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateSelection);

			selectedStartDate = String.valueOf(day);
			logger.info("Selected Start Date: " + selectedStartDate);
			test.log(Status.PASS, "Selected Start Date: " + selectedStartDate);
		});
	}

	public void startEndRange(int day) {
		step("Select End Date", () -> {
			WebElement dateSelection = driver.findElement(
					By.xpath("//td[contains(@class,'ant-picker-cell-in-view')]//div[text()='" + day + "']"));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateSelection);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateSelection);

			selectedEndDate = String.valueOf(day);
			logger.info("Selected End Date: " + selectedEndDate);
			test.log(Status.PASS, "Selected End Date: " + selectedEndDate);
		});
	}

	public void disputesDate(int day) {
		step("Select Dispute Date", () -> {
			safeClick(disputeDate);

			// XPath for enabled dates only
			String enabledDateXpath = "//td[contains(@class,'ant-picker-cell-in-view') "
					+ "and not(contains(@class,'ant-picker-cell-disabled'))]//div[text()='" + day + "']";

			try {
				// Try to select the requested date if enabled
				WebElement dateSelection = driver.findElement(By.xpath(enabledDateXpath));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateSelection);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateSelection);

				selectedStartDate = String.valueOf(day);
				logger.info("Selected Start Date: " + selectedStartDate);
				test.log(Status.PASS, "Selected Start Date: " + selectedStartDate);

			} catch (NoSuchElementException e) {
				// Fallback → pick today's date
				pickTodayDate();
			}
		});
	}

	// New no-arg version → auto-selects today's date
	public void disputesDate() {
		step("Select Dispute Date (Default Today)", () -> {
			safeClick(disputeDate);
			pickTodayDate();
		});
	}

	// Reusable private helper to click today’s date
	private void pickTodayDate() {
		String todayXpath = "//td[contains(@class,'ant-picker-cell-in-view') "
				+ "and contains(@class,'ant-picker-cell-today') "
				+ "and not(contains(@class,'ant-picker-cell-disabled'))]//div";

		WebElement todayDate = driver.findElement(By.xpath(todayXpath));
		String todayDay = todayDate.getText();

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", todayDate);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", todayDate);

		selectedStartDate = todayDay;
		logger.info("Defaulted to today's date: " + todayDay);
		test.log(Status.PASS, "Defaulted to today's date: " + todayDay);
	}

	public void validateDisputeCalendar() {
		step("Validate Dispute Date Calendar", () -> {
			safeClick(disputeDate);

			// All past/future date cells
			List<WebElement> pastFutureEnabledDates = driver
					.findElements(By.xpath("//td[contains(@class,'ant-picker-cell-in-view') "
							+ "and not(contains(@class,'ant-picker-cell-disabled')) " + // enabled
							"and not(contains(@class,'ant-picker-cell-today'))]//div" // exclude today
			));

			if (!pastFutureEnabledDates.isEmpty()) {
				for (WebElement wrongDate : pastFutureEnabledDates) {
					String errorMsg = " ERROR: Date " + wrongDate.getText() + " is ENABLED but should be DISABLED.";
					logger.error(errorMsg);
					test.log(Status.FAIL, errorMsg);
				}
				throw new AssertionError("Calendar validation failed. Past/Future dates are enabled.");
			} else {
				String successMsg = " Validation passed: Only today's date is enabled, all past/future dates are disabled.";
				logger.info(successMsg);
				test.log(Status.PASS, successMsg);
			}
		});
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

	private WebElement waitForClickable(WebElement element) {
		return new WebDriverWait(driver, Duration.ofSeconds(15))
				.until(ExpectedConditions.elementToBeClickable(element));
	}

	public String searchStartDate(int day) {
		safeClick(startSearch);

		By dateLocator = By.xpath("//td[contains(@class,'ant-picker-cell-in-view')]//div[text()='" + day + "']");

		WebElement dateSelection = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(dateLocator));

		safeClick(dateSelection);

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

	public void validateErrorMessage(String expectedMessage) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		List<WebElement> errors = driver
				.findElements(By.xpath("//div[contains(@class,'ant-form-item-explain-error')]"));

		if (errors.isEmpty()) {
			logger.error("No error messages found. Expected: " + expectedMessage);
			test.log(Status.FAIL, "No error messages found. Expected: " + expectedMessage);
			throw new AssertionError("No error messages found. Expected: " + expectedMessage);
		}

		List<String> actualMessages = new ArrayList<>();
		for (WebElement error : errors) {
			String errorMsg = error.getText().trim();
			actualMessages.add(errorMsg);
		}

		logger.info("Error Messages Found: " + actualMessages);
		test.log(Status.INFO, "Error Messages Found: " + actualMessages);

		if (actualMessages.contains(expectedMessage)) {
			logger.info("Expected error message displayed: " + expectedMessage);
			test.log(Status.PASS, "Expected error message displayed: " + expectedMessage);
		} else {
			logger.error("Expected error message NOT found: " + expectedMessage);
			test.log(Status.FAIL, "Expected error message NOT found: " + expectedMessage);
			throw new AssertionError("Expected error message NOT found: " + expectedMessage);
		}
	}

}