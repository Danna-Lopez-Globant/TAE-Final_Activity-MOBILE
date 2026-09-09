package com.globant.mobile.base;

import com.globant.mobile.screens.HomeScreen;
import com.globant.mobile.utils.TestData;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.time.Duration;

/**
 * Shared TestNG lifecycle: driver session, SoftAssert and entry-point screens.
 * Tests must not create the driver themselves.
 */
public abstract class BaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);

    protected AppiumDriver driver;
    protected SoftAssert softAssert;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws MalformedURLException {
        softAssert = new SoftAssert();
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(TestData.getPlatformName())
                .setDeviceName(TestData.getDeviceName())
                .setAutomationName(TestData.getAutomationName())
                .setAppPackage(TestData.getAppPackage())
                .setAppActivity(TestData.getAppActivity())
                .setApp(resolveAppPath())
                .setNoReset(false)
                .setAutoGrantPermissions(true);

        LOGGER.info("Starting Android session against {}", TestData.getAppiumServerUrl());
        driver = new AndroidDriver(new URL(TestData.getAppiumServerUrl()), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestData.getImplicitWaitSeconds()));
    }

    /**
     * Soft assertions are collected during the test and evaluated here so multiple
     * visibility / property checks can fail in a single run.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            if (softAssert != null) {
                softAssert.assertAll();
            }
        } finally {
            if (driver != null) {
                LOGGER.info("Quitting Appium session");
                driver.quit();
            }
        }
    }

    /**
     * Entry screen for the WDIO demo app after a fresh launch.
     *
     * @return home screen instance bound to the active driver
     */
    protected HomeScreen getHomeScreen() {
        return new HomeScreen(driver);
    }

    private String resolveAppPath() {
        Path appPath = Path.of(System.getProperty("user.dir"), TestData.getAppPath()).toAbsolutePath().normalize();
        LOGGER.info("Using app under test: {}", appPath);
        return appPath.toString();
    }
}
