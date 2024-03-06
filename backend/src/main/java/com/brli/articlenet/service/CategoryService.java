package com.brli.articlenet.service;

import com.brli.articlenet.model.Category;

import java.util.List;

public interface CategoryService {
    /**
     * Create new category
     * @param category pojo
     */
    void add(Category category);

    /**
     * Get category list from current user
     * @return category list
     */
    List<Category> list();

    /**
     * Find category by its id
     * @param id unique category id
     * @return category pojo
     */
    Category findById(Integer id);

    /**
     * update category
     * @param category JSON converted to pojo
     */
    void update(Category category);

    /**
     * Delete category by its id
     * @param id category id
     */
    void delete(Integer id);
}
