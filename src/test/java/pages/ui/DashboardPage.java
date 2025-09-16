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
import java.util.function.Supplier;

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

	@FindBy(xpath = "//span[normalize-space()='Inspections']")
	private WebElement selectInspection;

	@FindBy(xpath = "//a[normalize-space()='Inspections Management']")
	private WebElement selectInspectionManagement;

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

	// ===== Get Messages =====
	public String getDashboardMessage() {
		try {
			return dashboardMessage.getText().trim();
		} catch (NoSuchElementException e) {
			return "";
		}
	}

	public String getPermitMessage() {
		try {
			return permitMessage.getText().trim();
		} catch (NoSuchElementException e) {
			return "";
		}
	}

	public String getInspectionMessage() {
		try {
			return inspectionMessage.getText().trim();
		} catch (NoSuchElementException e) {
			return "";
		}
	}

	public String getDisputeMessage() {
		try {
			return disputeMessage.getText().trim();
		} catch (NoSuchElementException e) {
			return "";
		}
	}

	public String getFineMessage() {
		try {
			return fineMessage.getText().trim();
		} catch (NoSuchElementException e) {
			return "";
		}
	}

	public String getPledgeMessage() {
		try {
			return pledgeMessage.getText().trim();
		} catch (NoSuchElementException e) {
			return "";
		}
	}

	// ===== Click Menus =====
	public void clickWhitelistManagement() {
		step("Click Whitelist Menu", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			WebElement link = wait.until(ExpectedConditions.elementToBeClickable(selectWhitelistManagement));
			link.click();

			logger.info("Clicked on Whitelist");
			test.pass("Clicked on Whitelist");

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

	public void clickPermits() {
		step("Click Permits Menu", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			WebElement link = wait.until(ExpectedConditions.elementToBeClickable(selectPermits));
			link.click();

			logger.info("Clicked on Permits");
			test.pass("Clicked on Permits");
		});
	}

	public void clickObstacle() {
		step("Click Obstacle Menu", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			WebElement link = wait.until(ExpectedConditions.elementToBeClickable(selectObstacle));
			link.click();

			logger.info("Clicked on Obstacles");
			test.pass("Clicked on Obstacles");

			try {
				Thread.sleep(4000); // wait for splash/dashboard
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	public void clickDisputeManagement() {
		step("Click Disputes Menu", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			WebElement link = wait.until(ExpectedConditions.elementToBeClickable(selectDisputeManagement));
			link.click();

			logger.info("Clicked on Disputes");
			test.pass("Clicked on Disputes");
		});
	}

	public void clickFineManagement() {
		step("Click Fine Management Menu", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			WebElement link = wait.until(ExpectedConditions.elementToBeClickable(selectFineManagement));
			link.click();

			logger.info("Clicked on Fine Management");
			test.pass("Clicked on Fine Management");
		});
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

	// ===== Submenu Handling =====
	public void openWhitelistManagementMenu() {
		clickWhitelistManagement();
	}

	public List<String> getWhitelistManagementSubmenuItemsText() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		List<WebElement> submenuItems = wait.until(ExpectedConditions.visibilityOfAllElements(subMenuList));

		List<String> texts = new ArrayList<>();
		for (WebElement element : submenuItems) {
			texts.add(element.getText());
		}
		return texts;
	}

	public void validateWhitelistManagementSubmenuItems() {
		step("Validate Whitelist Management Submenu Items", () -> {
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
		});
	}

	// ===== Page Validations =====
	public boolean dashboardMessageValidation() {
		return step("Validate Dashboard Page Message", () -> {
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
		});
	}

	public boolean permitPageMessageValidation() {
		return step("Validate Permit Page Message", () -> {
			String successMsg = getPermitMessage();

			if (successMsg != null && !successMsg.trim().isEmpty()) {
				logger.info("Navigated to permit search page: " + successMsg);
				test.log(Status.PASS, "Navigated to permit search page: " + successMsg);
				return true;
			} else {
				logger.error("Failed to navigate to permit search page");
				test.log(Status.FAIL, "Failed to navigate to permit search page");
				return false;
			}
		});
	}

	public boolean inspectionPageMessageValidation() {
		return step("Validate Inspection Page Message", () -> {
			String successMsg = getInspectionMessage();

			if (successMsg != null && !successMsg.trim().isEmpty()) {
				logger.info("Navigated to inspection page: " + successMsg);
				test.log(Status.PASS, "Navigated to inspection page: " + successMsg);
				return true;
			} else {
				logger.error("Failed to navigate to inspection page");
				test.log(Status.FAIL, "Failed to navigate to inspection page");
				return false;
			}
		});
	}

	public boolean disputeManagementPageMessageValidation() {
		return step("Validate Dispute Management Page Message", () -> {
			String successMsg = getDisputeMessage();

			if (successMsg != null && !successMsg.trim().isEmpty()) {
				logger.info("Navigated to dispute management page: " + successMsg);
				test.log(Status.PASS, "Navigated to dispute management page: " + successMsg);
				return true;
			} else {
				logger.error("Failed to navigate to dispute management page");
				test.log(Status.FAIL, "Failed to navigate to dispute management page");
				return false;
			}
		});
	}

	public boolean fineManagementPageMessageValidation() {
		return step("Validate Fine Management Page Message", () -> {
			String successMsg = getFineMessage();

			if (successMsg != null && !successMsg.trim().isEmpty()) {
				logger.info("Navigated to fine management page: " + successMsg);
				test.log(Status.PASS, "Navigated to fine management page: " + successMsg);
				return true;
			} else {
				logger.error("Failed to navigate to fine management page");
				test.log(Status.FAIL, "Failed to navigate to fine management page");
				return false;
			}
		});
	}

	public boolean pledgeManagementPageMessageValidation() {
		return step("Validate Pledge Management Page Message", () -> {
			String successMsg = getPledgeMessage();

			if (successMsg != null && !successMsg.trim().isEmpty()) {
				logger.info("Navigated to pledge management page: " + successMsg);
				test.log(Status.PASS, "Navigated to pledge management page: " + successMsg);
				return true;
			} else {
				logger.error("Failed to navigate to pledge management page");
				test.log(Status.FAIL, "Failed to navigate to pledge management page");
				return false;
			}
		});
	}

	public void clickInspections() {
		step("Click Inspections Menu", () -> {
			try {
				Thread.sleep(3000); // wait for splash/dashboard
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			WebElement link = wait.until(ExpectedConditions.elementToBeClickable(selectInspection));
			link.click();

			logger.info("Inspections menu opened successfully");
			test.pass("Inspections menu opened successfully");
		});
	}

	public void clickInspectionManagement() {
		step("Click Inspection Menu", () -> {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			WebElement link = wait.until(ExpectedConditions.elementToBeClickable(selectInspectionManagement));
			link.click();

			logger.info("Clicked on Inspection");
			test.pass("Clicked on Inspection");

			try {
				Thread.sleep(4000); // wait for splash/dashboard
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
}