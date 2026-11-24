package com.gs1.assessment.support;

import java.io.IOException;
import java.io.InputStream;
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

    public static int positiveInteger(String key) {
        int value = Integer.parseInt(get(key));
        if (value <= 0) throw new IllegalArgumentException(key + " must be positive");
        return value;
    }

}
