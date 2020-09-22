package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.lang.StringUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.SignupPage;

import java.io.IOException;

import static org.testng.AssertJUnit.assertTrue;
import static utils.Browser.closeBrowser;
import static utils.Browser.getDriver;
import static utils.Browser.initializeDriver;
import static utils.GenerateTestData.generateRandomEmail;
import static utils.MofitConstants.INVALID_SIGNUP_EMAIL_ERROR;
import static utils.MofitConstants.INVALID_SIGNUP_PASSWORD_ERROR;
import static utils.MofitConstants.TEST_PASSWORD;
import static utils.SaveScreenshots.getScreenshot;

public class SignupTests {

    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest test;
    private HomePage homePage;

    @Test
    public void signupValidUser() {
        test = extent.createTest("signupValidUser");

        homePage.visit();
        LoginPage loginPage = homePage.clickLoginButton();
        SignupPage signupPage = loginPage.clickSignupButton();

        signupPage.enterEmailAndPassword(generateRandomEmail(), TEST_PASSWORD);
        signupPage.signup();
    }

    @Test
    public void signupUserWithInvalidEmail() {
        test = extent.createTest("signupUserWithInvalidEmail");

        homePage.visit();
        LoginPage loginPage = homePage.clickLoginButton();
        SignupPage signupPage = loginPage.clickSignupButton();

        signupPage.enterEmailAndPassword("InvalidEmailFormat", TEST_PASSWORD);
        signupPage.signup();

        verifyErrorMessage(INVALID_SIGNUP_EMAIL_ERROR, signupPage.getErrorMessage());
    }

    @Test
    public void signupUserWithInvalidPassword() {
        test = extent.createTest("signupUserWithInvalidPassword");

        homePage.visit();
        LoginPage loginPage = homePage.clickLoginButton();
        SignupPage signupPage = loginPage.clickSignupButton();

        signupPage.enterEmailAndPassword(generateRandomEmail(), "12");
        signupPage.signup();

        verifyErrorMessage(INVALID_SIGNUP_PASSWORD_ERROR, signupPage.getErrorMessage());
    }

    @Test
    public void signupUserWithEmptyEmail() {
        test = extent.createTest("signupUserWithEmptyEmail");

        homePage.visit();
        LoginPage loginPage = homePage.clickLoginButton();
        SignupPage signupPage = loginPage.clickSignupButton();

        signupPage.enterEmailAndPassword(StringUtils.EMPTY, TEST_PASSWORD);
        signupPage.signup();

        verifyErrorMessage(INVALID_SIGNUP_EMAIL_ERROR, signupPage.getErrorMessage());
    }

    @Test
    public void signupSameUserTwice() {
        test = extent.createTest("signupSameUserTwice");
    }

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

        //Passing General information
        extent.setSystemInfo("Environemnt", "Production");
        extent.setSystemInfo("user", "kirilraykov");
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
            String screenshotPath = getScreenshot(getDriver(), result.getName());
            test.addScreenCaptureFromPath(screenshotPath);// adding screen shot
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test Case SKIPPED IS " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test Case PASSED IS " + result.getName());
        }
        closeBrowser();
    }

    private void verifyErrorMessage(final String expected, final String actual) {
        assertTrue("Expected error message is wrong",
                   actual.contains(expected));
    }
}
