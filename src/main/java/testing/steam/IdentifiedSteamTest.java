package testing.steam;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import testing.steam.AuthenticatedSteamTest;

public class IdentifiedSteamTest extends AuthenticatedSteamTest {
    private static final String ACCOUNT_URL = "https://store.steampowered.com/account/";

    private String steamId;

    @BeforeClass
    public void readSteamIdFromAccountPage() {
        driver.get(ACCOUNT_URL);
        WebElement steamIdElement = driver.findElement(By.className("youraccount_steamid"));
        steamId = steamIdElement.getText().replace("Steam ID: ", "").trim();
        Assert.assertTrue(steamId.length() > 0);
        System.out.format("Steam ID: %s\n", steamId);
    }

    protected String getSteamId() {
        return steamId;
    }
};
