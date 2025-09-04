package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By txtUserName = By.name("UserName");
    private By txtPassword = By.name("Password");
    private By btnLogin = By.name("Login");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Enter username and password
    public void Login(String userName, String password) {
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(txtUserName));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(txtPassword));

        usernameField.clear();
        usernameField.sendKeys(userName);

        passwordField.clear();
        passwordField.sendKeys(password);
    }

    // Click login button
    public void ClickLogin() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(btnLogin));
        loginButton.click();
    }
}
