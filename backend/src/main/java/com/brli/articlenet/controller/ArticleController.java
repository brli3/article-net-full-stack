package com.brli.articlenet.controller;

import com.brli.articlenet.model.Article;
import com.brli.articlenet.model.PageBean;
import com.brli.articlenet.model.Result;
import com.brli.articlenet.service.ArticleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public Result<Void> add(@RequestBody @Validated Article article) {
        articleService.add(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(Integer pageNum,
                                          Integer pageSize,
                                          @RequestParam(required = false) Integer categoryId,
                                          @RequestParam(required = false) String state) {
        PageBean<Article> pb = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(pb);
    }

    @GetMapping("/detail")
    public Result<Article> detail(@RequestParam("id") Integer id) {
        Article article = articleService.findById(id);
        return Result.success(article);
    }

    @PutMapping
    public Result<Void> update(@RequestBody @Validated Article article) {
        articleService.update(article);
        return Result.success();
    }

    @DeleteMapping
    public Result<Void> delete(@RequestParam("id") Integer id) {
        articleService.delete(id);
        return Result.success();
    }

}
