package com.globant.mobile.screens;

import com.globant.mobile.base.BaseScreen;
import com.globant.mobile.screens.components.TabBar;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

/**
 * Drag and drop section screen.
 */
public class DragScreen extends BaseScreen {

    private final By screen = AppiumBy.accessibilityId("Drag-drop-screen");
    private final By dragL1 = AppiumBy.accessibilityId("drag-l1");
    private final By dropL1 = AppiumBy.accessibilityId("drop-l1");
    private final By renewButton = AppiumBy.accessibilityId("renew");

    public DragScreen(AppiumDriver driver) {
        super(driver);
    }

    public TabBar getTabBar() {
        return new TabBar(driver);
    }

    public boolean isScreenDisplayed() {
        return isDisplayed(screen);
    }

    public boolean isDragItemDisplayed() {
        return isDisplayed(dragL1);
    }

    public boolean isDropZoneDisplayed() {
        return isDisplayed(dropL1);
    }

    public boolean isRenewDisplayed() {
        return isDisplayed(renewButton);
    }
}
