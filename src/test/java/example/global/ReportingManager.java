package example.global;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.util.HashMap;
import java.util.Map;

public class ReportingManager {
    private static ExtentReports extentReport;
    private static final Map<String, ExtentTest> testClassNodes = new HashMap<>();
    private static final Object lock = new Object();

    public static void initializeExtentReport() {
        synchronized (lock) {
            if (extentReport == null) {
                extentReport = new ExtentReports();
                ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentReport.html");
                extentReport.attachReporter(spark);
            }
        }
    }

    public static void getOrCreateTestClassNode(String className) {
        synchronized (lock) {
            testClassNodes.computeIfAbsent(className, extentReport::createTest);
        }
    }

    public static ExtentTest createTestMethodNode(String className, String methodName) {
        synchronized (lock) {
            ExtentTest classNode = testClassNodes.get(className);
            if (classNode == null) {
                throw new IllegalStateException("Test class node not found: " + className);
            }
            return classNode.createNode(methodName);
        }
    }

    public static void logTestMethodStatus(ExtentTest methodNode, boolean testResult, String screenshotBase64, String path, Throwable exception) {
        synchronized (lock) {
            if (testResult) {
                methodNode.pass("Test passed");
            } else {
                methodNode.fail("Test failed", MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
                methodNode.info("<a href='" + path + "' target='_blank'>Video</a>");
                if (exception != null) {
                    methodNode.log(Status.FAIL, exception);
                }
            }
        }
    }

    public static void flushExtentReport() {
        synchronized (lock) {
            if (extentReport != null) {
                extentReport.flush();
            }
        }
    }
}
