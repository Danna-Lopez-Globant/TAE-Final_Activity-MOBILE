package com.globant.mobile.screens.components;

import com.globant.mobile.base.BaseScreen;
import com.globant.mobile.screens.DragScreen;
import com.globant.mobile.screens.FormsScreen;
import com.globant.mobile.screens.HomeScreen;
import com.globant.mobile.screens.LoginScreen;
import com.globant.mobile.screens.SwipeScreen;
import com.globant.mobile.screens.WebviewScreen;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

/**
 * Bottom navigation bar shared across demo app sections.
 */
public class TabBar extends BaseScreen {

    private final By homeTab = AppiumBy.accessibilityId("Home");
    private final By webviewTab = AppiumBy.accessibilityId("Webview");
    private final By loginTab = AppiumBy.accessibilityId("Login");
    private final By formsTab = AppiumBy.accessibilityId("Forms");
    private final By swipeTab = AppiumBy.accessibilityId("Swipe");
    private final By dragTab = AppiumBy.accessibilityId("Drag");

    public TabBar(AppiumDriver driver) {
        super(driver);
    }

    /**
     * Waits until the bottom tab bar is ready.
     *
     * @return {@code true} when the Home tab is visible
     */
    public boolean waitForTabBarShown() {
        return waitForVisibility(homeTab);
    }

    /**
     * @return whether the Home tab icon is displayed
     */
    public boolean isHomeTabDisplayed() {
        return isDisplayed(homeTab);
    }

    /**
     * @return whether the Webview tab icon is displayed
     */
    public boolean isWebviewTabDisplayed() {
        return isDisplayed(webviewTab);
    }

    /**
     * @return whether the Login tab icon is displayed
     */
    public boolean isLoginTabDisplayed() {
        return isDisplayed(loginTab);
    }

    /**
     * @return whether the Forms tab icon is displayed
     */
    public boolean isFormsTabDisplayed() {
        return isDisplayed(formsTab);
    }

    /**
     * @return whether the Swipe tab icon is displayed
     */
    public boolean isSwipeTabDisplayed() {
        return isDisplayed(swipeTab);
    }

    /**
     * @return whether the Drag tab icon is displayed
     */
    public boolean isDragTabDisplayed() {
        return isDisplayed(dragTab);
    }

    /**
     * Opens the Home section.
     *
     * @return Home screen
     */
    public HomeScreen openHome() {
        click(homeTab, "Opening Home tab");
        return new HomeScreen(driver);
    }

    /**
     * Opens the Webview section.
     *
     * @return Webview screen
     */
    public WebviewScreen openWebview() {
        click(webviewTab, "Opening Webview tab");
        return new WebviewScreen(driver);
    }

    /**
     * Opens the Login section.
     *
     * @return Login screen
     */
    public LoginScreen openLogin() {
        click(loginTab, "Opening Login tab");
        return new LoginScreen(driver);
    }

    /**
     * Opens the Forms section.
     *
     * @return Forms screen
     */
    public FormsScreen openForms() {
        click(formsTab, "Opening Forms tab");
        return new FormsScreen(driver);
    }

    /**
     * Opens the Swipe section.
     *
     * @return Swipe screen
     */
    public SwipeScreen openSwipe() {
        click(swipeTab, "Opening Swipe tab");
        return new SwipeScreen(driver);
    }

    /**
     * Opens the Drag section.
     *
     * @return Drag screen
     */
    public DragScreen openDrag() {
        click(dragTab, "Opening Drag tab");
        return new DragScreen(driver);
    }
}
