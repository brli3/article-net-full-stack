package com.brli.articlenet.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Md5UtilTest {
    @Test
    void testGen() {
        String user03 = Md5Util.getMD5String("user03");
        System.out.println(user03);
    }

    @Test
    void testMd5String() {
        String s1 = "user01";
        String s2 = "b75705d7e35e7014521a46b532236ec3";
        assertEquals(s2, Md5Util.getMD5String(s1));
    }

    @Test
    void testMd5String2() {
        String s1 = "user02";
        String s2 = "8bd108c8a01a892d129c52484ef97a0d";
        assertEquals(s2, Md5Util.getMD5String(s1));
    }

    @Test
    void testMd5String3() {
        String s1 = "user03";
        String s2 = "a7d39043afa25be5cc235d943b64917a";
        assertEquals(s2, Md5Util.getMD5String(s1));
    }

    @Test
    void testMd5checkPasswordPass() {
        String s1 = "user03";
        String s2 = "a7d39043afa25be5cc235d943b64917a";
        boolean result = Md5Util.checkPassword(s1, s2);
        assertTrue(result);
    }

    @Test
    void testMd5checkPasswordFail() {
        String s1 = "user02";
        String s2 = "a7d39043afa25be5cc235d943b64917a";
        boolean result = Md5Util.checkPassword(s1, s2);
        assertFalse(result);
    }
}
