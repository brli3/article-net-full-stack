package com.brli.articlenet.controller;

import com.brli.articlenet.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/hello")
public class HelloController {
    private static int count = 1;
    @GetMapping
    public Result<String> hello() {
        return Result.success("Hello " + count++);
    }

}
