package steps;

import Base.BaseUtil;
import com.aventstack.extentreports.GherkinKeyword;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.LoginPage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class LoginStep extends BaseUtil {

    private BaseUtil base;

    public LoginStep(BaseUtil base) {
        this.base = base;
    }

    @DataTableType(replaceWithEmptyString = "[blank]")
    public User convert(Map<String, String> entry) {
        return new User(
                entry.get("username"),
                entry.get("password").concat("$$$$$")
        );
    }

    @Given("^I navigate to the login page$")
    public void iNavigateToTheLoginPage() throws Throwable {
        base.scenarioDef.createNode(new GherkinKeyword("Given"), "I navigate to the login page");
        base.Driver.navigate().to("http://www.executeautomation.com/demosite/Login.html");
        System.out.println("Navigated to login page");
    }

    @And("^I enter the following for Login$")
    public void iEnterTheFollowingForLogin(List<User> table) throws Throwable {
        base.scenarioDef.createNode(new GherkinKeyword("And"), "I enter the following for login");
        LoginPage page = new LoginPage(base.Driver);
        page.Login(table.get(0).username, table.get(0).password);
        System.out.println("Entered credentials: " + table.get(0).username);
    }

    @And("^I click login button$")
    public void iClickLoginButton() throws Throwable {
        base.scenarioDef.createNode(new GherkinKeyword("And"), "I click login button");
        LoginPage page = new LoginPage(base.Driver);
        page.ClickLogin();
        System.out.println("Clicked login button");
    }

    @Then("^I should see the userform page$")
    public void iShouldSeeTheUserformPage() throws Throwable {
        base.scenarioDef.createNode(new GherkinKeyword("Then"), "I should see the userform page");

        System.out.println("Current URL: " + base.Driver.getCurrentUrl());
        System.out.println("Page title: " + base.Driver.getTitle());

        try {
            WebDriverWait wait = new WebDriverWait(base.Driver, Duration.ofSeconds(30));
            WebElement initialField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Initial")));

            if (initialField.isDisplayed()) {
                System.out.println("Userform page loaded successfully");
                Assert.assertTrue(true);
            } else {
                System.out.println("Initial field is present but not visible");
                Assert.fail("Initial field is not visible");
            }

        } catch (Exception e) {
            System.out.println("❌ Timeout or error locating 'Initial' field: " + e.getMessage());

            // Optional: capture screenshot on failure
            try {
                File screenshot = ((TakesScreenshot) base.Driver).getScreenshotAs(OutputType.FILE);
                File destination = new File("target/screenshots/userform_failure.png");
                destination.getParentFile().mkdirs();
                Files.copy(screenshot.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Screenshot saved to: " + destination.getAbsolutePath());
            } catch (Exception ex) {
                System.out.println("Failed to capture screenshot: " + ex.getMessage());
            }

            Assert.fail("Userform page did not load as expected");
        }
    }

    public class User {
        public String username;
        public String password;

        public User(String userName, String passWord) {
            username = userName;
            password = passWord;
        }
    }
}
