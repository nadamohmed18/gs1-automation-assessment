package com.gs1.assessment.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class UploadResultPage extends BasePage {
    public UploadResultPage(WebDriver driver) {
        super(driver);
        visible(By.id("uploaded-files"));
    }

    public String heading() { return visible(By.cssSelector("#content h3")).getText(); }
    public String uploadedFilename() { return visible(By.id("uploaded-files")).getText().trim(); }
}
