package com.duo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duo.constants.SystemConstants;
import com.duo.domain.ResponseResult;
import com.duo.domain.dto.LinkDto;
import com.duo.domain.entity.Link;
import com.duo.domain.vo.LinkUpdateVo;
import com.duo.domain.vo.LinkVo;
import com.duo.domain.vo.PageVo;
import com.duo.enums.AppHttpCodeEnum;
import com.duo.exception.SystemException;
import com.duo.mapper.LinkMapper;
import com.duo.service.LinkService;
import com.duo.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-07-27 16:48:31
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Override
    @Transactional
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        //转换成vo
        List<Link> links = list(queryWrapper);
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links,LinkVo.class);
        //封装返回
        return ResponseResult.okResult(linkVos);
    }

    @Override
    @Transactional
    public ResponseResult<PageVo> Linklist(Integer pageNum, Integer pageSize, String name, String status) {
        // 构建查询条件(模糊查询)
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)) {
            wrapper.like(Link::getName, name);
        }

        // 分页查询
        Page<Link> page = new Page<>(pageNum, pageSize);
        IPage<Link> rolePage = linkMapper.selectPage(page, wrapper);

        // 组装响应数据
        PageVo pageVo = new PageVo(rolePage.getRecords(), rolePage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult<?> addLink(LinkDto linkDto) {
        //可以重复
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        link.setCreateTime(new Date());
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult<?> getLink(Long id) {
        Link Link = getById(id);
        if (Link == null) {
            throw new SystemException(AppHttpCodeEnum.LINK_NOT_EXIST);
        }
        LinkUpdateVo linkUpdateVo = BeanCopyUtils.copyBean(Link, LinkUpdateVo.class);
        return ResponseResult.okResult(linkUpdateVo);
    }

    @Override
    @Transactional
    public ResponseResult<?> updateLink(LinkDto linkDto) {
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        link.setUpdateTime(new Date());
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult<?> deleteLink(Long id) {
        //判断有没有这个角色
        Link link = linkMapper.selectById(id);
        if (link == null) {
            throw new SystemException(AppHttpCodeEnum.LINK_NOT_EXIST);
        }
        //逻辑删除
        LambdaUpdateWrapper<Link> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Link::getId,link.getId())
                .set(Link::getDelFlag,1);
        link.setUpdateTime(new Date());
        int success = linkMapper.update(link,updateWrapper);
        if (success > 0) {
            return ResponseResult.okResult();
        } else {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

}
