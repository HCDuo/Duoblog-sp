package com.duo.controller;

import com.duo.annotation.SystemLog;
import com.duo.domain.ResponseResult;
import com.duo.domain.dto.TagDto;
import com.duo.domain.dto.TagListDto;
import com.duo.domain.entity.Tag;
import com.duo.domain.vo.PageVo;
import com.duo.domain.vo.TagVo;
import com.duo.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/31 16:44
 */
@RestController
@RequestMapping("/content/tag")
@Api(tags = "标签",description = "标签相关接口")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    @SystemLog(BusinessName = "展示标签分页信息")
    @ApiOperation(value = "展示标签分页信息",notes = "展示标签分页信息")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @PostMapping
    @SystemLog(BusinessName = "增加标签")
    @ApiOperation(value = "增加标签",notes = "增加标签")
    public ResponseResult<?> addTag(@Validated @RequestBody TagDto tagDtO) {
        return tagService.addTag(tagDtO);
    }

    @DeleteMapping("/{id}")
    @SystemLog(BusinessName = "删除标签")
    @ApiOperation(value = "删除标签",notes = "删除标签")
    public ResponseResult<?> deleteTag(@PathVariable("id") Long id) {
        return tagService.deleteTag(id);
    }

    @GetMapping("/{id}")
    @SystemLog(BusinessName = "根据id查找更新标签")
    @ApiOperation(value = "根据id查找更新标签",notes = "根据id查找更新标签")
    public ResponseResult getTagDetail(@PathVariable("id") Long id){
        return tagService.getTagDetail(id);
    }
    @PutMapping()
    @SystemLog(BusinessName = "更新标签")
    @ApiOperation(value = "更新标签",notes = "更新标签")
    public ResponseResult updateTag(@RequestBody Tag tag){
        return tagService.updateTag(tag);
    }
    @GetMapping("/listAllTag")
    @SystemLog(BusinessName = "显示标签列表")
    @ApiOperation(value = "显示标签列表",notes = "显示标签列表")
    public ResponseResult listAllTag(){
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }
}
