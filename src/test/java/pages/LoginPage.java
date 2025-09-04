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

    private By txtUserName = By.id("username");
    private By txtPassword = By.id("password");
    private By btnLogin = By.id("submit");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void Login(String userName, String password) {
        System.out.println("🔍 Current URL: " + driver.getCurrentUrl());
        System.out.println("🔍 Page Title: " + driver.getTitle());

        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(txtUserName));
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(txtPassword));

        usernameField.clear();
        usernameField.sendKeys(userName);

        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void ClickLogin() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(btnLogin));
        loginButton.click();
    }
}
