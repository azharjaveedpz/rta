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

public class InspectionObstaclesPage {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	private WebDriver driver;
	private Properties prop;
	protected ExtentReports extent;
	protected ExtentTest test;
	Faker faker = new Faker();
	private String lastGeneratedPlate;

	// Constructor with config passed from BaseTest
	public InspectionObstaclesPage(WebDriver driver, Properties prop, ExtentTest test) {
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
		private WebElement dropdownZone;

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


		

		// Actions

		public void addInspection() {
			addNewInspectionButton.click();
		}

		public String getInspectionMessage() {
			try {
				return addInspectionPageMessage.getText().trim();
			} catch (NoSuchElementException e) {
				return "";
			}
		}

		// Full login with data loaded from login.properties

		public boolean navigateToAddInspectionPage() {
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



		public void enterObstacleNumber() {
			String obstacleNumber = faker.regexify("[A-Z]{2}[0-9]{2}[A-Z]{4}");
			lastGeneratedPlate = obstacleNumber; // save for later

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(ObstacleNumber));

			ObstacleNumber.sendKeys(obstacleNumber);

			logger.info("Entered Plate Number: " + obstacleNumber);
			test.log(Status.INFO, "Entered Plate Number: " + obstacleNumber);
		}

		public void selectZone(int index) {
			dropdownZone.click();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfAllElements(chooseList));

			chooseList.get(index).click();

			logger.info("Selected Zone by index: " + index);
			test.log(Status.INFO, "Selected PZone by index: " + index);
		}

		public void selectArea(int index) {
			dropdownsArea.click();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfAllElements(chooseList));

			chooseList.get(index).click();

			logger.info("Selected Area by index: " + index);
			test.log(Status.INFO, "Selected Area by index: " + index);
		}

		public void selectObstacle(int index) {
			dropdowObstacle.click();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfAllElements(chooseList));

			chooseList.get(index).click();

			logger.info("Selected Obstacle by index: " + index);
			test.log(Status.INFO, "Selected Obstacle by index: " + index);
		}

		public void enterDeviceAndReportedDetails() {
		    String device = faker.company().industry();
		    String name = faker.name().firstName();

		    sendKeysWithWait(paymentDevice, device, "device name");
		    sendKeysWithWait(reported, name, "reported by");
		}

		private void sendKeysWithWait(WebElement element, String value, String fieldName) {
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    wait.until(ExpectedConditions.visibilityOf(element));
		  //  element.clear();
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
				logger.error("Obstacle number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualInspection);
				test.log(Status.FAIL,
						"Obstacle number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualInspection);
				throw new AssertionError(
						"Obstacle number mismatch! Expected: " + lastGeneratedPlate + ", but found: " + actualInspection);

			}
		}

	}
