package com.gs1.assessment.api;

import com.gs1.assessment.support.TestData;
import java.time.OffsetDateTime;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public final class CreateBookTest extends BooksTestBase {
    @DataProvider(name = "newBooks")
    public Object[][] newBooks() {
        return new Object[][] {{TestData.convert(TestData.read("data/book-cases.json").required("newBook"), Book.class)}};
    }

    @Test(groups = "api", dataProvider = "newBooks", description = "Happy path: create a book and verify all returned fields")
    public void createsBookResponse(Book expected) {
        Book actual = books.createBook(expected).statusCode(200).contentType("application/json")
                .body("id", equalTo(expected.id())).body("title", equalTo(expected.title()))
                .body("description", equalTo(expected.description())).body("pageCount", equalTo(expected.pageCount()))
                .body("excerpt", equalTo(expected.excerpt())).extract().as(Book.class);
        assertEquals(OffsetDateTime.parse(actual.publishDate()).toInstant(),
                OffsetDateTime.parse(expected.publishDate()).toInstant(), "Returned publication instant");
    }
}
