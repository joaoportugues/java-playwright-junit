package example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import javax.naming.Context;


// Subclasses will inherit PER_CLASS behavior.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestFixtures {
    // Shared between all tests in the class.
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;
    static ExtentReports extent;

    @BeforeAll
    void launchBrowser() {
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
        extent.attachReporter(spark);

        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(100));
    }

    @AfterAll
    void closeBrowser() {
        extent.flush();

        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        extent.createTest("MyFirstTest")
                .log(Status.PASS, "This is a logging event for MyFirstTest, and it passed!");

        context.close();
    }
}