package com.brli.articlenet.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import static com.brli.articlenet.constants.ValidationConstants.PASSWORD_PATTERN;
import static com.brli.articlenet.constants.ValidationConstants.USERNAME_PATTERN;

public record LoginDTO(
        @NotEmpty(message = "username cannot be empty")
        @Pattern(regexp = USERNAME_PATTERN, message = "must match " + USERNAME_PATTERN)
        String username,

        @NotEmpty(message = "password cannot be empty")
        @Pattern(regexp = PASSWORD_PATTERN, message = "must match " + PASSWORD_PATTERN)
        String password
) {}
