package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Browser;

import static utils.Browser.getDriver;

public class HomePage {

    private final Logger logger = Logger.getLogger(HomePage.class);

    @FindBy(className = "profile-link")
    private WebElement profileLink;

    @FindBy(id = "signOutButton")
    private WebElement signOutButton;

    public HomePage() {
        PageFactory.initElements(getDriver(), this);
    }

    public void visit() {
        logger.info("Visiting home page");
        Browser.navigateToUrl("http://mofit.madnet-bg.com/");
    }

    public boolean isLoggedIn() {
        return Browser.isElementPresent(signOutButton);
    }

    public LoginPage clickLoginButton() {
        logger.info("Clicking logging button");
        Browser.clickElement(profileLink);
        return new LoginPage();
    }

}
