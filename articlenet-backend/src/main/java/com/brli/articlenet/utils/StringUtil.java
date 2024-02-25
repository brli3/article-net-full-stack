package com.brli.articlenet.utils;

import com.github.javafaker.Faker;

import java.util.Random;

public class StringUtil {
    /**
     * Remove if the text has more than one consecutive spaces
     * e.g. "  a    b " -> "a b"
     * @param input original string
     * @return trimmed string
     */
    public static String removeExcessiveSpaces(String input) {
        String result = input.replaceAll("\\s+", " ");
        return result.trim();
    }

    /**
     * Generate random username with min and max characters
     * useful for testing and populating database tables
     * @param minNoOfChar min number of characters
     * @param maxNoOfChar max number of characters
     * @return random username string
     */
    public static String randomUsername(int minNoOfChar, int maxNoOfChar) {
        Faker faker = new Faker();
        String username = faker.name().username();
        StringBuilder sb = new StringBuilder(username);
        Random random = new Random();
        int start = sb.length();
        for (int i = start; i < minNoOfChar; i++) {
            if (i == start) {
                sb.append('_');
                continue;
            }
            int randomInt = random.nextInt(26);
            char randomChar = (char) ('a' + randomInt);
            sb.append(randomChar);
        }
        return sb.substring(0, Math.min(sb.length(), maxNoOfChar));
    }

    public static String randomPassword(int minNoOfChar, int maxNoOfChar) {
        Faker faker = new Faker();
        String password = faker.lorem().characters(minNoOfChar, maxNoOfChar, true, true);
        return password;
    }
}
