package tests;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.Browser;

public class HomeTests {

    private final Logger logger = Logger.getLogger(HomeTests.class);

    private LoginPage loginPage;
    private HomePage homePage;

    @Before
    public void setup() {
        loginPage = new LoginPage();
        homePage = new HomePage();

        loginPage.visit();
        loginPage.login("kiril.raykov@scalefocus.com", "insecure");
    }

    @Test
    //posts random string and asserts it's present in toots section
    public void createTootValid() {

    }

    @After
    public void tearDown() {
        Browser.closeBrowser();
    }
}
