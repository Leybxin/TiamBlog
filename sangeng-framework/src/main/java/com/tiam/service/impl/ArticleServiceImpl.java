package com.tiam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiam.constants.SystemConstants;
import com.tiam.domain.ResponseResult;
import com.tiam.domain.entity.Article;
import com.tiam.domain.entity.Category;
import com.tiam.domain.vo.ArticleListVo;
import com.tiam.domain.vo.ArticleVo;
import com.tiam.domain.vo.HotArticleVo;
import com.tiam.domain.vo.PageVo;
import com.tiam.service.ArticleService;
import com.tiam.mapper.ArticleMapper;
import com.tiam.service.CategoryService;
import com.tiam.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
* @author 67460
* @description 针对表【sg_article(文章表)】的数据库操作Service实现
* @createDate 2023-03-17 20:30:31
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

    @Resource
    private CategoryService categoryService;

    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 查询正常的文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 浏览量降序排序
        queryWrapper.orderByDesc(Article::getViewCount);
        Page<Article> page = new Page<>(1,10);
        page(page,queryWrapper);
        // 获取分页之后的结果
        List<Article> records = page.getRecords();
        // 将查询到到所有数据转化为所需要的vo数据
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(records, HotArticleVo.class);
        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // 查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 如果有categoryId那么就按照这个条件进行查询
        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        // 查询正式发布的文章状态
        queryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        // 按照是否置顶进行排序(将置顶的放到第一个)
        queryWrapper.orderByDesc(Article::getIsTop);
        // 分页查询
        IPage<Article> page = new Page<>(pageNum,pageSize);
        page = page(page, queryWrapper);
        // 有categoryId那么就根据这个ID查询相应的字段名称
        List<Article> articleList = page.getRecords();
        articleList = articleList.stream()
                .map(article -> {
                    String name = categoryService.getById(article.getCategoryId()).getName();
                    article.setCategoryName(name);
                    return article;
                })
                .collect(Collectors.toList());
        // 封装vo并返回
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Integer id) {
        // 查询指定文章的详情
        Article articleDetail = getById(id);
        // 封装Vo
        ArticleVo articleVo = BeanCopyUtils.copyBean(articleDetail, ArticleVo.class);
        // 根据id查询分类名称
        Category category = categoryService.getById(categoryService.getById(articleVo.getCategoryId()));
        if (category != null) {
            articleVo.setCategoryName(category.getName());
        }
        // 返回结果数据
        return ResponseResult.okResult(articleVo);
    }
}




