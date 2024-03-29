package example.pageModels;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WikiSearchPageInjection extends BaseLocatorSearch{
    private final Page page;

    public WikiSearchPageInjection(Page page) {
        this.page = page;
    }

    public void navigate(String url) {
        page.navigate(url);
    }

    public void search(String locator, String text) {
        findElementByDefaultSelectors(page, locator).fill(text);
        //page.locator(locator).fill(text);
        page.locator(locator).press("Enter");
    }

    public void assertUrl(String url) {
        assertEquals(url, page.url());
    }
}