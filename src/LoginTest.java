import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.logging.Level;

public class LoginTest {
    public static void main(String[] args) {
        // Set the path to the chromedriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Jezreel\\OneDrive\\Documents\\chromedriver.exe");

        // Configure ChromeDriver to redirect logs to a file
        ChromeOptions options = new ChromeOptions();
        // Set the path and filename for the log file
        System.setProperty("webdriver.chrome.logfile", "C:\\Users\\Jezreel\\IdeaProjects\\Asurion_java\\lib\\logs_file.log");

        // Enable logging
        System.setProperty("webdriver.chrome.verboseLogging", "true");


        // Create a new instance of the ChromeDriver
        WebDriver driver = new ChromeDriver(options);

        // Scenario 1: Log in using standard user
        driver.get("https://www.saucedemo.com/");
        WebElement usernameInput = driver.findElement(By.id("user-name"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameInput.sendKeys("standard_user");
        passwordInput.sendKeys("secret_sauce");
        loginButton.click();

        // Verify that user is able to navigate to home page
        WebElement inventoryContainer = driver.findElement(By.id("inventory_container"));
        if (inventoryContainer.isDisplayed()) {
            System.out.println("User successfully logged in.");
        } else {
            System.out.println("User login failed.");
        }

        // Log out
        WebElement menuButton = driver.findElement(By.className("bm-burger-button"));
        menuButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
        logoutLink.click();

        // Verify that user is navigated to login page
        WebElement loginContainer = driver.findElement(By.id("login_button_container"));
        if (loginContainer.isDisplayed()) {
            System.out.println("User successfully logged out.");
        } else {
            System.out.println("User logout failed.");
        }

        // Re-locate the elements after the logout action
        usernameInput = driver.findElement(By.id("user-name"));
        passwordInput = driver.findElement(By.id("password"));
        loginButton = driver.findElement(By.id("login-button"));

        // Scenario 2: Log in using locked out user
        usernameInput.sendKeys("locked_out_user");
        passwordInput.sendKeys("secret_sauce");
        loginButton.click();

        // Verify error message
        WebElement errorMessage = driver.findElement(By.cssSelector("[data-test='error']"));
        if (errorMessage.isDisplayed() && errorMessage.getText().equals("Sorry,user has been locked out.")) {
            System.out.println("Locked out user login error message verified.");
        } else {
            System.out.println("Locked out user login error message not found.");
        }

        // Quit the browser
        driver.quit();
    }
}
