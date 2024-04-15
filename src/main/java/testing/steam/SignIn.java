package testing.steam;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import testing.steam.WebDriverTest;

public class SignIn extends WebDriverTest {
    private final String ACCOUNT_NAME = "STEAM_ACCOUNT_NAME";
    private final String PASSWORD = "STEAM_PASSWORD";
    private final String SIGN_IN_URL = "https://store.steampowered.com/login/";
    private final Duration SIGN_IN_TIMEOUT = Duration.ofSeconds(5);
    private final Duration SIGN_OUT_TIMEOUT = Duration.ofSeconds(20);

    @Test(groups = {"signedOut"})
    public void navigateToSignInPage() {
        driver.get(SIGN_IN_URL);
        Assert.assertEquals(driver.getCurrentUrl(), SIGN_IN_URL);
        Assert.assertEquals(driver.getTitle(), "Sign In");

        waitForSignInPageToLoad();

        // There should be a help link.
        By helpLinkLocator = By.linkText("Help, I can't sign in");
        Assert.assertEquals(driver.findElements(helpLinkLocator).size(), 1);
    }

    @Test(groups = {"signedOut"})
    public void navigateToSignInPageFromHomePage() throws URISyntaxException {
        driver.get("https://store.steampowered.com/");
        WebElement globalActionMenu = driver.findElement(By.id("global_action_menu"));
        WebElement signInLink = globalActionMenu.findElement(By.linkText("login"));
        signInLink.click();
        // The sign-in link on the home page contains a few query parameters.
        // We're only interested in comparing the scheme, host, and path.
        URI currentUrl = new URI(driver.getCurrentUrl());
        URI expected = new URI(SIGN_IN_URL);
        Assert.assertEquals(currentUrl.getScheme(), expected.getScheme());
        Assert.assertEquals(currentUrl.getHost(), expected.getHost());
        Assert.assertEquals(currentUrl.getPath(), expected.getPath());
        Assert.assertEquals(driver.getTitle(), "Sign In");
    }

    @Test(groups = {"signedOut"})
    public void redirectToSignInPage() throws URISyntaxException, InterruptedException {
        driver.get("https://store.steampowered.com/");
        WebElement byFriendsLink = driver.findElement(By.linkText("By Friends"));
        byFriendsLink.click();
        // The sign-in link on the home page contains a few query parameters.
        // We're only interested in comparing the scheme, host, and path.
        URI currentUrl = new URI(driver.getCurrentUrl());
        URI expected = new URI(SIGN_IN_URL);
        Assert.assertEquals(currentUrl.getScheme(), expected.getScheme());
        Assert.assertEquals(currentUrl.getHost(), expected.getHost());
        Assert.assertEquals(currentUrl.getPath(), expected.getPath());
        Assert.assertEquals(driver.getTitle(), "Sign In");
    }

    @Test(groups = {"signedOut"})
    public void leaveAccountNameAndPasswordBlank() {
        driver.get(SIGN_IN_URL);
        findSignInButton().click();
        // We should remain on the sign in page.
        Assert.assertEquals(driver.getCurrentUrl(), SIGN_IN_URL);
        Assert.assertEquals(driver.getTitle(), "Sign In");
    }

    @Test(groups = {"signedOut"})
    public void leaveAccountNameBlank() {
        driver.get(SIGN_IN_URL);
        findPasswordTextBox().sendKeys("password");
        findSignInButton().click();
        // We should remain on the sign in page.
        Assert.assertEquals(driver.getCurrentUrl(), SIGN_IN_URL);
        Assert.assertEquals(driver.getTitle(), "Sign In");
    }

    @Test(groups = {"signedOut"})
    public void leavePasswordBlank() {
        driver.get(SIGN_IN_URL);
        findAccountNameTextBox().sendKeys("accountname");
        findSignInButton().click();
        // We should remain on the sign in page.
        Assert.assertEquals(driver.getCurrentUrl(), SIGN_IN_URL);
        Assert.assertEquals(driver.getTitle(), "Sign In");
    }

