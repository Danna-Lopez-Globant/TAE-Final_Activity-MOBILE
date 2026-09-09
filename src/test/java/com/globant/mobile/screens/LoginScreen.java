package com.globant.mobile.screens;

import com.globant.mobile.base.BaseScreen;
import com.globant.mobile.screens.components.TabBar;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

/**
 * Login / Sign Up screen. Credentials are always received as method parameters.
 */
public class LoginScreen extends BaseScreen {

    private final By screen = AppiumBy.accessibilityId("Login-screen");
    private final By loginContainerButton = AppiumBy.accessibilityId("button-login-container");
    private final By signUpContainerButton = AppiumBy.accessibilityId("button-sign-up-container");
    private final By emailInput = AppiumBy.accessibilityId("input-email");
    private final By passwordInput = AppiumBy.accessibilityId("input-password");
    private final By repeatPasswordInput = AppiumBy.accessibilityId("input-repeat-password");
    private final By loginButton = AppiumBy.accessibilityId("button-LOGIN");
    private final By signUpButton = AppiumBy.accessibilityId("button-SIGN UP");

    public LoginScreen(AppiumDriver driver) {
        super(driver);
    }

    /**
     * @return shared bottom tab bar
     */
    public TabBar getTabBar() {
        return new TabBar(driver);
    }

    /**
     * @return {@code true} when the Login screen root is visible
     */
    public boolean isScreenDisplayed() {
        return isDisplayed(screen);
    }

    /**
     * @return {@code true} when the Login container tab is visible
     */
    public boolean isLoginContainerDisplayed() {
        return isDisplayed(loginContainerButton);
    }

    /**
     * @return {@code true} when the Sign up container tab is visible
     */
    public boolean isSignUpContainerDisplayed() {
        return isDisplayed(signUpContainerButton);
    }

    /**
     * @return {@code true} when the email field is visible
     */
    public boolean isEmailInputDisplayed() {
        return isDisplayed(emailInput);
    }

    /**
     * @return {@code true} when the password field is visible
     */
    public boolean isPasswordInputDisplayed() {
        return isDisplayed(passwordInput);
    }

    /**
     * Switches to the Login form container.
     *
     * @return this screen
     */
    public LoginScreen openLoginForm() {
        click(loginContainerButton, "Opening Login form container");
        return this;
    }

    /**
     * Switches to the Sign Up form container.
     *
     * @return this screen
     */
    public LoginScreen openSignUpForm() {
        click(signUpContainerButton, "Opening Sign Up form container");
        return this;
    }
}
