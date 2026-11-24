package com.gs1.assessment.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public final class DynamicExamplePage extends BasePage {
    private static final By START = By.cssSelector("#start button");
    private static final By LOADING = By.id("loading");
    private static final By RESULT = By.cssSelector("#finish h4");

    public DynamicExamplePage(WebDriver driver) {
        super(driver);
        visible(START);
    }

    public DynamicExamplePage start() {
        click(START);
        return this;
    }

    public DynamicExamplePage awaitCompletion() {
        visible(RESULT);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(LOADING));
        return this;
    }

    public String resultText() { return visible(RESULT).getText(); }
}
