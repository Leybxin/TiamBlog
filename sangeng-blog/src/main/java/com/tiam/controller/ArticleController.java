package com.tiam.controller;


import com.tiam.domain.ResponseResult;
import com.tiam.domain.entity.Article;
import com.tiam.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


/*    @GetMapping("/getAllArticle")
    public List<Article> getAllArticle(){
        return articleService.list();
    }*/

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
       return articleService.hotArticleList();
    }

    @GetMapping("/articleList")
    public ResponseResult ArticleList(Integer pageNum,Integer pageSize, Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable Integer id){
        return articleService.getArticleDetail(id);
    }
}
