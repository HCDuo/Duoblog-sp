package com.duo.controller;


import com.duo.annotation.SystemLog;
import com.duo.domain.ResponseResult;
import com.duo.domain.entity.Article;
import com.duo.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章表(Article)表控制层
 *
 * @author makejava
 * @since 2023-07-26 14:30:41
 */
@RestController
@RequestMapping("/article")
@Api(tags = "文章",description = "文章相关接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @GetMapping("/list")
    @SystemLog(BusinessName = "展示文章全部信息")
    @ApiOperation(value = "文章列表",notes = "展示文章")
    public List<Article> test(){
        return articleService.list();
    }
    @GetMapping("/hotArticleList")
    @SystemLog(BusinessName = "展示热门文章部分信息")
    @ApiOperation(value = "热门文章列表",notes = "展示热门文章")
    public ResponseResult hotArticleList(){
        ResponseResult result =  articleService.hotArticleList();
        return result;
    }
    @GetMapping("/articleList")
    @SystemLog(BusinessName = "展示文章列表")
    @ApiOperation(value = "展示文章列表",notes = "获取文章")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }
    @GetMapping("/{id}")
    @SystemLog(BusinessName = "展示热门文章内容")
    @ApiOperation(value = "展示热门文章内容",notes = "展示热门文章")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    @SystemLog(BusinessName = "更新文章浏览量")
    @ApiOperation(value = "更新文章浏览量",notes = "更新文章浏览量")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }
}

