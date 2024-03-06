package com.brli.articlenet.controller;

import com.brli.articlenet.model.Category;
import com.brli.articlenet.model.Result;
import com.brli.articlenet.service.CategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Result<Void> add(@RequestBody @Validated(Category.Add.class)
                          Category category) {
        categoryService.add(category);
        return Result.success();
    }

    @GetMapping
    public Result<List<Category>> list() {
        List<Category> categories = categoryService.list();
        return Result.success(categories);
    }

    @GetMapping("/detail")
    public Result<Category> detail(Integer id) {
        Category category = categoryService.findById(id);
        return Result.success(category);
    }

    @PutMapping
    public Result<Void> update(@RequestBody @Validated(Category.Update.class)
                             Category category) {
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping
    public Result<Void> delete(Integer id) {
        if (categoryService.findById(id) == null) {
            return Result.error("Category does not exist");
        }
        categoryService.delete(id);
        return Result.success();
    }
}