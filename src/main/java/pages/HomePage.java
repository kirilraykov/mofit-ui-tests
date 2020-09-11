package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Browser;

public class HomePage {

    private final Logger logger = Logger.getLogger(HomePage.class);

    @FindBy(className = "navigation-bar__profile-account")
    private WebElement profileName;

    public HomePage() {
        PageFactory.initElements(Browser.getDriver(), this);
    }

}
