# GS1 Automation Assessment

Two independent Java automation projects, with one test scenario per Java file.

| Project | Stack | Coverage |
| --- | --- | --- |
| `ui-automation` | Selenium WebDriver, TestNG, Maven | File upload and Dynamic Loading Example 2 |
| `api-automation` | REST Assured, TestNG, Maven | Existing book, creation response, missing-book negative case |

## Run

Requirements: JDK 17+, Maven 3.9+, internet access, and Chrome for UI tests.

Run both projects from the repository root:

```shell
mvn clean test
```

Run either project independently:

```shell
mvn -f ui-automation/pom.xml clean test
mvn -f api-automation/pom.xml clean test
```

For a visible UI demonstration with two-second pauses:

```shell
mvn -f ui-automation/pom.xml test -Dheadless=false -Ddemo.pauseMillis=2000
```

For full API request/response logging:

```shell
mvn -f api-automation/pom.xml test -Dapi.logAll=true
```

Selenium Manager obtains a compatible ChromeDriver. Test data and environment
configuration are externalized under each project's `src/test/resources`.
See the [UI guide](ui-automation/README.md) and [API guide](api-automation/README.md)
for code explanations, configuration overrides and individual-test commands.

## Design

- UI uses Page Object Model, fluent actions, explicit waits and a fresh browser per test.
- API uses reusable service objects, immutable book payloads and meaningful response assertions.
- Required string fields reject null, empty and whitespace-only values.
- FakeRESTApi creation is checked through its response without assuming persistence.
- Failures remain visible: no automatic retries or skipped network failures.

## Reports

Each project generates `target/surefire-reports/index.html`,
`emailable-report.html` and XML results. UI failure screenshots are attempted
under `ui-automation/target/screenshots/`. Generated reports, logs and caches
are excluded from Git.

Latest live verification: **24 November 2026**, **5 passed**, zero failures,
errors or skips. See [VALIDATION.md](VALIDATION.md).

## Target applications

- [The Internet UI](https://the-internet.herokuapp.com/)
- [FakeRESTApi Books OpenAPI](https://fakerestapi.azurewebsites.net/swagger/v1/swagger.json)
