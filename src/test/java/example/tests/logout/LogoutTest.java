package example.tests.logout;

import com.microsoft.playwright.Page;
import example.engine.TestFixtures;
import example.engine.extensions.ExceptionLoggingExtension;
import example.engine.extensions.RunnerExtension;
import example.engine.extensions.TimingExtension;
import example.pageModels.WikiSearchPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(TimingExtension.class)
@ExtendWith({ExceptionLoggingExtension.class})
@ExtendWith(RunnerExtension.class)
public class LogoutTest extends TestFixtures {
    protected Page page;

    @BeforeEach
    void beforeEach() {
        page = getPage();
    }

    @DisplayName("Test Wikipedia3")
    @Order(1)
    @Test
    void shouldSearchWiki3() {
        var wikiSearchPage = new WikiSearchPage(page);
        wikiSearchPage.navigate();
        wikiSearchPage.search("playwright");
        assertEquals("https://en.wikipedia.org/wiki/Playwright", page.url());
    }

    @DisplayName("Test Wikipedia Fail3")
    @Order(2)
    @Test
    @Disabled
    void shouldSearchWikiFail3() {
        var wikiSearchPage = new WikiSearchPage(page);
        wikiSearchPage.navigate();
        wikiSearchPage.search("playwright");
        assertEquals("https://en.wikipedia.org/wiki/NotPlaywright", page.url());
    }
}