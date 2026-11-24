package com.gs1.assessment.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.gs1.assessment.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.blankOrNullString;

public final class MissingBookTest extends BooksTestBase {
    @DataProvider(name = "missingBooks")
    public Object[][] missingBooks() {
        JsonNode data = TestData.read("data/book-cases.json").required("missingBook");
        return new Object[][] {{data.required("id").asInt(), data.required("status").asInt(), data.required("title").asText()}};
    }

    @Test(groups = "api", dataProvider = "missingBooks", description = "Negative: a nonexistent book produces an explicit 404 problem")
    public void rejectsMissingBook(int id, int expectedStatus, String expectedTitle) {
        books.getBook(id).statusCode(expectedStatus).contentType("application/problem+json")
                .body("status", equalTo(expectedStatus)).body("title", equalTo(expectedTitle))
                .body("type", not(blankOrNullString())).body("traceId", not(blankOrNullString()));
    }
}
