package com.brli.articlenet.mapper;

import com.brli.articlenet.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from users where username=#{username}")
    User findByUsername(String username);

    @Select("select * from users where id=#{id}")
    User findById(Integer id);

    @Insert("insert into users(username, password, create_time, update_time)" +
            "values(#{username},#{password},now(),now())")
    void add(String username, String password);

    @Update("update users set nickname=#{nickname},email=#{email},update_time=#{updateTime} " +
            "where id=#{id}")
    void update(User user);

    @Update("update users set user_pic=#{url},update_time=now() where id=#{id}")
    void updateAvatar(String url, Integer id);

    @Update("update users set password=#{password},update_time=now() where id=#{id}")
    void updatePwd(String password, Integer id);
}
