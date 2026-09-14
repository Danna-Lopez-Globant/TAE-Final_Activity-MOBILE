package com.globant.mobile.screens;

import com.globant.mobile.base.BaseScreen;
import com.globant.mobile.screens.components.NativeAlert;
import com.globant.mobile.screens.components.TabBar;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

/**
 * Login / Sign Up screen. Credentials are always received as method parameters.
 * Submit buttons use {@code accessible=false}, so they are clicked by visible text.
 */
public class LoginScreen extends BaseScreen {

    private final By screen = AppiumBy.accessibilityId("Login-screen");
    private final By loginContainerButton = AppiumBy.accessibilityId("button-login-container");
    private final By signUpContainerButton = AppiumBy.accessibilityId("button-sign-up-container");
    private final By emailInput = AppiumBy.accessibilityId("input-email");
    private final By passwordInput = AppiumBy.accessibilityId("input-password");
    private final By repeatPasswordInput = AppiumBy.accessibilityId("input-repeat-password");
    private final By formTitle = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"Login / Sign up\")");

    public LoginScreen(AppiumDriver driver) {
        super(driver);
    }

    public TabBar getTabBar() {
        return new TabBar(driver);
    }

    public boolean isScreenDisplayed() {
        return isDisplayed(screen) || isDisplayed(formTitle);
    }

    public boolean isLoginContainerDisplayed() {
        return isDisplayed(loginContainerButton)
                || isElementPresentQuick(AppiumBy.androidUIAutomator("new UiSelector().text(\"Login\")"));
    }

    public boolean isSignUpContainerDisplayed() {
        return isDisplayed(signUpContainerButton)
                || isElementPresentQuick(AppiumBy.androidUIAutomator("new UiSelector().text(\"Sign up\")"));
    }

    public boolean isEmailInputDisplayed() {
        return isDisplayed(emailInput);
    }

    public boolean isPasswordInputDisplayed() {
        return isDisplayed(passwordInput);
    }

    public LoginScreen openLoginForm() {
        click(loginContainerButton, "Opening Login form container");
        return this;
    }

    public LoginScreen openSignUpForm() {
        click(signUpContainerButton, "Opening Sign Up form container");
        return this;
    }

    public NativeAlert signUp(String email, String password) {
        sendKeys(emailInput, email, "Typing signup email");
        sendKeys(passwordInput, password, "Typing signup password");
        sendKeys(repeatPasswordInput, password, "Typing signup confirm password");
        dismissKeyboardSafely();
        clickByVisibleText("SIGN UP", "Submitting Sign Up form");
        NativeAlert alert = new NativeAlert(driver);
        alert.waitForIsShown();
        return alert;
    }

    public NativeAlert login(String email, String password) {
        sendKeys(emailInput, email, "Typing login email");
        sendKeys(passwordInput, password, "Typing login password");
        dismissKeyboardSafely();
        clickByVisibleText("LOGIN", "Submitting Login form");
        NativeAlert alert = new NativeAlert(driver);
        alert.waitForIsShown();
        return alert;
    }

    public LoginScreen createUser(String email, String password) {
        openSignUpForm();
        NativeAlert alert = signUp(email, password);
        alert.tapButton("OK");
        openLoginForm();
        return this;
    }
}
