package com.brli.articlenet.exception;

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String msg) {
        super(msg);
    }
}
