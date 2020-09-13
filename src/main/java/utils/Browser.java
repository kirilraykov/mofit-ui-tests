package utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.apache.commons.lang.StringUtils.substringBetween;

public class Browser {

    private static final Logger logger = Logger.getLogger(Browser.class);
    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    public static void initializeDriver() {
        final String browser = System.getProperty("browser", "chrome");
        if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", "src//main//resources//geckodriver");
            driver = new FirefoxDriver();
        } else {
            System.setProperty("webdriver.chrome.driver", "src//main//resources//chromedriver");
            driver = new ChromeDriver();
        }
    }

    public static void closeBrowser() {
        if (driver != null) {
            driver.close();
            driver = null;
        }
    }

    public static void writeText(final WebElement element, final String text) {
        explicitWaitForElement(element, 5);
        element.clear();
        element.sendKeys(text);
    }

    public static void clickElement(final WebElement element) {
        explicitWaitForElement(element, 5);
        element.click();
    }

    public static void navigateToUrl(final String url) {
        driver.get(url);
    }

    public static String getElementText(final WebElement element) {
        explicitWaitForElement(element, 3);
        return element.getText();
    }

    public static String getElementHref(final WebElement element) {
        explicitWaitForElement(element, 3);
        return element.getAttribute("href");
    }

    public static boolean isElementEnabled(final WebElement element) {
        return element.isEnabled();
    }

    public static String getUploadedImageHashCode(final WebElement element) {
        final String fullUrl = element.getCssValue("background-image");
        return substringBetween(fullUrl, "small/", ".jpeg");
    }

    public static void threadSleep(final int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void explicitWaitForElement(final WebElement element, final int seconds) {
        final WebDriverWait wait = new WebDriverWait(driver, seconds, 20);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void injectJavaScript(final String js) {
        ((JavascriptExecutor) driver).executeScript(js);
    }

    public static boolean isElementPresent(WebElement element) {
        try {
            element.isDisplayed();
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
}
