package com.duo.controller;

import com.duo.annotation.SystemLog;
import com.duo.domain.ResponseResult;
import com.duo.domain.dto.AddArticleDto;
import com.duo.domain.dto.ArticleDto;
import com.duo.service.ArticleService;
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
 * @date:2023/8/2 17:30
 */
@RestController
@RequestMapping("/content/article")
@Api(tags = "文章",description = "文章相关接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    @SystemLog(BusinessName = "写博文")
    @ApiOperation(value = "添加文章",notes = "添加文章")
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }

    @GetMapping("/list")
    @SystemLog(BusinessName = "显示文章列表")
    @ApiOperation(value = "文章列表",notes = "文章列表")
    public ResponseResult adminArticleList(Integer pageNum,Integer pageSize,String title, String summary){
        return articleService.adminArticleList(pageNum,pageSize,title,summary);
    }

    @GetMapping("{id}")
    @SystemLog(BusinessName = "显示文章更新列表")
    @ApiOperation(value = "更新文章的显示信息列表",notes = "更新文章的显示信息列表")
    public ResponseResult adminArticleUpdateList(@PathVariable("id") Long id){
        return articleService.adminArticleUpdateList(id);
    }

    @PutMapping()
    @SystemLog(BusinessName = "文章更新")
    @ApiOperation(value = "更新文章",notes = "更新文章")
    public ResponseResult adminArticleUpdate(@Validated @RequestBody ArticleDto articleDto){
        return articleService.adminArticleUpdate(articleDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(BusinessName = "文章删除")
    @ApiOperation(value = "文章删除",notes = "删除文章")
    public ResponseResult<?> deleteTag(@PathVariable("id") Long id) {
        return articleService.deleteArtcle(id);
    }
}
