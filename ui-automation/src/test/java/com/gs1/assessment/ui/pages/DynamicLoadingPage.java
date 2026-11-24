package com.gs1.assessment.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class DynamicLoadingPage extends BasePage {
    public DynamicLoadingPage(WebDriver driver) { super(driver); }

    public DynamicExamplePage openExampleTwo() {
        click(By.cssSelector("a[href='/dynamic_loading/2']"));
        return new DynamicExamplePage(driver);
    }
}
