package steps;

import Base.BaseUtil;
import com.aventstack.extentreports.GherkinKeyword;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
                entry.get("password").concat("$$$$$")
        );
    }

    @Given("^I navigate to the login page$")
    public void iNavigateToTheLoginPage() throws Throwable {
        base.scenarioDef.createNode(new GherkinKeyword("Given"), "I navigate to the login page");
        base.Driver.navigate().to("http://www.executeautomation.com/demosite/Login.html");
    }

    @And("^I enter the following for Login$")
    public void iEnterTheFollowingForLogin(List<User> table) throws Throwable {
        base.scenarioDef.createNode(new GherkinKeyword("And"), "I enter the following for login");
        LoginPage page = new LoginPage(base.Driver);
        page.Login(table.get(0).username, table.get(0).password);
    }

    @And("^I click login button$")
    public void iClickLoginButton() throws Throwable {
        base.scenarioDef.createNode(new GherkinKeyword("And"), "I click login button");
        LoginPage page = new LoginPage(base.Driver);
        page.ClickLogin();
    }

    @Then("^I should see the userform page$")
    public void iShouldSeeTheUserformPage() throws Throwable {
        base.scenarioDef.createNode(new GherkinKeyword("Then"), "I should see the userform page");
        WebDriverWait wait = new WebDriverWait(base.Driver, Duration.ofSeconds(10));
        WebElement initialField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Initial")));
        Assert.assertTrue("Initial field is not displayed", initialField.isDisplayed());
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
