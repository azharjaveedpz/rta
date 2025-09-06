package pages.ui;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import utils.TestDataReader;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;



public class LoginPage {
    private WebDriver driver;
    private Properties prop;

    // Constructor with config passed from BaseTest
    public LoginPage(WebDriver driver, Properties prop, ExtentTest test) {
        this.driver = driver;
        this.prop = prop;
        PageFactory.initElements(driver, this);
    }

    // WebElements using @FindBy
    @FindBy(id = "login_username")
    private WebElement emailField;

    @FindBy(id = "login_password")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    @FindBy(xpath = "//em[normalize-space()='An incorrect Username or Password was specified']")
    private WebElement loginErrorMessage;
    
    @FindBy(xpath = "//em[normalize-space()='An incorrect Username or Password was specified']")
    private WebElement loginSucessMessage;

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
    public void login(String email, String password ) {
        Properties loginData = TestDataReader.load("login", "password", prop);
       

           enterEmail(email);
          enterPassword(password);
        clickLogin();
    }

    public boolean loginErrMessageValidation() {
        String errorMsg = getLoginErrorMessage();
        return !errorMsg.isEmpty(); // returns true if there's a message
    }
    
 // ================= ChromePasswordPopup =================
    public void dismissChromePasswordAlert() {
        try {
            // Switch to alert if present
            Alert alert = driver.switchTo().alert();
            alert.dismiss();  // or alert.accept();
            System.out.println("⚡ Chrome password alert dismissed");
        } catch (NoAlertPresentException ignored) {
            // No popup → safe to continue
        }
    }


}