    @Test(groups = {"signedOut"})
    public void enterIncorrectCredentials() throws InterruptedException {
        driver.get(SIGN_IN_URL);
        findAccountNameTextBox().sendKeys("accountname");
        findPasswordTextBox().sendKeys("password");
        findSignInButton().click();
        // An error message should appear.
        Wait<WebDriver> wait = new WebDriverWait(driver, SIGN_IN_TIMEOUT);
        wait.until(ExpectedConditions.textToBe(
            By.xpath("//div[@class='page_content']//form/div[5]"),
            "Please check your password and account name and try again."
        ));
        // We should remain on the sign in page.
        Assert.assertEquals(driver.getCurrentUrl(), SIGN_IN_URL);
        Assert.assertEquals(driver.getTitle(), "Sign In");
    }

    @Test(groups = {"signedIn"}, dependsOnGroups = {"signedOut"})
    public void enterCorrectCredentials() throws InterruptedException {
        driver.get(SIGN_IN_URL);

        String accountName = System.getenv(ACCOUNT_NAME);
        String password = System.getenv(PASSWORD);

        findAccountNameTextBox().sendKeys(accountName);
        Thread.sleep(500);
        findPasswordTextBox().sendKeys(password);
        Thread.sleep(500);
        findSignInButton().click();

        // Wait until the sign-in process is complete.
        Wait<WebDriver> wait = new WebDriverWait(driver, SIGN_IN_TIMEOUT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("store_header")));
        Assert.assertEquals(driver.getCurrentUrl(), "https://store.steampowered.com/");
    }

    @Test(dependsOnGroups = {"signedIn"})
    public void signOut() {
        driver.get("https://store.steampowered.com/");

        WebElement pulldown = driver.findElement(By.id("account_pulldown"));
        pulldown.click();

        WebElement dropdown = driver.findElement(By.id("account_dropdown"));
        WebElement signOutLink = dropdown.findElement(By.linkText("Sign out of account..."));
        signOutLink.click();

        // We should navigate to a logging out page.
        Wait<WebDriver> wait = new WebDriverWait(driver, SIGN_OUT_TIMEOUT);
        By locator = By.className("login_client_waitforauth_desc");
        WebElement description = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        Assert.assertEquals(description.getText(), "Logging you out...");

        // Then, we should navigate back to the home page.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("store_header")));
        Assert.assertEquals(driver.getCurrentUrl(), "https://store.steampowered.com/");
    }

    @Test(groups = {"signedOut"})
    public void clickRememberMeCheckBox() {
        driver.get(SIGN_IN_URL);
        Wait<WebDriver> wait = new WebDriverWait(driver, SIGN_IN_TIMEOUT);
        By checkBoxLocator = By.xpath("//div[@class='page_content']//form/div[@class='Focusable']/div");
        WebElement checkBox = wait.until(ExpectedConditions.elementToBeClickable(checkBoxLocator));

        // The check box should be initially checked, which is indicated by the
        // presence of an SVG check icon under the check box element.
        Assert.assertEquals(checkBox.findElements(By.tagName("svg")).size(), 1);
        checkBox.click();
        Assert.assertEquals(checkBox.findElements(By.tagName("svg")).size(), 0);
    }

    private WebElement findAccountNameTextBox() {
        Wait<WebDriver> wait = new WebDriverWait(driver, SIGN_IN_TIMEOUT);
        By locator = By.cssSelector("div.page_content form input[type='text']");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement findPasswordTextBox() {
        Wait<WebDriver> wait = new WebDriverWait(driver, SIGN_IN_TIMEOUT);
        By locator = By.cssSelector("div.page_content form input[type='password']");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement findSignInButton() {
        Wait<WebDriver> wait = new WebDriverWait(driver, SIGN_IN_TIMEOUT);
        By locator = By.cssSelector("div.page_content form button[type='submit']");
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void waitForSignInPageToLoad() {
        // Wait for the sign-in button to load.
        findSignInButton();
    }
};
