package example.pageModels;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WikiSearchPageInjection {
    private final Page page;

    public WikiSearchPageInjection(Page page) {
        this.page = page;
    }

    public void navigate(String url) {
        page.navigate(url);
    }

    public void search(String locator, String text) {
        page.locator(locator).fill(text);
        page.locator(locator).press("Enter");
    }
}