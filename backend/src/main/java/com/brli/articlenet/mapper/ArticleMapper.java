package com.brli.articlenet.mapper;

import com.brli.articlenet.model.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("insert into articles(title,content,cover_img," +
            "state,category_id,create_user,create_time,update_time) " +
            "values(#{title},#{content},#{coverImg},#{state},#{categoryId}," +
            "#{createUser},#{createTime},#{updateTime})")
    void add(Article article);

    // defined in xml
    List<Article> list(Integer userId, Integer categoryId, String state);

    @Select("select * from articles where id=#{id} and create_user=#{userId}")
    Article findById(Integer id, Integer userId);

    @Update("update articles set title=#{title},content=#{content},cover_img=#{coverImg}," +
            "state=#{state},category_id=#{categoryId},update_time=#{updateTime} where id=#{id}")
    void update(Article article);

    @Delete("delete from articles where id=#{id}")
    void delete(Integer id);
}
