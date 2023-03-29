package com.tiam.service;

import com.tiam.domain.ResponseResult;
import com.tiam.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 67460
* @description 针对表【sg_article(文章表)】的数据库操作Service
* @createDate 2023-03-17 20:30:31
*/
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();


    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Integer id);
}
