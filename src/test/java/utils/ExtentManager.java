package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class ExtentManager {
    private static ExtentReports extent;
    private static String reportPath;

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    private static void createInstance() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportsDir = System.getProperty("user.dir") + "/reports";
        new File(reportsDir).mkdirs();

        reportPath = reportsDir + "/TestReport_" + timestamp + ".html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setReportName("Automation Test Report");
        sparkReporter.config().setDocumentTitle("Test Execution Report");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
            try {
                if (reportPath != null) {
                    File reportFile = new File(reportPath);
                    if (reportFile.exists()) {
                        // Open in default system browser
                        Desktop.getDesktop().browse(reportFile.toURI());
                    }
                }
            } catch (Exception e) {
                System.err.println("Failed to open report automatically: " + e.getMessage());
            }
        }
    }


    public static String getReportPath() {
        return reportPath;
    }
}
