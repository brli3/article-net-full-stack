package com.brli.articlenet.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    private boolean moreThanOneConsecutiveSpace(String text) {
        char[] arr = text.toCharArray();
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == ' ' && arr[i - 1] == ' ') {
                return true;
            }
        }
        return false;
    }

    @Test
    void removeExcessiveSpaces() {
        String text= " a a b  b v c & tom / va f   j   j2 * *   ";
        assertTrue(moreThanOneConsecutiveSpace(text));
        String output = StringUtil.removeExcessiveSpaces(text);
        assertFalse(moreThanOneConsecutiveSpace(output));
        System.out.println(text);
        System.out.println(output);
    }

    @Test
    void randomUsername() {
        int min = 20, max = 30;
        String result = StringUtil.randomUsername(min, max);
        assertFalse(result.isEmpty());
        assertTrue(result.length() >= min && result.length() <= max);
    }

    @Test
    void randomPassword() {
        int min = 5, max = 16;
        String result = StringUtil.randomPassword(min, max);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.length() >= min && result.length() <= max);
    }
}