package com.brli.articlenet.mapper;

import com.brli.articlenet.utils.AbstractTestcontainer;
import com.brli.articlenet.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
class CategoryMapperTest extends AbstractTestcontainer {
    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    void add() {
        Category category = new Category();
        category.setCategoryName("Drinks");
        category.setCategoryAlias("drink");
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(2);
        categoryMapper.add(category);
        List<Category> categoryList = categoryMapper.list(2);
        assertTrue(categoryList.stream().anyMatch(c->c.getCategoryAlias().equals("drink")));
    }

    @Test
    void list() {
        assertFalse(categoryMapper.list(1).isEmpty());
    }

    @Test
    void findById() {
        assertEquals("tech", categoryMapper.findById(2).getCategoryAlias());
    }

    @Test
    void update() {
        Category category = categoryMapper.findById(2);
        category.setCategoryName("Technology");
        category.setCategoryAlias("technology");
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
        Category category1 = categoryMapper.findById(2);
        assertEquals("technology", category1.getCategoryAlias());
    }

    @Test
    void delete() {
        Category category = categoryMapper.findById(10);
        assertNotNull(category);
        // make sure no article is related to this category (foreign key constraint)
        categoryMapper.delete(10);
        assertNull(categoryMapper.findById(10));
    }
}