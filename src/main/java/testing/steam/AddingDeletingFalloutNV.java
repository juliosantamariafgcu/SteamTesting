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

public class AddingDeletingFalloutNV extends AuthenticatedSteamTest{
    String url_current;
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(5);
    private static final String WISHLIST_URL = "https://store.steampowered.com/wishlist/profiles/";
    private static final String FALLOUT_NEW_VEGAS_ID = "22380";

    @Test (priority = 5)
    public void addWishlist() throws InterruptedException {
        // Go to Fallout NV and pass the authentication
        searchFalloutNV();
        ageAuthenticationFalloutNV();

        // Add Fallout New Vegas To the Wishlist
        driver.findElement(By.xpath("//*[@id=\"add_to_wishlist_area\"]/a")).click();
        Thread.sleep(500);

        // Go to Wishlist to See Fallout New Vegas in Wishlist
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        driver.findElement(By.id("wishlist_link")).click();
        List<String> gameIds = getWishlistGameIds();
        Assert.assertTrue(gameIds.contains(FALLOUT_NEW_VEGAS_ID));
        Thread.sleep(1000);

        // Back to Home
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);
    }

    @Test (priority = 6)
    public void addToCart() throws InterruptedException {
        // Search for "Fallout New Vegas" and click on the first result
        searchFalloutNV();

        // Add Fallout New Vegas To the Cart
        driver.findElement(By.cssSelector("#btn_add_to_cart_6442 > span")).click();
        Thread.sleep(500);

        // Go to Shopping Cart to See Fallout New Vegas in Cart
        driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[3]/div/div[3]/button[2]")).click();
        Thread.sleep(1000);

        // Back to Home
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);
    }

    @Test (priority = 7)
    public void followGame() throws InterruptedException {
        // Search for "Fallout New Vegas" and click on the first result
        searchFalloutNV();

        // Follow Fallout New Vegas
        driver.findElement(By.cssSelector("#queueBtnFollow > div.btnv6_blue_hoverfade.btn_medium.queue_btn_inactive > span")).click();
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

        // Remove Fallout New Vegas From Wishlist
        driver.findElement(By.xpath("//*[@id=\"wishlist_row_22380\"]/div[2]/div[2]/div[2]/div[2]/div")).click();
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

        // Remove Fallout New Vegas From Cart
        driver.findElement(By.xpath("//*[@id=\"page_root\"]/div[2]/div/div[2]/div[3]/div[1]/div[1]/div/div/div/div[2]/div[4]/div[2]/div[2]")).click();
        Thread.sleep(500);

        // Assert if the # of elements changed--> If it did, then Fallout New Vegas was removed
        List<WebElement> linkcurrent = driver.findElements(By.tagName("a"));
        Assert.assertEquals(linkexpected.size(), linkcurrent.size());


        // Back to Store Page
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
    }

    @Test(priority = 10)
    public void unfollowGame() throws InterruptedException {
        // Search for "Fallout New Vegas" and click on the first result
        searchFalloutNV();

        // Unfollow Fallout New Vegas
        driver.findElement(By.xpath("//*[@id=\"queueBtnFollow\"]/div[2]/span")).click();
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

    // Searches for FalloutNV
    private void searchFalloutNV() throws InterruptedException {
        // Search for "Fallout New Vegas" and click on the first result
        driver.findElement(By.id("store_nav_search_term")).sendKeys("Fallout New Vegas");
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
    }

    // Passes the Age Authentication
    private void ageAuthenticationFalloutNV() throws InterruptedException {

        // Select the Birth date April 2, 2006
        driver.findElement(By.id("ageDay")).click();
        driver.findElement(By.xpath("//*[@id=\"ageDay\"]/option[2]")).click();
        Thread.sleep(500);
        boolean daySelected = driver.findElement(By.xpath("//*[@id=\"ageDay\"]/option[2]")).isSelected();

        driver.findElement(By.id("ageMonth")).click();
        driver.findElement(By.xpath("//*[@id=\"ageMonth\"]/option[4]")).click();
        Thread.sleep(500);
        boolean monthSelected = driver.findElement(By.xpath("//*[@id=\"ageMonth\"]/option[4]")).isSelected();


        driver.findElement(By.id("ageYear")).click();
        driver.findElement(By.xpath("//*[@id=\"ageYear\"]/option[107]")).click();
        Thread.sleep(500);
        boolean yearSelected = driver.findElement(By.xpath("//*[@id=\"ageYear\"]/option[107]")).isSelected();

        // Assert if the proper date is selected
        Assert.assertTrue(daySelected);
        Assert.assertTrue(monthSelected);
        Assert.assertTrue(yearSelected);

        // Click View Page
        driver.findElement(By.xpath("//*[@id=\"view_product_page_btn\"]")).click();
        Thread.sleep(500);
    }
}
