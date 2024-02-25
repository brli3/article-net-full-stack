package com.brli.articlenet.mapper;

import com.brli.articlenet.utils.AbstractTestcontainer;
import com.brli.articlenet.model.User;
import com.brli.articlenet.utils.Md5Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
// not need to load application context with @MybatisTest
//@ExtendWith(SpringExtension.class)
//to prevent Spring Boot from replacing the Testcontainers database with an embedded one.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext // needed for running multiple test classes
class UserMapperTest extends AbstractTestcontainer  {
    @Autowired
    UserMapper userMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByUsername() {
        User user = userMapper.findByUsername("user01");
        assertEquals(1, user.getId());
    }

    @Test
    void add() {
        userMapper.add("user03", "a7d39043afa25be5cc235d943b64917a");
        assertNotNull(userMapper.findByUsername("user03"));;
    }

    @Test
    void addExistingUser() {
        assertThrows(DuplicateKeyException.class, () -> {
            userMapper.add("user01", "password");
        });
    }

    @Test
    void findById() {
        User user = userMapper.findById(1);
        assertEquals("user01", user.getUsername());
    }

    @Test
    void update() {
        User user = userMapper.findById(1);
        user.setUsername("user01_new");
        user.setNickname("nick01_new");
        user.setEmail("user01_upd@email.com");
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
        User user1 = userMapper.findById(1);
        // username cannot be changed
        assertEquals("user01", user1.getUsername());
        assertEquals("nick01_new", user1.getNickname());
        assertNotEquals(user1.getCreateTime(), user1.getUpdateTime());
    }

    @Test
    void updateAvatar() {
        String url = "https://new_avatar.com/img.png";
        userMapper.updateAvatar(url, 1);
        assertEquals(url, userMapper.findById(1).getUserPic());
    }

    @Test
    void updatePwd() {
        String pwd = Md5Util.getMD5String("password_upd");
        userMapper.updatePwd(pwd, 1);
        assertEquals(pwd, userMapper.findById(1).getPassword());
    }
}