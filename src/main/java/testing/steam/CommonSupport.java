package testing.steam;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class CommonSupport {
    public static final String SUPPORT_URL = "https://help.steampowered.com/en/";
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(5);

    public static void navigateToSupportPage(WebDriver driver) {
        driver.get(SUPPORT_URL);
        Assert.assertEquals(driver.getCurrentUrl(), SUPPORT_URL);
        Assert.assertEquals(driver.getTitle(), "Steam Support");
    }

    public static void navigateToSupportPageFromHomePage(WebDriver driver) {
        driver.get("https://store.steampowered.com/");
        WebElement globalHeader = driver.findElement(By.id("global_header"));
        WebElement supportLink = globalHeader.findElement(By.linkText("SUPPORT"));
        supportLink.click();
        Assert.assertEquals(driver.getCurrentUrl(), SUPPORT_URL);
        Assert.assertEquals(driver.getTitle(), "Steam Support");
    }

    public static void getHelpWithGame(WebDriver driver) {
        driver.get(SUPPORT_URL);
        getLargeHelpButton(driver, 0).click();
        waitForHelpPageToLoad(driver);
        Assert.assertEquals(driver.getCurrentUrl(), "https://help.steampowered.com/en/wizard/HelpWithGame");
        Assert.assertEquals(driver.getTitle(), "Steam Support - Games and Applications");
    }

    public static void getHelpWithPurchase(WebDriver driver) {
        driver.get(SUPPORT_URL);
        getLargeHelpButton(driver, 1).click();
        waitForHelpPageToLoad(driver);
        Assert.assertEquals(driver.getCurrentUrl(), "https://help.steampowered.com/en/wizard/HelpWithPurchase");
        Assert.assertEquals(driver.getTitle(), "Steam Support - Recent Purchases");
    }

    public static void getHelpWithAccount(WebDriver driver) {
        driver.get(SUPPORT_URL);
        getLargeHelpButton(driver, 2).click();
        waitForHelpPageToLoad(driver);
        Assert.assertEquals(driver.getCurrentUrl(), "https://help.steampowered.com/en/wizard/HelpWithAccount");
        Assert.assertEquals(driver.getTitle(), "Steam Support - Account Issues");
    }

    public static List<WebElement> getLargeHelpButtons(WebDriver driver) {
        WebElement helpHomeBlock = driver.findElement(By.id("help_home_block"));
        return helpHomeBlock.findElements(By.className("help_wizard_button_large"));
    }

    public static WebElement getLargeHelpButton(WebDriver driver, int index) {
        return getLargeHelpButtons(driver).get(index);
    }

    // Even though the help buttons are links, Steam adds event listeners to
    // all of the help buttons that override the default behavior of navigating
    // to a new webpage and instead modify the current webpage. This reduces
    // the number of browser reloads, but it means that we must manually wait
    // since Selenium only waits when the browser is "properly" navigating.
    public static void waitForHelpPageToLoad(WebDriver driver) {
        // Breadcrumbs (e.g. "Home > Recent Purchases") are not visible on the
        // main support page; you must navigate to a more specific support page
        // to see them, so we can use them to determine if the page has loaded.
        Wait<WebDriver> wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        By locator = By.cssSelector("#wizard_contents .breadcrumbs");
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
};
