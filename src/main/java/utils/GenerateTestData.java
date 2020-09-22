package utils;

import org.apache.commons.lang.RandomStringUtils;

public class GenerateTestData {

    public static String generateRandomEmail() {
        return RandomStringUtils.random(10, true, true) + "@gmail.com";
    }
}
