package example.engine.extensions;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ExceptionLoggingExtension implements AfterTestExecutionCallback {

    private static final ThreadLocal<Throwable> exception = new ThreadLocal<>();

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        context.getExecutionException().ifPresent(exception::set);
    }

    public static Throwable getException() {
        return exception.get();
    }
}
