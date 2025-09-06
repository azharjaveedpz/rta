package base;

import java.util.HashMap;
import java.util.Map;
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

		    // Headless (optional)
		    if (Boolean.parseBoolean(prop.getProperty("headless", "false"))) {
		        chromeOptions.addArguments("--headless=new");
		    }

		    // Incognito (optional)
		    if (Boolean.parseBoolean(prop.getProperty("incognito", "false"))) {
		        chromeOptions.addArguments("--incognito");
		    }

		    // Disable notifications + password manager popups
		    chromeOptions.addArguments(
		        "--start-maximized",
		        "--disable-notifications",
		        "--disable-save-password-bubble",
		        "--disable-password-manager-reauthentication",
		        "--disable-features=PasswordManagerEnabled,PasswordLeakDetection,AutofillServerCommunication,AutofillEnableAccountWalletStorage"
		    );

		    // Preferences (disable password manager completely)
		    Map<String, Object> prefs = new HashMap<>();
		    prefs.put("credentials_enable_service", false);
		    prefs.put("profile.password_manager_enabled", false);
		    prefs.put("profile.password_leak_detection_enabled", false);
		    chromeOptions.setExperimentalOption("prefs", prefs);

		    // Use a fresh profile to avoid Google account sync interference
		    chromeOptions.addArguments("--guest");  // ensures a clean profile

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
