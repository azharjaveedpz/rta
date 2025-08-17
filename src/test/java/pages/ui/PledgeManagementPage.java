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

public class PledgeManagementPage {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	private WebDriver driver;
	private Properties prop;
	protected ExtentReports extent;
	protected ExtentTest test;
	Faker faker = new Faker();
	private String lastGeneratedPlate;

	// Constructor with config passed from BaseTest
	public PledgeManagementPage(WebDriver driver, Properties prop, ExtentTest test) {
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


		

		// Actions

		public void addPledge() {
			addNewPledgeButton.click();
		}

		public String getPledgeMessage() {
			try {
				return addPledgePageMessage.getText().trim();
			} catch (NoSuchElementException e) {
				return "";
			}
		}

		// Full login with data loaded from login.properties

		public boolean navigateToAddPledgePage() {
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
				test.log(Status.PASS, "Navigated to Add New Pledge page: " + sucMsg);
				return true;
			} else {
				logger.error("Failed to navigate to Add New Pledge page. Message was empty.");
				test.log(Status.FAIL, "Failed to navigate to Add New Pledge page. Message was empty.");
				throw new AssertionError("Failed to navigate to Add New Pledge page. Message was empty.");
			}
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
			String licNumber = faker.regexify("[A-Z]{2}[0-9]{2}[A-Z]{4}");

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(tradeLicense));

			tradeLicense.sendKeys(licNumber);

			logger.info("Entered license Number: " + licNumber);
			test.log(Status.INFO, "Entered license Number: " + licNumber);
		}
		
		public void enterBusinessName() {
			String bus = faker.company().industry();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(businessName));

			businessName.sendKeys(bus);

			logger.info("Entered business name: " + bus);
			test.log(Status.INFO, "Entered business name: " + bus);
		}

		public void enterRemark() {
			String bus = faker.lorem().sentence();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(remarks));

			remarks.sendKeys(bus);

			logger.info("Entered remarks : " + bus);
			test.log(Status.INFO, "Entered remarks : " + bus);
		}
		public void selectPledgeType(int index) {
			pledgeType.click();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfAllElements(chooseList));

			chooseList.get(index).click();

			logger.info("Selected Pledge  type by index: " + index);
			test.log(Status.INFO, "Selected Pledge  type by index: " + index);
		}

		
	
		

		public void uploadPhoto() {
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("documentPath")));

		    String filePath = System.getProperty("user.dir") + "/src/test/java/utils/sample.png";

		    
		    imageUpload.sendKeys(filePath);

		    logger.info("Uploaded photo from: " + filePath);
		    test.log(Status.INFO, "Uploaded photo from: " + filePath);
		}

		
		public void submitPledge() {
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
				logger.info("Pledge number successfully added: " + actualInspection);
				test.log(Status.PASS, "Pledge number successfully added: " + actualInspection);
			} else {
				logger.error("Pledge number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualInspection);
				test.log(Status.FAIL,
						"Pledge number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualInspection);
				throw new AssertionError(
						"Pledge number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualInspection);

			}
		}

	}
