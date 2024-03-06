package com.brli.articlenet.service;

import com.brli.articlenet.dto.RegisterDTO;
import com.brli.articlenet.model.User;

public interface UserService {
    User findByUsername(String username);

    boolean usernameMatchesId(String username, Integer id);

    Integer getIdFromLoginUser();

    String getUsernameFromLoginUser();

    void update(User user);

    void updateAvatar(String url);

    void updatePwd(String newPwd);

    boolean isUsernameAvailable(String username);

    void register(RegisterDTO registerDTO);
}
