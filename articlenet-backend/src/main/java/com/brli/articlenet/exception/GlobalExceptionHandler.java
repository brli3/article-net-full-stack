package com.brli.articlenet.exception;

import com.brli.articlenet.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error(Arrays.toString(e.getStackTrace()));
        String msg = e.getMessage();
        return Result.error(StringUtils.hasLength(msg) ? msg : "Failed");
    }
}
