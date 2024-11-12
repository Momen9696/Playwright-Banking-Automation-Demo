package pages;
import base.BasePage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import utilis.ExcelUtils;


import java.util.Random;

import static constants.ElementLocators.*;
import static constants.StaticTestData.*;


public class RegistrationPage extends BasePage {
    String uniqueUserName;
    LoginPage loginPage = new LoginPage(page);
    public RegistrationPage(Page page) {
        super(page);
    }
    ExcelUtils registerTestDataSheet = loginPage.getTestDataSheet();

    public void setRegistrationFormValidData(){
    page.fill(registerFirstNameField,REGISTER_FIRST_NAME);
    page.fill(registerLastNameField,REGISTER_LAST_NAME);
    page.fill(registerAddressField,REGISTER_ADDRESS);
    page.fill(registerCityField,REGISTER_CITY);
    page.fill(registerStateField,REGISTER_STATE);
    page.fill(registerZipCodeField,REGISTER_ZIP_CODE);
    page.fill(registerSsnField,REGISTER_SSN);
    }

    public void setUniqueValidUserName(){
        Random random = new Random();
        int randomEightDigits = random.nextInt(90000000) + 10000000;
        uniqueUserName = String.valueOf(randomEightDigits);
        page.fill(registerUserNameField,uniqueUserName);
        registerTestDataSheet.setCellData(registerTestDataSheet.getRowNum("validData"),
                registerTestDataSheet.getColNumber("loginUserName"),uniqueUserName);
    }
    public void getEncryptedPassword(String encryptedPassword){
        page.fill(registerPasswordField,encryptedPassword);
        page.fill(registerConfirmField,encryptedPassword);
    }
    
    public void makeValidRegistration(String encryptedPassword)  {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Register")).click();
        setRegistrationFormValidData();
        setUniqueValidUserName();
        getEncryptedPassword(encryptedPassword);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Register")).click();
    }
    public boolean assertOnSuccessMessage(){
        page.waitForSelector("text=Your account was created",
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
        boolean isMessageVisible = page.getByText("Your account was created").isVisible();
        return isMessageVisible;
    }
}





