package example.engine.extensions;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class RunnerExtension implements AfterTestExecutionCallback {
    private static Boolean testResult;
    private static String testName;

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        synchronized (RunnerExtension.class) {
            testResult = context.getExecutionException().isEmpty();
            testName = context.getDisplayName();
        }
    }

    public static Boolean getTestResult() {
        synchronized (RunnerExtension.class) {
            return testResult;
        }
    }

    public static String getTestName() {
        synchronized (RunnerExtension.class) {
            return testName;
        }
    }
}
