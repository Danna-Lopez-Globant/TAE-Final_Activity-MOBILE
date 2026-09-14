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
                swipeScreen.isCardActive(0) || swipeScreen.isCardDisplayed(0),
                "First card should be active/visible on open");

        swipeScreen.swipeToNextCard();
        softAssert.assertTrue(
                swipeScreen.isPreviousCardHidden(0) || swipeScreen.isCardActive(1) || swipeScreen.isCardDisplayed(1),
                "After swipe, first card should no longer be the only active card");
        softAssert.assertTrue(
                swipeScreen.isCardActive(1) || swipeScreen.isCardDisplayed(1) || swipeScreen.isCardTitleDisplayed("Great community"),
                "Second card should become active/visible after swipe");

        softAssert.assertTrue(swipeScreen.swipeUntilLastCard(), "Should reach the last carousel card");
        softAssert.assertTrue(
                swipeScreen.isOnlyLastCardFullyVisible() || swipeScreen.isCardActive(5) || swipeScreen.isCardDisplayed(5),
                "Last card should be visible as the active card");

        boolean found = swipeScreen.findYouFoundMeMessage();
        softAssert.assertTrue(found, "Vertical swipe should reveal the hidden logo message");
        if (found) {
            softAssert.assertTrue(
                    swipeScreen.getYouFoundMeText().toLowerCase().contains("found me")
                            || swipeScreen.getYouFoundMeText().equals("You found me!!!"),
                    "Hidden message text should match");
        }
    }
}
