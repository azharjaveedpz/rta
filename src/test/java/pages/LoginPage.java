package pages;

import org.openqa.selenium.WebDriver;

import utils.TestDataReader;

import java.util.NoSuchElementException;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



public class LoginPage {
    private WebDriver driver;
    private Properties prop;

    // Constructor with config passed from BaseTest
    public LoginPage(WebDriver driver, Properties prop) {
        this.driver = driver;
        this.prop = prop;
        PageFactory.initElements(driver, this);
    }

    // WebElements using @FindBy
    @FindBy(id = "username")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "btn_login")
    private WebElement loginButton;

    @FindBy(xpath = "//em[normalize-space()='An incorrect Username or Password was specified']")
    private WebElement loginErrorMessage;

    // Actions
    public void enterEmail(String email) {
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public String getLoginErrorMessage() {
        try {
            return loginErrorMessage.getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    // Full login with data loaded from login.properties
    public void login() {
        Properties loginData = TestDataReader.load("login", "password", prop);
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
