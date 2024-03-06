package com.brli.articlenet.service.impl;

import com.brli.articlenet.service.CategoryService;
import com.brli.articlenet.mapper.CategoryMapper;
import com.brli.articlenet.model.Category;
import com.brli.articlenet.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;

    private final UserService userService;

    public CategoryServiceImpl(CategoryMapper categoryMapper, UserService userService) {
        this.categoryMapper = categoryMapper;
        this.userService = userService;
    }

    @Override
    public void add(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(userService.getIdFromLoginUser());
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
        return categoryMapper.list(userService.getIdFromLoginUser());
    }

    @Override
    public Category findById(Integer id) {
        return categoryMapper.findById(id);
    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }
}
