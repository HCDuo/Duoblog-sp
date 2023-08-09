package com.duo.runner;

import com.duo.domain.entity.Article;
import com.duo.mapper.ArticleMapper;
import com.duo.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <pre>
 * ViewCountRunner 是一个在应用启动时被自动执行的组件，它查询数据库中的博客信息，并将文章ID和浏览量数据存储到 Redis 缓存中，
 * 以便后续的定时任务能够使用这些数据进行更新操作。这样可以保证在应用启动时，Redis 缓存中已经有了最新的文章浏览量数据，避免数据同步问题
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/30 1:09
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //查询博客信息  id  viewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> {
                    return article.getViewCount().intValue();
                }));
        //存储到redis中
        redisCache.setCacheMap("article:viewCount",viewCountMap);
    }
}
