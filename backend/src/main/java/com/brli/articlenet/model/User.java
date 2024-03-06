package com.brli.articlenet.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {
    // use with @validated in controller
    @NotNull // for validating User passed from controller
    private Integer id; // key id

    private String username; // username

    @JsonIgnore // ignore password when converting User to Json
    private String password; // password

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$") // 1-10 characters
    private String nickname; //nickname

    @NotEmpty
    @Email
    private String email; //email

    private String userPic; //avatar

    private LocalDateTime createTime;//time when user is registered

    private LocalDateTime updateTime;//time when user info is updated

}
