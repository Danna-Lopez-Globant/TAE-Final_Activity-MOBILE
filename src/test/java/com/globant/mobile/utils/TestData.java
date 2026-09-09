package com.globant.mobile.utils;

/**
 * Typed accessors for test configuration values.
 */
public final class TestData {

    private TestData() {
    }

    public static String getAppiumServerUrl() {
        return ConfigReader.get("appium.server.url");
    }

    public static String getPlatformName() {
        return ConfigReader.get("platform.name");
    }

    public static String getDeviceName() {
        return ConfigReader.get("device.name");
    }

    public static String getAutomationName() {
        return ConfigReader.get("automation.name");
    }

    public static String getAppPackage() {
        return ConfigReader.get("app.package");
    }

    public static String getAppActivity() {
        return ConfigReader.get("app.activity");
    }

    public static String getAppPath() {
        return ConfigReader.get("app.path");
    }

    public static int getImplicitWaitSeconds() {
        return ConfigReader.getInt("implicit.wait");
    }

    public static int getExplicitWaitSeconds() {
        return ConfigReader.getInt("explicit.wait");
    }

    public static String getUserPassword() {
        return ConfigReader.get("user.password");
    }
}
