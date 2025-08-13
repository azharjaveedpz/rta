package pages.ui;

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



public class DashboardPage {
    private WebDriver driver;
    private Properties prop;

    // Constructor with config passed from BaseTest
    public DashboardPage(WebDriver driver, Properties prop) {
        this.driver = driver;
        this.prop = prop;
        PageFactory.initElements(driver, this);
    }

    // WebElements using @FindBy
    @FindBy(xpath = "//h3[normalize-space()='Dashboard']")
    private WebElement dashboardMessage;
    
    @FindBy(xpath = "//span[normalize-space()='Whitelist Management']")
    private WebElement selectWhitelistManagement;
    
    @FindBy(xpath = "//li[@class='ant-menu-item ant-menu-item-only-child']")
    private List<WebElement> subMenuList;

    // Actions
    public String getDashboardMessage() {
        try {
            return dashboardMessage.getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
    public void clickWhitelistManagement() {
    	selectWhitelistManagement.click();
    }
    
    
    
    //code
    public boolean dashboardrMessageValidation() {
        String sucessMsg = getDashboardMessage();
        return !sucessMsg.isEmpty(); // returns true if there's a message
    }
    public void openWhitelistManagementMenu() {
        clickWhitelistManagement();
    }

    public List<String> getWhitelistManagementSubmenuItemsText() {
        List<WebElement> submenuLocator = subMenuList;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> submenuItems = wait.until(ExpectedConditions.visibilityOfAllElements(submenuLocator));

        List<String> texts = new ArrayList<>();
        for (WebElement element : submenuItems) {
            texts.add(element.getText());
        }
        return texts;
    }




}
