package example.tests.search;

import com.microsoft.playwright.Page;
import example.engine.TestFixtures;
import example.engine.extensions.ExceptionLoggingExtension;
import example.engine.extensions.RunnerExtension;
import example.engine.extensions.TimingExtension;
import example.pageModels.WikiSearchPageChain;
import example.pageModels.WikiSearchPageInjection;
import org.junit.jupiter.api.*;
import example.pageModels.WikiSearchPage;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(TimingExtension.class)
@ExtendWith({ExceptionLoggingExtension.class})
@ExtendWith(RunnerExtension.class)
public class SearchTest extends TestFixtures {
    protected Page page;

    @BeforeEach
    void beforeEach() {
        page = getPage();
    }

    @DisplayName("Test Wikipedia2 Chaining")
    @Order(1)
    @Test
    void shouldSearchWiki2() {
        // create a chain instead of usual pom
        var wikiSearchPage = new WikiSearchPageChain(page);
        wikiSearchPage.navigate()
                .search("playwright")
                .pressEnter()
                .assertUrl("https://en.wikipedia.org/wiki/Playwright");
    }

    @DisplayName("Test Wikipedia2 Dependency Injection")
    @Order(2)
    @Test
    void shouldSearchWiki2Injection() {
        // dependency injection
        var wikiSearchPage = new WikiSearchPageInjection(page);
        wikiSearchPage.navigate("https://www.wikipedia.org/");
        wikiSearchPage.search("input[name=\"search\"]", "playwright");
        wikiSearchPage.assertUrl("https://en.wikipedia.org/wiki/Playwright");
    }

    @DisplayName("Test Wikipedia Fail2")
    @Order(3)
    @Test
    void shouldSearchWikiFail2() {
        // page model
        var wikiSearchPage = new WikiSearchPage(page);
        wikiSearchPage.navigate();
        wikiSearchPage.search("playwright");
        assertEquals("https://en.wikipedia.org/wiki/NotPlaywright", page.url());
    }
}