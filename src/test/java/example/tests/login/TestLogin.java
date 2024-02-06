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
import example.global.GlobalSetupExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(RunnerExtension.class)
@ExtendWith(TimingExtension.class)
@ExtendWith({ExceptionLoggingExtension.class})
public class TestLogin extends TestFixtures {
    protected Page page;

    @BeforeEach
    void beforeEach() {
        page = getPage();
    }

/*    @Test
    void shouldClickButton() {
        page.navigate("data:text/html,<script>var result;</script><button onclick='result=\"Clicked\"'>Go</button>");
        page.locator("button").click();
        assertEquals("Clicked", page.evaluate("result"));
    }

    @Test
    void shouldCheckTheBox() {
        page.setContent("<input id='checkbox' type='checkbox'></input>");
        page.locator("input").check();
        assertTrue((Boolean) page.evaluate("() => window['checkbox'].checked"));
    }*/

    @Test
    @DisplayName("Test Wikipedia")
    @Order(1)
    void shouldSearchWiki() {
        WikiSearchPage wikiSearchPage = new WikiSearchPage(page);
        wikiSearchPage.navigate();
        wikiSearchPage.search("playwright");
        assertEquals("https://en.wikipedia.org/wiki/Playwright", page.url());
    }

    @Test
    @DisplayName("Test Wikipedia Fail")
    @Order(2)
    void shouldSearchWikiFail() {
        WikiSearchPage wikiSearchPage = new WikiSearchPage(page);
        wikiSearchPage.navigate();
        wikiSearchPage.search("playwright");
        assertEquals("https://en.wikipedia.org/wiki/NotPlaywright", page.url());
    }
}