package example.engine;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.model.Test;
import com.microsoft.playwright.*;
import example.engine.extensions.ExceptionLoggingExtension;
import example.engine.extensions.PlaywrightExtention;
import org.junit.jupiter.api.*;
import example.engine.extensions.RunnerExtension;
import example.global.ReportingManager;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.nio.file.Paths;

import static java.util.Base64.getEncoder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Subclasses will inherit PER_CLASS behavior.
public class TestFixtures {

    @RegisterExtension
    final static PlaywrightExtention PLAYWRIGHT_EXTENTION = new PlaywrightExtention();



    protected Page getPage() {
        return PLAYWRIGHT_EXTENTION.getPage();
    }
}