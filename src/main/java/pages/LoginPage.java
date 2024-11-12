package pages;
import base.BasePage;
import com.microsoft.playwright.Page;
import utilis.ConfigManager;
import utilis.ExcelUtils;
import static constants.ElementLocators.*;
import static constants.ElementLocators.loginBtn;

public class LoginPage extends BasePage {


    public LoginPage(Page page) {
        super(page);
    }
    ExcelUtils testDataSheet = new ExcelUtils(
            ConfigManager.getInstance().
                    getString("preProdTestDateDirectory")+ConfigManager.env
                    +"/"+ConfigManager.getProjectId()
            +ConfigManager.getInstance().getString("loginTestDataSheet")
            ,"LoginTestData");

    public ExcelUtils getTestDataSheet() {
        return testDataSheet;
    }

    public boolean assertOnText(String expectedText, String actualTextSelector) {
        String actualText = page.innerText(actualTextSelector); // Get text from the page using selector
        if (actualText.contains(expectedText)) {  // Use .equals for string comparison
            return true;
        } else {
            System.out.println("The text should be \"" + expectedText + "\" but got \"" + actualText + "\".");
            return false;
        }
    }

    public void makeValidLogin(String userName, String password) {
        page.fill(emailUsernameField, userName);
        page.fill(passwordField, password);
        page.click(loginBtn);
    }



}
