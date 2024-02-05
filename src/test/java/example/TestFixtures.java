package example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.*;
import example.extensions.ExceptionLoggingExtension;
import org.junit.jupiter.api.*;
import example.extensions.RunnerExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Base64;

// Subclasses will inherit PER_CLASS behavior.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(RunnerExtension.class)
class TestFixtures {
    // Shared between all tests in the class.
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;
    static ExtentReports extentReport;

    @BeforeAll
    void launchBrowser() {
        extentReport = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
        extentReport.attachReporter(spark);

        playwright = Playwright.create();
        browser = playwright.chromium().launch(
/*                new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(100)*/
        );
    }

    @AfterAll
    void closeBrowser() {
        extentReport.flush();

        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        ExtentTest test = extentReport.createTest(RunnerExtension.getTestName());
        boolean testResult = RunnerExtension.getTestResult();

        test.log(testResult ? Status.PASS : Status.FAIL,
                testResult ? null : MediaEntityBuilder.createScreenCaptureFromBase64String(
                        Base64.getEncoder().encodeToString(
                                page.screenshot())).build());

        if(!testResult) {
            test.log(Status.FAIL, ExceptionLoggingExtension.getException());
        }

        context.close();
    }
}