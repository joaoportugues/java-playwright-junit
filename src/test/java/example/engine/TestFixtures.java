package example.engine;

import com.aventstack.extentreports.ExtentReports;
import com.microsoft.playwright.*;
import example.engine.extensions.ExceptionLoggingExtension;
import org.junit.jupiter.api.*;
import example.engine.extensions.RunnerExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import example.global.ReportingManager;
import org.junit.jupiter.api.extension.RegisterExtension;

import static java.util.Base64.getEncoder;

// Subclasses will inherit PER_CLASS behavior.
@ExtendWith(RunnerExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestFixtures {
    // Shared between all tests in the class.
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;
    @BeforeAll
    void launchBrowser() {

        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(100)
        );
    }

    @BeforeEach
    void createContextAndPage(TestInfo testInfo) {
        String className = testInfo.getTestClass().orElseThrow().getSimpleName();
        ReportingManager.getOrCreateTestClassNode(className);

        context = browser.newContext(
                new Browser.NewContextOptions().setLocale("en-US")
        );
        page = context.newPage();
    }

    @AfterEach
    void closeContext(TestInfo testInfo) {
        String className = testInfo.getTestClass().orElseThrow().getSimpleName();
        String methodName = testInfo.getDisplayName();
        boolean testResult = RunnerExtension.getTestResult();
        String screenshotBase64 = testResult ? null : getEncoder().encodeToString(page.screenshot());
        Throwable exception = testResult ? null : ExceptionLoggingExtension.getException();

        ReportingManager.logTestMethodStatus(ReportingManager.createTestMethodNode(className, methodName), testResult, screenshotBase64, exception);

        context.close();
    }

    @AfterAll
    void closeBrowser() {
        browser.close();
        playwright.close();
    }

    public Page getPage(){
        return page;
    }
}