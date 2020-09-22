package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Browser;

import static utils.Browser.getDriver;

public class SignupPage {

    private final Logger logger = Logger.getLogger(SignupPage.class);

    @FindBy(id = "signup-email")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "passwordConfirm")
    private WebElement passwordConfirmField;

    @FindBy(id = "passwordEye")
    private WebElement passwordEye;

    @FindBy(id = "passwordConfirmEye")
    private WebElement passwordConfirmEye;

    @FindBy(id = "signUpFormButton")
    private WebElement signupButton;

    @FindBy(className = "badge-warning")
    private WebElement errorMessage;

    public SignupPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public void enterEmailAndPassword(final String username, final String password) {
        logger.info("Signup as " + username);
        Browser.writeText(emailField, username);
        Browser.writeText(passwordField, password);
        Browser.writeText(passwordConfirmField, password);
    }

    public String getPasswordFieldText() {
        final String currentPassword = Browser.getElementText(passwordField);
        logger.info("Currently entered password" + currentPassword);
        return currentPassword;
    }

    public void showPassword() {
        logger.info("Showing current password");
        Browser.clickElement(passwordEye);
    }

    public void showConfirmPassword() {
        logger.info("Showing confirm password");
        Browser.clickElement(passwordConfirmEye);
    }

    public DashboardPage signup() {
        Browser.clickElement(signupButton);
        return new DashboardPage();
    }

    public String getErrorMessage() {
        return Browser.getElementText(errorMessage);
    }
}
