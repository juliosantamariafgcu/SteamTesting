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

public class StoreTabSteamTest extends AuthenticatedSteamTest{

    @Test (priority = 1)
    public void storeTabLinks() throws InterruptedException {

        // Side/Gutter Links
        driver.findElement(By.linkText("Tags")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);

        driver.findElement(By.linkText("Upcoming")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);

        driver.findElement(By.linkText("Steam Deck Dock")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);

        driver.findElement(By.linkText("Free to Play")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);

        // Your Store Flyout (Clicking on DLC For You)
        driver.findElement(By.xpath("//*[@id=\"foryou_tab\"]/span")).click();
        driver.findElement(By.cssSelector("#foryou_flyout > div > a:nth-child(11)")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);

        // New & Noteworthy Flyout (Clicking on Top Sellers)
        driver.findElement(By.xpath("//*[@id=\"noteworthy_tab\"]/span/a[1]")).click();
        driver.findElement(By.cssSelector("#noteworthy_flyout > div > div:nth-child(1) > a:nth-child(2)")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);

        // Categories Flyout (Clicking on Card & Board)
        driver.findElement(By.xpath("//*[@id=\"genre_tab\"]/span/a[1]")).click();
        driver.findElement(By.cssSelector("#genre_flyout > div > div:nth-child(4) > div:nth-child(4) > a:nth-child(1)")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);

        // Points Shop Link
        driver.findElement(By.linkText("Points Shop")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);

        // News Link
        driver.findElement(By.linkText("News")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
        Thread.sleep(500);
    }
}
