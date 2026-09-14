package com.globant.mobile.screens.components;

import com.globant.mobile.base.BaseScreen;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

/**
 * Native Android success / error alert shown after login or signup.
 */
public class NativeAlert extends BaseScreen {

    /** Resource-id used by the WDIO demo app AlertDialog title (camelCase). */
    private final By alertTitle = By.id("com.wdiodemoapp:id/alertTitle");
    private final By alertTitleFramework = By.id("android:id/alertTitle");
    private final By alertMessage = By.id("android:id/message");
    private final By successTitle = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"Success\")");
    private final By signedUpTitle = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"Signed Up\")");

    public NativeAlert(AppiumDriver driver) {
        super(driver);
    }

    /**
     * Waits until any known alert title variant is visible.
     *
     * @return {@code true} when the alert is shown
     */
    public boolean waitForIsShown() {
        return waitForVisibility(alertTitle)
                || waitForVisibility(alertTitleFramework)
                || waitForVisibility(successTitle)
                || waitForVisibility(signedUpTitle);
    }

    /**
     * @return locator of the visible alert title
     */
    private By resolveTitleLocator() {
        if (isElementPresentQuick(alertTitle)) {
            return alertTitle;
        }
        if (isElementPresentQuick(alertTitleFramework)) {
            return alertTitleFramework;
        }
        if (isElementPresentQuick(successTitle)) {
            return successTitle;
        }
        return signedUpTitle;
    }

    /**
     * @return alert title text
     */
    public String getTitle() {
        return getText(resolveTitleLocator());
    }

    /**
     * @return alert message body when present; empty string otherwise
     */
    public String getMessage() {
        if (isElementPresentQuick(alertMessage)) {
            return getText(alertMessage);
        }
        return "";
    }

    /**
     * Combined title and message, matching cross-platform boilerplate expectations.
     *
     * @return title and message separated by a new line
     */
    public String getFullText() {
        String message = getMessage();
        if (message.isEmpty()) {
            return getTitle();
        }
        return getTitle() + "\n" + message;
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
        waitForInvisibility(resolveTitleLocator());
    }
}
