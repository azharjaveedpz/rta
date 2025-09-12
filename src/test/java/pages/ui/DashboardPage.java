package pages.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.TestDataReader;

import java.time.Duration;
import java.util.ArrayList;
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



public class DashboardPage {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

    private WebDriver driver;
    private Properties prop;
    protected ExtentReports extent;
    protected ExtentTest test;


    // Constructor with config passed from BaseTest
    public DashboardPage(WebDriver driver, Properties prop, ExtentTest test) {
        this.driver = driver;
        this.prop = prop;
        this.test = test; 
        PageFactory.initElements(driver, this);
    }

    // WebElements using @FindBy
    @FindBy(xpath = "//h3[normalize-space()='Dashboard']")
    private WebElement dashboardMessage;
    
    @FindBy(xpath = "//span[normalize-space()='Configuration']")
    private WebElement selectConfiguration;
    
    @FindBy(xpath = "//a[normalize-space()='Whitelist']")
    private WebElement selectWhitelistManagement;
    
    @FindBy(xpath = "//li[@class='ant-menu-item ant-menu-item-only-child']")
    private List<WebElement> subMenuList;
    
    @FindBy(xpath = "//a[normalize-space()='Permits']")
    private WebElement selectPermits;
    
    @FindBy(xpath = "//h3[normalize-space()='Permits Search']")
    private WebElement permitMessage;
    
    @FindBy(xpath = "//a[.='Obstacles']")
    private WebElement selectObstacle;
    
    @FindBy(xpath = "//h3[.='Inspection Obstacles']")
    private WebElement inspectionMessage;
    
    @FindBy(xpath = "//a[normalize-space()='Disputes']")
    private WebElement selectDisputeManagement;
    
    @FindBy(xpath = "//h3[normalize-space()='Dispute Management']")
    private WebElement disputeMessage;
    
    @FindBy(xpath = "//a[.='Fines Management']")
    private WebElement selectFineManagement;
    
    @FindBy(xpath = "//h3[normalize-space()='Fines Management']")
    private WebElement fineMessage;
    
    @FindBy(xpath = "//a[normalize-space()='Pledges']")
    private WebElement selectPledgeManagement;
    
    @FindBy(xpath = "//h3[.='Pledges Management']")
    private WebElement pledgeMessage;

 // ================= Step Logging =================
 	public void step(String stepDescription, Runnable action) {
 	    // Log step in Extent
 	    test.info("Step: " + stepDescription);

 	    // Execute the actual action
 	    action.run();
 	}


