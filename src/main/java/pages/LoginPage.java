package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Browser;

public class LoginPage {

    private final Logger logger = Logger.getLogger(LoginPage.class);

    @FindBy(id = "user_email")
    private WebElement usernameField;

    public LoginPage() {
        PageFactory.initElements(Browser.getDriver(), this);
    }

    public void visit() {
        logger.info("Visiting login page");
        Browser.navigateToUrl("http://mofit.madnet-bg.com/");
    }

    public void login(final String username, final String password) {
        logger.info("Login as " + username);
        Browser.writeText(usernameField, username);
    }
}
