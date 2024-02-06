package example.engine.extensions;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class RunnerExtension implements AfterTestExecutionCallback {
    private static Boolean testResult;
    private static String testName;

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        testResult = context.getExecutionException().isEmpty();
        testName = context.getDisplayName();
    }

    public static Boolean getTestResult() {
        return testResult;
    }

    public static String getTestName() {
        return testName;
    }
}
