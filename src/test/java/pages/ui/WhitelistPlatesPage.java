package pages.ui;

import org.openqa.selenium.WebDriver;

import utils.TestDataReader;

import java.util.NoSuchElementException;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



public class WhitelistPlatesPage {
    private WebDriver driver;
    private Properties prop;

    // Constructor with config passed from BaseTest
    public WhitelistPlatesPage(WebDriver driver, Properties prop) {
        this.driver = driver;
        this.prop = prop;
        PageFactory.initElements(driver, this);
    }

    // WebElements using @FindBy
    @FindBy(xpath = "//a[normalize-space()='Plates']")
    private WebElement plates;
    
    @FindBy(xpath = "//h3[normalize-space()='Plate Whitelist']")
    private WebElement plateMessage;
    // Actions
   
    public void clickPlates() {
    	plates.click();
    }

    public String getPlatesMessage() {
        try {
            return plateMessage.getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    // Full login with data loaded from login.properties
    public void plates() {
    	clickPlates();
    }

    public boolean platePageMessage() {
        String sucMsg = getPlatesMessage();
        return !sucMsg.isEmpty(); // returns true if there's a message
    }
    
    
}
