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
     * Card title locator for text-based assertions.
     *
     * @param title visible card title
     * @return locator strategy
     */
    public By cardTitle(String title) {
        return AppiumBy.androidUIAutomator("new UiSelector().text(\"" + title + "\")");
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
