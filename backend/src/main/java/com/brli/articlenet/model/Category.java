package com.brli.articlenet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {

    @NotNull(groups = Update.class)
    private Integer id;

    @NotEmpty
    private String categoryName;

    @NotEmpty
    private String categoryAlias;

    // User ID who is logged in
    private Integer createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    // Grouping for validation
    // if not specified, validation uses "Default" grouping

    public interface Add extends Default {}

    // Grouping for validation
    public interface Update extends Default {}
}
