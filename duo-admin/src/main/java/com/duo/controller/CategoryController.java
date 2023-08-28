package com.duo.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.duo.annotation.SystemLog;
import com.duo.domain.ResponseResult;
import com.duo.domain.dto.CategoryDto;
import com.duo.domain.entity.Category;
import com.duo.domain.vo.CategoryVo;
import com.duo.domain.vo.ExcelCategoryVo;
import com.duo.domain.vo.PageVo;
import com.duo.enums.AppHttpCodeEnum;
import com.duo.service.CategoryService;
import com.duo.utils.BeanCopyUtils;
import com.duo.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/2 17:18
 */
@RestController
@RequestMapping("/content/category")
@Api(tags = "分类",description = "分类相关接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    @SystemLog(BusinessName = "分类列表")
    @ApiOperation(value = "分类列表",notes = "分类列表")
    public ResponseResult listAllCategory(){
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }
    @GetMapping("/list")
    @SystemLog(BusinessName = "分类分页列表")
    @ApiOperation(value = "分类分页列表",notes = "分类分页列表")
    public ResponseResult<PageVo> listCategory(Integer pageNum, Integer pageSize, String name, String status){
        return categoryService.listCategory(pageNum,pageSize,name,status);
    }

    @PostMapping
    @SystemLog(BusinessName = "添加分类")
    @ApiOperation(value = "添加分类",notes = "添加分类")
    public ResponseResult<?> addCategory(@RequestBody @Validated CategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @GetMapping("/{id}")
    @SystemLog(BusinessName = "根据id查找更新分类")
    @ApiOperation(value = "根据id查找更新分类",notes = "根据id查找更新分类")
    public ResponseResult<?> getCategory(@PathVariable("id") Integer id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping
    @SystemLog(BusinessName = "更新分类")
    @ApiOperation(value = "更新分类",notes = "更新分类")
    public ResponseResult<?> updateCategory(@RequestBody @Validated CategoryDto categoryDto) {
        return categoryService.updateCategory(categoryDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(BusinessName = "删除分类")
    @ApiOperation(value = "删除分类",notes = "删除分类")
    public ResponseResult<?> deleteCategory(@PathVariable("id") Long id) {
        return categoryService.deleteCategory(id);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

}
