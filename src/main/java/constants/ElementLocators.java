package constants;

public interface ElementLocators {
    /*
    Will be used in valid login test case
     */
    String emailUsernameField = "[name='username']";
    String passwordField = "[name='password']";
    String loginBtn = "input[type=\"submit\"]";
    String welcomeTextBanner = "p[class=\"smallText\"]";

    /*
    Will be used in valid register test case
     */
    String registerFirstNameField = "[id=\"customer\\.firstName\"]";
    String registerLastNameField = "[id=\"customer\\.lastName\"]";
    String registerAddressField = "[id=\"customer\\.address\\.street\"]";
    String registerCityField = "[id=\"customer\\.address\\.city\"]";
    String registerStateField = "[id=\"customer\\.address\\.state\"]";
    String registerZipCodeField = "[id=\"customer\\.address\\.zipCode\"]";
    String registerSsnField = "[id=\"customer\\.ssn\"]";
    String registerUserNameField = "[id=\"customer\\.username\"]";
    String registerPasswordField = "[id=\"customer\\.password\"]";
    String registerConfirmField = "#repeatedPassword";

    /*
    Will be used in valid open new account test case
     */
    String accountNumberField ="div[id=\"openAccountResult\"]";

    /*
    Will be used in valid request loan test case
     */
    String loanAmountField ="#amount";
    String loanDownPaymentField ="#downPayment";
    String loanRequestSubmitButton= "input[value=\"Apply Now\"]";
    String loanRequestResult = "div[id=\"requestLoanResult\"]";
}
