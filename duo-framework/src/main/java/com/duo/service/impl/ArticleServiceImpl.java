package com.duo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duo.constants.SystemConstants;
import com.duo.domain.ResponseResult;
import com.duo.domain.dto.AddArticleDto;
import com.duo.domain.dto.ArticleDto;
import com.duo.domain.entity.Article;
import com.duo.domain.entity.ArticleTag;
import com.duo.domain.entity.Category;
import com.duo.domain.vo.*;
import com.duo.enums.AppHttpCodeEnum;
import com.duo.exception.SystemException;
import com.duo.mapper.ArticleMapper;
import com.duo.mapper.ArticleTagMapper;
import com.duo.service.ArticleService;
import com.duo.service.ArticleTagService;
import com.duo.service.CategoryService;
import com.duo.utils.BeanCopyUtils;
import com.duo.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2023-07-26 14:30:51
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleTagService articleTagService;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Override
    @Transactional
    public ResponseResult hotArticleList() {
        //查询热门文章，封装ResponseResult返回
        //LambdaQueryWrapper是MyBatis-Plus框架中的一个类，用于构建数据库查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page<>(1,10);
        page(page,queryWrapper);
        //显示出来返回
        List<Article> articles = page.getRecords();
        //bean拷贝
        //它使用反射实现了Bean属性的复制,相同字段
//        List<HotArticleVo> articleVos = new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article,vo);
//            articleVos.add(vo);
//        }

        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(vs);
    }

    @Override
    @Transactional
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0,Article::getCategoryId,categoryId);
        // 状态是正式发布的
        queryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        queryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page  = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);

        List<Article> articles = page.getRecords();
        //查询categoryName
        //在这种情况下，改用 forEach 方法，而不是 map 方法。
        // forEach 方法接受一个 Consumer 对象，允许您对每个元素执行自定义的操作，而不需要返回一个新的流。
        //通过使用 forEach，您可以遍历每篇文章，为其设置对应的分类名称，而无需返回一个新的流。
        // 注意，在这种情况下，forEach 是一个终端操作，因此没有必要再调用 collect(Collectors.toList())，因为它不会返回一个新的流。
        // 修改后的代码将直接在原始的 articles 列表上进行更改。
        articles.stream()
                .forEach(article ->
                        article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()));
        //封装查询的结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    @Transactional
    public ResponseResult updateViewCount(Long id) {
        //更新redis的数据
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult add(AddArticleDto articleDto) {
        //添加 博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);

        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult adminArticleList(Integer pageNum, Integer pageSize, String title, String summary) {
        // 构建查询条件(模糊查询)
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(title)) {
            wrapper.like(Article::getTitle, title);
        }
        if (StringUtils.hasText(summary)) {
            wrapper.like(Article::getSummary, summary);
        }

        // 分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        IPage<Article> articlePage = articleMapper.selectPage(page, wrapper);

        // 组装响应数据
        PageVo pageVo = new PageVo(articlePage.getRecords(), articlePage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult adminArticleUpdateList(Long id) {
        // 根据ID查询文章信息
        Article article = articleMapper.selectById(id);
        if (article == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ARTICLE_NOT_EXIST);
        }
        List<Long> tags = articleTagMapper.selectList(new QueryWrapper<ArticleTag>()
                        .eq("article_id", id))
                        .stream()
                        .map(ArticleTag::getTagId)
                        .collect(Collectors.toList());
        ArticleTagVo articleTagVo = BeanCopyUtils.copyBean(article,ArticleTagVo.class);
        articleTagVo.setTags(tags);
        // 构建响应数据
        return ResponseResult.okResult(articleTagVo);
    }

    @Override
    @Transactional
    public ResponseResult adminArticleUpdate(ArticleDto articleDto) {
        // 判断文章是否存在
        Article existingArticle = articleMapper.selectById(articleDto.getId());
        if (existingArticle == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ARTICLE_NOT_EXIST);
        }
        // 第一步：更新文章
        BeanUtils.copyProperties(articleDto, existingArticle);
        articleMapper.updateById(existingArticle);

        // 第二步：更新标签
        // 删除duo_article_tag表中与文章关联的所有标签记录
        articleTagMapper.delete(new QueryWrapper<ArticleTag>().eq("article_id", articleDto.getId()));

        // 遍历ArticleDto中tags字段中的标签ID列表，为每个标签ID创建一个新的duo_article_tag记录
        List<Long> tagIds = articleDto.getTags();
        if (tagIds != null && !tagIds.isEmpty()) {
            for (Long tagId : tagIds) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleDto.getId());
                articleTag.setTagId(tagId);
                articleTagMapper.insert(articleTag);
            }
        }

        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult<?> deleteArtcle(Long id) {
        //判断有没有这个文章
        Article existingArticle = articleMapper.selectById(id);
        if (existingArticle == null) {
            throw new SystemException(AppHttpCodeEnum.ARTICLE_NOT_EXIST);
        }

        // 逻辑删除文章，将 delFlag 字段设置为 1
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId, existingArticle.getId())
                .set(Article::getDelFlag, 1);
        int success = articleMapper.update(existingArticle, updateWrapper);
        if (success > 0) {
            return ResponseResult.okResult();
        } else {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public void updateViewCountInDatabase(Map<String, Integer> viewCountMap) {
        for (Map.Entry<String, Integer> entry : viewCountMap.entrySet()) {
            Long idString = Long.valueOf(entry.getKey());
            Long viewCount = Long.valueOf(entry.getValue());
            try {
                Long id = Long.valueOf(idString);
                articleMapper.updateViewCountById(id,viewCount);
            } catch (NumberFormatException e) {
                System.err.println("Invalid ID format: " + idString);
            }
        }
    }

}
