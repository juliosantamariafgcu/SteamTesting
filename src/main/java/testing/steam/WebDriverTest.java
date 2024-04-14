package testing.steam;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.testng.annotations.*;

public class WebDriverTest {
    private static final String WEB_DRIVER = "STEAM_WEB_DRIVER";

    protected WebDriver driver;

    @BeforeTest
    public void setDefaultDriverLocations() {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
                "C:/Users/santa/Downloads/java/chromedriver-win64/chromedriver-win64/chromedriver.exe");
        // System.setProperty(EdgeDriverService.EDGE_DRIVER_EXE_PROPERTY, "");
        // System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, "");
    }

    @BeforeClass
    public void createDriver() {
        String choice = System.getenv().getOrDefault(WEB_DRIVER, "Chrome");
        switch (choice) {
            case "Chrome":
                driver = new ChromeDriver();
                break;
            case "Edge":
                driver = new EdgeDriver();
                break;
            case "Firefox":
                driver = new FirefoxDriver();
                break;
            default:
                String message = WEB_DRIVER + " must be 'Chrome', 'Edge', or 'Firefox'; "
                    + "currently it is '" + choice + "'";
                throw new RuntimeException(message);
        }
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        driver.quit();
    }
};
