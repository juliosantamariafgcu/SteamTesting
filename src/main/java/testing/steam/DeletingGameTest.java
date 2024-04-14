package testing.steam;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;

public class DeletingGameTest extends AuthenticatedSteamTest{
    String url_current;

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
        System.out.println("\n\n");

        // Remove Portal From Wishlist
        driver.findElement(By.className("delete")).click();
        driver.findElement(By.cssSelector("body > div.newmodal > div.newmodal_content_border" +
                " > div > div.newmodal_buttons > div.btn_green_steamui.btn_medium > span")).click();
        Thread.sleep(500);

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
        System.out.println("On Wishlist Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n\n");

        // Remove Portal From Wishlist
        driver.findElement(By.cssSelector("#page_root > div:nth-child(4) > div > div._22xtsolKcQit92o-LBeRWD >" +
                " div._1jqUY_WcPgZnIOE-d9x7wc.Panel.Focusable > div._17GFdSD2pc0BquZk5cejg8.Panel.Focusable > div._3SgHVt1Zp2MeobFUVwwJ2q " +
                "> div > div > div > div.jRg6Oo9hDgKCGaXi9NJvU > div:nth-child(4) " +
                "> div._2CK0flAYrzVYpiphBImAD2._3fTNy8bloFM_PmW3CPbJF9 > div._33j4SwfO2YH9eI6qV9BKaL.Panel.Focusable")).click();
        Thread.sleep(500);

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

        // Unfollow Portal
        driver.findElement(By.xpath("//*[@id=\"queueBtnFollow\"]/div[2]/span")).click();
        Thread.sleep(500);
    }
}