package base;
import com.microsoft.playwright.*;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import utilis.ConfigManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static helpers.FunctionsHelper.getCurrentDateAndTime;

public class BrowserFactory {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    boolean headlessEnabled;
    String browserType;


    @BeforeTest
    public void initDriver() {
        playwright = Playwright.create();
        browserType = ConfigManager.getInstance().getString("browserEngine").toLowerCase();
        headlessEnabled = false;
        switch (browserType) {
            case "chrome":
            case "chromium":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headlessEnabled));
                break;
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headlessEnabled));
                break;
            case "webkit":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headlessEnabled));
                break;
            default:
                throw new IllegalArgumentException("Unknown browser type: " + browserType);
        }
        context = browser.newContext();
        page = context.newPage();
        page.navigate(ConfigManager.getInstance().getString("baseURL"));
    }

    public void captureScreenShot(ITestResult result) {
        String directory = (result.getStatus() == ITestResult.SUCCESS) ? "Passed" : "Failed";
        System.out.println("Test Status: " + directory); // Debugging print for test status
        String path = String.format(".\\screenshots\\%s\\%s_%s_test_screenshot.png",
                directory,
                result.getName(),
                getCurrentDateAndTime());
        Path absolutePath = Paths.get(path).toAbsolutePath();
        System.out.println("Screenshot will be saved to: " + absolutePath);
        Path dirPath = absolutePath.getParent();

        if (!Files.exists(dirPath)) {
            try {
                Files.createDirectories(dirPath); // Create the directory
                System.out.println("Created directories: " + dirPath);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error creating directories.");
            }
        } else {
            System.out.println("Directories already exist: " + dirPath);
        }
        try {
            page.screenshot(new Page.ScreenshotOptions().setPath(absolutePath));
            System.out.println("Screenshot saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to take screenshot.");
        }
    }

    @AfterClass
    public void tearDown() {
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

}
