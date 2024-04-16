package testing.steam;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import testing.steam.WebDriverTest;

record OsSupport(boolean windows, boolean macOs, boolean linux) {}
record SearchResult(String title, int priceCents, OsSupport supports) {}

public class SearchFilters extends WebDriverTest{
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(5);
    private static final String SEARCH_URL = "https://store.steampowered.com/search/";

    @Test
    public void navigateToSearchPage() {
        driver.get(SEARCH_URL);
        Assert.assertEquals(driver.getCurrentUrl(), SEARCH_URL);
        Assert.assertEquals(driver.getTitle(), "Steam Search");
    }

    @Test
    public void navigateToSearchPageFromHomePage() throws URISyntaxException {
        driver.get("https://store.steampowered.com/");
        WebElement searchButton = driver.findElement(By.xpath("//a[@id='store_search_link']/img"));
        searchButton.click();

        Wait<WebDriver> wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("additional_search_options")));

        URI currentUrl = new URI(driver.getCurrentUrl());
        URI expected = new URI(SEARCH_URL);
        // Don't compare the URL query parameters.
        Assert.assertEquals(currentUrl.getScheme(), expected.getScheme());
        Assert.assertEquals(currentUrl.getHost(), expected.getHost());
        Assert.assertEquals(currentUrl.getPath(), expected.getPath());
        Assert.assertEquals(driver.getTitle(), "Steam Search");
    }

    @Test
    public void getAllSearchResults() {
        driver.get(SEARCH_URL);
        var results = scrapeSearchResults();
        Assert.assertTrue(results.size() > 0);

        // Display the search results.
        for (SearchResult result : results) {
            OsSupport supports = result.supports();
            System.out.format("""
                %s:
                  Price:   $%.2f
                  Windows? %s
                  macOS?   %s
                  Linux?   %s
                """,
                result.title(),
                result.priceCents() / 100.0,
                supports.windows() ? "✔️" : "❌",
                supports.macOs() ? "✔️" : "❌",
                supports.linux() ? "✔️" : "❌"
            );
        }
    }

    @Test
    public void filterSearchResultsByOs() {
        // Select only Linux-compatible games.
        driver.get(SEARCH_URL);
        WebElement previousContainer = getSearchResultContainer();
        WebElement linuxCheckbox = driver.findElement(
            By.cssSelector("span[data-param='os'][data-value='linux']")
        );
        linuxCheckbox.click();
        waitForUpdatedSearchResults(previousContainer);

        // Check the search results.
        var results = scrapeSearchResults();
        Assert.assertTrue(results.size() > 0);
        for (SearchResult result : results) {
            OsSupport supports = result.supports();
            Assert.assertTrue(supports.linux());
        }
    }

    @Test
    public void filterSearchResultsByFreeToPlay() {
        // Hide free-to-play items.
        driver.get(SEARCH_URL);
        WebElement previousContainer = getSearchResultContainer();
        WebElement freeToPlayCheckbox = driver.findElement(
            By.cssSelector("span[data-param='hidef2p'][data-value='__toggle']")
        );
        freeToPlayCheckbox.click();
        waitForUpdatedSearchResults(previousContainer);

        // Check the search results.
        var results = scrapeSearchResults();
        Assert.assertTrue(results.size() > 0);
        for (SearchResult result : results) {
            Assert.assertNotEquals(result.priceCents(), 0);
        }
    }

    @Test
    public void filterSearchResultsByName() {
        // Search for "Portal".
        driver.get(SEARCH_URL);
        WebElement previousContainer = getSearchResultContainer();
        WebElement searchBar = driver.findElement(
            By.cssSelector(".searchbar:has(#term)")
        );
        WebElement searchBox = searchBar.findElement(By.id("term"));
        WebElement searchButton = searchBar.findElement(By.tagName("button"));
        searchBox.sendKeys("Portal");
        searchButton.click();
        waitForUpdatedSearchResults(previousContainer);

        // Extract titles from the search results.
        var results = scrapeSearchResults();
        ArrayList<String> titles = new ArrayList<String>(results.size());
        for (SearchResult result : results) {
            titles.add(result.title());
        }

        // Check the search results.
        Assert.assertTrue(titles.contains("Portal"));
        Assert.assertTrue(titles.contains("Portal 2"));
        Assert.assertTrue(titles.contains("Portal Bundle"));
        Assert.assertTrue(titles.contains("Portal: Revolution"));
    }

    private WebElement getSearchResultContainer() {
        return driver.findElement(By.id("search_result_container"));
    }

    // Wait for `previousContainer` to be detached from the DOM, indicating
    // that it's been replaced by the updated search results container.
    private void waitForUpdatedSearchResults(WebElement previousContainer) {
        Wait<WebDriver> wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        wait.until(ExpectedConditions.stalenessOf(previousContainer));
    }

    private List<SearchResult> scrapeSearchResults() {
        List<WebElement> rows = driver.findElements(By.xpath("//div[@id='search_resultsRows']/a"));
        ArrayList<SearchResult> results = new ArrayList<SearchResult>();
        for (WebElement row : rows) {
            try {
                WebElement title = row.findElement(By.className("title"));
                // TODO: Read `data-price-final` attribute on `div.search_price_discount_combined`?
                WebElement price = row.findElement(By.className("discount_final_price"));
                OsSupport supports = scrapeOsSupport(row);
                int priceCents;
                if (price.getText().equals("Free")) {
                    priceCents = 0;
                } else {
                    String priceText = price.getText().replaceAll("[^0-9.]", "");
                    priceCents = (int)(Double.parseDouble(priceText) * 100);
                }
                results.add(new SearchResult(title.getText(), priceCents, supports));
            } catch (org.openqa.selenium.NoSuchElementException exception) {
                // Ignore search results that haven't completely loaded.
            }
        }
        return results;
    }

    private OsSupport scrapeOsSupport(WebElement row) {
        boolean windows = row.findElements(By.className("win")).size() > 0;
        boolean macOs = row.findElements(By.className("mac")).size() > 0;
        boolean linux = row.findElements(By.className("linux")).size() > 0;
        return new OsSupport(windows, macOs, linux);
    }
};
