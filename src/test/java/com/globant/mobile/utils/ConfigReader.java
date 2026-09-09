package com.globant.mobile.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads classpath configuration from {@code config.properties}.
 */
public final class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                throw new IllegalStateException("config.properties was not found on the classpath");
            }
            PROPERTIES.load(inputStream);
        } catch (IOException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }

    private ConfigReader() {
    }

    /**
     * Returns a required property value.
     *
     * @param key property key
     * @return trimmed property value
     */
    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing required property: " + key);
        }
        return value.trim();
    }

    /**
     * Returns a required integer property value.
     *
     * @param key property key
     * @return parsed integer
     */
    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
