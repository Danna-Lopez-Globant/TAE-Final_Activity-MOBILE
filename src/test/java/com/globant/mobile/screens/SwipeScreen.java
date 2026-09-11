package com.globant.mobile.screens;

import com.globant.mobile.base.BaseScreen;
import com.globant.mobile.screens.components.TabBar;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

/**
 * Swipe section with horizontal carousel cards and a vertically hidden logo message.
 */
public class SwipeScreen extends BaseScreen {

    private static final String[] CARD_TITLES = {
            "Fully Open Source",
            "Great community",
            "JS.Foundation",
            "Support Videos",
            "Extendable",
            "Compatible"
    };

    private final By screen = AppiumBy.accessibilityId("Swipe-screen");
    /** On Android the carousel exposes a resource-id (not accessibility id). */
    private final By carousel = By.id("Carousel");
    private final By logo = AppiumBy.accessibilityId("WebdriverIO logo");
    private final By youFoundMe = AppiumBy.androidUIAutomator(
            "new UiSelector().text(\"You found me!!!\")");
    private final By swipeHorizontalTitle = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"Swipe horizontal\")");

    public SwipeScreen(AppiumDriver driver) {
        super(driver);
    }

    public TabBar getTabBar() {
        return new TabBar(driver);
    }

    public boolean isScreenDisplayed() {
        return isDisplayed(screen);
    }

    public boolean isCarouselDisplayed() {
        return isDisplayed(carousel);
    }

    public boolean isSwipeTitleDisplayed() {
        return isDisplayed(swipeHorizontalTitle);
    }

    /**
     * Card locator by zero-based index using Android resource-id from the carousel.
     *
     * @param index card index (0..5)
     * @return locator strategy
     */
    public By cardByIndex(int index) {
        return By.id("__CAROUSEL_ITEM_" + index + "__");
    }

    /**
     * Card title locator for text-based assertions.
     *
     * @param title visible card title
     * @return locator strategy
     */
    public By cardTitle(String title) {
        return AppiumBy.androidUIAutomator("new UiSelector().text(\"" + title + "\")");
    }

    /**
     * @param index card index
     * @return {@code true} when the card node is displayed
     */
    public boolean isCardDisplayed(int index) {
        return isElementPresentQuick(cardByIndex(index));
    }

    /**
     * @param title card title text
     * @return {@code true} when that title is visible
     */
    public boolean isCardTitleDisplayed(String title) {
        return isElementPresentQuick(cardTitle(title));
    }

    /**
     * A card is considered active when its left edge is near the screen origin (x ≈ 0).
     *
     * @param index card index
     * @return {@code true} when the card is the active / fully visible one
     */
    public boolean isCardActive(int index) {
        if (!isCardDisplayed(index)) {
            return false;
        }
        Rectangle rect = getWebElement(cardByIndex(index)).getRect();
        return rect.x <= 5;
    }

    /**
     * Swipes the carousel to the next card.
     * The practice wording says "swipe right"; on this carousel the finger moves left
     * so cards advance toward the last item.
     */
    public void swipeToNextCard() {
        if (isCarouselDisplayed()) {
            swipeGestureOnElement(carousel, "left");
        } else {
            swipeHorizontal(screen, "left");
        }
    }

    /**
     * Advances until the last carousel card is active.
     *
     * @return {@code true} when the last card becomes active
     */
    public boolean swipeUntilLastCard() {
        int lastIndex = CARD_TITLES.length - 1;
        for (int i = 0; i < CARD_TITLES.length + 1; i++) {
            if (isCardActive(lastIndex) || isCardTitleDisplayed(CARD_TITLES[lastIndex])) {
                if (isCardActive(lastIndex) || isOnlyLastCardFullyVisible()) {
                    return true;
                }
            }
            swipeToNextCard();
        }
        return isCardActive(lastIndex) || isCardTitleDisplayed(CARD_TITLES[lastIndex]);
    }

    /**
     * Verifies that only the last card remains fully on screen (active at x≈0).
     *
     * @return {@code true} when the last card is active and the first card is not
     */
    public boolean isOnlyLastCardFullyVisible() {
        int lastIndex = CARD_TITLES.length - 1;
        boolean lastVisible = isCardActive(lastIndex) || isCardTitleDisplayed(CARD_TITLES[lastIndex]);
        boolean firstGone = !isCardActive(0);
        return lastVisible && firstGone;
    }

    /**
     * After swiping once from the first card, checks the previous card is no longer active.
     *
     * @param previousIndex index of the card that should no longer be active
     * @return {@code true} when that card is no longer the active card
     */
    public boolean isPreviousCardHidden(int previousIndex) {
        return !isCardActive(previousIndex);
    }

    /**
     * Scrolls vertically until the hidden logo message is visible.
     *
     * @return {@code true} when {@code You found me!!!} is displayed
     */
    public boolean findYouFoundMeMessage() {
        boolean found = swipeUpUntilVisible(youFoundMe, 8);
        if (!found) {
            found = swipeUpUntilVisible(logo, 3) && isElementPresentQuick(youFoundMe);
        }
        return found;
    }

    /**
     * @return text of the hidden message
     */
    public String getYouFoundMeText() {
        return getText(youFoundMe);
    }

    /**
     * @return all known card titles in order
     */
    public String[] getCardTitles() {
        return CARD_TITLES.clone();
    }

    /**
     * Fallback when resource-id cards are unavailable: uses title text visibility.
     *
     * @param title card title
     * @return whether an element with that exact text exists and is displayed
     */
    public boolean isTitleOnScreen(String title) {
        try {
            WebElement element = driver.findElement(cardTitle(title));
            return element.isDisplayed();
        } catch (Exception exception) {
            return false;
        }
    }
}
