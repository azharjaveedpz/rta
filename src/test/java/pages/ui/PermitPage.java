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

public class PermitPage {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	private WebDriver driver;
	private Properties prop;
	protected ExtentReports extent;
	protected ExtentTest test;
	Faker faker = new Faker();
	private String lastGeneratedPlate;

	// Constructor with config passed from BaseTest
	public PermitPage(WebDriver driver, Properties prop, ExtentTest test) {
		this.driver = driver;
		this.prop = prop;
		this.test = test;
		PageFactory.initElements(driver, this);
	}

	// WebElements using @FindBy
	
	@FindBy(xpath = "//input[@id='plateNumber']")
	private WebElement inputPlateNumber;
	
	@FindBy(xpath = "//button[.='Search']")
	private WebElement searchButton;
	
	@FindBy(xpath = "(//td[@class='ant-table-cell'])[3]")
	private WebElement validatePlateNumber;


	

	// Actions

	

	// Full login with data loaded from login.properties

	

	public void enterPlateNumber(String plateNumber) {
	   
	    inputPlateNumber.sendKeys(plateNumber);

	    logger.info("Entered Plate Number: " + plateNumber);
	    test.log(Status.INFO, "Entered Plate Number: " + plateNumber);
	}


public void searchPermit() {
		
	searchButton.click();;

	
	}
	
	public void validateSearchedPlateNumber(String expectedPlateNumber) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOf(validatePlateNumber));

    String actualPlateNumber = validatePlateNumber.getText().trim();

    if (expectedPlateNumber.equals(actualPlateNumber)) {
        logger.info("Plate Number matched! Expected & Actual: " + actualPlateNumber);
        test.log(Status.PASS, "Plate Number matched! Expected & Actual: " + actualPlateNumber);
    } else {
        logger.error("Plate Number mismatch! Expected: " + expectedPlateNumber + ", Found: " + actualPlateNumber);
        test.log(Status.FAIL, "Plate Number mismatch! Expected: " + expectedPlateNumber + ", Found: " + actualPlateNumber);
        throw new AssertionError("Plate Number mismatch!"); // ensures test fails if mismatch
    }
}

}
