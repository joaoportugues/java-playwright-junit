package example.engine;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.*;
import example.engine.extensions.ExceptionLoggingExtension;
import example.global.GlobalSetupExtension;
import org.junit.jupiter.api.*;
import example.engine.extensions.RunnerExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import example.global.ReportingManager;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.Base64;

import static java.util.Base64.getEncoder;

// Subclasses will inherit PER_CLASS behavior.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(RunnerExtension.class)
public class TestFixtures {
    // Shared between all tests in the class.
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;
    static ExtentReports extentReport;

    @RegisterExtension
    static GlobalSetupExtension globalSetupExtension = new GlobalSetupExtension();

    @BeforeAll
    void launchBrowser() {

        playwright = Playwright.create();
        browser = playwright.chromium().launch(
/*                new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(100)*/
        );
    }

    @AfterAll
    void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext(
                new Browser.NewContextOptions().setLocale("en-US")
        );
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        boolean testResult = RunnerExtension.getTestResult();
        String testName = RunnerExtension.getTestName();
        String screenshotBase64 = testResult ? null : getEncoder().encodeToString(page.screenshot());
        Throwable exception = testResult ? null : ExceptionLoggingExtension.getException();

        ReportingManager.logTestStatus(ReportingManager.createTest(testName), testResult, screenshotBase64, exception);

        context.close();
    }

    public Page getPage(){
        return page;
    }
}