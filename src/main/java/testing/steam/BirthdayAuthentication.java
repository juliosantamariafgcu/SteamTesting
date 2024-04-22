package testing.steam;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;

public class BirthdayAuthentication extends AuthenticatedSteamTest{
    String url_current;

    @Test (priority = 11)
    public void birthdayLimitIncorrect() throws InterruptedException {
        // Search for "Fallout 4" and click on the first result
        driver.findElement(By.id("store_nav_search_term")).sendKeys("Fallout 4");
        Thread.sleep(500);
        driver.findElement(By.cssSelector("#store_search_link > img")).click();
        Thread.sleep(500);
        driver.findElement(By.cssSelector("#search_resultsRows > a:nth-child(1) > div.responsive_search_name_combined")).click();
        Thread.sleep(500);

        // Select the Birth date December 31, 2024
        driver.findElement(By.id("ageDay")).click();
        driver.findElement(By.xpath("//*[@id=\"ageDay\"]/option[31]")).click();
        Thread.sleep(500);
        boolean daySelected = driver.findElement(By.xpath("//*[@id=\"ageDay\"]/option[31]")).isSelected();

        driver.findElement(By.id("ageMonth")).click();
        driver.findElement(By.xpath("//*[@id=\"ageMonth\"]/option[12]")).click();
        Thread.sleep(500);
        boolean monthSelected = driver.findElement(By.xpath("//*[@id=\"ageMonth\"]/option[12]")).isSelected();


        driver.findElement(By.id("ageYear")).click();
        driver.findElement(By.xpath("//*[@id=\"ageYear\"]/option[125]")).click();
        Thread.sleep(500);
        boolean yearSelected = driver.findElement(By.xpath("//*[@id=\"ageYear\"]/option[125]")).isSelected();

        // Assert if the proper date is selected
        Assert.assertTrue(daySelected);
        Assert.assertTrue(monthSelected);
        Assert.assertTrue(yearSelected);

        // Click View Page
        driver.findElement(By.xpath("//*[@id=\"view_product_page_btn\"]")).click();
        Thread.sleep(500);

        // Click Ok on Pop-up
        driver.findElement(By.cssSelector("body > div.newmodal > div.newmodal_content_border > div > div.newmodal_buttons > div > span")).click();

        // Assert if URL Changed
        url_current = driver.getCurrentUrl();
        Thread.sleep(500);
        Assert.assertNotEquals(url_current, "https://store.steampowered.com/app/377160/Fallout_4/", "URL Did Change!\n\n");
        System.out.println("URL Did Not Change!\n\n");
        System.out.println("Current URL: ");
        System.out.println(url_current);
        System.out.println("\n");

        // Back to Home Page
        driver.findElement(By.cssSelector("#global_header > div > div.supernav_container > a.menuitem.supernav.supernav_active")).click();
    }

    @Test (priority = 12)
    public void birthdayCorrect() throws InterruptedException {
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

        // Back to Home Page
        driver.findElement(By.xpath("//*[@id=\"global_header\"]/div/div[2]/a[1]")).click();
    }
}