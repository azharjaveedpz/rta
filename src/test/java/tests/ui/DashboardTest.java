package tests.ui;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import pages.ui.DashboardPage;
import pages.ui.LoginPage;

public class DashboardTest extends BaseTest {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);

		public boolean validateDashboardMessage() {
        DashboardPage dashboard = new DashboardPage(driver, prop);
        boolean valid = dashboard.dashboardrMessageValidation();

        if (valid) {
            String sucMsg = dashboard.getDashboardMessage();
            logger.info("Navigated to dashboard: " + sucMsg);
            test.log(Status.PASS, "Navigated to dashboard and welcome message appear as " + sucMsg);
        } else {
            logger.info("Login failed");
            test.log(Status.FAIL, "Login failed");
        }
        return valid;
    }

    // Helper to validate Whitelist Management submenu
    public void validateWhitelistManagementSubmenu() {
        DashboardPage dashboard = new DashboardPage(driver, prop);
        dashboard.openWhitelistManagementMenu();

        List<String> submenuItems = dashboard.getWhitelistManagementSubmenuItemsText();

        if (submenuItems.size() >= 2) {
            logger.info("Submenu contains: " + submenuItems.get(0) + " and " + submenuItems.get(1));
            test.log(Status.PASS, "Submenu contains both expected items.");
        } else {
            logger.info("Submenu does not contain both expected items.");
            test.log(Status.FAIL, "Submenu does not contain both expected items.");
            throw new AssertionError("Submenu does not contain both expected items.");
        }
    }

    // Existing test methods calling helper methods
    @Test
    public void testDashboard() {
        validateDashboardMessage();
    }

    @Test
    public void validateWhitelistManagementSubmenuList() {
        validateWhitelistManagementSubmenu();
    }

}

	


