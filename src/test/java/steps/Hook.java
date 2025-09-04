package steps;

import Base.BaseUtil;
import io.cucumber.java.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Hook extends BaseUtil {

    private BaseUtil base;

    public Hook(BaseUtil base) {
        this.base = base;
    }

    @Before
    public void InitializeTest(Scenario scenario) {
        base.scenarioDef = base.features.createNode(scenario.getName());

        // Clear any cached or outdated ChromeDriver versions
        WebDriverManager.chromedriver().clearDriverCache();
        WebDriverManager.chromedriver().clearResolutionCache();
        WebDriverManager.chromedriver().setup();

        // Configure Chrome (non-headless for debugging)
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--window-size=1920,1080");

        base.Driver = new ChromeDriver(chromeOptions);
        System.out.println("✅ Chrome browser launched successfully");
    }

    @After
    public void TearDownTest(Scenario scenario) {
        if (scenario.isFailed()) {
            System.out.println("❌ Test failed: " + scenario.getName());
            // You can add screenshot capture logic here
        }

        System.out.println("🧹 Closing the browser");

        if (base.Driver != null) {
            base.Driver.quit();
            System.out.println("✅ Browser closed successfully");
        } else {
            System.out.println("⚠️ Driver was not initialized — skipping quit.");
        }
    }

    @BeforeStep
    public void BeforeEveryStep(Scenario scenario) {
        System.out.println("🔄 Before step: " + scenario.getName());
    }

    @AfterStep
    public void AfterEveryStep(Scenario scenario) {
        System.out.println("✅ After step: " + scenario.getName());
    }
}
