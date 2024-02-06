# java-playwright-junit

### Implementation
Thread-safe implementation of PlayWright with Extent Reports with multiple extensions and hooks to allow for, exception catching, logging and timings. It also implements the Page Object Model.

* Playwright - https://playwright.dev/java/docs/test-runners
* Reporting - https://www.extentreports.com/docs/versions/5/java/index.html

### Sequence vs Parallel executions configured in the pom build
```xml
<plugin>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.2.5</version>
    <configuration>
        <properties>
            <configurationParameters>
                junit.platform.reporting.open.xml.enabled = true
                junit.platform.reporting.output.dir = target/surefire-reports
                junit.jupiter.execution.parallel.enabled=false
                junit.jupiter.execution.parallel.mode.default = concurrent
            </configurationParameters>
        </properties>
    </configuration>
</plugin>
```
### How to run?
```mvn test```