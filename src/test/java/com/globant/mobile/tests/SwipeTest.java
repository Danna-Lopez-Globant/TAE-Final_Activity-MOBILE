package com.globant.mobile.tests;

import com.globant.mobile.base.BaseTest;
import com.globant.mobile.screens.HomeScreen;
import com.globant.mobile.screens.SwipeScreen;
import org.testng.annotations.Test;

/**
 * Scenario 4: horizontal card swipes and vertical scroll to the hidden message.
 */
public class SwipeTest extends BaseTest {

    @Test(description = "Swipe cards horizontally and find You found me!!! vertically")
    public void swipeCardsAndFindHiddenMessage() {
        HomeScreen homeScreen = getHomeScreen();
        softAssert.assertTrue(homeScreen.getTabBar().waitForTabBarShown(), "Tab bar should be ready");

        SwipeScreen swipeScreen = homeScreen.getTabBar().openSwipe();
        softAssert.assertTrue(swipeScreen.isScreenDisplayed(), "Swipe section should be visible");
        softAssert.assertTrue(swipeScreen.isCarouselDisplayed(), "Carousel should be visible");
        softAssert.assertTrue(
                swipeScreen.isCardActive(0) || swipeScreen.isCardTitleDisplayed("Fully Open Source"),
                "First card should be active on open");

        // Advance once and verify the previous card is no longer the active one.
        swipeScreen.swipeToNextCard();
        softAssert.assertTrue(
                swipeScreen.isPreviousCardHidden(0),
                "Previous (first) card should no longer be the active card after swipe");
        softAssert.assertTrue(
                swipeScreen.isCardActive(1) || swipeScreen.isCardTitleDisplayed("Great community"),
                "Second card should become active after swipe");

        softAssert.assertTrue(
                swipeScreen.swipeUntilLastCard(),
                "Should reach the last carousel card");
        softAssert.assertTrue(
                swipeScreen.isOnlyLastCardFullyVisible(),
                "Last card should be the only fully visible / active card");

        softAssert.assertTrue(
                swipeScreen.findYouFoundMeMessage(),
                "Vertical swipe should reveal the hidden logo message");
        softAssert.assertEquals(
                swipeScreen.getYouFoundMeText(),
                "You found me!!!",
                "Hidden message text should match");
    }
}
