package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.HomePage;

import java.io.IOException;

import static org.testng.Assert.fail;
import static utils.Browser.closeBrowser;
import static utils.Browser.getDriver;
import static utils.Browser.initializeDriver;
import static utils.SaveScreenshots.getScreenshot;

public class ChangeAvatarTests {

    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest test;
    private HomePage homePage;

    @Test
    public void setValidAvatar() {
        test = extent.createTest("setValidAvatar");
    }

    @Test
    public void setAvatarInvalidImage() {
        test = extent.createTest("setAvatarInvalidImage");
    }

    @Test
    public void setAvatarSmallImage() {
        test = extent.createTest("setAvatarSmallImage");
    }

    @Test
    public void setAvatarBigImage() {
        test = extent.createTest("setAvatarBigImage");
        fail();
    }

    @Test
    public void updateAvatarValidImage() {
        test = extent.createTest("updateAvatarValidImage");
    }

    @Test
    public void updateAvatarInvalidImage() {
        test = extent.createTest("updateAvatarValidImage");
    }

    @Test
    public void deleteUserAvatar() {
        test = extent.createTest("deleteUserAvatar");
    }

    @Test
    public void getUserAvatar() {
        test = extent.createTest("getUserAvatar");
    }

    @Test
    public void getNonExistentUserAvatar() {
        test = extent.createTest("getNonExistentUserAvatar");
        fail();
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
}
