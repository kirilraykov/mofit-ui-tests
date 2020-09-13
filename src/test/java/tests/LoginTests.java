package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.testng.Assert.assertNotEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
import static utils.Browser.closeBrowser;
import static utils.Browser.getDriver;
import static utils.Browser.initializeDriver;
import static utils.Browser.threadSleep;
import static utils.MofitConstants.AUTOMATION_USER;
import static utils.MofitConstants.EMPTY_LOGIN_FIELDS_ERROR;
import static utils.MofitConstants.NON_EXISTENT_EMAIL;
import static utils.MofitConstants.NON_EXISTENT_EMAIL_ERROR;
import static utils.MofitConstants.TEST_PASSWORD;

public class LoginTests {

    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest test;
    private HomePage homePage;

    @BeforeMethod
    public void setupMethod() {
        initializeDriver();
        homePage = new HomePage();
    }

    @BeforeTest
    public void setupTest() {
        // specify location of the report
        htmlReporter =
            new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/myReport.html");

        htmlReporter.config().setDocumentTitle("Automation Report"); // Tile of report
        htmlReporter.config().setReportName("Functional Testing"); // Name of the report
        htmlReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        // Passing General information
//        extent.setSystemInfo("Host name", "localhost");
//        extent.setSystemInfo("Environemnt", "QA");
//        extent.setSystemInfo("user", "pavan");
    }

    /**
     * Verify button for showing password works
     */
    @Test
    public void loginValidUser() {
        test = extent.createTest("loginValidUser");

        homePage.visit();

        assertFalse("User should not be logged initially", homePage.isLoggedIn());

        LoginPage loginPage = homePage.clickLoginButton();
        loginPage.enterEmailAndPassword(AUTOMATION_USER, TEST_PASSWORD);
        homePage = loginPage.login();

        threadSleep(2000);

        assertTrue("User should be logged after signin", homePage.isLoggedIn());

    }

    /**
     * Try to sign in with non-existent email and verify the error message
     */
    @Test
    public void loginNonExistentEmail() {
        test = extent.createTest("loginNonExistentEmail");

        homePage.visit();
        final LoginPage loginPage = homePage.clickLoginButton();

        loginPage.enterEmailAndPassword(NON_EXISTENT_EMAIL, TEST_PASSWORD);
        loginPage.login();

        verifyErrorMessage(NON_EXISTENT_EMAIL_ERROR, loginPage.getErrorMessage());
    }

    /**
     * Try to sign in with empty password and verify the error message
     */
    @Test
    public void loginEmptyPassword() {
        test = extent.createTest("loginEmptyPassword");

        homePage.visit();
        final LoginPage loginPage = homePage.clickLoginButton();

        loginPage.enterEmailAndPassword(NON_EXISTENT_EMAIL, StringUtils.EMPTY);
        loginPage.login();

        verifyErrorMessage(EMPTY_LOGIN_FIELDS_ERROR, loginPage.getErrorMessage());
    }

    /**
     * Try to sign in with empty email and verify the error message
     */
    @Test
    public void loginEmptyEmail() {
        test = extent.createTest("loginEmptyEmail");

        homePage.visit();
        final LoginPage loginPage = homePage.clickLoginButton();

        loginPage.enterEmailAndPassword(StringUtils.EMPTY, TEST_PASSWORD);
        loginPage.login();

        verifyErrorMessage(EMPTY_LOGIN_FIELDS_ERROR, loginPage.getErrorMessage());
    }

    /**
     * Verify button for showing password works
     */
    @Test
    public void loginWithShownPassword() {
        test = extent.createTest("loginWithShownPassword");

        homePage.visit();
        LoginPage loginPage = homePage.clickLoginButton();
        loginPage.enterEmailAndPassword(StringUtils.EMPTY, TEST_PASSWORD);
        String hiddenPassword = loginPage.getPasswordFieldText();
        loginPage.showPassword();
        String shownPassword = loginPage.getPasswordFieldText();
        assertNotEquals("Password texts should be different",
                        hiddenPassword, shownPassword);
    }

    private void verifyErrorMessage(final String expected, final String actual) {
        assertTrue("Expected error message is wrong",
                   actual.contains(expected));
    }


    @AfterTest
    public void teardown() {
        extent.flush();
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL,
                     "TEST CASE FAILED IS " + result.getName()); // to add name in extent report
            test.log(Status.FAIL, "TEST CASE FAILED IS " + result
                .getThrowable()); // to add error/exception in extent report
            String screenshotPath = LoginTests.getScreenshot(getDriver(), result.getName());
            test.addScreenCaptureFromPath(screenshotPath);// adding screen shot
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test Case SKIPPED IS " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test Case PASSED IS " + result.getName());
        }
        closeBrowser();
//        getDriver().close();

    }

    public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        // after execution, you could see a folder "FailedTestsScreenshots" under src folder
        String destination =
            System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }

}
