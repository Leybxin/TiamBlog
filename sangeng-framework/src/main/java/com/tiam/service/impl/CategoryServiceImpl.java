package com.tiam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiam.constants.SystemConstants;
import com.tiam.domain.ResponseResult;
import com.tiam.domain.entity.Article;
import com.tiam.domain.entity.Category;
import com.tiam.domain.vo.CategoryVo;
import com.tiam.mapper.ArticleMapper;
import com.tiam.service.ArticleService;
import com.tiam.service.CategoryService;
import com.tiam.mapper.CategoryMapper;
import com.tiam.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 67460
* @description 针对表【sg_category(分类表)】的数据库操作Service实现
* @createDate 2023-03-19 10:00:16
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        // 查询文章是已发布的状态
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);

        // 获取文章分类id并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        // 根据id查询所有的分类列表
        List<Category> categories = listByIds(categoryIds);
        // 分类列表需要是正常的
        categories = categories.stream()
                .filter(category -> SystemConstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        // 将查询的数据封装成vo返回
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}




