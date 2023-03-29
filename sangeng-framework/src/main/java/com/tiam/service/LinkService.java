package com.tiam.service;

import com.tiam.domain.ResponseResult;
import com.tiam.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 67460
* @description 针对表【sg_link(友链)】的数据库操作Service
* @createDate 2023-03-24 16:18:15
*/
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
