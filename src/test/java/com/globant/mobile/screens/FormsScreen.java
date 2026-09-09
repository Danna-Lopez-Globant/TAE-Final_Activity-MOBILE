package com.globant.mobile.screens;

import com.globant.mobile.base.BaseScreen;
import com.globant.mobile.screens.components.TabBar;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

/**
 * Forms screen with input, switch and buttons.
 */
public class FormsScreen extends BaseScreen {

    private final By screen = AppiumBy.accessibilityId("Forms-screen");
    private final By textInput = AppiumBy.accessibilityId("text-input");
    private final By inputResult = AppiumBy.accessibilityId("input-text-result");
    private final By switchElement = AppiumBy.accessibilityId("switch");
    private final By switchText = AppiumBy.accessibilityId("switch-text");
    private final By dropdown = AppiumBy.accessibilityId("Dropdown");
    private final By activeButton = AppiumBy.accessibilityId("button-Active");
    private final By inactiveButton = AppiumBy.accessibilityId("button-Inactive");

    public FormsScreen(AppiumDriver driver) {
        super(driver);
    }

    public TabBar getTabBar() {
        return new TabBar(driver);
    }

    public boolean isScreenDisplayed() {
        return isDisplayed(screen);
    }

    public boolean isTextInputDisplayed() {
        return isDisplayed(textInput);
    }

    public boolean isInputResultDisplayed() {
        return isDisplayed(inputResult);
    }

    public boolean isSwitchDisplayed() {
        return isDisplayed(switchElement);
    }

    public boolean isSwitchTextDisplayed() {
        return isDisplayed(switchText);
    }

    public boolean isDropdownDisplayed() {
        return isDisplayed(dropdown);
    }

    public boolean isActiveButtonDisplayed() {
        return isDisplayed(activeButton);
    }

    public boolean isInactiveButtonDisplayed() {
        return isDisplayed(inactiveButton);
    }

    /**
     * @return switch {@code checked} attribute value
     */
    public String getSwitchCheckedAttribute() {
        return getAttribute(switchElement, "checked");
    }
}
