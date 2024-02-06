package example.suites;

import org.junit.jupiter.api.BeforeAll;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.SuiteDisplayName;

@SuiteDisplayName("JUnit Platform Suite Demo")
@SelectPackages({"example.tests.login", "example.tests.search"})
@org.junit.platform.suite.api.Suite
class Suite {
}