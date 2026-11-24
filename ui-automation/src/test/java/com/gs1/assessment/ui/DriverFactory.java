package com.gs1.assessment.ui;

import com.gs1.assessment.support.Config;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class DriverFactory {
    private DriverFactory() { }

    public static WebDriver create() {
        ChromeOptions options = new ChromeOptions();
        if (Config.headless()) options.addArguments("--headless=new");
        options.addArguments("--window-size=1440,1000");
        String binary = System.getProperty("chrome.binary");
        if (binary != null && !binary.isBlank()) options.setBinary(binary);
        WebDriver driver = new ChromeDriver(options);
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ZERO);
            driver.manage().timeouts().pageLoadTimeout(Config.uiTimeout());
            return driver;
        } catch (RuntimeException exception) {
            try { driver.quit(); } catch (RuntimeException cleanup) { exception.addSuppressed(cleanup); }
            throw exception;
        }
    }
}