    // Actions
    public String getDashboardMessage() {
        try {
            return dashboardMessage.getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
    public void clickWhitelistManagement() {
    	step("Click Whitelist Menu", () -> {
    	
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(selectWhitelistManagement));
        link.click();

        logger.info("Clicked on plates");
        test.pass("Clicked on plates");
        try {
            Thread.sleep(4000); // wait for splash/dashboard
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	 });
    }
    
    public void clickConfiguration() {
        step("Click Configuration Menu", () -> {
            try {
                Thread.sleep(3000); // wait for splash/dashboard
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(selectConfiguration));
            link.click();

            logger.info("Configuration menu opened successfully");
            test.pass("Configuration menu opened successfully");
        });
    }

    
    
    
    //code
    public boolean dashboardrMessageValidation() {
        String successMsg = getDashboardMessage();

        if (successMsg != null && !successMsg.trim().isEmpty()) {
            logger.info("Navigated to dashboard: " + successMsg);
            test.log(Status.PASS, "Navigated to dashboard and welcome message: " + successMsg);
            return true;
        } else {
            logger.error("Login failed");
            test.log(Status.FAIL, "Login failed");
            return false;
        }
    }


    public void openWhitelistManagementMenu() {
        clickWhitelistManagement();
    }
    public List<String> getWhitelistManagementSubmenuItemsText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> submenuItems = wait.until(
            ExpectedConditions.visibilityOfAllElements(subMenuList)
        );

        List<String> texts = new ArrayList<>();
        for (WebElement element : submenuItems) {
            texts.add(element.getText());
        }
        return texts;
    }


    public void validateWhitelistManagementSubmenuItems() {
        List<String> submenuItems = getWhitelistManagementSubmenuItemsText();

        if (submenuItems.size() >= 2) {
            logger.info("Submenu contains: {} and {}", submenuItems.get(0), submenuItems.get(1));
            test.log(Status.PASS,
                String.format("Submenu contains: %s and %s", submenuItems.get(0), submenuItems.get(1)));
        } else {
            logger.error("Submenu does not contain both expected items. Found: {}", submenuItems);
            test.log(Status.FAIL, "Submenu does not contain both expected items.");
            throw new AssertionError("Submenu validation failed.");
        }
    }

    public void clickPermits() {
    	selectPermits.click();
    }
    
    
    public String getPermitMessage() {
        try {
            return permitMessage.getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
    //code
    public boolean permitPageMessageValidation() {
        String successMsg = getPermitMessage();

        if (successMsg != null && !successMsg.trim().isEmpty()) {
            logger.info("Navigated to permit search page: " + successMsg);
            test.log(Status.PASS, "Navigated to permit search page: " + successMsg);
            return true;
        } else {
            logger.error("Fail to Navigated to permit search page:");
            test.log(Status.FAIL, "Fail to Navigated to permit search page");
            return false;
        }
    }

    public void clickObstacle() {
    	   step("Click Obstacle Menu", () -> {
    	
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(selectObstacle));
        link.click();

        logger.info("Clicked on obstacles");
        test.pass("Clicked on obstacles");
        try {
            Thread.sleep(4000); // wait for splash/dashboard
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       });

    }
    
    
    public String getInspectionMessage() {
        try {
            return inspectionMessage.getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
    //code
    public boolean inspectionPageMessageValidation() {
    	
        String successMsg = getInspectionMessage();

        if (successMsg != null && !successMsg.trim().isEmpty()) {
            logger.info("Navigated to inspection page: " + successMsg);
            test.log(Status.PASS, "Navigated to inspection page: " + successMsg);
            return true;
        } else {
            logger.error("Fail to Navigated to inspection page:");
            test.log(Status.FAIL, "Fail to Navigated to inspection page");
            return false;
        }
    }
    
    public void clickDisputeManagement() {
    	
    	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
         WebElement link = wait.until(ExpectedConditions.elementToBeClickable(selectDisputeManagement));
         link.click();

         logger.info("Clicked on disputes");
         test.info("Clicked on disputes");
    }
    
    
    public String getDisputeMessage() {
        try {
            return disputeMessage.getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
    //code
    public boolean disputeManagementPageMessageValidation() {
        String successMsg = getDisputeMessage();

        if (successMsg != null && !successMsg.trim().isEmpty()) {
            logger.info("Navigated to dispute management page: " + successMsg);
            test.log(Status.PASS, "Navigated to dispute management page: " + successMsg);
            return true;
        } else {
            logger.error("Fail to Navigated to dispute management page:");
            test.log(Status.FAIL, "Fail to Navigated to dispute management page");
            return false;
        }
    }
    
    public void clickFineManagement() {
    	selectFineManagement.click();
    }
    
    
    public String getFineMessage() {
        try {
            return fineMessage.getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
    //code
    public boolean fineManagementPageMessageValidation() {
        String successMsg = getFineMessage();

        if (successMsg != null && !successMsg.trim().isEmpty()) {
            logger.info("Navigated to fine management page: " + successMsg);
            test.log(Status.PASS, "Navigated to fine management page: " + successMsg);
            return true;
        } else {
            logger.error("Fail to Navigated to fine management page:");
            test.log(Status.FAIL, "Fail to Navigated to fine management page");
            return false;
        }
    }

   
    
    private By pledgeLink = By.xpath("//a[normalize-space()='Pledges']");

    public void clickPledgeManagement() {
    	  step("Click Pledge Menu", () -> {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(pledgeLink));
        link.click();

        logger.info("Pledge menu opened successfully");
        test.pass("Pledge menu opened successfully");
        try {
            Thread.sleep(4000); // wait for splash/dashboard
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	  });
    }


    public String getPledgeMessage() {
        try {
            return pledgeMessage.getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
    //code
    public boolean pledgeManagementPageMessageValidation() {
        String successMsg = getPledgeMessage();

        if (successMsg != null && !successMsg.trim().isEmpty()) {
            logger.info("Navigated to pledge management page: " + successMsg);
            test.log(Status.PASS, "Navigated to pledge management page: " + successMsg);
            return true;
        } else {
            logger.error("Fail to Navigated to pledge management page:");
            test.log(Status.FAIL, "Fail to Navigated to pledge management page");
            return false;
        }
    }
}
