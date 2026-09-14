package com.globant.mobile.tests;

import com.globant.mobile.base.BaseTest;
import com.globant.mobile.screens.HomeScreen;
import com.globant.mobile.screens.LoginScreen;
import com.globant.mobile.screens.components.NativeAlert;
import com.globant.mobile.utils.RandomData;
import com.globant.mobile.utils.TestData;
import org.testng.annotations.Test;

/**
 * Scenario 2: successful Sign Up with a unique email so the test is repeatable.
 */
public class SignUpTest extends BaseTest {

    @Test(description = "Sign up successfully with a random email")
    public void successfulSignUp() {
        String email = RandomData.randomEmail();
        String password = TestData.getUserPassword();

        HomeScreen homeScreen = getHomeScreen();
        softAssert.assertTrue(homeScreen.getTabBar().waitForTabBarShown(), "Tab bar should be ready");

        LoginScreen loginScreen = homeScreen.getTabBar().openLogin();
        softAssert.assertTrue(loginScreen.isScreenDisplayed(), "Login section should be visible");

        loginScreen.openSignUpForm();
        softAssert.assertTrue(loginScreen.isEmailInputDisplayed(), "Signup email field should be visible");
        softAssert.assertTrue(loginScreen.isPasswordInputDisplayed(), "Signup password field should be visible");

        NativeAlert alert = loginScreen.signUp(email, password);
        boolean shown = alert.waitForIsShown();
        softAssert.assertTrue(shown, "Signup success alert should appear");
        if (shown) {
            softAssert.assertTrue(
                    alert.getFullText().toLowerCase().contains("signed up"),
                    "Alert should confirm signed up. Actual: " + alert.getFullText());
            alert.tapButton("OK");
        }
    }
}
