# Task 1 — GUI Test Automation

Standalone Java 17+ / Selenium / TestNG / Maven project. Requires Maven 3.9+, Chrome and internet access. No dependency on the API task or parent POM.

## Run

```powershell
cd ui-automation
mvn clean test

# Show Chrome instead of running headlessly
mvn test '-Dheadless=false'

# Visible demonstration: pause two seconds between narrated steps
mvn test '-Dheadless=false' '-Ddemo.pauseMillis=2000'

# Run just Dynamic Loading
mvn test '-Dtest=DynamicLoadingTest#waitsForDynamicExampleTwo'

# Run the file-upload test class only
mvn test '-Dtest=FileUploadTest'

# Override the explicit-wait and page-load timeout
mvn test '-Dui.timeoutSeconds=30'
```

Selenium Manager obtains a compatible ChromeDriver when needed. Each test uses a fresh temporary browser session, without your personal Chrome profile. Override the Chrome executable with `'-Dchrome.binary=C:\path\chrome.exe'` or provide a local driver with `'-Dwebdriver.chrome.driver=C:\path\chromedriver.exe'`.

## Scenarios

1. **File upload:** Open the home page, choose File Upload, upload `fixtures/upload-image.png`, submit, and assert `File Uploaded!` and the exact filename.
2. **Dynamic loading:** Open Dynamic Loading > Example 2, click Start, explicitly wait for completion, and assert `Hello World!`.

## Code explained

Each scenario is in its own Java file: `FileUploadTest.java` and
`DynamicLoadingTest.java`. Each file contains exactly one `@Test` method and
its corresponding data provider. Browser lifecycle and demonstration helpers
are shared through `UiTestBase`.

- `ui/FileUploadTest.java` and `ui/DynamicLoadingTest.java` each contain one scenario and its assertions. TestNG data providers load the external expectations and image resource from `data/ui-cases.json`.
- `ui/pages/` implements the Page Object Model. `HomePage` navigates to features; `FileUploadPage` chooses/submits a file; `UploadResultPage` reads the outcome; `DynamicLoadingPage` opens Example 2; `DynamicExamplePage` starts and waits for the result.
- `ui/pages/BasePage.java` centralizes waits for visibility and clickability. Locators stay inside page classes, keeping DOM changes localized.
- `ui/DriverFactory.java` configures Chrome and timeouts. `UiTestBase.java` creates a browser before each test, provides demonstration logging/pauses, attempts screenshots on failure, and quits it in teardown.
- `support/Config.java` loads defaults with JVM overrides. `TestData.java` reads JSON and resolves the fixture's absolute path.

Actions return the current/next page, giving fluent, readable test code:

```java
String text = new HomePage(driver).open().openDynamicLoading().openExampleTwo()
        .start().awaitCompletion().resultText();
assertEquals(text, expectedText);
```

`awaitCompletion()` waits for the rendered result and disappearance of the loading indicator. Synchronization uses explicit waits; implicit waits remain disabled. Optional `demo.pauseMillis` adds viewing time between narrated steps (0 by default, maximum 10000 ms). Those demonstration pauses do not replace element waits or affect normal runs.

Configuration in `src/test/resources/config.properties`: `ui.baseUrl`, `headless`, `ui.timeoutSeconds`. JVM `-D` properties override defaults; `chrome.binary` is optional. Test values live in JSON; locators are structural constants.

## Reports and troubleshooting

Open `target/surefire-reports/index.html` or `emailable-report.html`. XML/text results are alongside them. Failed tests attempt PNG screenshots in `target/screenshots/`. Tests run serially without retries, and Maven fails for assertions or setup errors.

Allow driver downloads, or supply a compatible local driver. `SE_CACHE_PATH` selects a writable driver cache. For public-service timeouts, inspect the failure and optionally increase `ui.timeoutSeconds`; outages remain failures. TLS verification is not disabled. Java 24 can print dependency/native-access warnings; check the final Maven summary for success.
