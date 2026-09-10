package com.globant.mobile.tests;

import com.globant.mobile.base.BaseTest;
import com.globant.mobile.screens.HomeScreen;
import com.globant.mobile.screens.LoginScreen;
import com.globant.mobile.screens.components.NativeAlert;
import com.globant.mobile.utils.RandomData;
import com.globant.mobile.utils.TestData;
import org.testng.annotations.Test;

/**
 * Scenario 3: successful Login. Creates its own user in-test so it does not depend on SignUpTest order.
 */
public class LoginTest extends BaseTest {

    @Test(description = "Login successfully with a previously created user")
    public void successfulLogin() {
        String email = RandomData.randomEmail();
        String password = TestData.getUserPassword();

        HomeScreen homeScreen = getHomeScreen();
        softAssert.assertTrue(homeScreen.getTabBar().waitForTabBarShown(), "Tab bar should be ready");

        LoginScreen loginScreen = homeScreen.getTabBar().openLogin();
        softAssert.assertTrue(loginScreen.isScreenDisplayed(), "Login section should be visible");

        // Independent precondition: create the account inside this test (reuses signup screen methods).
        loginScreen.createUser(email, password);

        NativeAlert alert = loginScreen.login(email, password);
        softAssert.assertTrue(alert.waitForIsShown(), "Login success alert should appear");
        softAssert.assertTrue(
                alert.getFullText().toLowerCase().contains("success"),
                "Alert should confirm successful login. Actual: " + alert.getFullText());

        alert.tapButton("OK");
    }
}
