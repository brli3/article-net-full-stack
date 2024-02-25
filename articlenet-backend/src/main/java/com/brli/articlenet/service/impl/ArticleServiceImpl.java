package com.brli.articlenet.service.impl;

import com.brli.articlenet.mapper.ArticleMapper;
import com.brli.articlenet.service.ArticleService;
import com.brli.articlenet.service.UserService;
import com.brli.articlenet.model.Article;
import com.brli.articlenet.model.PageBean;
import com.brli.articlenet.utils.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;

    private final UserService userService;

    public ArticleServiceImpl(ArticleMapper articleMapper, UserService userService) {
        this.articleMapper = articleMapper;
        this.userService = userService;
    }

    @Override
    public void add(Article article) {
        String title = StringUtil.removeExcessiveSpaces(article.getTitle());
        article.setTitle(title);
        String content = StringUtil.removeExcessiveSpaces(article.getContent());
        article.setContent(content);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        article.setCreateUser(userService.getIdFromLoginUser());
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        // 1. create PageBean object to encapsulate result after searching
        PageBean<Article> pb = new PageBean<>();
        // 2. enable paging (pageHelper plugin)
        PageHelper.startPage(pageNum, pageSize);
        // 3. call dao
        Integer userId = userService.getIdFromLoginUser();
        List<Article> articles = articleMapper.list(userId, categoryId, state);
        // Page has methods to show total records and data from current page
        Page<Article> page = (Page<Article>) articles;
        // put data into PageBean
        pb.setTotal(page.getTotal());
        pb.setItems(page.getResult());
        return pb;
    }

    @Override
    public Article findById(Integer id) {
        Integer userId = userService.getIdFromLoginUser();
        return articleMapper.findById(id, userId);
    }

    @Override
    public void update(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }
}
