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

        reportPath = reportsDir + "/TestReport_" + timestamp + ".html";  // ✅ save path in static variable

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setReportName("Automation Test Report");
        sparkReporter.config().setDocumentTitle("Test Execution Report");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    // ✅ log summary after suite
    public static void logSummary(ITestContext context) {
        int passed = context.getPassedTests().size();
        int failed = context.getFailedTests().size();
        int skipped = context.getSkippedTests().size();
        int total = passed + failed + skipped;

        ExtentTest summary = getInstance().createTest("Test Execution Summary");
        summary.info("Total Tests: " + total);

        if (passed > 0) summary.pass("Passed: " + passed);
        if (failed > 0) summary.fail("Failed: " + failed);
        if (skipped > 0) summary.skip("Skipped: " + skipped);
    }

    // ✅ flush once at the end & auto-open in browser
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
            try {
                Desktop.getDesktop().browse(new File(reportPath).toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}