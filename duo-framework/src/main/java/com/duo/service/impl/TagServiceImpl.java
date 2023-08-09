package com.duo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duo.domain.ResponseResult;
import com.duo.domain.dto.TagDto;
import com.duo.domain.dto.TagListDto;
import com.duo.domain.entity.Article;
import com.duo.domain.entity.Tag;
import com.duo.domain.vo.PageVo;
import com.duo.domain.vo.TagVo;
import com.duo.enums.AppHttpCodeEnum;
import com.duo.exception.SystemException;
import com.duo.mapper.ArticleMapper;
import com.duo.mapper.TagMapper;
import com.duo.service.TagService;
import com.duo.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-07-31 16:37:15
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    @Transactional
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        wrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,wrapper);
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult<?> addTag(TagDto tagDtO) {
        //判断标签是否存在
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getName,tagDtO.getName());
        int count = Math.toIntExact(tagMapper.selectCount(wrapper));
        if (count != 0) {
            throw new SystemException(AppHttpCodeEnum.TAG_EXIST);
        }
        //添加新标签
        Tag newTag = new Tag();
        newTag.setName(tagDtO.getName());
        newTag.setRemark(tagDtO.getRemark());
        newTag.setCreateTime(new Date());
        int success = tagMapper.insert(newTag);
        if (success > 0) {
            return ResponseResult.okResult();
        } else {
            throw new SystemException(AppHttpCodeEnum.TAG_ADD_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseResult<?> deleteTag(Long id) {
        // 标签下是否有文章
        int count = Math.toIntExact(articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .in(Article::getCategoryId, id)));
        //判断是否有文章，有就删除失败
        if (count > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.TAG_DELETE_ERROR, "标签下存在文章，无法删除");
        }
        //逻辑删除
        Tag existingTag = tagMapper.selectById(id);
        if (existingTag == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.TAG_NOT_FOUND);
        }
        // 假设标签对象中有一个名为 delFlag 的字段用于表示是否被删除，0 表示未删除，1 表示已删除
        LambdaUpdateWrapper<Tag> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Tag::getId, existingTag.getId())
                .set(Tag::getDelFlag, 1);
        int success = tagMapper.update(existingTag, updateWrapper);

        if (success > 0) {
            return ResponseResult.okResult();
        } else {
            throw new SystemException(AppHttpCodeEnum.TAG_DELETE_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseResult getTagDetail(Long id) {
        //根据id查询标签
        Tag tag = getById(id);
        //转换成VO
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        //封装响应返回
        return ResponseResult.okResult(tagVo);
    }

    @Override
    @Transactional
    public ResponseResult<?> updateTag(Tag tag) {
        // 判断标签是否存在
        Tag existTag = tagMapper.selectOne(new LambdaQueryWrapper<Tag>()
                .select(Tag::getId)
                .eq(Tag::getName, tag.getName()));
        // 标签不能为空
        if (tag.getName() == null) {
            throw new SystemException(AppHttpCodeEnum.NAME_NOT_NULL);
        }
        // 更新标签的更新时间
        tag.setUpdateTime(new Date());

        // 执行更新操作
        if (existTag != null && !existTag.getId().equals(tag.getId())) {
            // 已存在同名的标签，且不是当前编辑的标签
            throw new SystemException(AppHttpCodeEnum.TAG_NAME_EXIST);
        } else {
            // 更新标签
            boolean success = updateById(tag);
            if (success) {
                return ResponseResult.okResult();
            } else {
                throw new SystemException(AppHttpCodeEnum.TAG_UPDATE_ERROR);
            }
        }
    }

    @Override
    @Transactional
    public List<TagVo> listAllTag() {
        //查询数据
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        //封装数据
        List<Tag> list = list(wrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        //返回数据
        return tagVos;
    }
}
