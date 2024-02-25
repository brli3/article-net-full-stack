package com.brli.articlenet.model;


import com.brli.articlenet.annotation.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class Article {

    private Integer id; // primary key

    @NotEmpty
    @Pattern(regexp = "^(?:\\S+\\s*){1,20}$") //1 - 20 words
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    @URL
    private String coverImg;

    @State
    private String state; //status: published|draft

    @NotNull
    private Integer categoryId; // category id (foreign key)

    private Integer createUser; // creator id (foreign key)

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
