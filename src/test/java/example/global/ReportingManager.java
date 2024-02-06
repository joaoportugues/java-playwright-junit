package example.global;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.util.Base64;

public class ReportingManager {
    private static ExtentReports extentReport;

    public static void initializeExtentReport(String className) {
        extentReport = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("target/"+className+".html");
        extentReport.attachReporter(spark);
    }

    public static ExtentTest createTest(String testName) {
        return extentReport.createTest(testName);
    }

    public static void logTestStatus(ExtentTest test, boolean testResult, String screenshotBase64, Throwable exception) {
        if (testResult) {
            test.pass("Test passed");
        } else {
            test.fail("Test failed", MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
            if (exception != null) {
                test.log(Status.INFO, exception);
            }
        }
    }

    public static void flushExtentReport() {
        extentReport.flush();
    }
}
