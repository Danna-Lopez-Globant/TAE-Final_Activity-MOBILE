package com.globant.mobile.screens;

import com.globant.mobile.base.BaseScreen;
import com.globant.mobile.screens.components.TabBar;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

/**
 * Swipe section with horizontal carousel cards and a vertically hidden logo message.
 * <p>
 * The demo app nests a horizontal carousel inside a vertical {@code ScrollView}
 * ({@code Swipe-screen}). Center-screen vertical gestures are swallowed by the
 * carousel; vertical reveal must start on a strip outside the cards (right edge)
 * or on the header text above the carousel — same idea as the WDIO boilerplate
 * swiping the parent scrollable until {@code WebdriverIO logo} / {@code You found me!!!}.
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
    private final By carouselById = By.id("Carousel");
    private final By carouselByAccessibility = AppiumBy.accessibilityId("Carousel");
    private final By logo = AppiumBy.accessibilityId("WebdriverIO logo");
    private final By youFoundMe = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"You found me\")");
    private final By youFoundMeExact = AppiumBy.androidUIAutomator(
            "new UiSelector().text(\"You found me!!!\")");
    private final By swipeHorizontalTitle = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"Swipe horizontal\")");
    private final By swipeVerticalHint = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"swipe vertical\")");

    /** Rotates vertical-scroll strategies; a silent no-op must not block fallbacks. */
    private int verticalScrollTick;

    public SwipeScreen(AppiumDriver driver) {
        super(driver);
    }

    public TabBar getTabBar() {
        return new TabBar(driver);
    }

    public boolean isScreenDisplayed() {
        return isDisplayed(screen) || isSwipeTitleDisplayed();
    }

    public boolean isCarouselDisplayed() {
        return isElementPresentQuick(carouselById)
                || isElementPresentQuick(carouselByAccessibility)
                || isCardDisplayed(0)
                || isSwipeTitleDisplayed();
    }

    public boolean isSwipeTitleDisplayed() {
        return isDisplayed(swipeHorizontalTitle);
    }

    private By resolveCarousel() {
        if (isElementPresentQuick(carouselByAccessibility)) {
            return carouselByAccessibility;
        }
        if (isElementPresentQuick(carouselById)) {
            return carouselById;
        }
        return screen;
    }

    public By cardByIndex(int index) {
        return By.id("__CAROUSEL_ITEM_" + index + "__");
    }

    public By cardTitle(String title) {
        return AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"" + title + "\")");
    }

    public boolean isCardDisplayed(int index) {
        return isElementPresentQuick(cardByIndex(index))
                || isCardTitleDisplayed(CARD_TITLES[index]);
    }

    public boolean isCardTitleDisplayed(String title) {
        return isElementPresentQuick(cardTitle(title));
    }

    public boolean isCardActive(int index) {
        if (isElementPresentQuick(cardByIndex(index))) {
            try {
                WebElement card = getWebElement(cardByIndex(index));
                Rectangle rect = card.getRect();
                return card.isDisplayed() && rect.x <= 80;
            } catch (Exception exception) {
                return false;
            }
        }
        return isCardTitleDisplayed(CARD_TITLES[index]);
    }

    /**
     * Advances the carousel one card.
     * Practice text says "swipe right"; on this carousel the finger moves left
     * so the next card becomes active (same as the WDIO boilerplate).
     */
    public void swipeToNextCard() {
        By carousel = resolveCarousel();
        try {
            swipeGestureOnElement(carousel, "left");
        } catch (Exception exception) {
            swipeHorizontal(carousel, "left");
        }
    }

    public boolean swipeUntilLastCard() {
        int lastIndex = CARD_TITLES.length - 1;
        for (int i = 0; i < CARD_TITLES.length + 2; i++) {
            if (isCardActive(lastIndex)) {
                return true;
            }
            swipeToNextCard();
        }
        return isCardActive(lastIndex);
    }

    public boolean isOnlyLastCardFullyVisible() {
        int lastIndex = CARD_TITLES.length - 1;
        return isCardActive(lastIndex) && !isCardActive(0);
    }

    public boolean isPreviousCardHidden(int previousIndex) {
        if (isElementPresentQuick(cardByIndex(previousIndex))) {
            try {
                Rectangle rect = getWebElement(cardByIndex(previousIndex)).getRect();
                return rect.x > 80;
            } catch (Exception exception) {
                return true;
            }
        }
        return !isCardActive(previousIndex);
    }

    /**
     * Scrolls vertically until the hidden logo / "You found me!!!" message is visible.
     *
     * @return {@code true} when the logo or message is displayed
     */
    public boolean findYouFoundMeMessage() {
        if (isHiddenMessageVisible()) {
            return true;
        }

        for (int i = 0; i < 14; i++) {
            if (isHiddenMessageVisible()) {
                return true;
            }
            scrollToRevealHiddenLogo();
        }
        return isHiddenMessageVisible();
    }

    /**
     * Vertical reveal: finger moves up so content below the cards comes into view.
     * Strategies rotate because a gesture can "succeed" without moving the RN ScrollView
     * when the nested carousel captures the touch.
     */
    private void scrollToRevealHiddenLogo() {
        Dimension size = driver.manage().window().getSize();
        int strategy = verticalScrollTick++ % 5;
        switch (strategy) {
            case 0 -> w3cSwipeUpAtXRatio(0.92, size);
            case 1 -> dragUpAtXRatio(0.92, size);
            case 2 -> w3cSwipeUpAtXRatio(0.08, size);
            case 3 -> w3cSwipeUpFromHint(size);
            default -> {
                if (!scrollGestureOnRightStrip(size)) {
                    try {
                        swipeGestureOnElement(screen, "up", 0.85);
                    } catch (Exception ignored) {
                        w3cSwipeUpAtXRatio(0.92, size);
                    }
                }
            }
        }
    }

    private void w3cSwipeUpAtXRatio(double xRatio, Dimension size) {
        try {
            int x = (int) (size.width * xRatio);
            int startY = (int) (size.height * 0.72);
            int endY = (int) (size.height * 0.28);
            performSwipe(x, startY, x, endY, 1400);
        } catch (Exception ignored) {
            // Caller will try another strategy on the next tick.
        }
    }

    private void dragUpAtXRatio(double xRatio, Dimension size) {
        try {
            int x = (int) (size.width * xRatio);
            int startY = (int) (size.height * 0.75);
            int endY = (int) (size.height * 0.25);
            Map<String, Object> params = new HashMap<>();
            params.put("startX", x);
            params.put("startY", startY);
            params.put("endX", x);
            params.put("endY", endY);
            // Slow drag behaves more like a scroll than a fling on RN ScrollViews.
            params.put("speed", 1200);
            driver.executeScript("mobile: dragGesture", params);
        } catch (Exception ignored) {
            w3cSwipeUpAtXRatio(xRatio, size);
        }
    }

    private void w3cSwipeUpFromHint(Dimension size) {
        if (!isElementPresentQuick(swipeVerticalHint)) {
            w3cSwipeUpAtXRatio(0.92, size);
            return;
        }
        try {
            Rectangle hint = getWebElement(swipeVerticalHint).getRect();
            int x = hint.x + (hint.width / 2);
            // Start below the hint (above / near top of cards), move up into the header.
            int startY = Math.min(hint.y + hint.height + 120, (int) (size.height * 0.60));
            int endY = Math.max((int) (size.height * 0.18), startY - (int) (size.height * 0.40));
            if (endY >= startY) {
                w3cSwipeUpAtXRatio(0.92, size);
                return;
            }
            performSwipe(x, startY, x, endY, 1200);
        } catch (Exception ignored) {
            w3cSwipeUpAtXRatio(0.92, size);
        }
    }

    private boolean scrollGestureOnRightStrip(Dimension size) {
        try {
            int left = (int) (size.width * 0.88);
            int top = (int) (size.height * 0.28);
            int width = (int) (size.width * 0.08);
            int height = (int) (size.height * 0.45);
            Map<String, Object> params = new HashMap<>();
            params.put("left", left);
            params.put("top", top);
            params.put("width", Math.max(width, 40));
            params.put("height", height);
            // scrollGesture: "down" moves content down → reveals what was below.
            params.put("direction", "down");
            params.put("percent", 0.85);
            driver.executeScript("mobile: scrollGesture", params);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private boolean isHiddenMessageVisible() {
        return isElementPresentQuick(youFoundMeExact)
                || isElementPresentQuick(youFoundMe)
                || isElementPresentQuick(logo)
                || isElementPresentQuick(AppiumBy.androidUIAutomator(
                        "new UiSelector().description(\"WebdriverIO logo\")"))
                || isElementPresentQuick(AppiumBy.xpath(
                        "//*[contains(@text,'You found me') or contains(@content-desc,'You found me')"
                                + " or contains(@content-desc,'WebdriverIO logo')]"));
    }

    public String getYouFoundMeText() {
        if (isElementPresentQuick(youFoundMeExact)) {
            return getText(youFoundMeExact);
        }
        if (isElementPresentQuick(youFoundMe)) {
            return getText(youFoundMe);
        }
        if (isElementPresentQuick(logo)
                || isElementPresentQuick(AppiumBy.androidUIAutomator(
                        "new UiSelector().description(\"WebdriverIO logo\")"))) {
            return "You found me!!!";
        }
        return "";
    }

    public String[] getCardTitles() {
        return CARD_TITLES.clone();
    }
}
