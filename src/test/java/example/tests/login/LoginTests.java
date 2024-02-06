package example.tests.login;

import com.microsoft.playwright.Page;
import example.engine.TestFixtures;
import example.engine.extensions.ExceptionLoggingExtension;
import example.engine.extensions.RunnerExtension;
import example.engine.extensions.TimingExtension;
import example.models.WikiSearchPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(TimingExtension.class)
@ExtendWith({ExceptionLoggingExtension.class})
@ExtendWith(RunnerExtension.class)
public class LoginTests extends TestFixtures {
    protected Page page;

    @BeforeEach
    void beforeEach() {
        page = getPage();
    }

    @DisplayName("Test Wikipedia")
    @Order(1)
    @Test
    void shouldSearchWiki() {
        WikiSearchPage wikiSearchPage = new WikiSearchPage(page);
        wikiSearchPage.navigate();
        wikiSearchPage.search("playwright");
        assertEquals("https://en.wikipedia.org/wiki/Playwright", page.url());
    }

    @DisplayName("Test Wikipedia Fail")
    @Order(2)
    @Test
    void shouldSearchWikiFail() {
        WikiSearchPage wikiSearchPage = new WikiSearchPage(page);
        wikiSearchPage.navigate();
        wikiSearchPage.search("playwright");
        assertEquals("https://en.wikipedia.org/wiki/NotPlaywright", page.url());
    }
}