package com.globant.mobile.utils;

import java.util.UUID;

/**
 * Helpers for generating unique test data so scenarios stay independent and repeatable.
 */
public final class RandomData {

    private RandomData() {
    }

    /**
     * Builds a unique email address for signup / login flows.
     *
     * @return random email under the webdriver.io domain
     */
    public static String randomEmail() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8) + "@webdriver.io";
    }
}
