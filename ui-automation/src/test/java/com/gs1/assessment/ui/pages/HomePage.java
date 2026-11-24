package com.gs1.assessment.ui.pages;

import com.gs1.assessment.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class HomePage extends BasePage {
    public HomePage(WebDriver driver) { super(driver); }

    public HomePage open() {
        driver.get(Config.get("ui.baseUrl"));
        visible(By.cssSelector("#content h1"));
        return this;
    }

    public FileUploadPage openFileUpload() {
        click(By.cssSelector("a[href='/upload']"));
        return new FileUploadPage(driver);
    }

    public DynamicLoadingPage openDynamicLoading() {
        click(By.cssSelector("a[href='/dynamic_loading']"));
        return new DynamicLoadingPage(driver);
    }
}
