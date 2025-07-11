package utils;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class ScreenshotUtils {

    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        String dateTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotFolder = System.getProperty("user.dir") + "/reports/screenshots/";
        String screenshotFile = screenshotName + "_" + dateTime + ".png";
        String fullPath = screenshotFolder + screenshotFile;

        try {
            // Ensure the folder exists
            File folder = new File(screenshotFolder);
            if (!folder.exists()) folder.mkdirs();

            // Take screenshot
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(fullPath);
            FileUtils.copyFile(src, dest);

            // Return relative path for Extent report to access it
            return "screenshots/" + screenshotFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
