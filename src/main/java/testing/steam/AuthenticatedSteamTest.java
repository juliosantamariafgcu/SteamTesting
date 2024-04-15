package testing.steam;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class AuthenticatedSteamTest extends WebDriverTest {
    private static final String ACCOUNT_NAME = "STEAM_ACCOUNT_NAME";
    private static final String PASSWORD = "STEAM_PASSWORD";
    private static final Duration SIGN_IN_TIMEOUT = Duration.ofSeconds(5);

    @BeforeClass
    public void signIn() throws InterruptedException {
        driver.get("https://store.steampowered.com/login/");

        String accountName = getAccountName();
        String password = getPassword();

        Wait<WebDriver> wait = new WebDriverWait(driver, SIGN_IN_TIMEOUT);
        By accountNameTextBoxLocator = By.cssSelector("div.page_content form input[type='text']");
        By passwordTextBoxLocator = By.cssSelector("div.page_content form input[type='password']");
        By signInButtonLocator = By.cssSelector("div.page_content form button[type='submit']");
        wait.until(
            ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(accountNameTextBoxLocator),
                ExpectedConditions.visibilityOfElementLocated(passwordTextBoxLocator),
                ExpectedConditions.elementToBeClickable(signInButtonLocator)
            )
        );

        // NOTE(Daniel): Small delays make the sign-in process succeed more consistently.
        WebElement accountNameTextBox = driver.findElement(accountNameTextBoxLocator);
        WebElement passwordTextBox = driver.findElement(passwordTextBoxLocator);
        WebElement signInButton = driver.findElement(signInButtonLocator);
        accountNameTextBox.sendKeys(accountName);
        Thread.sleep(500);
        passwordTextBox.sendKeys(password);
        Thread.sleep(500);
        signInButton.click();

        // Wait until the sign-in process is complete.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("store_header")));
        Assert.assertEquals(driver.getCurrentUrl(), "https://store.steampowered.com/");
    }

    @AfterClass
    public void signOut() {
        driver.get("https://store.steampowered.com/");

        WebElement pulldown = driver.findElement(By.id("account_pulldown"));
        pulldown.click();

        WebElement dropdown = driver.findElement(By.id("account_dropdown"));
        WebElement signOutLink = dropdown.findElement(By.xpath(".//a[@class='popup_menu_item'][last()]"));
        signOutLink.click();
    }

    protected String getAccountName() {
        return System.getenv(ACCOUNT_NAME);
    }

    protected String getPassword() {
        return System.getenv(PASSWORD);
    }
};
