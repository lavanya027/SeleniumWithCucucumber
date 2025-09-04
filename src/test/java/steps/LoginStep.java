package steps;

import Base.BaseUtil;
import com.aventstack.extentreports.GherkinKeyword;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.LoginPage;

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
                entry.get("password")
        );
    }

    @Given("^I navigate to the login page$")
    public void iNavigateToTheLoginPage() throws Throwable {
        base.scenarioDef.createNode(new GherkinKeyword("Given"), "I navigate to the login page");
        base.Driver.navigate().to("https://practicetestautomation.com/practice-test-login/");
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

    @Then("^I should see the success page$")
    public void iShouldSeeTheSuccessPage() throws Throwable {
        base.scenarioDef.createNode(new GherkinKeyword("Then"), "I should see the success page");

        WebDriverWait wait = new WebDriverWait(base.Driver, Duration.ofSeconds(20));
        WebElement logoutButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Log out']")));

        Assert.assertTrue("Logout button is not displayed", logoutButton.isDisplayed());
        System.out.println("✅ Login successful — success page loaded");
    }

    @Then("^I should not see the success page$")
    public void iShouldNotSeeTheSuccessPage() throws Throwable {
        base.scenarioDef.createNode(new GherkinKeyword("Then"), "I should not see the success page");

        boolean isLogoutVisible = base.Driver.findElements(By.xpath("//a[text()='Log out']")).size() > 0;
        Assert.assertFalse("Unexpected success page appeared", isLogoutVisible);
        System.out.println("✅ Login failed as expected — success page not shown");
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
