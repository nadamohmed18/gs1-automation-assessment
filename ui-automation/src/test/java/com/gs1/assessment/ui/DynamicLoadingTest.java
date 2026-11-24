package com.gs1.assessment.ui;

import com.gs1.assessment.support.TestData;
import com.gs1.assessment.ui.pages.HomePage;
import com.gs1.assessment.ui.pages.DynamicLoadingPage;
import com.gs1.assessment.ui.pages.DynamicExamplePage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public final class DynamicLoadingTest extends UiTestBase {
    @DataProvider(name = "dynamicLoading")
    public Object[][] dynamicLoadingData() {
        return new Object[][] {{TestData.read("data/ui-cases.json")
                .required("dynamicLoading").required("expectedText").asText()}};
    }

    @Test(groups = "ui", dataProvider = "dynamicLoading", description = "Wait for dynamically rendered Example 2 text")
    public void waitsForDynamicExampleTwo(String expectedText) {
        HomePage home = new HomePage(driver).open();
        demonstrate("Opened the home page. Next: select Dynamic Loading.");
        DynamicLoadingPage loading = home.openDynamicLoading();
        demonstrate("Opened Dynamic Loading. Next: select Example 2.");
        DynamicExamplePage example = loading.openExampleTwo();
        demonstrate("Opened Example 2. Next: click Start.");
        example.start();
        demonstrate("Clicked Start. The explicit wait now waits for the result to appear.");
        String text = example.awaitCompletion().resultText();
        demonstrate("Loading completed. Next: assert the text equals " + expectedText);
        assertEquals(text, expectedText, "Dynamic loading completion text");
        demonstrate("PASS: dynamic text matches. Chrome will close.");
    }
}
