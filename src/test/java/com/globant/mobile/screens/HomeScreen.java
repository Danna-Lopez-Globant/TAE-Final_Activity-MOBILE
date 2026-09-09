package com.globant.mobile.screens;

import com.globant.mobile.base.BaseScreen;
import com.globant.mobile.screens.components.TabBar;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

/**
 * Home (landing) screen of the WDIO native demo app.
 */
public class HomeScreen extends BaseScreen {

    private final By screen = AppiumBy.accessibilityId("Home-screen");
    private final By webdriverLabel = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"WEBDRIVER\")");
    private final By descriptionLabel = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"Demo app\")");

    public HomeScreen(AppiumDriver driver) {
        super(driver);
    }

    /**
     * @return shared bottom tab bar for navigation
     */
    public TabBar getTabBar() {
        return new TabBar(driver);
    }

    /**
     * @return {@code true} when the Home screen root is visible
     */
    public boolean isScreenDisplayed() {
        return isDisplayed(screen);
    }

    /**
     * @return {@code true} when the WEBDRIVER brand label is visible
     */
    public boolean isWebdriverLabelDisplayed() {
        return isDisplayed(webdriverLabel);
    }

    /**
     * @return {@code true} when the demo app description is visible
     */
    public boolean isDescriptionDisplayed() {
        return isDisplayed(descriptionLabel);
    }

    /**
     * @return description label text
     */
    public String getDescriptionText() {
        return getText(descriptionLabel);
    }
}
