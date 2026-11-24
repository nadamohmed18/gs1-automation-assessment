package com.gs1.assessment.support;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public final class Config {
    private static final Properties DEFAULTS = load();

    private Config() { }

    private static Properties load() {
        Properties properties = new Properties();
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) throw new IllegalStateException("Missing config.properties");
            properties.load(input);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot load configuration", exception);
        }
    }

    public static String get(String key) {
        String value = System.getProperty(key, DEFAULTS.getProperty(key));
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Missing configuration: " + key);
        return value;
    }

    public static Duration uiTimeout() {
        return Duration.ofSeconds(positiveInteger("ui.timeoutSeconds"));
    }

    public static int positiveInteger(String key) {
        int value = Integer.parseInt(get(key));
        if (value <= 0) throw new IllegalArgumentException(key + " must be positive");
        return value;
    }

    public static boolean headless() {
        String value = get("headless");
        if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
            throw new IllegalArgumentException("headless must be true or false");
        }
        return Boolean.parseBoolean(value);
    }
}
