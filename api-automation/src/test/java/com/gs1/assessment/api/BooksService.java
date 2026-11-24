package com.gs1.assessment.api;

import com.gs1.assessment.support.Config;
import java.time.Duration;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

/** Owns HTTP configuration and endpoint paths; test classes own assertions. */
public final class BooksService {
    private static final String BOOKS = "/api/v1/Books";
    private final RequestSpecification specification;

    public BooksService() {
        Duration timeout = Duration.ofMillis(Config.positiveInteger("api.timeoutMillis"));
        specification = new RequestSpecBuilder().setBaseUri(Config.get("api.baseUrl"))
                .setAccept(ContentType.JSON).setContentType(ContentType.JSON)
                .setConfig(RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                        .connectTimeout(timeout).readTimeout(timeout)))
                .build();
    }

    public ValidatableResponse getBook(int id) {
        return validate(request().pathParam("id", id).get(BOOKS + "/{id}"));
    }

    public ValidatableResponse createBook(Book book) {
        return validate(request().body(book).post(BOOKS));
    }

    private RequestSpecification request() {
        RequestSpecification request = given().spec(specification).log().ifValidationFails();
        if (Boolean.getBoolean("api.logAll")) request.log().all();
        return request;
    }

    private ValidatableResponse validate(Response response) {
        ValidatableResponse result = response.then();
        if (Boolean.getBoolean("api.logAll")) result.log().all();
        return result.log().ifValidationFails(LogDetail.ALL);
    }
}
