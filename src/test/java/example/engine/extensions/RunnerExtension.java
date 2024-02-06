package example.engine.extensions;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class RunnerExtension implements AfterTestExecutionCallback {
    private static Boolean testResult;

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        synchronized (RunnerExtension.class) {
            testResult = context.getExecutionException().isEmpty();
        }
    }

    public static Boolean getTestResult() {
        synchronized (RunnerExtension.class) {
            return testResult;
        }
    }
}
