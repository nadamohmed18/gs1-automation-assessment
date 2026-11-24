package com.gs1.assessment.ui.pages;

import java.nio.file.Files;
import java.nio.file.Path;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class FileUploadPage extends BasePage {
    private static final By FILE_INPUT = By.id("file-upload");
    private static final By SUBMIT = By.id("file-submit");

    public FileUploadPage(WebDriver driver) {
        super(driver);
        visible(FILE_INPUT);
    }

    public FileUploadPage chooseFile(Path file) {
        if (!Files.isRegularFile(file)) throw new IllegalArgumentException("Upload file does not exist: " + file);
        visible(FILE_INPUT).sendKeys(file.toAbsolutePath().toString());
        return this;
    }

    public UploadResultPage submit() {
        click(SUBMIT);
        return new UploadResultPage(driver);
    }
}
