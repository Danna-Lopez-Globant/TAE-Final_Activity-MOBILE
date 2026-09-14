package com.globant.mobile.screens;

import com.globant.mobile.base.BaseScreen;
import com.globant.mobile.screens.components.TabBar;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

/**
 * Webview section for WDIO demo app v1.0.8: loads https://webdriver.io inside a native WebView
 * (there is no URL input / Go button in this APK version).
 */
public class WebviewScreen extends BaseScreen {

    private final By androidWebView = By.className("android.webkit.WebView");
    private final By loadingLabel = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"Loading\")");

    public WebviewScreen(AppiumDriver driver) {
        super(driver);
    }

    public TabBar getTabBar() {
        return new TabBar(driver);
    }

    public boolean isScreenDisplayed() {
        return isDisplayed(androidWebView) || isElementPresentQuick(loadingLabel);
    }

    public boolean isWebViewDisplayed() {
        return isDisplayed(androidWebView);
    }

    public boolean isLoadingOrContentDisplayed() {
        return isElementPresentQuick(loadingLabel) || isWebViewDisplayed();
    }
}
