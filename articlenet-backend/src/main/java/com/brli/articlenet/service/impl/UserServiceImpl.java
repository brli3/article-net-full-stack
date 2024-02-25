package com.brli.articlenet.service.impl;

import com.brli.articlenet.mapper.UserMapper;
import com.brli.articlenet.service.UserService;
import com.brli.articlenet.dto.RegisterDTO;
import com.brli.articlenet.exception.DuplicateResourceException;
import com.brli.articlenet.exception.ResourceNotFoundException;
import com.brli.articlenet.model.User;
import com.brli.articlenet.utils.Md5Util;
import com.brli.articlenet.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public boolean usernameMatchesId(String username, Integer id) {
        User user = userMapper.findById(id);
        if (Objects.isNull(user)) {
            throw new ResourceNotFoundException("user with id [%d] not found".formatted(id));
        }
        return username.equals(user.getUsername());
    }

    @Override
    public Integer getIdFromLoginUser() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        return (Integer) claims.get("id");
    }

    @Override
    public String getUsernameFromLoginUser() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        return (String) claims.get("username");
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String url) {
        userMapper.updateAvatar(url, getIdFromLoginUser());
    }

    @Override
    public void updatePwd(String newPwd) {
        userMapper.updatePwd(Md5Util.getMD5String(newPwd), getIdFromLoginUser());
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        return userMapper.findByUsername(username) == null;
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        // verify if user exists
        if (!isUsernameAvailable(registerDTO.username())) {
            String msg = "username already exists";
            log.info(msg);
            throw new DuplicateResourceException(msg);
        }
        // encrypt password Md5
        String pwdEncrypted = Md5Util.getMD5String(registerDTO.password());
        // add user to database
        userMapper.add(registerDTO.username(), pwdEncrypted);
    }
}
