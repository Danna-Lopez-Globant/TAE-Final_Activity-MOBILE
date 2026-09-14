# TAE Final Activity — Mobile

Mobile automation using **Appium + Java + TestNG** on the [WebdriverIO](https://github.com/webdriverio/native-demo-app/releases) native demo app.

## Stack

| Technology         | Version / detail                          |
|--------------------|-------------------------------------------|
| JDK                | 17+                                       |
| Maven              | 3.9+                                      |
| Appium             | 2.x / 3.x                                 |
| Driver             | UiAutomator2                              |
| Appium Java Client | 9.5.0                                     |
| TestNG             | 7.10.2                                    |
| APK                | `apps/android.wdio.native.app.v1.0.8.apk` |

## Framework practices

- **No PageFactory**: screens use `By` / `AppiumBy` locators resolved upon interaction.
- **Assertions only in tests**.
- **No `driver` in tests**: access via `getHomeScreen()` and screen objects.
- Implicit wait set to **0** during explicit waits.
- **SLF4J / Logback** logging.
- **Independent** tests.

## Prerequisites

1. JDK 17+ and Maven 3.9+
2. [Appium](https://appium.io/) with the UiAutomator2 driver:
   ```bash
   appium driver install uiautomator2
   ```
3. Android Studio emulator **or** physical device with USB debugging enabled
4. APK in `apps/` (included in the repo)

Check for the device:

```bash
adb devices
```

## Configuration

Edit `src/test/resources/config.properties`:

| Key | Example | Notes |
|-------|---------|--------|
| `appium.server.url` | `http://127.0.0.1:4723` | Appium server |
| `device.name` | `emulator-5554` | Output of `adb devices` |
| `app.path` | `apps/android.wdio.native.app.v1.0.8.apk` | Relative to project root |
| `implicit.wait` / `explicit.wait` | `5` / `15` | Seconds |
| `user.password` | `Test1234!` | Shared password for Sign Up / Login |

## How to run
Connect an Android device via USB or launch an emulator (a Pixel 6 API 34 was used in this case).
From `TAE-Final_Activity-MOBILE/`:

```bash
# Terminal 1
appium

# Terminal 2 (emulator/device ready)
cd TAE-Final_Activity-MOBILE
mvn clean test
```

Running a specific scenario:

```bash
mvn -Dtest=NavigationTest test
mvn -Dtest=SignUpTest test
mvn -Dtest=LoginTest test
mvn -Dtest=SwipeTest test
```

## Scenarios

| # | Class | What it validates |
|---|--------|------------|
| 1 | `NavigationTest` | Navigates the bottom menu (Home, Login, Forms, Webview, Swipe, Drag) and verifies key elements of each section |
| 2 | `SignUpTest` | Signs up using a random email and verifies the success alert |
| 3 | `LoginTest` | Creates a user within the test, logs in, and verifies the success alert |
| 4 | `SwipeTest` | Performs a horizontal swipe on cards (previous card hidden, last card active) and vertical scrolling until "You found me!!!" appears |


## Package structure

```
com.globant.mobile
├── base          BaseTest, BaseScreen
├── screens       Home, Login, Forms, Swipe, Webview, Drag
│   └── components TabBar, NativeAlert
├── tests         Navigation, SignUp, Login, Swipe
└── utils         ConfigReader, TestData, RandomData
```
