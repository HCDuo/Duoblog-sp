package com.duo.controller;

import com.duo.annotation.SystemLog;
import com.duo.domain.ResponseResult;
import com.duo.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * 类别表(Category)表控制层
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/26 19:42
 */
@RestController
@RequestMapping("/category")
@Api(tags = "分类",description = "分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    @SystemLog(BusinessName = "获取文章分类信息")
    @ApiOperation(value = "获取文章分类信息",notes = "总页获取分类信息")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}
