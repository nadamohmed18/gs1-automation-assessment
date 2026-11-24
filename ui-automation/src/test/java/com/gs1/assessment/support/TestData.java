package com.gs1.assessment.support;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public final class TestData {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private TestData() { }

    public static JsonNode read(String resource) {
        try (InputStream input = TestData.class.getClassLoader().getResourceAsStream(resource)) {
            if (input == null) throw new IllegalArgumentException("Missing test data: " + resource);
            return MAPPER.readTree(input);
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot read test data: " + resource, exception);
        }
    }

    public static <T> T convert(JsonNode node, Class<T> type) {
        return MAPPER.convertValue(node, type);
    }

    public static Path resourcePath(String resource) {
        URL location = TestData.class.getClassLoader().getResource(resource);
        if (location == null) throw new IllegalArgumentException("Missing fixture: " + resource);
        try {
            return Path.of(location.toURI()).toAbsolutePath();
        } catch (URISyntaxException exception) {
            throw new IllegalStateException("Invalid fixture path: " + resource, exception);
        }
    }
}
