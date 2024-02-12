package example.engine;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.model.Test;
import com.microsoft.playwright.*;
import example.engine.extensions.ExceptionLoggingExtension;
import org.junit.jupiter.api.*;
import example.engine.extensions.RunnerExtension;
import example.global.ReportingManager;

import java.nio.file.Paths;

import static java.util.Base64.getEncoder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Subclasses will inherit PER_CLASS behavior.
public class TestFixtures {
    // Shared between all tests in the class.
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    @BeforeAll
    void launchBrowser() {
        // getting parameter passed in -Dbrowser
        //System.out.println(System.getProperty("browser"));

        playwright = Playwright.create();
        browser = playwright.chromium().launch(
/*                new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(100)*/
        );
    }

    @BeforeEach
    void createContextAndPage(TestInfo testInfo) {
        String className = testInfo.getTestClass().orElseThrow().getSimpleName();
        ReportingManager.getOrCreateTestClassNode(className);

        context = browser.newContext(
                new Browser.NewContextOptions().setLocale("en-US")
                        .setRecordVideoDir(Paths.get("target/videos/"))
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

        context.close();
        if (testResult) {
            page.video().delete();
            ReportingManager.logTestMethodStatus(ReportingManager.createTestMethodNode(className, methodName), true, null, null, null);

        } else {
            ReportingManager.logTestMethodStatus(
                    ReportingManager.createTestMethodNode(className, methodName), false, screenshotBase64, String.valueOf(page.video().path()), exception);
        }
    }

    @AfterAll
    void closeBrowser() {
        browser.close();
        playwright.close();
    }

    public Page getPage() {
        return page;
    }
}