package com.gs1.assessment.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class UiTestBase {
    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void startBrowser() { driver = DriverFactory.create(); }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser(ITestResult result) {
        if (driver == null) return;
        try {
            if (!result.isSuccess() && driver instanceof TakesScreenshot screenshot) {
                Path output = Path.of("target", "screenshots", result.getName() + "-" + System.nanoTime() + ".png");
                Files.createDirectories(output.getParent());
                Files.write(output, screenshot.getScreenshotAs(OutputType.BYTES));
                Reporter.log("Failure screenshot: " + output.toAbsolutePath(), true);
            }
        } catch (IOException | RuntimeException exception) {
            Reporter.log("Could not capture failure screenshot: " + exception.getMessage(), true);
        } finally {
            driver.quit();
            driver = null;
        }
    }

    /** Optional viewing time for a human demonstration; never used for synchronization. */
    protected void demonstrate(String message) {
        Reporter.log("[UI STEP] " + message, true);
        long pauseMillis = Long.parseLong(System.getProperty("demo.pauseMillis", "0"));
        if (pauseMillis < 0 || pauseMillis > 10000) {
            throw new IllegalArgumentException("demo.pauseMillis must be between 0 and 10000");
        }
        if (pauseMillis == 0) return;
        try {
            Thread.sleep(pauseMillis);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Demonstration interrupted", exception);
        }
    }
}
