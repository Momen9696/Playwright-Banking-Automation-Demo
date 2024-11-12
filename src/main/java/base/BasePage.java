package base;

import com.microsoft.playwright.Page;

public class BasePage {

    protected Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    public String getPageTitle() {
        return page.title();
    }
}

