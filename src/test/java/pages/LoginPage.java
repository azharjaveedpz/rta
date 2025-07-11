package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.TestDataReader;

import java.util.NoSuchElementException;
import java.util.Properties;

public class LoginPage {
    private WebDriver driver;
    private Properties prop;

    // Locators
    private By emailField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.id("btn_login");
    private By loginErrorMessage = By.xpath("//em[normalize-space()='An incorrect Username or Password was specified']"); 

    // Constructor with config passed from BaseTest
    public LoginPage(WebDriver driver, Properties prop) {
        this.driver = driver;
        this.prop = prop;
    }

    // Actions
    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }
    
    public String getLoginErrorMessage() {
        try {
            return driver.findElement(loginErrorMessage).getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }


    // Full login with data loaded from login.properties
    public void login() {
        Properties loginData = TestDataReader.load("login","password", prop);
        String username = loginData.getProperty("username");
        String password = loginData.getProperty("password");

        enterEmail(username);
        enterPassword(password);
        clickLogin();
    }
    
    public boolean loginErrMessageValidation() {
        String errorMsg = getLoginErrorMessage();
        return !errorMsg.isEmpty(); // returns true if there's a message
    }
}
