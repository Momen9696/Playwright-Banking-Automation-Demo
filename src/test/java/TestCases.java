import base.BrowserFactory;
import static constants.ElementLocators.*;
import static constants.StaticTestData.*;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.*;
import utilis.EncryptionUtility;
import utilis.ExcelUtils;


public class TestCases extends BrowserFactory {
    /*
    * Test cases shouldn't be together but as it's an assessment I do merge it
    */
    SoftAssert softAssert;
    LoginPage loginPage;
    LogoutPage logoutPage;
    RegistrationPage registrationPage;
    OpenNewAccountPage openNewAccountPage;
    RequestLoanPage requestLoanPage;
    String loginUserName, loginPassword;
    int row;

    ExcelUtils testDataSheet;

    @BeforeClass
    public void beforeClassInitialization() throws Exception {
        loginPage = new LoginPage(page);
        registrationPage = new RegistrationPage(page);
        logoutPage = new LogoutPage(page);
        openNewAccountPage = new OpenNewAccountPage(page);
        requestLoanPage = new RequestLoanPage(page);
        testDataSheet = loginPage.getTestDataSheet();
        row = testDataSheet.getRowNum("validData");
        loginUserName = testDataSheet.getCellData(row
                , testDataSheet.getColNumber("loginUserName"));

        loginPassword = EncryptionUtility
                .decrypt(testDataSheet.getCellData
                        (row, testDataSheet.getColNumber("loginPassword")));

    }

    @BeforeMethod
    public void beforeMethodInitialization() {

        softAssert = new SoftAssert();
    }

    @Test (priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    @Description(" Validate the successful user registration when entering valid data in mandatory fields ")
    public void validRegistration() {
        registrationPage.makeValidRegistration(loginPassword);
        softAssert.assertTrue(registrationPage.assertOnSuccessMessage(), "Welcome message didn't display");
        softAssert.assertAll();
    }

    @Test(priority = 2)
    @Severity(SeverityLevel.CRITICAL)
    @Description(" Validate the successful user login when entering valid credentials ")
    public void validLogin() {
        loginPage.makeValidLogin(loginUserName,
                loginPassword);
        softAssert.assertTrue(loginPage.
                assertOnText(EXPECTED_SUCCESSFUL_LOGIN_TEXT, welcomeTextBanner), "User didn't login successfully or welcome banner isn't displayed");
        softAssert.assertAll();
    }
    @Test(priority = 2)
    @Severity(SeverityLevel.CRITICAL)
    @Description(" Validate the successful user logout after login")
    public void validLogout()  {
        loginPage.makeValidLogin(loginUserName,
                loginPassword);        logoutPage.makeValidLogout();
        softAssert.assertTrue(logoutPage.assertTheLoginButtonDisplayed(),"User didn't logout or navigated to invalid");
        softAssert.assertAll();
    }
    @Test(priority = 2)
    @Severity(SeverityLevel.CRITICAL)
    @Description(" Validate the successful user open new account after login")
    public void validOpenNewAccount() {
        loginPage.makeValidLogin(loginUserName,
                loginPassword);        openNewAccountPage.makeValidOpenAccount();
        softAssert.assertTrue(openNewAccountPage.assertNewAccountNumberGenerated());
        softAssert.assertAll();
    }
    @Test(priority = 2)
    @Severity(SeverityLevel.CRITICAL)
    @Description(" Validate the successful request loan when user fill the form with valid data")
    public void validRequestLoan()  {
        loginPage.makeValidLogin(loginUserName,
                loginPassword);
        requestLoanPage.makeValidRequestLoan();
        requestLoanPage.assertLoanSubmittedMessage();
        softAssert.assertAll();
    }
    @AfterMethod

    public void screenshot(ITestResult result) {
        softAssert.assertAll();
        captureScreenShot(result);
        }
    }
