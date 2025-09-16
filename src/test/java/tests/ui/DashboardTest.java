package tests.ui;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;


import base.BaseTest;
import pages.ui.DashboardPage;

public class DashboardTest extends BaseTest {
	private static final Logger logger = LogManager.getLogger(TestLaunchBrowser.class);
	// DashboardPage dashboard = new DashboardPage(driver, prop);
	
    @Test
	public void validateDashboardMessage() {
    	DashboardPage dashboard = new DashboardPage(driver, prop, test);        
      // dashboard.dashboardrMessageValidation();

    }

    @Test
   	public void validateWhitelistSubMenu() {
       	DashboardPage dashboard = new DashboardPage(driver, prop, test);        
         // dashboard.dashboardrMessageValidation();
          
          dashboard.clickWhitelistManagement();
          dashboard.validateWhitelistManagementSubmenuItems();

       }

  
    

}

	


