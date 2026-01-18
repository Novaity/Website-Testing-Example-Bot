package akakcebot;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class BOT {
    // Main login page URL
    private final String loginUrl = "https://www.akakce.com/akakcem/giris/";
    private final int waitTimeMS =550;
    private final int TIMEOUT = 10;
    private int followed =0;

    private WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;
    private Set<Cookie> cookieSet;

    public BOT() {
        // Initializes WebDriver, WebDriverWait and Actions
        this.driver = new ChromeDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        this.actions = new Actions(driver);
        cookieSet = new LinkedHashSet<>();
    }
    public WebDriverWait getWait() {
        return wait;
    }
    public WebDriver getDriver() {
        return driver;
    }

    public Set<Cookie> getCookie(){
        return cookieSet;
    }
    public void setCookies() {
        cookieSet.clear();
        for (Cookie cookie : driver.manage().getCookies()) {
            cookieSet.add(cookie);
        }
    }

    public WebElement waitForAndFind(By selector) {
        // Waits for an element to appear and returns it
        return wait.until(ExpectedConditions.presenceOfElementLocated(selector));
    }

    void handleTabs() throws InterruptedException {
        // Switches to new browser tab
        String mainTab = driver.getWindowHandle();
        Thread.sleep(waitTimeMS);
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainTab)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    void closeCookieBannerIfExists() {
        // Closes cookie popup if exists
        try {
            WebElement acceptBtn = driver.findElement(By.id("59e066d1-086d-4238-a9a7-b31ba072937c"));
            if (acceptBtn.isDisplayed()) {
                acceptBtn.click();
                Thread.sleep(500);
            }
        } catch (Exception e) {

            ((JavascriptExecutor) driver).executeScript(
                    "let el = document.getElementById('e1f8b8d6-1261-4e20-8b61-73485e29069d'); if(el) el.style.display='none';"
            );

        }
    }

    public boolean login(String email, String password) {
        // Attempts login with provided credentials
        boolean success = false;
        driver.get(loginUrl);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("life")));
            emailField.clear();
            emailField.sendKeys(email);

            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lifp")));
            passwordField.clear();
            passwordField.sendKeys(password);

            // Optionally: Remember Me
            WebElement remember = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#lifr")));
            if (!remember.isSelected()) remember.click();

            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("lfb")));
            submitButton.click();
            // Checks if login is successful via user menu
            try {
                WebElement userMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#HM_v8 > i > a")));
                if (userMenu != null && userMenu.isDisplayed()) {
                    System.out.println("Login successful! User menu found.");
                    success = true;
                }
            } catch (TimeoutException ex) {
                System.out.println("Login failed: Menu not found.");
            }
        } catch (Exception e) {

            System.out.println("Login failed or fields not found: " + e.getMessage());
        }
        return success;
    }

    public void searchProduct(String productName) {
        // Searches for a product by name
        WebElement searchInput = waitForAndFind(By.id("q"));
        searchInput.clear();
        searchInput.sendKeys(productName + Keys.ENTER);
        String encodedName = productName.replace(" ", "+");
        wait.until(ExpectedConditions.urlContains("/arama/?q="));
    }

    public void goFollowingList() throws InterruptedException {
        // Navigates to following list and opens edit
        WebElement profile = waitForAndFind(By.cssSelector("#HM_v8 > i > a"));
        profile.click();
        Thread.sleep(waitTimeMS);
        WebElement followings = waitForAndFind(By.cssSelector("#AL > li:nth-child(2) > a"));
        followings.click();
        Thread.sleep(waitTimeMS);
        WebElement edit = waitForAndFind(By.cssSelector("#editBtn"));
        edit.click();
    }

    public void follow() {
        // Clicks follow button for first listed product
        closeCookieBannerIfExists();
        WebElement followBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#APL > li:nth-child(1) > a > span > span.ufo_v8")));
        followBtn.click();
        System.out.println("followed");
        followed++;
    }

    public void multifollow(int count) throws InterruptedException {
        // Follows multiple products in a row
        closeCookieBannerIfExists();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 0; i < count ; i++) {
            if (isFollowLimitPopupVisible()) {
                closeFollowLimitPopup();
                System.out.println("Follow limit popup seen, stopping. Added product: " + i);
                break;
            }

            Thread.sleep(waitTimeMS);

            WebElement follow;
            try {
                follow = driver.findElement(By.cssSelector("#APL > li:nth-child(" + (i + 1) + ") > a > span > span.ufo_v8"));
                follow.click();
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                System.out.println("Popup prevented click, breaking loop.");
                break;
            }

            Thread.sleep(waitTimeMS);

            // Checks follow limit popup again
            if (isFollowLimitPopupVisible()) {
                closeFollowLimitPopup();
                System.out.println("Follow limit popup seen, stopping. Added product: " + (i)); // (Takip limiti popup’ı görüldü, takip işlemi bitti. Eklenen ürün: ...)
                break;
            }
            System.out.println("followed " + (i));
            followed++;

            js.executeScript("window.scrollBy(0,250)");
        }
    }

    // Checks if follow limit popup is visible
    public boolean isFollowLimitPopupVisible() {
        try {
            return driver.findElement(By.xpath("//*[contains(text(),'Takip listesine en fazla 200 ürün ekleyebilirsiniz')]")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Closes follow limit popup if visible (Takip limiti popup'ı varsa kapatır)
    private void closeFollowLimitPopup() {
        try {
            WebElement closeBtn = driver.findElement(By.cssSelector("#m-w .alertX .buttons button"));
            closeBtn.click();
            Thread.sleep(300);
        } catch (Exception e) {

            System.out.println("Popup could not be closed or was already closed.");
        }
    }

    public void unfollow(String itemtext) {
        // Unfollows a given item by name
        try{
            goFollowingList();
            Thread.sleep(waitTimeMS);
            closeCookieBannerIfExists();
            Thread.sleep(waitTimeMS);
            List<WebElement> items = driver.findElements(By.className("fl-mc-i"));
            for (WebElement item : items) {
                if(item.findElement(By.className("fl-mc-i-n")).getText().toLowerCase().contains(itemtext)){
                    WebElement checkbox = item.findElement(By.className("fl-li-chk"));
                    checkbox.click();
                    Thread.sleep(waitTimeMS);
                    break;
                }
            }
            WebElement click= waitForAndFind(By.cssSelector("#deleteItemModalButton"));
            click.click();
            Thread.sleep(waitTimeMS);
            WebElement conf = waitForAndFind(By.cssSelector("#m-w > div > div.m-c > div > div > button:nth-child(2)"));
            conf.click();
            Thread.sleep(waitTimeMS);
        }
        catch (Exception e){
            System.out.println("No item to unfollow");
        }
        driver.get("https://www.akakce.com/");
    }

    public void unfollowall() {
        // Unfollows all items in the following list
        try{
            goFollowingList();
            Thread.sleep(waitTimeMS);
            closeCookieBannerIfExists();
            WebElement button = waitForAndFind(By.cssSelector("#allSelectBtn"));
            button.click();
            Thread.sleep(waitTimeMS);
            WebElement click= waitForAndFind(By.cssSelector("#deleteItemModalButton"));
            click.click();
            Thread.sleep(waitTimeMS);
            WebElement conf = waitForAndFind(By.cssSelector("#m-w > div > div.m-c > div > div > button:nth-child(2)"));
            conf.click();
            Thread.sleep(waitTimeMS);
        }
        catch (Exception e){
            driver.get("https://www.akakce.com/");
            System.out.println("No item to unfollow");
        }
    }

    public void gotoDetail(String item) throws InterruptedException {
        // Goes to the detail page of a product
        try {
            searchProduct(item);
            closeCookieBannerIfExists();
            Thread.sleep(waitTimeMS);
            WebElement firstProduct = waitForAndFind(By.className("bt_v8"));
            firstProduct.click();
        }
        catch (Exception e) {
            System.out.println("gotoDetail error: " + e.getMessage());
            throw e; // or return;
        }
    }

    public void Redirect() throws InterruptedException {
        // Redirects to vendor detail page in new tab
        Thread.sleep(waitTimeMS);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,800)");
        Thread.sleep(waitTimeMS);
        js.executeScript("let el = document.querySelector('efilli-layout-dynamic'); if(el) el.style.display='none';");
        Thread.sleep(200);
        closeCookieBannerIfExists();
        WebElement vendorButton = waitForAndFind(By.cssSelector("#PL > li.c > a > span.bt_v8"));
        vendorButton.click();
        handleTabs();
        System.out.println(driver.getCurrentUrl());
    }

    public void logout() throws InterruptedException {
        // Logs out from current user session
        try {
            WebElement profileIcon = waitForAndFind(By.id("H_a_v8"));
            if (profileIcon.isDisplayed()) {
                Actions actions = new Actions(driver);
                actions.moveToElement(profileIcon).perform();
                Thread.sleep(500);
                WebElement exit = waitForAndFind(By.xpath("//*[@id=\"HM_v8\"]/ul/li[6]/a"));
                exit.click();
                Thread.sleep(waitTimeMS);
                driver.get("https://www.akakce.com/");
            }
        } catch (Exception e) {
            driver.get("https://www.akakce.com/");
            System.out.println("Logout already done or profile icon not found. Exception: " + e.getMessage());
        }
    }

    public void filterTest(String url, String priceMin, String priceMax, String brandId, String featureId, String productBrand) throws InterruptedException {
        // Tests filtering options on product list page
        driver.get(url);
        closeCookieBannerIfExists();
        Thread.sleep(waitTimeMS);
        if (priceMin != null && !priceMin.isEmpty() && priceMax != null && !priceMax.isEmpty()) {
            WebElement priceMinField = waitForAndFind(By.id("pf1"));
            WebElement priceMaxField = waitForAndFind(By.id("pf2"));
            Thread.sleep(waitTimeMS);
            priceMinField.clear();
            priceMinField.sendKeys(priceMin);
            priceMaxField.clear();
            priceMaxField.sendKeys(priceMax);
            WebElement applyButton = waitForAndFind(By.cssSelector("#FF_v9 > span.fpl_v9 > span > button"));
            applyButton.click();
            Thread.sleep(waitTimeMS);
        }
        if (brandId != null && !brandId.isEmpty()) {
            WebElement brandFilter = waitForAndFind(By.xpath("//input[@id='" + brandId + "']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", brandFilter);
            if (brandFilter.isEnabled() && brandFilter.isDisplayed()) {
                try {
                    brandFilter.click();
                } catch (ElementClickInterceptedException e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", brandFilter);
                }
            }
            Thread.sleep(waitTimeMS);
        }

        // Feature filter (Özellik filtresi)
        if (featureId != null && !featureId.isEmpty()) {
            WebElement featuresFilter = waitForAndFind(By.xpath("//input[@id='" + featureId + "']"));
            if (featuresFilter.isEnabled() && featuresFilter.isDisplayed()) {
                try {
                    featuresFilter.click();
                } catch (ElementClickInterceptedException e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", featuresFilter);
                }
            }
            Thread.sleep(waitTimeMS);
        }

        // Finds products and prints product count
        List<WebElement> productElements;
        if (productBrand != null && !productBrand.isEmpty()) {
            productElements = driver.findElements(By.xpath("//ul[@id='CPL']//li[@data-mk='" + productBrand + "']"));
            System.out.println(productBrand + " brand product count: " + productElements.size());
        } else {
            productElements = driver.findElements(By.xpath("//ul[@id='CPL']//li"));
            System.out.println("All product count: " + productElements.size());
        }
    }

    public void quit() {
        // Ends browser session
        try {
            if (actions != null) {
                actions.pause(Duration.ofSeconds(2)).perform();
            }
            if (driver != null) {
                driver.quit();
            }
        }
        catch (Exception E){
            driver.quit();
            System.out.println(E);
        }
    }
}