package com.brli.articlenet.dto;

import com.brli.articlenet.constants.ValidationConstants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import static com.brli.articlenet.constants.ValidationConstants.*;

public record RegisterDTO(
        @NotEmpty(message = "username cannot be empty")
        @Pattern(regexp = USERNAME_PATTERN, message = "must match " + USERNAME_PATTERN)
        String username,

        @NotEmpty(message = "password cannot be empty")
        @Pattern(regexp = PASSWORD_PATTERN, message = "must match " + PASSWORD_PATTERN)
        String password
) {}
