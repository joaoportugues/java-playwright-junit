package example.suites;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.SuiteDisplayName;

@org.junit.platform.suite.api.Suite
@SuiteDisplayName("JUnit Platform Suite Demo")
@SelectPackages({"example.tests.login", "example.tests.search"})
class Suite {
}