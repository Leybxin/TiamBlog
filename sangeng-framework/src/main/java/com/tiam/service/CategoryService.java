package com.tiam.service;

import com.tiam.domain.ResponseResult;
import com.tiam.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 67460
* @description 针对表【sg_category(分类表)】的数据库操作Service
* @createDate 2023-03-19 10:00:16
*/
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
