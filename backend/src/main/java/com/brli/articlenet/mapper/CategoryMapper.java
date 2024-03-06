package com.brli.articlenet.mapper;

import com.brli.articlenet.model.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Insert("insert into categories(category_name,category_alias,create_user,create_time,update_time)" +
            "values(#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void add(Category category);

    @Select("select * from categories where create_user=#{userId}")
    List<Category> list(Integer userId);

    @Select("select * from categories where id=#{id}")
    Category findById(Integer id);

    @Update("update categories set category_name=#{categoryName}," +
            "category_alias=#{categoryAlias},update_time=#{updateTime} where id=#{id}")
    void update(Category category);

    @Delete("delete from categories where id=#{id}")
    void delete(Integer id);
}
