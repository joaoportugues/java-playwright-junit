package example.tests.logout;

import com.microsoft.playwright.Page;
import example.engine.TestFixtures;
import example.engine.extensions.ExceptionLoggingExtension;
import example.engine.extensions.RunnerExtension;
import example.engine.extensions.TimingExtension;
import example.models.WikiSearchPage;
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

    @DisplayName("Test Wikipedia3")
    @Order(1)
    @Test
    void shouldSearchWiki3() {
        WikiSearchPage wikiSearchPage = new WikiSearchPage(page);
        wikiSearchPage.navigate();
        wikiSearchPage.search("playwright");
        assertEquals("https://en.wikipedia.org/wiki/Playwright", page.url());
    }

    @DisplayName("Test Wikipedia Fail3")
    @Order(2)
    @Test
    @Disabled
    void shouldSearchWikiFail3() {
        WikiSearchPage wikiSearchPage = new WikiSearchPage(page);
        wikiSearchPage.navigate();
        wikiSearchPage.search("playwright");
        assertEquals("https://en.wikipedia.org/wiki/NotPlaywright", page.url());
    }
}