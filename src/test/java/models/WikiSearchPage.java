package models;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class WikiSearchPage {
    private final Page page;
    private final Locator searchTermInput;

    public WikiSearchPage(Page page) {
        this.page = page;
        this.searchTermInput = page.locator("input[name=\"search\"]");
    }

    public void navigate() {
        page.navigate("https://www.wikipedia.org/");
    }

    public void search(String text) {
        searchTermInput.fill(text);
        searchTermInput.press("Enter");
    }
}