package com.duo.controller;

import com.duo.annotation.SystemLog;
import com.duo.domain.ResponseResult;
import com.duo.domain.dto.LinkDto;
import com.duo.domain.vo.PageVo;
import com.duo.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/4 16:27
 */
@RestController
@RequestMapping("/content/link")
@Api(tags = "友链",description = "友链相关接口")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    @SystemLog(BusinessName = "友链分页列表")
    @ApiOperation(value = "友链分页列表",notes = "友链分页列表")
    public ResponseResult<PageVo> Linklist(Integer pageNum, Integer pageSize, String name, String status){
        return linkService.Linklist(pageNum,pageSize,name,status);
    }

    @PostMapping
    @SystemLog(BusinessName = "添加友链")
    @ApiOperation(value = "添加友链",notes = "添加友链")
    public ResponseResult<?> addLink(@RequestBody @Validated LinkDto linkDto) {
        return linkService.addLink(linkDto);
    }

    @GetMapping("/{id}")
    @SystemLog(BusinessName = "根据id查找更新友链")
    @ApiOperation(value = "根据id查找更新友链",notes = "根据id查找更新友链")
    public ResponseResult<?> getLink(@PathVariable("id") Long id) {
        return linkService.getLink(id);
    }

    @PutMapping
    @SystemLog(BusinessName = "更新友链")
    @ApiOperation(value = "更新友链",notes = "更新友链")
    public ResponseResult<?> updateLink(@RequestBody @Validated LinkDto linkDto) {
        return linkService.updateLink(linkDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(BusinessName = "删除友链")
    @ApiOperation(value = "删除友链",notes = "删除友链")
    public ResponseResult<?> deleteLink(@PathVariable("id") Long id) {
        return linkService.deleteLink(id);
    }
}
