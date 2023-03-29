package com.tiam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiam.constants.SystemConstants;
import com.tiam.domain.ResponseResult;
import com.tiam.domain.entity.Link;
import com.tiam.domain.vo.LinkListVo;
import com.tiam.service.LinkService;
import com.tiam.mapper.LinkMapper;
import com.tiam.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 67460
* @description 针对表【sg_link(友链)】的数据库操作Service实现
* @createDate 2023-03-24 16:18:15
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
    implements LinkService{

    @Override
    public ResponseResult getAllLink() {
        // 查询所有的审核通过的友联
        LambdaQueryWrapper<Link> qw = new LambdaQueryWrapper<>();
        qw.eq(Link::getStatus, SystemConstants.LINK_PASS);
        List<Link> linkList = list(qw);
        // 封装成Vo
        List<LinkListVo> linkListVos = BeanCopyUtils.copyBeanList(linkList, LinkListVo.class);
        return ResponseResult.okResult(linkListVos);
    }
}




