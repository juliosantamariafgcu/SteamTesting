package testing.steam;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;

public class StoreTabSteamTest extends AuthenticatedSteamTest{

    @BeforeClass
    public void assertURL() {
        String url_expected = "https://store.steampowered.com";
        String url_current = driver.getCurrentUrl();
        Assert.assertFalse(url_current.equals(url_expected),"URLs Match!\n\n");
        System.out.println("Urls Do Not Match!\n\n");
    }

    @Test (priority = 1)
    public void storeTabLinks() throws InterruptedException {

        // Side or "Gutter" Links
        driver.findElement(By.linkText("Tags")).click();
        String url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertNotEquals(url_current, "https://store.steampowered.com", "URL Did Not Change!\n\n");
        System.out.println("On Tags Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n\n");
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);

        driver.findElement(By.linkText("Upcoming")).click();
        url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertNotEquals(url_current, "https://store.steampowered.com", "URL Did Not Change!\n\n");
        System.out.println("On Upcoming Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n\n");
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);

        driver.findElement(By.linkText("Steam Deck Dock")).click();
        url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertNotEquals(url_current, "https://store.steampowered.com", "URL Did Not Change!\n\n");
        System.out.println("On Steam Deck Dock Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n\n");
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);

        driver.findElement(By.linkText("Free to Play")).click();
        url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertNotEquals(url_current, "https://store.steampowered.com", "URL Did Not Change!\n\n");
        System.out.println("On Free to Play Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n\n");
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);

        // Points Shop Link
        driver.findElement(By.linkText("Points Shop")).click();
        url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertNotEquals(url_current, "https://store.steampowered.com", "URL Did Not Change!\n\n");
        System.out.println("On Points Shop Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n\n");
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);

        // News Link
        driver.findElement(By.linkText("News")).click();
        url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertNotEquals(url_current, "https://store.steampowered.com", "URL Did Not Change!\n\n");
        System.out.println("On News Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n\n");
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);

        // Wishlist Link
        driver.findElement(By.linkText("Wishlist")).click();
        url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertNotEquals(url_current, "https://store.steampowered.com", "URL Did Not Change!\n\n");
        System.out.println("On Wishlist Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n\n");
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);

        // Support Link
        driver.findElement(By.linkText("SUPPORT")).click();
        url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertNotEquals(url_current, "https://store.steampowered.com", "URL Did Not Change!\n\n");
        System.out.println("On Support Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n\n");
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);

        // Racing Category Link
        driver.findElement(By.linkText("Racing")).click();
        url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertNotEquals(url_current, "https://store.steampowered.com", "URL Did Not Change!\n\n");
        System.out.println("On Racing Category Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n\n");
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);

        // Indie Category Link
        driver.findElement(By.linkText("Indie")).click();
        url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertNotEquals(url_current, "https://store.steampowered.com", "URL Did Not Change!\n\n");
        System.out.println("On Indie Category Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n\n");
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);
    }

    @Test (priority = 2)
    public void flyoutTest() throws InterruptedException{
        String url_current;

        // Your Store Flyout (Clicking on DLC For You)
        driver.findElement(By.xpath("//*[@id=\"foryou_tab\"]/span")).click();
        driver.findElement(By.cssSelector("#foryou_flyout > div > a:nth-child(11)")).click();
        url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertNotEquals(url_current, "https://store.steampowered.com", "URL Did Not Change!\n\n");
        System.out.println("On DLC Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n\n");
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);

        // New & Noteworthy Flyout (Clicking on Top Sellers)
        driver.findElement(By.xpath("//*[@id=\"noteworthy_tab\"]/span/a[1]")).click();
        driver.findElement(By.cssSelector("#noteworthy_flyout > div > div:nth-child(1) > a:nth-child(2)")).click();
        url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertNotEquals(url_current, "https://store.steampowered.com", "URL Did Not Change!\n\n");
        System.out.println("On Top Sellers Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n\n");
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);

        // Categories Flyout (Clicking on Card & Board)
        driver.findElement(By.xpath("//*[@id=\"genre_tab\"]/span/a[1]")).click();
        driver.findElement(By.cssSelector("#genre_flyout > div > div:nth-child(4) > div:nth-child(4) > a:nth-child(1)")).click();
        url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertNotEquals(url_current, "https://store.steampowered.com", "URL Did Not Change!\n\n");
        System.out.println("On Card & Board Games Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n\n");
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);
    }

    @Test (priority = 3)
    public void homeClusterTesting() throws InterruptedException {
        // Click left arrow on the HomeCluster twice
        driver.findElement(By.cssSelector("#home_maincap_v7 > div.arrow.left")).click();
        driver.findElement(By.cssSelector("#home_maincap_v7 > div.arrow.left")).click();

        // Click right arrow on the HomeCluster three times
        driver.findElement(By.cssSelector("#home_maincap_v7 > div.arrow.right")).click();
        driver.findElement(By.cssSelector("#home_maincap_v7 > div.arrow.right")).click();
        driver.findElement(By.cssSelector("#home_maincap_v7 > div.arrow.right")).click();

        // Click the third Carousel Thumb
        driver.findElement(By.xpath("//*[@id=\"home_maincap_v7\"]/div[2]/div[3]")).click();

        // Click the ninth Carousel Thumb
        driver.findElement(By.xpath("//*[@id=\"home_maincap_v7\"]/div[2]/div[9]")).click();
    }

    @Test (priority = 4)
    public void searchBarTesting() throws InterruptedException {
        driver.findElement(By.id("store_nav_search_term")).sendKeys("Portal");
        Thread.sleep(500);
        driver.findElement(By.cssSelector("#store_search_link > img")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);
    }
}
