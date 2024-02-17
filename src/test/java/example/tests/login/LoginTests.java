package example.tests.login;

import com.microsoft.playwright.Page;
import example.database.dao.UserDAO;
import example.engine.TestFixtures;
import example.engine.extensions.ExceptionLoggingExtension;
import example.engine.extensions.RunnerExtension;
import example.engine.extensions.TimingExtension;
import example.pageModels.WikiSearchPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.SQLException;

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
        var wikiSearchPage = new WikiSearchPage(page);
        wikiSearchPage.navigate();
        wikiSearchPage.search("playwright");
        assertEquals("https://en.wikipedia.org/wiki/Playwright", page.url());
    }

    @DisplayName("Test Wikipedia Fail")
    @Order(2)
    @Test
    void shouldSearchWikiFail() throws SQLException {
        var email = "test";

        var wikiSearchPage = new WikiSearchPage(page);
        wikiSearchPage.navigate();
        wikiSearchPage.search("playwright");
        var userId = UserDAO.getUserById(1).getEmail();
        System.out.println(userId);
        assertEquals("https://en.wikipedia.org/wiki/NotPlaywright", page.url());
    }
}