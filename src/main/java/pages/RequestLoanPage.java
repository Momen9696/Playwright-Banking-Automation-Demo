package pages;

import base.BasePage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static constants.ElementLocators.*;
import static constants.StaticTestData.*;

public class RequestLoanPage extends BasePage {
    public RequestLoanPage(Page page) {
        super(page);
    }
    public void makeValidRequestLoan() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Request Loan")).click();// Wait for the form to be visible and ready (with a timeout of 30 seconds)
        page.fill(loanAmountField,LOAN_AMOUNT);
        page.fill(loanDownPaymentField,LOAN_DOWN_PAYMENT);
        page.click(loanRequestSubmitButton);
    }
    public boolean assertLoanSubmittedMessage() {
        page.waitForSelector(loanRequestResult);
        String text = page.innerText(loanRequestResult);
        if (text.contains(LOAN_SUBMITTED_SUCCESS_MESSAGE)) {
            return true;
        } else {
            System.out.println("The loan didn't send or success message is invalid");
            return false;
        }
    }
}


