# Task 2 — API Test Automation

Standalone Java 17+ / REST Assured / TestNG / Maven project. Requires Maven 3.9+ and internet access. No browser, UI task or parent POM is required.

## Run

```powershell
cd api-automation
mvn clean test

# Show full requests and responses for learning/debugging
mvn test '-Dapi.logAll=true'

# Run only the negative scenario
mvn test '-Dtest=MissingBookTest#rejectsMissingBook'

# Run either happy-path class independently
mvn test '-Dtest=GetBookTest'
mvn test '-Dtest=CreateBookTest'

# Override connection/read timeouts
mvn test '-Dapi.timeoutMillis=30000'
```

## Scenarios

| Scenario | Request | Assertions |
| --- | --- | --- |
| Existing book | GET `/api/v1/Books/1` | 200, JSON, ID/title/page count, nonempty description/excerpt and valid timestamp. |
| Creation response | POST `/api/v1/Books` | 200, JSON and every submitted field, comparing timestamp instants. |
| Missing book | GET `/api/v1/Books/0` | 404, problem JSON, status/title and nonempty type/trace ID. |

These independent scenarios meet the PDF requirement for two happy paths and one meaningful negative/edge case. They do not claim CRUD coverage of every endpoint.

## Code explained

Each scenario is in its own Java file: `GetBookTest.java`, `CreateBookTest.java`
and `MissingBookTest.java`. Each has exactly one `@Test` method and its own
data provider. `BooksTestBase` provides shared service initialization.

- `api/GetBookTest.java`, `api/CreateBookTest.java` and `api/MissingBookTest.java` each contain one scenario, its data provider and assertions. `BooksTestBase.java` initializes the shared service for each test class.
- `api/BooksService.java` implements the Service Object Model: base URI, headers, endpoint paths, timeouts and reusable GET/POST methods. It returns REST Assured's fluent `ValidatableResponse` without changing global REST Assured state.

Timeouts use REST Assured's supported `connectTimeout(Duration)` and
`readTimeout(Duration)` settings. The `api.timeoutMillis` configuration value
is converted with `Duration.ofMillis`; legacy `setParam` calls are not used.
- `api/Book.java` is an immutable Java record matching the six JSON fields. Jackson handles serialization and deserialization.
- `support/Config.java` reads `config.properties` with JVM `-D` overrides. Supported keys: `api.baseUrl`, `api.timeoutMillis`.
- `support/TestData.java` reads `data/book-cases.json` and maps the creation payload to `Book`. IDs, payloads and expected values live outside Java test code.

The negative test checks HTTP and error-body semantics:

```java
books.getBook(id).statusCode(expectedStatus)
        .contentType("application/problem+json")
        .body("status", equalTo(expectedStatus))
        .body("title", equalTo(expectedTitle));
```

It also checks `type` and `traceId`, so a bare 404 without meaningful problem details does not pass. The `description`, `excerpt`, `type` and `traceId` assertions use `not(blankOrNullString())` to reject null, empty and whitespace-only strings, including spaces, tabs and line breaks.

The fake service can simulate writes without saving them. The creation test validates its response and does not depend on a subsequent GET or cleanup. Existing-book publication dates are generated dynamically, so the test validates timestamp format rather than a changing literal.

## Reports and troubleshooting

Open `target/surefire-reports/index.html` or `emailable-report.html`; XML/text results are in the same folder. HTTP details are logged on validation failures. Cases have no execution-order dependencies or automatic retries. Failures make Maven exit with a nonzero status.

Check public-service access for connection errors. For TLS failures, configure the trusted CA in the JDK; certificate verification remains enabled. Java 24 may print dependency/native-access warnings; use the final Maven summary to assess success.
