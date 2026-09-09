package com.globant.mobile.screens;

import com.globant.mobile.base.BaseScreen;
import com.globant.mobile.screens.components.TabBar;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

/**
 * Webview section screen.
 */
public class WebviewScreen extends BaseScreen {

    private final By screen = AppiumBy.accessibilityId("Webview-screen");
    private final By urlInput = AppiumBy.accessibilityId("input-url");
    private final By goButton = AppiumBy.accessibilityId("button-Go");

    public WebviewScreen(AppiumDriver driver) {
        super(driver);
    }

    public TabBar getTabBar() {
        return new TabBar(driver);
    }

    public boolean isScreenDisplayed() {
        return isDisplayed(screen);
    }

    public boolean isUrlInputDisplayed() {
        return isDisplayed(urlInput);
    }

    public boolean isGoButtonDisplayed() {
        return isDisplayed(goButton);
    }
}
