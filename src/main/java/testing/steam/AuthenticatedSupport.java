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
import org.testng.asserts.SoftAssert;
import testing.steam.AuthenticatedSteamTest;
import testing.steam.CommonSupport;

public class AuthenticatedSupport extends AuthenticatedSteamTest {
    private static final String SUPPORT_URL = CommonSupport.SUPPORT_URL;
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(5);

    @Test
    public void checkIntroduction() {
        driver.get(SUPPORT_URL);
        String name = getAccountName();
        WebElement introduction = driver.findElement(By.className("help_intro_text"));
        Assert.assertEquals(introduction.getText(), "What do you need help with, %s?".formatted(name));
    }

    @Test
    public void checkLargeHelpButtons() {
        SoftAssert soft = new SoftAssert();
        driver.get(SUPPORT_URL);

        Wait<WebDriver> wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
            By.id("help_home_block"),
            "Trading, Gifting, Market and Steam Points"
        ));

        var buttons = CommonSupport.getLargeHelpButtons(driver);
        String[] labels = {
            "Games, Software, etc.",
            "Purchases",
            "My Account",
            "Trading, Gifting, Market and Steam Points",
            "Steam Client",
            "Steam Community",
            "Steam Hardware",
        };
        soft.assertEquals(
            buttons.size(), labels.length,
            "Incorrect number of buttons;"
        );
        for (int i = 0; i < Math.min(buttons.size(), labels.length); i++) {
            soft.assertEquals(
                buttons.get(i).getText(), labels[i],
                "Incorrect button label at index %d;".formatted(i)
            );
        }
        soft.assertAll();
    }

    @Test
    public void navigateToSupportPage() {
        CommonSupport.navigateToSupportPage(driver);
    }

    @Test
    public void navigateToSupportPageFromHomePage() {
        CommonSupport.navigateToSupportPageFromHomePage(driver);
    }

    @Test
    public void getHelpWithGame() {
        CommonSupport.getHelpWithGame(driver);
    }

    @Test
    public void getHelpWithPurchase() {
        CommonSupport.getHelpWithPurchase(driver);
    }

    @Test
    public void getHelpWithAccount() {
        CommonSupport.getHelpWithAccount(driver);
    }

    @Test
    public void getHelpWithCommunity() {
        // Community support requires authentication.
        driver.get(SUPPORT_URL);
        CommonSupport.getLargeHelpButton(driver, 5).click();
        CommonSupport.waitForHelpPageToLoad(driver);
        Assert.assertEquals(driver.getCurrentUrl(), "https://help.steampowered.com/en/wizard/HelpWithCommunity");
        Assert.assertEquals(driver.getTitle(), "Steam Support - Steam Community");
    }

    @Test
    public void getHelpWithUnknownCharges() {
        // Authenticated users can still directly navigate to the URL.
        driver.get("https://help.steampowered.com/en/wizard/HelpWithUnknownCharges");
        Assert.assertEquals(driver.getCurrentUrl(), "https://help.steampowered.com/en/wizard/HelpWithUnknownCharges");
        Assert.assertEquals(driver.getTitle(), "Steam Support");
    }
};
