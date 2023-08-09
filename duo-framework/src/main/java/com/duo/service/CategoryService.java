package com.duo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duo.domain.ResponseResult;
import com.duo.domain.dto.CategoryDto;
import com.duo.domain.entity.Category;
import com.duo.domain.vo.CategoryVo;
import com.duo.domain.vo.PageVo;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-07-26 18:32:26
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

    ResponseResult<PageVo> listCategory(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult<?> addCategory(CategoryDto categoryDto);

    ResponseResult<?> getCategoryById(Integer id);

    ResponseResult<?> updateCategory(CategoryDto categoryDto);

    ResponseResult<?> deleteCategory(Long id);
}
