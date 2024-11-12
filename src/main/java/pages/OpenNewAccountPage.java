package pages;
import base.BasePage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static constants.ElementLocators.accountNumberField;

public class OpenNewAccountPage extends BasePage {
    public OpenNewAccountPage(Page page) {

        super(page);

    }
    public void makeValidOpenAccount() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Open New Account")).click();// Wait for the form to be visible and ready (with a timeout of 30 seconds)
        page.waitForTimeout(1000);
        page.evaluate("document.querySelector('input[value=\"Open New Account\"]').click();");
    }

    /*
    * There is multi ways to assert as it contains a duplication account number error
    , total balance doesn't affected but as it's a training the assertion will be created
    to ensure the execution doesn't have an error
    */
    public boolean assertNewAccountNumberGenerated() {
        page.waitForSelector(accountNumberField);
        String text = page.innerText(accountNumberField);
        if (text != null && !text.isEmpty()) {
            return true;
        } else {
            System.out.println("The element's text is null or empty.");
            return false;
        }
    }
}


