package com.globant.mobile.screens.components;

import com.globant.mobile.base.BaseScreen;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

/**
 * Native Android success / error alert shown after login or signup.
 */
public class NativeAlert extends BaseScreen {

    private final By alertTitle = By.id("com.wdiodemoapp:id/alert_title");
    private final By alertMessage = By.id("android:id/message");

    public NativeAlert(AppiumDriver driver) {
        super(driver);
    }

    /**
     * Waits until the native alert title is visible.
     *
     * @return {@code true} when the alert is shown
     */
    public boolean waitForIsShown() {
        return waitForVisibility(alertTitle);
    }

    /**
     * @return alert title text
     */
    public String getTitle() {
        return getText(alertTitle);
    }

    /**
     * @return alert message body
     */
    public String getMessage() {
        return getText(alertMessage);
    }

    /**
     * Combined title and message, matching cross-platform boilerplate expectations.
     *
     * @return title and message separated by a new line
     */
    public String getFullText() {
        return getTitle() + "\n" + getMessage();
    }

    /**
     * Taps an alert button by its visible text (Android uses uppercase labels).
     *
     * @param buttonText visible button label, e.g. {@code OK}
     */
    public void tapButton(String buttonText) {
        By button = AppiumBy.androidUIAutomator(
                "new UiSelector().className(\"android.widget.Button\").text(\""
                        + buttonText.toUpperCase() + "\")");
        click(button, "Tapping alert button: " + buttonText);
        waitForInvisibility(alertTitle);
    }
}
