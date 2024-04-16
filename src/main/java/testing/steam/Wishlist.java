package testing.steam;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import testing.steam.IdentifiedSteamTest;

public class Wishlist extends IdentifiedSteamTest {
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(5);
    private static final String MY_WISHLIST_URL = "https://store.steampowered.com/wishlist/";
    private static final String WISHLIST_URL = "https://store.steampowered.com/wishlist/profiles/";
    private static final String PORTAL_1_ID = "400";
    private static final String PORTAL_2_ID = "620";
    private static final String PORTAL_STORIES_MEL_ID = "317400";

    @Test(priority = 1)
    public void navigateToWishlist() throws URISyntaxException {
        driver.get(MY_WISHLIST_URL);
        URI currentUrl = new URI(driver.getCurrentUrl());
        URI expected = new URI(WISHLIST_URL + getSteamId());
        // Don't compare the URL fragment since Steam appends "#sort=order".
        Assert.assertEquals(currentUrl.getScheme(), expected.getScheme());
        Assert.assertEquals(currentUrl.getHost(), expected.getHost());
        Assert.assertEquals(currentUrl.getPath(), expected.getPath());

        // Make sure our display name is visible.
        String displayName = getDisplayName().toLowerCase();
        WebElement header = driver.findElement(By.className("wishlist_header"));
        Assert.assertEquals(header.getText().toLowerCase(), displayName + "'s wishlist");
    }

    @Test(priority = 2)
    public void navigateToWishlistFromHomePage() throws URISyntaxException {
        driver.get("https://store.steampowered.com/");
        WebElement wishlistLink = driver.findElement(By.id("wishlist_link"));
        wishlistLink.click();
        URI currentUrl = new URI(driver.getCurrentUrl());
        URI expected = new URI(WISHLIST_URL + getSteamId());
        // Don't compare the URL fragment since Steam appends "#sort=order".
        Assert.assertEquals(currentUrl.getScheme(), expected.getScheme());
        Assert.assertEquals(currentUrl.getHost(), expected.getHost());
        Assert.assertEquals(currentUrl.getPath(), expected.getPath());
    }

    @Test(priority = 3)
    public void addPortal1ToWishlist() {
        addGameToWishlist(PORTAL_1_ID);
    }

    @Test(priority = 4)
    public void addPortal2ToWishlist() {
        addGameToWishlist(PORTAL_2_ID);
    }

    @Test(priority = 5)
    public void addPortalStoriesMelToWishlist() {
        addGameToWishlist(PORTAL_STORIES_MEL_ID);
    }

    @Test(priority = 6)
    public void lookAtWishlist() {
        driver.get(MY_WISHLIST_URL);
        List<String> gameIds = getWishlistGameIds();
        Assert.assertTrue(gameIds.contains(PORTAL_1_ID));
        Assert.assertTrue(gameIds.contains(PORTAL_2_ID));
        Assert.assertTrue(gameIds.contains(PORTAL_STORIES_MEL_ID));
        sleep(1_000);

        System.out.println("\nlookAtWishlist():");
        for (int i = 0; i < gameIds.size(); i++) {
            System.out.format("  %d. Game ID: %s\n", i + 1, gameIds.get(i));
        }
    }

    @Test(priority = 7)
    public void removePortalStoriesMelFromWishlist() {
        removeGameFromWishlist(PORTAL_STORIES_MEL_ID);
    }

    @Test(priority = 8)
    public void clearWishlist() {
        driver.get(MY_WISHLIST_URL);
        List<String> gameIds = getWishlistGameIds();
        Assert.assertTrue(gameIds.contains(PORTAL_1_ID));
        Assert.assertTrue(gameIds.contains(PORTAL_2_ID));
        Assert.assertFalse(gameIds.contains(PORTAL_STORIES_MEL_ID));

        System.out.println("\nclearWishlist():");
        for (int i = 0; i < gameIds.size(); i++) {
            System.out.format("  %d. Game ID: %s\n", i + 1, gameIds.get(i));
            removeGameOnWishlist(gameIds.get(i));
            sleep(1_000);
        }

        Assert.assertTrue(getWishlistGameIds().isEmpty());
    }

    // Navigates to the store page for the given game.
    private void addGameToWishlist(String id) {
        driver.get("https://store.steampowered.com/app/" + id);
        WebElement addToWishlistButton = driver.findElement(By.id("add_to_wishlist_area"));
        if (addToWishlistButton.isDisplayed()) {
            scrollIntoView(addToWishlistButton);
            addToWishlistButton.click();

            Wait<WebDriver> wait = new WebDriverWait(driver, WAIT_TIMEOUT);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add_to_wishlist_area_success")));
        }
    }

    // Navigates to the store page for the given game.
    private void removeGameFromWishlist(String id) {
        driver.get("https://store.steampowered.com/app/" + id);
        WebElement removeFromWishlistButton = driver.findElement(By.id("add_to_wishlist_area_success"));
        if (removeFromWishlistButton.isDisplayed()) {
            scrollIntoView(removeFromWishlistButton);
            removeFromWishlistButton.click();

            Wait<WebDriver> wait = new WebDriverWait(driver, WAIT_TIMEOUT);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add_to_wishlist_area")));
        }
    }

    // Asserts that the web driver has already navigated to the wishlist.
    private void removeGameOnWishlist(String id) {
        Assert.assertTrue(driver.getCurrentUrl().contains(WISHLIST_URL));
        waitForWishlistToLoad();

        // Click on the remove button in parentheses.
        WebElement row = driver.findElement(By.cssSelector("#wishlist_ctn > #wishlist_row_" + id));
        WebElement removeButton = row.findElement(By.className("delete"));
        removeButton.click();

        // Click OK in the modal dialog.
        Wait<WebDriver> wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        WebElement okButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("body > .newmodal .newmodal_buttons > .btn_green_steamui")
        ));
        okButton.click();
    }

    // Asserts that the web driver has already navigated to the wishlist.
    private List<String> getWishlistGameIds() {
        Assert.assertTrue(driver.getCurrentUrl().contains(WISHLIST_URL));
        waitForWishlistToLoad();

        var rows = driver.findElements(By.cssSelector("#wishlist_ctn > .wishlist_row"));
        ArrayList<String> gameIds = new ArrayList<String>(rows.size());
        for (WebElement row : rows) {
            gameIds.add(row.getAttribute("data-app-id"));
        }
        return gameIds;
    }

    // Asserts that the web driver has already navigated to the wishlist.
    private void waitForWishlistToLoad() {
        Assert.assertTrue(driver.getCurrentUrl().contains(WISHLIST_URL));
        Wait<WebDriver> wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("throbber")));
    }

    private void scrollIntoView(WebElement element) {
        // HACK: Execute some JavaScript code that calls `scrollIntoView`.
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("""
            arguments[0].scrollIntoView({
                behavior: 'instant',
                block: 'center',
            });
            """,
            element
        );
        sleep(1_000);
    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException exception) {
            // Ignore interrupted exceptions.
        }
    }
};
