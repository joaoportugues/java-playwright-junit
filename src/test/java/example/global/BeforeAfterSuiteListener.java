package example.global;

import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;

public class BeforeAfterSuiteListener implements TestExecutionListener {
    private ReportingManager reportingManager;

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        reportingManager = new ReportingManager();
        reportingManager.initializeExtentReport();
        System.out.println("Extent report initialized");
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        reportingManager.flushExtentReport();
        System.out.println("Extent report flushed");
    }
}