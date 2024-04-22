package testing.steam;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;

public class GamePageTest extends AuthenticatedSteamTest{
    String url_current;
    @Test (priority = 13)
    public void sliderArrows() throws InterruptedException {
        // Navigate to Fallout 4 Page
        navToFallout();
        Thread.sleep(1000);

        // use left slider arrow 10 times
        leftSlider();

        // use right slider arrow 5 times
        rightSlider();
    }

    @Test (priority = 14)
    public void communityTab() throws InterruptedException{
        // Go to the Community Tab of a Game
        driver.findElement(By.cssSelector("#tabletGrid > div.page_content_ctn > div.page_title_area.game_title_area.page_content > div.apphub_HomeHeaderContent > div > div.apphub_OtherSiteInfo > a")).click();

        // Assert that the URL Changed
        url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertEquals(url_current, "https://steamcommunity.com/app/377160", "URL Did Not Change!\n\n");
        System.out.println("On Fallout 4 Community Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n");
    }

    private void navToFallout() throws InterruptedException {
        // Search for "Fallout 4" and click on the first result
        driver.findElement(By.id("store_nav_search_term")).sendKeys("Fallout 4");
        Thread.sleep(500);
        driver.findElement(By.cssSelector("#store_search_link > img")).click();
        Thread.sleep(500);
        driver.findElement(By.cssSelector("#search_resultsRows > a:nth-child(1) > div.responsive_search_name_combined")).click();
        Thread.sleep(500);

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

        // Assert if URL Changed
        url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertEquals(url_current, "https://store.steampowered.com/app/377160/Fallout_4/", "URL Did Not Change!\n\n");
        System.out.println("On Fallout 4 Page!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n");
    }

    private void leftSlider() throws InterruptedException{
        driver.findElement(By.id("highlight_slider_left")).click();
        Thread.sleep(300);
        driver.findElement(By.id("highlight_slider_left")).click();
        Thread.sleep(300);
        driver.findElement(By.id("highlight_slider_left")).click();
        Thread.sleep(300);
        driver.findElement(By.id("highlight_slider_left")).click();
        Thread.sleep(300);
        driver.findElement(By.id("highlight_slider_left")).click();
        Thread.sleep(300);
        driver.findElement(By.id("highlight_slider_left")).click();
        Thread.sleep(300);
        driver.findElement(By.id("highlight_slider_left")).click();
        Thread.sleep(300);
        driver.findElement(By.id("highlight_slider_left")).click();
        Thread.sleep(300);
        driver.findElement(By.id("highlight_slider_left")).click();
        Thread.sleep(300);
        driver.findElement(By.id("highlight_slider_left")).click();
        Thread.sleep(300);
    }
    private void rightSlider() throws InterruptedException{
        driver.findElement(By.id("highlight_slider_right")).click();
        Thread.sleep(200);
        driver.findElement(By.id("highlight_slider_right")).click();
        Thread.sleep(200);
        driver.findElement(By.id("highlight_slider_right")).click();
        Thread.sleep(200);
        driver.findElement(By.id("highlight_slider_right")).click();
        Thread.sleep(200);
        driver.findElement(By.id("highlight_slider_right")).click();
        Thread.sleep(200);
    }
}
