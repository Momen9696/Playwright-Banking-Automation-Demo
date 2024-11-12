package pages;
import base.BasePage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import static constants.ElementLocators.loginBtn;

public class LogoutPage extends BasePage {
    public LogoutPage(Page page) {
        super(page);
    }

    public void makeValidLogout() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Log Out")).click();
    }

    public boolean assertTheLoginButtonDisplayed() {
        page.waitForSelector(loginBtn);
        return page.isVisible(loginBtn);
    }

    }

