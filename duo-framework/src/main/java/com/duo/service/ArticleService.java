package com.duo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duo.domain.ResponseResult;
import com.duo.domain.dto.AddArticleDto;
import com.duo.domain.dto.ArticleDto;
import com.duo.domain.entity.Article;

import java.util.Map;

/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2023-07-26 14:30:50
 */
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);

    ResponseResult adminArticleList(Integer pageNum, Integer pageSize, String title, String summary);

    ResponseResult adminArticleUpdateList(Long id);

    ResponseResult adminArticleUpdate(ArticleDto articleDto);

    ResponseResult<?> deleteArtcle(Long id);

    void updateViewCountInDatabase(Map<String, Integer> viewCountMap);
}

