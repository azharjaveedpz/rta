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

public class DisputeManagementPage {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	private WebDriver driver;
	private Properties prop;
	protected ExtentReports extent;
	protected ExtentTest test;
	Faker faker = new Faker();
	private String lastGeneratedPlate;

	// Constructor with config passed from BaseTest
	public DisputeManagementPage(WebDriver driver, Properties prop, ExtentTest test) {
		this.driver = driver;
		this.prop = prop;
		this.test = test;
		PageFactory.initElements(driver, this);
	}

	// WebElements using @FindBy

		@FindBy(xpath = "//button[.='Add New']")
		private WebElement addNewDisputeButton;

		@FindBy(xpath = "//div[contains(text(),'Add New Dispute')]")
		private WebElement addDisputePageMessage;

		@FindBy(xpath = "//input[@id='fine_Number']")
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
		
		@FindBy(xpath = "(//td[@class='ant-table-cell'])[4]")
		private WebElement confirmAddedInspectionObstacleNumber;


		

		// Actions

		public void addDisputeManagement() {
			addNewDisputeButton.click();
		}

		public String getDisputeMessage() {
			try {
				return addDisputePageMessage.getText().trim();
			} catch (NoSuchElementException e) {
				return "";
			}
		}

		// Full login with data loaded from login.properties

		public boolean navigateToAddDisputePage() {
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
		}



		

		public void enterFineNumber() {
		   
		    String fineNumber = faker.regexify("[A-Z]{2}[0-9]{2}[A-Z]{4}");
		   // lastGeneratedPlate = fineNumber; // save for later

		    // Wait until field is visible
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    wait.until(ExpectedConditions.visibilityOf(fineNumberField));

		 
		    
		    fineNumberField.sendKeys(fineNumber);

		    logger.info("Entered fine Number: " + fineNumber);
		    test.log(Status.INFO, "Entered fine Number: " + fineNumber);
		}
		
		public void enterReason() {
			   
		    String reason = faker.lorem().sentence();
		   
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    wait.until(ExpectedConditions.visibilityOf(reasonField));

		 
		    
		    reasonField.sendKeys(reason);

		    logger.info("Entered Reason is : " + reason);
		    test.log(Status.INFO, "Entered Reason is: " + reason);
		}
		
		public void enterCRM() {
			   
			 String crm = faker.regexify("[A-Z]{2}[0-9]{2}[A-Z]{4}");
			 lastGeneratedPlate = crm; // save for later
		   
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    wait.until(ExpectedConditions.visibilityOf(crmField));

		 
		    
		    crmField.sendKeys(crm);

		    logger.info("Entered crm is : " + crm);
		    test.log(Status.INFO, "Entered crm is: " + crm);
		}
		
		public void enterEmail() {
			   
			 String email = faker.internet().emailAddress();
		   
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    wait.until(ExpectedConditions.visibilityOf(emailField));

		 
		    
		    emailField.sendKeys(email);

		    logger.info("Entered email is : " + email);
		    test.log(Status.INFO, "Entered email is: " + email);
		}

		public void enterPhone() {
			   
			 String ph = faker.phoneNumber().cellPhone();
		   
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    wait.until(ExpectedConditions.visibilityOf(phoneField));

		 
		    
		    phoneField.sendKeys(ph);

		    logger.info("Entered phone is : " + ph);
		    test.log(Status.INFO, "Entered phone is: " + ph);
		}

		public void enterAddress() {
			   
			 String add = faker.address().fullAddress();
		   
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    wait.until(ExpectedConditions.visibilityOf(addressField));

		 
		    
		    addressField.sendKeys(add);

		    logger.info("Entered address is : " + add);
		    test.log(Status.INFO, "Entered address is: " + add);
		}


		public void selectDepartment(int index) {
			selectDepartment.click();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfAllElements(chooseList));

			chooseList.get(index).click();

			logger.info("Selected Department by index: " + index);
			test.log(Status.INFO, "Selected department by index: " + index);
		}

		public void selectPaymentType(int index) {
			selectPayment.click();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfAllElements(chooseList));

			chooseList.get(index).click();

			logger.info("Selected payment type by index: " + index);
			test.log(Status.INFO, "Selected payment type by index: " + index);
		}

		
		public void submitDisputeManagement() {
			submit.click();
			 try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(confirmAddedInspectionObstacleNumber));


			String actualCRM = confirmAddedInspectionObstacleNumber.getText().trim();

			if (actualCRM.equalsIgnoreCase(lastGeneratedPlate)) {
				logger.info("CRM number successfully added: " + actualCRM);
				test.log(Status.PASS, "CRM number successfully added: " + actualCRM);
			} else {
				logger.error("CRM number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualCRM);
				test.log(Status.FAIL,
						"CRM number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualCRM);
				throw new AssertionError(
						"CRM number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualCRM);

			}
		}

	}
