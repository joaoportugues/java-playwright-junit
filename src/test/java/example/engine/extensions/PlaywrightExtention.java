package example.engine.extensions;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import example.global.ReportingManager;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.nio.file.Paths;

import static java.util.Base64.getEncoder;

public class PlaywrightExtention implements
        BeforeEachCallback, BeforeAllCallback,
        AfterAllCallback, AfterEachCallback {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    @Override
    public void beforeAll(ExtensionContext context) {
        // getting parameter passed in -Dbrowser
        //System.out.println(System.getProperty("browser"));

        playwright = Playwright.create();
        browser = playwright.chromium().launch(
/*                new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(100)*/
        );
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        String className = context.getTestClass().orElseThrow().getSimpleName();
        ReportingManager.getOrCreateTestClassNode(className);

        browserContext = browser.newContext(
                new Browser.NewContextOptions().setLocale("en-US")
                        .setRecordVideoDir(Paths.get("target/videos/"))
        );
        page = browserContext.newPage();
    }

    @Override
    public void afterEach(ExtensionContext context) {
        String className = context.getTestClass().orElseThrow().getSimpleName();
        String methodName = context.getDisplayName();
        boolean testResult = RunnerExtension.getTestResult();
        String screenshotBase64 = testResult ? null : getEncoder().encodeToString(page.screenshot());
        Throwable exception = testResult ? null : ExceptionLoggingExtension.getException();

        browserContext.close();
        if (testResult) {
            page.video().delete();
            ReportingManager.logTestMethodStatus(ReportingManager.createTestMethodNode(className, methodName), true, null, null, null);

        } else {
            ReportingManager.logTestMethodStatus(
                    ReportingManager.createTestMethodNode(className, methodName), false, screenshotBase64, String.valueOf(page.video().path()), exception);
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {
        browser.close();
        playwright.close();
    }

    public Page getPage() {
        return page;
    }
}
