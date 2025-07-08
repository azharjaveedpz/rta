package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.TestDataReader;

import java.util.Properties;

public class LoginPage {
    private WebDriver driver;
    private Properties prop;

    // Locators
    private By emailField = By.id("email");
    private By passwordField = By.id("password");
    private By loginButton = By.id("loginBtn");

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

    // Full login with data loaded from login.properties
    public void login() {
        Properties loginData = TestDataReader.load("login", prop);
        String username = loginData.getProperty("username");
      //  String password = loginData.getProperty("password");

        enterEmail(username);
      //  enterPassword(password);
       // clickLogin();
    }
}
