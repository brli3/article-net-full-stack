package com.brli.articlenet.service.impl;

import com.brli.articlenet.mapper.UserMapper;
import com.brli.articlenet.model.User;
import com.brli.articlenet.utils.Md5Util;
import com.brli.articlenet.dto.RegisterDTO;
import com.brli.articlenet.exception.DuplicateResourceException;
import com.brli.articlenet.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// JUnit 5 will use Mockito's extension to initialize the mocks and inject them into the test class
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock // full mock
    private UserMapper userMapper;

    @Spy // partial mock
    @InjectMocks // no need to instantiate
    private UserServiceImpl userService; // cannot be an interface

    @Test
    void findByUsername() {
        String username = "user01";
        userService.findByUsername(username);
        verify(userMapper).findByUsername(username);
    }

    @Test
    void usernameMatchesId() {
        // Given
        int id = 1;
        String username = "user01";
        User user = new User(id, username, "pwd01",
                "abc", "user01@email.com", "https://abc.com",
                LocalDateTime.now(), LocalDateTime.now());
        when(userMapper.findById(id)).thenReturn(user);

        // When
        boolean actual = userService.usernameMatchesId(username, id);

        // Then
        assertTrue(actual);
    }

    @Test
    void usernameNotMatchesId() {
        // Given
        int id = 1;
        String username = "user01";

        // When
        when(userMapper.findById(id)).thenReturn(null);

        // Then
        assertThatThrownBy(()->userService.usernameMatchesId(username, id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("user with id [%d] not found".formatted(id));
    }

    @Test
    void usernameAvailable() {
        // Mocking userMapper to return a null user as it does not exist
        String username = "newUser";
        when(userMapper.findByUsername(username)).thenReturn(null);
        boolean result = userService.isUsernameAvailable(username);
        assertTrue(result);
        verify(userMapper, times(1)).findByUsername(username);
    }

    @Test
    void usernameNotAvailable() {
        // Mocking userMapper to return a user when it already exists
        String username = "newUser";
        when(userMapper.findByUsername(username)).thenReturn(new User());
        boolean result = userService.isUsernameAvailable(username);
        assertFalse(result);
        verify(userMapper, times(1)).findByUsername(username);
    }

    @Test
    void registerSuccessful() {
        // given
        String username = "newUser";
        String pwd = "password1";
        RegisterDTO registerDTO = new RegisterDTO(username, pwd);
        when(userService.isUsernameAvailable(registerDTO.username()))
                .thenReturn(true);
        String pwdEncrypted = Md5Util.getMD5String(registerDTO.password());

        // call method under test
        userService.register(registerDTO);

        // verify
        verify(userService).isUsernameAvailable(username);
        verify(userMapper).add(registerDTO.username(), pwdEncrypted);
    }

    @Test
    void registerFailedWhenUsernameExists() {
        String username = "newUser";
        String pwd = "password1";
        RegisterDTO registerDTO = new RegisterDTO(username, pwd);
        when(userService.isUsernameAvailable(registerDTO.username())).thenReturn(false);

        assertThatThrownBy(() -> userService.register(registerDTO))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("username already exists");
        verify(userService).isUsernameAvailable(registerDTO.username());
    }
}