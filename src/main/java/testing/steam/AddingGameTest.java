package testing.steam;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;

public class AddingGameTest extends AuthenticatedSteamTest{

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
        driver.findElement(By.id("add_to_wishlist_area")).click();
        Thread.sleep(500);

        // Go to Wishlist to See Portal in Wishlist
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        driver.findElement(By.id("wishlist_link")).click();
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

        // Follow Portal
        driver.findElement(By.cssSelector("#queueBtnFollow" +
                " > div.btnv6_blue_hoverfade.btn_medium.queue_btn_inactive > span")).click();
        Thread.sleep(500);
    }
}
