package com.brli.articlenet.constants;

public class ValidationConstants {
    public static final int MIN_USERNAME_LENGTH = 5;
    public static final int MAX_USERNAME_LENGTH = 16;
    public static final int MIN_PASSWORD_LENGTH = 5;
    public static final int MAX_PASSWORD_LENGTH = 16;
    public static final String USERNAME_PATTERN =
            "^\\S{"+MIN_USERNAME_LENGTH+ "," +MAX_USERNAME_LENGTH +"}$";
    public static final String PASSWORD_PATTERN =
            "^\\S{"+MIN_PASSWORD_LENGTH+ "," +MAX_PASSWORD_LENGTH +"}$";
}
