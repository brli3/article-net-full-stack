package com.brli.articlenet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Provide consistent response type for each request
 * @param <T>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data // need it for converting to JSON
public class Result<T> {
    private Integer code; // code  0-success  1-error
    private String message; // message
    private T data; // response data / body

    // success with data
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "success", data);
    }

    // success without data
    public static <E> Result<E> success() {
        return new Result<>(0, "success", null);
    }

    // error
    public static <E> Result<E> error(String message) {
        return new Result<>(1, message, null);
    }
}
