package example.pageModels;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WikiSearchPageChain {
    private final Page page;
    private final Locator searchTermInput;

    public WikiSearchPageChain(Page page) {
        this.page = page;
        this.searchTermInput = page.locator("input[name=\"search\"]");
    }

    public WikiSearchPageChain navigate() {
        page.navigate("https://www.wikipedia.org/");
        return this;
    }

    public WikiSearchPageChain search(String text) {
        searchTermInput.fill(text);
        return this;
    }

    public WikiSearchPageChain pressEnter() {
        searchTermInput.press("Enter");
        return this;
    }

    public WikiSearchPageChain assertUrl(String url) {
        assertEquals(url, page.url());
        return this;
    }
}