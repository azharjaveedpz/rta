package base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {

	public static WebDriver getDriver(Properties prop) {
		String browser = prop.getProperty("browser", "chrome").toLowerCase();
		WebDriver driver;

		switch (browser) {
		case "chrome":
			ChromeOptions chromeOptions = new ChromeOptions();

			if (Boolean.parseBoolean(prop.getProperty("headless", "false"))) {
				chromeOptions.addArguments("--headless=new");
			}
			if (Boolean.parseBoolean(prop.getProperty("incognito", "false"))) {
				chromeOptions.addArguments("--incognito");
			}

			chromeOptions.addArguments("--start-maximized", "--disable-notifications");

			// Selenium Manager will download and configure ChromeDriver automatically
			driver = new ChromeDriver(chromeOptions);
			break;

		case "safari":
			driver = new SafariDriver(); // SafariDriver comes bundled with Safari on macOS
			break;

		default:
			throw new RuntimeException("Browser not supported: " + browser);
		}

		return driver;
	}
}
