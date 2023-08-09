package com.duo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duo.domain.ResponseResult;
import com.duo.domain.dto.LinkDto;
import com.duo.domain.entity.Link;
import com.duo.domain.vo.PageVo;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-07-27 16:48:30
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult<PageVo> Linklist(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult<?> addLink(LinkDto linkDto);

    ResponseResult<?> getLink(Long id);

    ResponseResult<?> updateLink(LinkDto linkDto);

    ResponseResult<?> deleteLink(Long id);
}
