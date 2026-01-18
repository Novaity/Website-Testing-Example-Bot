package akakcebot;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
class SearchTest {
    static WebDriver driver;
    static BOT bot;
    private static WebDriverWait wait;

    @BeforeAll
    public static void initiliaze() throws InterruptedException {
        // Initializes WebDriver and BOT before all tests
        bot=new BOT();
        driver = bot.getDriver();
        driver.manage().window().maximize();
        driver.get("https://www.akakce.com");
        wait = bot.getWait();
    }

    @AfterAll
    public static void end(){
        // Quits the WebDriver after all tests (Tüm testler bittikten sonra WebDriver kapatılır)
        bot.quit();
    }

    @ParameterizedTest
    @ValueSource(strings = {"iphone 13", "samsung"})
    public void testValidSearchQuery(String productName) throws InterruptedException {
        // Tests search with a valid product name
        boolean succes = false;
        bot.searchProduct(productName);
        Thread.sleep(2000);
        List<WebElement> results = driver.findElements(By.cssSelector("#APL > li > a.pw_v8"));
        for(WebElement result : results) {
            String title = result.getAttribute("title");
            System.out.println("Title: " + title);
            if(title != null && title.toLowerCase().contains(productName)) {
                succes = true;
                break;
            }
        }
        assertTrue(succes, productName+" must be found");
    }

    @Test
    public void testEmptySearchQuery() throws InterruptedException {
        // Tests search with an empty query string
        bot.searchProduct(" ");
        Thread.sleep(2000);
        List<WebElement> results = driver.findElements(By.cssSelector("#APL > li > a.pw_v8"));
        assertTrue(results.size() > 0, "Products should arrive on empty search");
    }

    @ParameterizedTest
    @ValueSource(strings = {"@", "@#$%^&*\""})
    public void testSpecialCharacterQuery() throws InterruptedException {
        // Tests search using only special characters
        bot.searchProduct("@#$%^&*");
        List<WebElement> results = driver.findElements(By.cssSelector("#APL > li > a.pw_v8"));
        assertEquals(0, results.size(), "The product should not come in a special character");
    }

    @ParameterizedTest
    @ValueSource(strings = {"iphone 13", "samsung"})
    public void testCaseInsensitivity(String productName) throws InterruptedException {
        // Tests case insensitivity in search queries
        bot.searchProduct(productName);
        List<WebElement> results = driver.findElements(By.cssSelector("#APL > li > a.pw_v8"));
        boolean found = results.stream().anyMatch(el -> el.getText().toLowerCase().contains(productName));
        assertTrue(found, productName+" should also come with a capital letter");
    }

    @ParameterizedTest
    @ValueSource(strings = {"iphone 13", "samsung"})
    public void testPartialMatch(String partial) throws InterruptedException {
        // Tests partial keyword search functionality
        bot.searchProduct(partial);
        List<WebElement> results = driver.findElements(By.cssSelector("#APL > li > a.pw_v8"));
        boolean found=false;
        for(WebElement result : results) {
            String title = result.getAttribute("title");
            System.out.println("Title: " + title);
            if(title != null && title.toLowerCase().contains(partial)) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Product must come in partial matching");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Galaxyİphone 992", "FKLHJASBJKFAkjb"})
    public void testNonExistingProduct(String invalidProduct) throws InterruptedException {
        // Tests search with a non-existing product
        bot.searchProduct(invalidProduct);
        Thread.sleep(500);
        List<WebElement> results = driver.findElements(By.cssSelector("#APL > li > a.pw_v8"));
        assertEquals(0, results.size(), "There should be no result on a product that does not exist");
    }

    @ParameterizedTest
    @ValueSource(strings = {"iphone 13", "samsung"})
    public void testSpecialCharacterHandling(String productName) throws InterruptedException {
        // Tests handling of special characters like dash
        try {
            bot.searchProduct(productName.replace(' ', '-'));
        }
        catch (Exception e){
            bot.searchProduct(productName);
        }
        List<WebElement> results = driver.findElements(By.cssSelector("#APL > li > a.pw_v8"));
        String normalizedProduct = productName.replace(" ", "").replace("-", "").toLowerCase();
        boolean found = results.stream().anyMatch(el ->
                el.getText().replace(" ", "").replace("-", "").toLowerCase().contains(normalizedProduct)
        );
        assertTrue(found, "The product should also appear in the hyphenated search");
    }

    @Test
    public void testDropdownSuggestions() throws InterruptedException {
        // Tests dropdown search suggestions under searchbox
        bot.searchProduct("iph");
        List<WebElement> suggestions = driver.findElements(By.cssSelector(("body > div.rw_v8.search_v8 > p:nth-child(2)")));
        assertTrue(suggestions.size() > 0, "Suggestions should come under the search box");
    }

    @Test
    public void testLongSearchQuery() throws InterruptedException {
        String longQuery = "a".repeat(955);
        bot.searchProduct(longQuery);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#APL")));
        List<WebElement> results = driver.findElements(By.cssSelector("#APL > li > a.pw_v8"));
        assertEquals(0, results.size(), "The product should not take too long to arrive");
    }

    @ParameterizedTest
    @CsvSource({"ipohne 13,iphone 13"})
    public void testTypoTolerance(String wrongProductName, String validProductName) throws InterruptedException {
        // Tests tolerance to typo in search
        bot.searchProduct(wrongProductName);
        List<WebElement> results = driver.findElements(By.cssSelector("#APL > li > a.pw_v8"));
        boolean found = results.stream().anyMatch(el -> el.getText().toLowerCase().contains(validProductName));
        assertTrue(found, "In case of a typo, the correct product should still arrive");
    }
    @ParameterizedTest
    @ValueSource(strings = {"apple"})
    public void testbrandSearch(String productName) {
        bot.searchProduct("macbook");
        List<WebElement> listItems = driver.findElements(By.cssSelector("[data-mk]"));
        assertFalse(listItems.isEmpty(), "Search returns empty!");
        boolean allMatch = true;
        for (WebElement item : listItems) {
            String mk = item.getAttribute("data-mk").toLowerCase();
            System.out.println("brand: " + mk);
            if (!mk.equals(productName.toLowerCase())) {
                allMatch = false;
                break;
            }
        }
        assertTrue(allMatch, "Wanted brand (" + productName + ") not found.");
    }


}