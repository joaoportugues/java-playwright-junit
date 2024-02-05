package example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import example.extensions.RunnerExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

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
        boolean testResult = RunnerExtension.getTestResult();
        String testName = RunnerExtension.getTestName();

        extentReport.createTest(testName)
                .log(testResult ? Status.PASS : Status.FAIL, "This is a logging event for MyFirstTest");

        context.close();
    }
}