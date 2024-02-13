package example.global;

import com.aventstack.extentreports.ExtentTest;

public interface ReportManager {
    void flushExtentReport();

    void getOrCreateTestClassNode(String className);

    ExtentTest createTestMethodNode(String className, String methodName);

    void logTestMethodStatus(String className, String methodName, boolean testResult, String screenshotBase64, String path, Throwable exception);
}
