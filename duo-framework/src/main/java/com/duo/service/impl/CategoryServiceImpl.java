package com.duo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duo.constants.SystemConstants;
import com.duo.domain.ResponseResult;
import com.duo.domain.dto.CategoryDto;
import com.duo.domain.entity.Article;
import com.duo.domain.entity.Category;
import com.duo.domain.vo.CategoryUpdateVo;
import com.duo.domain.vo.CategoryVo;
import com.duo.domain.vo.PageVo;
import com.duo.enums.AppHttpCodeEnum;
import com.duo.exception.SystemException;
import com.duo.mapper.CategoryMapper;
import com.duo.service.ArticleService;
import com.duo.service.CategoryService;
import com.duo.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-07-26 18:32:27
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    @Transactional
    public ResponseResult getCategoryList() {
        //查询状态是已经发布的文章表
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的id
        Set<Long> categoryIds = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //返回vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories,CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    @Transactional
    public List<CategoryVo> listAllCategory() {
        //查询所有分类，判断是不是正常状态
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        //封装数据
        List<Category> list = list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        //返回数据
        return categoryVos;
    }

    @Override
    @Transactional
    public ResponseResult<PageVo> listCategory(Integer pageNum, Integer pageSize, String name, String status) {
        // 构建查询条件(模糊查询)
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)) {
            wrapper.like(Category::getName, name);
        }

        // 分页查询
        Page<Category> page = new Page<>(pageNum, pageSize);
        IPage<Category> rolePage = categoryMapper.selectPage(page, wrapper);

        // 组装响应数据
        PageVo pageVo = new PageVo(rolePage.getRecords(), rolePage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult<?> addCategory(CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        category.setCreateTime(new Date());
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult<?> getCategoryById(Integer id) {
        Category category = getById(id);
        if (category == null) {
            throw new SystemException(AppHttpCodeEnum.CATEGORY_NOT_ESIXT);
        }
        CategoryUpdateVo categoryUpdateVo = BeanCopyUtils.copyBean(category, CategoryUpdateVo.class);
        return ResponseResult.okResult(categoryUpdateVo);
    }

    @Override
    @Transactional
    public ResponseResult<?> updateCategory(CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        category.setUpdateTime(new Date());
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult<?> deleteCategory(Long id) {
        //判断有没有这个角色
        Category category = categoryMapper.selectById(id);
        if (category == null) {
            throw new SystemException(AppHttpCodeEnum.CATEGORY_NOT_ESIXT);
        }
        //逻辑删除
        LambdaUpdateWrapper<Category> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Category::getId,category.getId())
                .set(Category::getDelFlag,1);
        category.setUpdateTime(new Date());
        int success = categoryMapper.update(category,updateWrapper);
        if (success > 0) {
            return ResponseResult.okResult();
        } else {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }
}
