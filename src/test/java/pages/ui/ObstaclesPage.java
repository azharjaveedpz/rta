package pages.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.List;
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
import com.github.javafaker.Faker;

public class ObstaclesPage {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

	private WebDriver driver;
	private Properties prop;
	protected ExtentReports extent;
	protected ExtentTest test;
	Faker faker = new Faker();
	private String lastGeneratedPlate;

	// Constructor with config passed from BaseTest
	public ObstaclesPage(WebDriver driver, Properties prop, ExtentTest test) {
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
          //  test.pass("Step passed: " + description);
            return result;
        } catch (Exception e) {
            test.fail("Step failed: " + description + " → " + e.getMessage());
            throw e;
        }
    }

    private void step(String description, Runnable action) {
        step(description, () -> { action.run(); return true; });
    }
	// ======== WEBELEMENTS ========
    @FindBy(xpath = "//button[.='Add New']") private WebElement addNewInspectionButton;
    @FindBy(xpath = "//div[contains(text(),'Add New Inspection Obstacle')]") private WebElement addInspectionPageMessage;
    @FindBy(xpath = "//input[@id='ObstacleNumber']") private WebElement ObstacleNumber;
    @FindBy(xpath = "//input[@id='Zone']") private WebElement zoneType;
    @FindBy(xpath = "//input[@id='Area']") private WebElement dropdownsArea;
    @FindBy(xpath = "//input[@id='SourceOfObstacle']") private WebElement dropdowObstacle;
    @FindBy(xpath = "//input[@id='ClosestPaymentDevice']") private WebElement paymentDevice;
    @FindBy(xpath = "//textarea[@id='Comments']") private WebElement comments;
    @FindBy(id = "Photo") private WebElement imageUpload;
    @FindBy(xpath = "//button[.='Submit']") private WebElement submit;
    @FindBy(xpath = "//td[@class='ant-table-cell']") private WebElement confirmAddedInspectionObstacleNumber;
    @FindBy(xpath = "(//td[@class='ant-table-cell'])[1]") private WebElement zoneCell;
    @FindBy(xpath = "(//td[@class='ant-table-cell'])[2]") private WebElement areaCell;
    @FindBy(xpath = "(//td[@class='ant-table-cell'])[3]") private WebElement obstacleCell;
    @FindBy(xpath = "(//td[@class='ant-table-cell'])[4]") private WebElement statusCell;
    @FindBy(xpath = "//span[@aria-label='more']/ancestor::button") private WebElement threeDot;
    @FindBy(xpath = "//ul[contains(@class,'ant-dropdown-menu')]//li") private List<WebElement> menuList;
    @FindBy(xpath = "//div[contains(text(),'Inspection Obstacle Details')]") private WebElement viewObstaclePageMessage;
    @FindBy(xpath = "//tr[th/span[text()='Zone']]/td/span") private WebElement viewZoneType;
    @FindBy(xpath = "//tr[th/span[text()='Area']]/td/span") private WebElement viewArea;
    @FindBy(xpath = "//tr[th/span[text()='Source Of Obstacle']]/td/span") private WebElement viewSourcebstaclle;
    @FindBy(xpath = "//tr[th/span[text()='Closest Payment Device']]/td/span") private WebElement viewDevice;
    @FindBy(xpath = "//tr[th/span[text()='Comments']]/td/span") private WebElement viewComments;
    @FindBy(xpath = "//tr[th/span[text()='Status']]/td/span") private WebElement viewStatus;
    @FindBy(xpath = "//div[contains(@class,'ant-space')]//div[contains(@class,'ant-image')]//img") private List<WebElement> viewDocuments;
    @FindBy(xpath = "//div[@class='ant-statistic-title' and text()='Total Obstacles']/following-sibling::div//span[@class='ant-statistic-content-value-int']") private WebElement totalObstacle;
    @FindBy(xpath = "//div[@class='ant-statistic-title' and text()='Reported Obstacles']/following-sibling::div//span[@class='ant-statistic-content-value-int']") private WebElement reportedObstacle;
    @FindBy(xpath = "//div[@class='ant-statistic-title' and text()='Removed Obstacles']/following-sibling::div//span[@class='ant-statistic-content-value-int']") private WebElement removedObstacle;
    @FindBy(xpath = "//tbody[@class='ant-table-tbody']/tr[contains(@class, 'ant-table-row')]") private List<WebElement> rowList;
    @FindBy(xpath = "//li[contains(@class,'ant-pagination-prev')]/button") private WebElement previouBtn;
    @FindBy(xpath = "//li[contains(@class,'ant-pagination-next')]/button") private WebElement nestBtn;
    @FindBy(xpath = "//li[contains(@class,'ant-pagination-total-text')]") private WebElement totalItemPagination;
    @FindBy(xpath = "(//td[@class='ant-table-cell'])[3]") private List<WebElement> obstacleSource;
    @FindBy(xpath = "(//td[@class='ant-table-cell'])[4]") private List<WebElement> statusSource;
    @FindBy(xpath = "//div[contains(@class,'ant-select-dropdown') and not(contains(@class,'hidden'))]//div[contains(@class,'ant-select-item-option-content')]") private List<WebElement> chooseList;

	// Actions

    public boolean navigateToAddObstaclePage() {
        return step("Navigate to Add New Inspection Page", () -> {
            addNewInspectionButton.click();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement messageElement = wait.until(ExpectedConditions.visibilityOf(addInspectionPageMessage));
            String sucMsg = messageElement.getText().trim();
            if (sucMsg.isEmpty()) throw new AssertionError("Add New Inspection page message was empty");
            logger.info("Navigated to Add New Inspection page: " + sucMsg);
            test.pass("Navigated to Add New Inspection page: " + sucMsg);
            return true;
        });
    }

    public void selectZone(String zoneName) {
        step("Select Zone: " + zoneName, () -> selectFromDropdown(zoneType, zoneName));
    }

    public void selectArea(String areaName) {
        step("Select Area: " + areaName, () -> selectFromDropdown(dropdownsArea, areaName));
    }

    public void selectObstacle(String obsName) {
        step("Select Obstacle: " + obsName, () -> selectFromDropdown(dropdowObstacle, obsName));
    }

    private void selectFromDropdown(WebElement dropdown, String value) {
        dropdown.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElements(chooseList));
        for (WebElement option : chooseList) {
            if (option.getText().equalsIgnoreCase(value)) {
                option.click();
                logger.info("Selected: " + value);
                test.pass("Selected: " + value);
                return;
            }
        }
        throw new RuntimeException("Option not found: " + value);
    }


    public void enterDeviceAndReportedDetails() {
        step("Enter Device details", () -> {
            String[] devices = { "Laptop", "Mobile", "Pager", "Tab", "Palm Tab" };
            String device = devices[new java.util.Random().nextInt(devices.length)];
            sendKeysWithWait(paymentDevice, device, "device name");
        });
    }

    public void enterComments() {
        step("Enter Comments", () -> sendKeysWithWait(comments, faker.lorem().sentence(), "Comments"));
    }

    private void sendKeysWithWait(WebElement element, String value, String fieldName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(value);
        logger.info("Entered " + fieldName + ": " + value);
        test.pass("Entered " + fieldName + ": " + value);
    }

    public void uploadPhoto() {
        step("Upload Photo", () -> {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Photo")));
            String filePath = System.getProperty("user.dir") + "/src/test/java/utils/sample.png";
            imageUpload.sendKeys(filePath);
            logger.info("Uploaded photo from: " + filePath);
            test.pass("Uploaded photo from: " + filePath);
        });
    }
	

    public void validateZone(String expected) { step("Validate Zone", () -> validateCell(zoneCell, expected, "Zone")); }
    public void validateArea(String expected) { step("Validate Area", () -> validateCell(areaCell, expected, "Area")); }
    public void validateObstacle(String expected) { step("Validate Obstacle", () -> validateCell(obstacleCell, expected, "Obstacle")); }
    public void validateStatus(String expected) { step("Validate Status", () -> validateCell(statusCell, expected, "Status")); }

    private void validateCell(WebElement element, String expected, String fieldName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element));
        String actual = element.getText().trim();
        if (!actual.equalsIgnoreCase(expected))
            throw new AssertionError(fieldName + " mismatch! Expected: " + expected + ", Found: " + actual);
        logger.info(fieldName + " validated: " + actual);
        test.pass(fieldName + " validated: " + actual);
    }


	public void clickThreeDotAndValidateMenuList() {
		 step("Click three-dot menu and validate items", () -> {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				test.log(Status.PASS, "Menu Item: " + menuText);

			}
		}
		 });
	}

	 public boolean navigateToViewPage() {
	        return step("Navigate to View page", () -> {
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	            WebElement messageElement = wait.until(ExpectedConditions.visibilityOf(viewObstaclePageMessage));
	            String sucMsg = messageElement.getText().trim();
	            if (sucMsg.isEmpty()) throw new AssertionError("View page message was empty");
	            logger.info("Navigated to View page: " + sucMsg);
	            test.pass("Navigated to View page: " + sucMsg);
	            return true;
	        });
	    }

	public void getObstacleDetails() {
		step("Get Obstacle Details", () -> {
            validateAndPrint("Zone Type", viewZoneType.getText());
            validateAndPrint("Area Type", viewArea.getText());
            validateAndPrint("Source Of Obstacle", viewSourcebstaclle.getText());
            validateAndPrint("Device Used", viewDevice.getText());
            validateAndPrint("Comments", viewComments.getText());
            validateAndPrint("Status", viewStatus.getText());

		int docCount = viewDocuments.size();
		if (docCount == 0) {
			logger.error("Document is empty!");
			test.log(Status.FAIL, "Document is empty!");
		} else {
			logger.info("Documents Uploaded: " + docCount);
			test.log(Status.PASS, "Documents Uploaded: " + docCount);

			for (WebElement doc : viewDocuments) {
				String imgPath = doc.getAttribute("src");
				logger.info("Document Path: " + imgPath);
				test.log(Status.INFO, "Document Path: " + imgPath);
			}
		}
		 });
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

	public void validateTotalObstacleCount() {
	    step("Validate that table row count does not exceed Total Obstacles", () -> {
	        try {
	            int total = Integer.parseInt(totalObstacle.getText().trim());
	            int rowsOnPage = rowList.size();

	            if (rowsOnPage <= total) {
	                logger.info("Row count (" + rowsOnPage + ") matches or is less than total obstacle (" + total + ")");
	                test.pass("Row count (" + rowsOnPage + ") matches or is less than total obstacle (" + total + ")");
	            } else {
	                logger.error("Row count (" + rowsOnPage + ") exceeds total obstacle (" + total + ")");
	                test.fail("Row count (" + rowsOnPage + ") exceeds total obstacle (" + total + ")");
	            }

	        } catch (Exception e) {
	            logger.error("Error validating total obstacle: " + e.getMessage());
	            test.fail("Error validating total obstacle: " + e.getMessage());
	            throw new RuntimeException(e);
	        }
	    });
	}

	public void printAndValidateObstacleCounts() {
	    step("Print and validate obstacle counts", () -> {
	        int total = Integer.parseInt(totalObstacle.getText().trim());
	        int reported = Integer.parseInt(reportedObstacle.getText().trim());
	        int removed = Integer.parseInt(removedObstacle.getText().trim());

	        // Log counts
	        logger.info("Total obstacles: " + total);
	        test.info("Total obstacles: " + total);

	        logger.info("Reported obstacles: " + reported);
	        test.info("Reported obstacles: " + reported);

	        logger.info("Removed obstacles: " + removed);
	        test.info("Removed obstacles: " + removed);

	        // Validate total = reported + removed
	        if (total == reported + removed) {
	            logger.info("Obstacle count validation passed: Total = Reported + Removed");
	            test.pass("Obstacle count validation passed: Total = Reported + Removed");
	        } else {
	            String message = "Obstacle count validation FAILED! Total: " + total + ", Reported + Removed: " + (reported + removed);
	            logger.error(message);
	            test.fail(message);
	            throw new AssertionError("Obstacle count mismatch: Total != Reported + Removed");
	        }
	    });
	}

	

	public void validateTableRowsAgainstTotalObstacle() {
	    step("Validate table rows against total obstacle label", () -> {
	        int totalFromLabel = Integer.parseInt(totalObstacle.getText().trim());

	        List<WebElement> tableRows = driver.findElements(
	            By.xpath("//tbody[@class='ant-table-tbody']/tr[contains(@class, 'ant-table-row')]")
	        );
	        int totalFromTable = tableRows.size();

	        // Log values
	        logger.info("Total Obstacle from label: " + totalFromLabel);
	        test.info("Total Obstacle from label: " + totalFromLabel);

	        logger.info("Total Obstacle from table rows: " + totalFromTable);
	        test.info("Total Obstacle from table rows: " + totalFromTable);

	        // Validate counts
	        if (totalFromLabel == totalFromTable) {
	            logger.info("Validation passed: Total Obstacle label matches table row count.");
	            test.pass("Validation passed: Total Obstacle label matches table row count.");
	        } else {
	            String message = "Validation FAILED! Total Obstacle label: " + totalFromLabel + ", Table rows: " + totalFromTable;
	            logger.error(message);
	            test.fail(message);
	            throw new AssertionError("Total Obstacle mismatch: Label != Table row count");
	        }
	    });
	}

	

	public void checkMiddlePagesPagination() {
		 step("Validate Middle Page Pagination", () -> {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String paginationText = totalItemPagination.getText();
			String[] parts = paginationText.split(" of ");
			int itemsPerPage = Integer.parseInt(parts[0].split("-")[1].trim());
			int totalItems = Integer.parseInt(parts[1].replaceAll("[^0-9]", "").trim());
			int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
			int total = Integer.parseInt(totalObstacle.getText().trim());

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

		} catch (Exception e) {
			logger.error("Error in middle pages validation: " + e.getMessage());
			test.log(Status.FAIL, "Error in middle pages validation: " + e.getMessage());
			throw new RuntimeException(e);
		}
		 });
	}

	public void validateObstacleFilterList(String expectedObstacleSource) {
	    step("Validate Obstacle filter list for: " + expectedObstacleSource, () -> {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        // Wait for the list to be visible
	        wait.until(ExpectedConditions.visibilityOfAllElements(obstacleSource));

	        boolean allMatched = true;

	        for (int i = 0; i < obstacleSource.size(); i++) {
	            String actualValue = obstacleSource.get(i).getText().trim();

	            if (!actualValue.equalsIgnoreCase(expectedObstacleSource)) {
	                String message = "Row " + (i + 1) + " mismatch: expected = " + expectedObstacleSource + ", actual = " + actualValue;
	                logger.error(message);
	                test.fail(message);
	                allMatched = false;
	            } else {
	                String message = "Row " + (i + 1) + " matched: " + actualValue;
	                logger.info(message);
	                test.pass(message);
	            }
	        }

	        if (!allMatched) {
	            throw new AssertionError("One or more rows did not match the expected Obstacle Source: " + expectedObstacleSource);
	        }

	        logger.info("All rows matched the expected Obstacle Source: " + expectedObstacleSource);
	        test.pass("All rows matched the expected Obstacle Source: " + expectedObstacleSource);
	    });
	}

	

	public void validateStatusFilterList(String expectedStatus) {
	    step("Validate Status filter list for: " + expectedStatus, () -> {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        // Wait for status list to be visible
	        wait.until(ExpectedConditions.visibilityOfAllElements(statusSource));

	        boolean allMatched = true;

	        for (int i = 0; i < statusSource.size(); i++) {
	            String actualValue = statusSource.get(i).getText().trim();

	            if (!actualValue.equalsIgnoreCase(expectedStatus)) {
	                String message = "Row " + (i + 1) + " mismatch: expected = " + expectedStatus + ", actual = " + actualValue;
	                logger.error(message);
	                test.fail(message);
	                allMatched = false;
	            } else {
	                String message = "Row " + (i + 1) + " matched: " + actualValue;
	                logger.info(message);
	                test.pass(message);
	            }
	        }

	        if (!allMatched) {
	            throw new AssertionError("One or more rows did not match the expected Status Source: " + expectedStatus);
	        }

	        logger.info("All rows matched the expected Status Source: " + expectedStatus);
	        test.pass("All rows matched the expected Status Source: " + expectedStatus);
	    });
	}

	
	public void validateReportedAndRemovedObstacles() {
	    step("Validate Reported and Removed obstacle counts", () -> {
	        // Get counts from UI
	        int reportedUI = Integer.parseInt(reportedObstacle.getText().trim());
	        int removedUI = Integer.parseInt(removedObstacle.getText().trim());

	        // Get statuses from table
	        List<WebElement> statusList = driver.findElements(
	                By.xpath("//tbody[@class='ant-table-tbody']/tr[contains(@class,'ant-table-row')]/td[5]"));

	        long reportedTable = statusList.stream()
	                .map(WebElement::getText)
	                .map(String::trim)
	                .filter(status -> status.equalsIgnoreCase("Reported"))
	                .count();

	        long removedTable = statusList.stream()
	                .map(WebElement::getText)
	                .map(String::trim)
	                .filter(status -> status.equalsIgnoreCase("Removed"))
	                .count();

	        logger.info("Reported (UI/Table): " + reportedUI + "/" + reportedTable);
	        test.log(Status.INFO, "Reported (UI/Table): " + reportedUI + "/" + reportedTable);

	        logger.info("Removed (UI/Table): " + removedUI + "/" + removedTable);
	        test.log(Status.INFO, "Removed (UI/Table): " + removedUI + "/" + removedTable);

	        if (reportedUI == reportedTable && removedUI == removedTable) {
	            logger.info("Validation passed: UI counts match table counts");
	            test.log(Status.PASS, "Validation passed: UI counts match table counts");
	        } else {
	            String message = "Validation FAILED! Reported(UI/Table): " + reportedUI + "/" + reportedTable
	                    + " | Removed(UI/Table): " + removedUI + "/" + removedTable;
	            logger.error(message);
	            test.log(Status.FAIL, message);
	            throw new AssertionError(message);
	        }
	    });
	}


}
