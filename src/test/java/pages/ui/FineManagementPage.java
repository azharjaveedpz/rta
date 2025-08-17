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

public class FineManagementPage {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	private WebDriver driver;
	private Properties prop;
	protected ExtentReports extent;
	protected ExtentTest test;
	Faker faker = new Faker();
	private String lastGeneratedPlate;

	// Constructor with config passed from BaseTest
	public FineManagementPage(WebDriver driver, Properties prop, ExtentTest test) {
		this.driver = driver;
		this.prop = prop;
		this.test = test;
		PageFactory.initElements(driver, this);
	}

	// WebElements using @FindBy
	
	@FindBy(xpath = "//input[@id='fineNo']")
	private WebElement inputFineNumber;
	
	@FindBy(xpath = "//button[.='Search']")
	private WebElement searchButton;
	
	@FindBy(xpath = "//td[@class='ant-table-cell']")
	private WebElement validateFineNumber;


	

	// Actions

	

	// Full login with data loaded from login.properties

	

	public void enterFineNumber(String fineNumber) {
	   
		inputFineNumber.sendKeys(fineNumber);

	    logger.info("Entered fine Number: " + fineNumber);
	    test.log(Status.INFO, "Entered fine Number: " + fineNumber);
	}


public void searchFine() {
		
	searchButton.click();;

	
	}
	
	public void validateSearchedFineNumber(String expectedFineNumber) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOf(validateFineNumber));

    String actualFineNumber = validateFineNumber.getText().trim();

    if (expectedFineNumber.equals(actualFineNumber)) {
        logger.info("Fine Number matched! Expected & Actual: " + actualFineNumber);
        test.log(Status.PASS, "Fine Number matched! Expected & Actual: " + actualFineNumber);
    } else {
        logger.error("Fine Number mismatch! Expected: " + expectedFineNumber + ", Found: " + actualFineNumber);
        test.log(Status.FAIL, "Fine Number mismatch! Expected: " + expectedFineNumber + ", Found: " + actualFineNumber);
        throw new AssertionError("Fine Number mismatch!"); // ensures test fails if mismatch
    }
}

}
