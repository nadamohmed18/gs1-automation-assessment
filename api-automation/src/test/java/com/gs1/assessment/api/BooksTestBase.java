package com.gs1.assessment.api;

import org.testng.annotations.BeforeClass;

public abstract class BooksTestBase {
    protected BooksService books;

    @BeforeClass(alwaysRun = true)
    public void configureService() { books = new BooksService(); }
}
