package base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
    private static WebDriver driver;

    public static WebDriver getDriver(Properties prop) {
        String browser = prop.getProperty("browser", "chrome").toLowerCase();

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();

                if (Boolean.parseBoolean(prop.getProperty("headless", "false"))) {
                    chromeOptions.addArguments("--headless=new");
                }
                if (Boolean.parseBoolean(prop.getProperty("incognito", "false"))) {
                    chromeOptions.addArguments("--incognito");
                }

                chromeOptions.addArguments("--start-maximized", "--disable-notifications");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "safari":
                driver = new SafariDriver(); // macOS only
                break;

            default:
                throw new RuntimeException("Browser not supported: " + browser);
        }
        return driver;
    }
}
