package example.global;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class GlobalSetupExtension implements BeforeAllCallback, AfterAllCallback {
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        System.out.println("Starting "+context.getTestClass().map(Class::getSimpleName));
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        System.out.println("Finished "+context.getTestClass().map(Class::getSimpleName));
    }

    private final String getClassName(ExtensionContext context) {
        return context.getTestClass().map(Class::getSimpleName).orElse("unknown");
    }
}
