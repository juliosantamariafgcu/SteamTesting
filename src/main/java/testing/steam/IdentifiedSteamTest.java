package testing.steam;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import testing.steam.AuthenticatedSteamTest;

public class IdentifiedSteamTest extends AuthenticatedSteamTest {
    private static final String ACCOUNT_URL = "https://store.steampowered.com/account/";

    private String steamId;
    private String displayName;

    @BeforeClass
    public void readSteamIdFromAccountPage() {
        driver.get(ACCOUNT_URL);
        WebElement steamIdElement = driver.findElement(By.className("youraccount_steamid"));
        WebElement accountPulldown = driver.findElement(By.id("account_pulldown"));
        steamId = steamIdElement.getText().replace("Steam ID: ", "").trim();
        displayName = accountPulldown.getText().trim();
        Assert.assertTrue(steamId.length() > 0);
        Assert.assertTrue(displayName.length() > 0);
        System.out.format("Steam ID: %s\n", steamId);
        System.out.format("Display Name: %s\n", displayName);
    }

    protected String getSteamId() {
        return steamId;
    }

    protected String getDisplayName() {
        return displayName;
    }
};
