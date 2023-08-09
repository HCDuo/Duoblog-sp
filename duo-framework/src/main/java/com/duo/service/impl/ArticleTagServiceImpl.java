package com.duo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duo.domain.entity.ArticleTag;
import com.duo.mapper.ArticleTagMapper;
import com.duo.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-08-02 17:48:39
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}
