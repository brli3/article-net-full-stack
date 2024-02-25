package com.brli.articlenet.controller;

import com.brli.articlenet.dto.RegisterDTO;
import com.brli.articlenet.model.Result;
import com.brli.articlenet.model.User;
import com.brli.articlenet.service.UserService;
import com.brli.articlenet.utils.JwtUtil;
import com.brli.articlenet.utils.Md5Util;
import com.brli.articlenet.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
@Slf4j
public class UserController {
    private final UserService userService;

    private final StringRedisTemplate redisTemplate;

    public UserController(UserService userService, StringRedisTemplate redisTemplate) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }

    @Value("${jwt.expiry-time}")
    private Integer expiryTimeInHours;

//    @PostMapping("/register")
//    public Result<String> register(@Pattern(regexp = "^\\S{5,16}$") String username,
//                           @Pattern(regexp = "^\\S{5,16}$") String password) {
//        // check if user already exists
//        User user = userService.findByUsername(username);
//        if (user == null) {
//            // register the user
//            userService.register(username, password);
//            log.info("New user registered: {}", username);
//            return Result.success("User created");
//        }
//        return Result.error("Username already exists");
//    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody @Validated RegisterDTO registerDTO) {
        try {
            userService.register(registerDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,
                                @Pattern(regexp = "^\\S{5,16}$") String password) {
        // check username
        User loginUser = userService.findByUsername(username);
        if (loginUser == null) {
            return Result.error("Wrong username");
        }

        // check password
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            String token = JwtUtil.genToken(Map.of(
                    "id", loginUser.getId(),
                    "username", loginUser.getUsername()));

            // save token in Redis
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            ops.set(token, token,
                    Objects.nonNull(expiryTimeInHours)? expiryTimeInHours : 1,
                    TimeUnit.HOURS);
            return Result.success(token);
        }
        return  Result.error("Wrong password");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        // parse token
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result<Void> update(@RequestBody @Validated User user) {
        String username = user.getUsername();
        if (username != null &&
                !userService.usernameMatchesId(username, user.getId())) {
            return Result.error("Username does not match the one associated with this id");
        }
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result<Void> updateAvatar(@RequestParam("avatarUrl") @URL String url) {
        userService.updateAvatar(url);
        return Result.success();
    }

    /**
     *
     * @param params old password, new password in a map
     * @param token token in the request. Will be invalidated after changing password.
     *              This is done by deleting the existing token in Redis
     * @return Success or fail
     */
    @PatchMapping("/updatePwd")
    public Result<Void> updatePwd(@RequestBody Map<String, String> params,
                            @RequestHeader("Authorization") String token) {
        // need @RequestBody to convert JSON to map
        // 1. validate parameters
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd)
                || !StringUtils.hasLength(newPwd)
                || !StringUtils.hasLength(rePwd)) {
            return Result.error("Missing request parameters");
        }
        // validate password
        String username = userService.getUsernameFromLoginUser();
        User loginUser = userService.findByUsername(username);
        if (!Md5Util.checkPassword(oldPwd, loginUser.getPassword()))  {
            return Result.error("Wrong original password");
        }
        if (!newPwd.equals(rePwd)) {
            return Result.error("Two passwords are different");
        }
        if (Md5Util.checkPassword(newPwd, loginUser.getPassword())) {
            return Result.error("New password is same as old one");
        }
        // 2. call service to update password
        userService.updatePwd(newPwd);
        // 3. delete existing token in Redis
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.getOperations().delete(token);
        return Result.success();
    }
}
