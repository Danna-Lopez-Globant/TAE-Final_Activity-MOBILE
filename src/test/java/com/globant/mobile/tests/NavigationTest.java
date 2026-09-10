package com.globant.mobile.tests;

import com.globant.mobile.base.BaseTest;
import com.globant.mobile.screens.DragScreen;
import com.globant.mobile.screens.FormsScreen;
import com.globant.mobile.screens.HomeScreen;
import com.globant.mobile.screens.LoginScreen;
import com.globant.mobile.screens.SwipeScreen;
import com.globant.mobile.screens.WebviewScreen;
import com.globant.mobile.screens.components.TabBar;
import org.testng.annotations.Test;

/**
 * Scenario 1: navigate through every bottom-menu section and validate key elements.
 */
public class NavigationTest extends BaseTest {

    @Test(description = "Navigate bottom menu and assert each section elements")
    public void bottomMenuNavigationShouldShowSectionElements() {
        HomeScreen homeScreen = getHomeScreen();
        TabBar tabBar = homeScreen.getTabBar();

        softAssert.assertTrue(tabBar.waitForTabBarShown(), "Tab bar should be visible on Home");
        softAssert.assertTrue(homeScreen.isScreenDisplayed(), "Home screen root should be visible");
        softAssert.assertTrue(homeScreen.isWebdriverLabelDisplayed(), "WEBDRIVER label should be visible");
        softAssert.assertTrue(homeScreen.isDescriptionDisplayed(), "Home description should be visible");
        softAssert.assertTrue(
                homeScreen.getDescriptionText().toLowerCase().contains("demo app"),
                "Home description should mention the demo app");

        WebviewScreen webviewScreen = tabBar.openWebview();
        softAssert.assertTrue(webviewScreen.isScreenDisplayed(), "Webview screen should be visible");
        softAssert.assertTrue(webviewScreen.isUrlInputDisplayed(), "Webview URL input should be visible");
        softAssert.assertTrue(webviewScreen.isGoButtonDisplayed(), "Webview Go button should be visible");

        LoginScreen loginScreen = webviewScreen.getTabBar().openLogin();
        softAssert.assertTrue(loginScreen.isScreenDisplayed(), "Login screen should be visible");
        softAssert.assertTrue(loginScreen.isLoginContainerDisplayed(), "Login container tab should be visible");
        softAssert.assertTrue(loginScreen.isSignUpContainerDisplayed(), "Sign up container tab should be visible");
        softAssert.assertTrue(loginScreen.isEmailInputDisplayed(), "Email input should be visible");
        softAssert.assertTrue(loginScreen.isPasswordInputDisplayed(), "Password input should be visible");

        FormsScreen formsScreen = loginScreen.getTabBar().openForms();
        softAssert.assertTrue(formsScreen.isScreenDisplayed(), "Forms screen should be visible");
        softAssert.assertTrue(formsScreen.isTextInputDisplayed(), "Forms text input should be visible");
        softAssert.assertTrue(formsScreen.isSwitchDisplayed(), "Forms switch should be visible");
        softAssert.assertTrue(formsScreen.isDropdownDisplayed(), "Forms dropdown should be visible");
        softAssert.assertTrue(formsScreen.isActiveButtonDisplayed(), "Active button should be visible");
        softAssert.assertTrue(formsScreen.isInactiveButtonDisplayed(), "Inactive button should be visible");
        softAssert.assertNotNull(formsScreen.getSwitchCheckedAttribute(), "Switch checked attribute should exist");

        SwipeScreen swipeScreen = formsScreen.getTabBar().openSwipe();
        softAssert.assertTrue(swipeScreen.isScreenDisplayed(), "Swipe screen should be visible");
        softAssert.assertTrue(swipeScreen.isSwipeTitleDisplayed(), "Swipe title should be visible");
        softAssert.assertTrue(swipeScreen.isCarouselDisplayed(), "Carousel should be visible");

        DragScreen dragScreen = swipeScreen.getTabBar().openDrag();
        softAssert.assertTrue(dragScreen.isScreenDisplayed(), "Drag screen should be visible");
        softAssert.assertTrue(dragScreen.isDragItemDisplayed(), "Drag item should be visible");
        softAssert.assertTrue(dragScreen.isDropZoneDisplayed(), "Drop zone should be visible");

        HomeScreen backHome = dragScreen.getTabBar().openHome();
        softAssert.assertTrue(backHome.isScreenDisplayed(), "Returning to Home should show Home screen");
    }
}
