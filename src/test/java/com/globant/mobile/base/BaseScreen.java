package com.globant.mobile.base;

import com.globant.mobile.utils.TestData;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Base screen object. Locators are stored as {@link By}
 * strategies and resolved only when an interaction happens
 */
public abstract class BaseScreen {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseScreen.class);

    protected final AppiumDriver driver;
    protected final WebDriverWait wait;
    private final Duration implicitWait;

    protected BaseScreen(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestData.getExplicitWaitSeconds()));
        this.implicitWait = Duration.ofSeconds(TestData.getImplicitWaitSeconds());
    }

    /**
     * Finds an element using the provided search strategy at interaction time.
     *
     * @param locator Appium / Selenium locator strategy
     * @return located web element
     */
    protected WebElement getWebElement(By locator) {
        return driver.findElement(locator);
    }

    /**
     * Waits until the element is present in the DOM. Implicit wait is set to zero while
     * the explicit wait runs to avoid stacked timeouts, then restored in {@code finally}.
     *
     * @param locator search strategy
     * @return {@code true} when the element is present; {@code false} otherwise
     */
    protected boolean waitForPresence(By locator) {
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException exception) {
            LOGGER.warn("Element not present within explicit wait: {}", locator);
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(implicitWait);
        }
    }

    /**
     * Waits until the element is visible.
     *
     * @param locator search strategy
     * @return {@code true} when visible; {@code false} otherwise
     */
    protected boolean waitForVisibility(By locator) {
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException exception) {
            LOGGER.warn("Element not visible within explicit wait: {}", locator);
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(implicitWait);
        }
    }

    /**
     * Waits until the element becomes invisible or is removed from the DOM.
     *
     * @param locator search strategy
     * @return {@code true} when the element is no longer visible
     */
    protected boolean waitForInvisibility(By locator) {
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException exception) {
            LOGGER.warn("Element still visible after explicit wait: {}", locator);
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(implicitWait);
        }
    }

    /**
     * Clicks an element after waiting for it to be clickable.
     *
     * @param locator search strategy
     * @param message log message describing the action
     */
    protected void click(By locator, String message) {
        LOGGER.info(message);
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
        } finally {
            driver.manage().timeouts().implicitlyWait(implicitWait);
        }
    }

    /**
     * Clears and types text into an input field.
     *
     * @param locator search strategy
     * @param text    value to type
     * @param message log message describing the action
     */
    protected void sendKeys(By locator, String text, String message) {
        LOGGER.info(message);
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.clear();
            element.sendKeys(text);
        } finally {
            driver.manage().timeouts().implicitlyWait(implicitWait);
        }
    }

    /**
     * Returns whether the element is currently displayed without failing the flow.
     *
     * @param locator search strategy
     * @return {@code true} when displayed
     */
    public boolean isDisplayed(By locator) {
        try {
            return waitForVisibility(locator) && getWebElement(locator).isDisplayed();
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

    /**
     * Reads visible text from an element.
     *
     * @param locator search strategy
     * @return trimmed text content
     */
    protected String getText(By locator) {
        waitForVisibility(locator);
        return getWebElement(locator).getText().trim();
    }

    /**
     * Returns a named attribute from an element.
     *
     * @param locator   search strategy
     * @param attribute attribute name
     * @return attribute value or {@code null}
     */
    protected String getAttribute(By locator, String attribute) {
        waitForVisibility(locator);
        return getWebElement(locator).getAttribute(attribute);
    }

    /**
     * Best-effort keyboard dismiss by tapping the screen root when provided.
     * Avoids AndroidDriver-specific APIs that break across Selenium versions.
     *
     * @param fallbackLocator element to tap outside the keyboard (e.g. screen root)
     */
    protected void hideKeyboardIfShown(By fallbackLocator) {
        try {
            click(fallbackLocator, "Dismissing keyboard by tapping screen root");
        } catch (Exception exception) {
            LOGGER.debug("Keyboard dismiss tap skipped: {}", exception.getMessage());
        }
    }

    /**
     * Performs a horizontal W3C swipe inside the given element bounds.
     * Direction {@code left} moves the finger left (advances carousel cards in this app).
     *
     * @param elementLocator element that defines the swipe area
     * @param direction      {@code left} or {@code right}
     */
    protected void swipeHorizontal(By elementLocator, String direction) {
        LOGGER.info("Swiping {} on element {}", direction, elementLocator);
        Rectangle rect = getWebElement(elementLocator).getRect();
        int y = rect.y + (rect.height / 2);
        int startX;
        int endX;
        if ("left".equalsIgnoreCase(direction)) {
            startX = rect.x + (int) (rect.width * 0.8);
            endX = rect.x + (int) (rect.width * 0.2);
        } else {
            startX = rect.x + (int) (rect.width * 0.2);
            endX = rect.x + (int) (rect.width * 0.8);
        }
        performSwipe(startX, y, endX, y);
    }

    /**
     * Performs a vertical swipe on the screen to scroll content.
     *
     * @param direction {@code up} moves the finger up; {@code down} moves the finger down
     */
    protected void swipeVertical(String direction) {
        LOGGER.info("Swiping vertically {}", direction);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int startY;
        int endY;
        if ("up".equalsIgnoreCase(direction)) {
            startY = (int) (size.height * 0.75);
            endY = (int) (size.height * 0.25);
        } else {
            startY = (int) (size.height * 0.25);
            endY = (int) (size.height * 0.75);
        }
        performSwipe(x, startY, x, endY);
    }

    /**
     * Swipes vertically until the target locator is visible or max attempts are exhausted.
     *
     * @param targetLocator element to find
     * @param maxSwipes     maximum vertical swipes
     * @return {@code true} when the element becomes visible
     */
    protected boolean swipeUpUntilVisible(By targetLocator, int maxSwipes) {
        for (int attempt = 0; attempt < maxSwipes; attempt++) {
            if (isElementPresentQuick(targetLocator)) {
                return true;
            }
            swipeVertical("up");
        }
        return isElementPresentQuick(targetLocator);
    }

    /**
     * Quick visibility check without consuming the full explicit wait budget.
     *
     * @param locator search strategy
     * @return {@code true} when the element is displayed right now
     */
    protected boolean isElementPresentQuick(By locator) {
        Duration previous = implicitWait;
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException exception) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(previous);
        }
    }

    /**
     * Executes Android {@code mobile: swipeGesture} inside an element.
     *
     * @param elementLocator swipe container
     * @param direction      gesture direction accepted by UiAutomator2
     */
    protected void swipeGestureOnElement(By elementLocator, String direction) {
        LOGGER.info("Executing mobile swipeGesture {} on {}", direction, elementLocator);
        WebElement element = getWebElement(elementLocator);
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((RemoteWebElement) element).getId());
        params.put("direction", direction);
        params.put("percent", 0.75);
        driver.executeScript("mobile: swipeGesture", params);
    }

    private void performSwipe(int startX, int startY, int endX, int endY) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(new Pause(finger, Duration.ofMillis(200)));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(swipe));
    }
}
