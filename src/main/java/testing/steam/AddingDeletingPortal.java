package testing.steam;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class AddingDeletingPortal extends AuthenticatedSteamTest{
    String url_current;
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(5);
    private static final String WISHLIST_URL = "https://store.steampowered.com/wishlist/profiles/";
    private static final String PORTAL_1_ID = "400";

    @Test (priority = 5)
    public void addWishlist() throws InterruptedException {
        // Search for "Portal" and click on the first result
        driver.findElement(By.id("store_nav_search_term")).sendKeys("Portal");
        Thread.sleep(500);
        driver.findElement(By.cssSelector("#store_search_link > img")).click();
        Thread.sleep(500);
        driver.findElement(By.cssSelector("#search_resultsRows > a:nth-child(1) > div.responsive_search_name_combined")).click();
        Thread.sleep(500);

        // Add Portal To the Wishlist
        driver.findElement(By.xpath("//*[@id=\"add_to_wishlist_area\"]/a")).click();
        Thread.sleep(500);

        // Go to Wishlist to See Portal in Wishlist
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        driver.findElement(By.id("wishlist_link")).click();
        List<String> gameIds = getWishlistGameIds();
        Assert.assertTrue(gameIds.contains(PORTAL_1_ID));
        Thread.sleep(1000);

        // Back to Home
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);
    }

    @Test (priority = 6)
    public void addToCart() throws InterruptedException {
        // Search for "Portal" and click on the first result
        driver.findElement(By.id("store_nav_search_term")).sendKeys("Portal");
        Thread.sleep(500);
        driver.findElement(By.cssSelector("#store_search_link > img")).click();
        Thread.sleep(500);
        driver.findElement(By.cssSelector("#search_resultsRows > a:nth-child(1) > div.responsive_search_name_combined")).click();
        Thread.sleep(500);

        // Add Portal To the Cart
        driver.findElement(By.xpath("//*[@id=\"btn_add_to_cart_515\"]")).click();
        Thread.sleep(500);

        // Go to Shopping Cart to See Portal in Cart
        driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[3]/div/div[3]/button[2]")).click();
        Thread.sleep(1000);

        // Back to Home
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);
    }

    @Test (priority = 7)
    public void followGame() throws InterruptedException {
        // Search for "Portal" and click on the first result
        driver.findElement(By.id("store_nav_search_term")).sendKeys("Portal");
        Thread.sleep(500);
        driver.findElement(By.cssSelector("#store_search_link > img")).click();
        Thread.sleep(500);
        driver.findElement(By.cssSelector("#search_resultsRows > a:nth-child(1) > div.responsive_search_name_combined")).click();
        Thread.sleep(500);
        url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertNotEquals(url_current, "https://store.steampowered.com", "URL Did Not Change!\n\n");
        System.out.println("On Portal Game Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n");

        // Follow Portal
        driver.findElement(By.xpath("//*[@id=\"queueBtnFollow\"]")).click();
        Thread.sleep(500);
    }

    @Test(priority = 8)
    public void deleteWishlist() throws InterruptedException {
        // Go to Wishlist and Assert if the URL Changed
        driver.findElement(By.id("wishlist_link")).click();
        url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertNotEquals(url_current, "https://store.steampowered.com", "URL Did Not Change!\n\n");
        System.out.println("On Wishlist Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n");

        // Remove Portal From Wishlist
        driver.findElement(By.xpath("//*[@id=\"wishlist_row_400\"]/div[2]/div[2]/div[2]/div[2]/div")).click();
        driver.findElement(By.cssSelector("body > div.newmodal > div.newmodal_content_border" +
                " > div > div.newmodal_buttons > div.btn_green_steamui.btn_medium > span")).click();
        Thread.sleep(500);

        // Assert if the wishlist is empty
        Assert.assertTrue(getWishlistGameIds().isEmpty());

        // Back to Store Page
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
    }

    @Test(priority = 9)
    public void deleteFromCart() throws InterruptedException {
        // Go to Cart and Assert if the URL Changed
        driver.findElement(By.xpath("//*[@id=\"cart_status_data\"]/div[2]")).click();
        url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertNotEquals(url_current, "https://store.steampowered.com", "URL Did Not Change!\n\n");
        System.out.println("On Shopping Cart Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n");

        // Retrieve the # of total elements in the cart
        List<WebElement> linkexpected = driver.findElements(By.tagName("a"));

        // Remove Portal From Cart
        driver.findElement(By.xpath("//*[@id=\"page_root\"]/div[2]/div/div[2]/div[3]/div[1]/div[1]/div/div/div/div[2]/div[4]/div[2]/div[2]")).click();
        Thread.sleep(500);

        // Assert if the # of elements changed--> If it did, then Portal was removed
        List<WebElement> linkcurrent = driver.findElements(By.tagName("a"));
        Assert.assertEquals(linkexpected.size(), linkcurrent.size());


        // Back to Store Page
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
    }

    @Test(priority = 10)
    public void unfollowGame() throws InterruptedException {
        // Search for "Portal" and click on the first result
        driver.findElement(By.id("store_nav_search_term")).sendKeys("Portal");
        Thread.sleep(500);
        driver.findElement(By.cssSelector("#store_search_link > img")).click();
        Thread.sleep(500);
        driver.findElement(By.cssSelector("#search_resultsRows > a:nth-child(1) > div.responsive_search_name_combined")).click();
        Thread.sleep(500);
        url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertNotEquals(url_current, "https://store.steampowered.com", "URL Did Not Change!\n\n");
        System.out.println("On Game Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n");

        // Unfollow Portal
        driver.findElement(By.cssSelector("#queueBtnFollow > div.btnv6_blue_hoverfade.btn_medium.queue_btn_active > span")).click();
        Thread.sleep(500);
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
}
