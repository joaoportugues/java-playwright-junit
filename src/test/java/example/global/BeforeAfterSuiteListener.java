package example.global;

import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;

public class BeforeAfterSuiteListener implements TestExecutionListener {

    private static String className;

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        ReportingManager.initializeExtentReport("testReport.html");
        System.out.println("Extent report initialized");
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        ReportingManager.flushExtentReport();
        System.out.println("Extent report flushed");
    }
}