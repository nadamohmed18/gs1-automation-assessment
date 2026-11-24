package com.gs1.assessment.ui;

import com.fasterxml.jackson.databind.JsonNode;
import com.gs1.assessment.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.gs1.assessment.ui.pages.HomePage;
import com.gs1.assessment.ui.pages.FileUploadPage;
import com.gs1.assessment.ui.pages.UploadResultPage;
import java.nio.file.Path;
import static org.testng.Assert.assertEquals;

public final class FileUploadTest extends UiTestBase {
    @DataProvider(name = "upload")
    public Object[][] uploadData() {
        JsonNode data = TestData.read("data/ui-cases.json").required("upload");
        return new Object[][] {{TestData.resourcePath(data.required("resource").asText()),
                data.required("successHeading").asText()}};
    }

    @Test(groups = "ui", dataProvider = "upload", description = "Upload a bundled image and verify success and filename")
    public void uploadsSmallImage(Path image, String expectedHeading) {
        HomePage home = new HomePage(driver).open();
        demonstrate("Opened the home page. Next: select File Upload.");
        FileUploadPage upload = home.openFileUpload();
        demonstrate("Opened File Upload. Next: choose the bundled image.");
        upload.chooseFile(image);
        demonstrate("Selected " + image.getFileName() + ". Next: click Upload.");
        UploadResultPage result = upload.submit();
        demonstrate("Submitted the file. Next: verify the heading and uploaded filename.");
        assertEquals(result.heading(), expectedHeading, "Upload success heading");
        assertEquals(result.uploadedFilename(), image.getFileName().toString(), "Uploaded filename");
        demonstrate("PASS: success heading and uploaded filename match. Chrome will close.");
    }
}
