package akakcebot;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginTest {
    WebDriver driver;
    BOT bot;
    WebDriverWait wait;

    @BeforeEach

    public void initialize() throws InterruptedException {
        // Initializes WebDriver and BOT before all tests
        bot = new BOT();
        driver = bot.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        driver.manage().window().maximize();
    }

    @AfterEach
    public void end() {
        // Quits the browser session after all tests
        bot.quit();
    }

    @Test
    @Order(1)
    void testLoginWithValidCredentials() {
        // Tests login with correct credentials
        boolean result = bot.login("testmailtesting@gmail.com", "123456789Test");
        assertTrue(result);
    }

    @Test
    @Order(2)
    void testLoginWithIncorrectPassword() {
        // Tests login with incorrect password
        boolean result = bot.login("testmailtesting@gmail.com", "wrong_password");
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#m-w > div > div.m-c")));
        assertNotNull(alert);
        assertFalse(result);
    }

    @Test
    @Order(3)
    void testLoginWithEmptyFields() {
        // Tests login with empty email and password fields
        boolean result = bot.login("", "");
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#m-w > div")));
        assertNotNull(alert);
        assertFalse(result);
    }

    @Test
    @Order(4)
    void testRememberMeOption() throws InterruptedException {
        bot.login("testmailtesting@gmail.com", "123456789Test");
        bot.setCookies();
        assertFalse(bot.getCookie().isEmpty());
        driver.quit();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.akakce.com/");
        for (Cookie cookie : bot.getCookie()) {
            driver.manage().addCookie(cookie);
        }
        driver.navigate().refresh();
        Thread.sleep(1000);
        WebElement userMenu = driver.findElement(By.id("HM_v8"));
        assertTrue(userMenu.isDisplayed());
        driver.quit();
    }

    @Test
    @Order(5)
    void testPasswordInputMasked() {
        // Tests if password input field is masked
        driver.get("https://www.akakce.com/akakcem/giris/");
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lifp")));
        assertEquals("password", passwordField.getAttribute("type"));
    }

    @Test
    @Order(6)
    void testRedirectionAfterLogin() {
        // Tests if redirected to main page after login
        bot.login("testmailtesting@gmail.com", "123456789Test");
        assertTrue(driver.getCurrentUrl().contains("akakce.com"));
    }
    @Test
    @Order(7)
    void testLoginUsingEnterKey() {
        // Tests login action using Enter key
        driver.get("https://www.akakce.com/akakcem/giris/");
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("life")));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lifp")));
        emailField.sendKeys("testmailtesting@gmail.com");
        passwordField.sendKeys("123456789Test");
        passwordField.sendKeys(Keys.RETURN);
        wait.until(ExpectedConditions.urlContains("akakce.com"));
        assertTrue(driver.getCurrentUrl().startsWith("https://www.akakce.com"));
    }

    @Test
    @Order(8)
    void testLoginForNonExistAccount() {
        boolean result = bot.login("nonexistmailfortesting@hotmail.com", "password");
        WebElement warningMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alertX.t2 > p"))
        );
        assertEquals("Bu e-postaya kayıtlı bir hesap bulunamadı.", warningMessage.getText());
        assertFalse(result);
    }
    @Test
    @Order(9)
    void testRateLimiting() {
        // Tests rate limit after multiple failed login attempts
        int count = 0;
        while (true) {
            bot.login("testmailtesting@gmail.com", "wrong_password");
            WebElement warningMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#m-w > div > div.m-c > div > p")));
            String message = warningMessage.getText();
            System.out.println("Found message: " + message);
            if (message.equals("Lütfen daha sonra tekrar deneyin.")){
                break;
            }
            count++;
        }
        System.out.println("Rate Limit " + count); // (Rate Limit ...)
        assertTrue(true);
    }


}

