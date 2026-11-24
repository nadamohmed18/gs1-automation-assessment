# Validation results

Latest full-suite run: **24 November 2026**, completed at **20:18:28 Africa/Cairo**.
The root Maven clean build contacted the live public UI and API services.

| Project | Passed | Failures | Errors | Skipped |
| --- | --- | --- | --- | --- |
| UI automation | 2 | 0 | 0 | 0 |
| API automation | 3 | 0 | 0 | 0 |
| **Total** | **5** | **0** | **0** | **0** |

Result: **BUILD SUCCESS**. Total time: **45.322 seconds**.

Environment: Windows, OpenJDK 24.0.1, Maven 3.9.9, headless Chrome,
Selenium Manager and the native TestNG provider. Source/bytecode targets Java 17;
JDK 17 was not independently exercised.

The tests included supported Duration-based API timeout configuration and
nonblank-string assertions. No Java source deprecation warnings were emitted.
Maven runtime/dependency-state and SLF4J provider warnings did not prevent success.

## Reproduce

```shell
mvn clean test
```

Per-project HTML and XML reports are generated under `target/surefire-reports/`.
UI screenshots are attempted on failures. Logs and generated reports are not
committed.

## Limits

- API coverage consists of two selected happy paths and one meaningful negative case.
- The fake API may simulate writes without persistent storage.
- Tests are serial and do not retry failures.
- Passing runs do not exercise every diagnostic failure path.
- Public service availability and responses may change.
