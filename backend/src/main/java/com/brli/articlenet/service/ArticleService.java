package com.brli.articlenet.service;

import com.brli.articlenet.model.Article;
import com.brli.articlenet.model.PageBean;

public interface ArticleService {
    /**
     * Add an article
     * @param article pojo
     */
    void add(Article article);

    /**
     * list articles by page
     * @param pageNum number of pages
     * @param pageSize page size
     * @param categoryId categoryId
     * @param state state
     * @return articles on current page
     */
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    /**
     * Find article by its own id
     * @param id article ID
     * @return article pojo
     */
    Article findById(Integer id);

    /**
     * Update user's article
     * @param article new article
     */
    void update(Article article);

    /**
     * Delete article
     * @param id by its ID
     */
    void delete(Integer id);
}
