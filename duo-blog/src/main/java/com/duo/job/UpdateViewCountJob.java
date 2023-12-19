package com.duo.job;

import com.duo.service.ArticleService;
import com.duo.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <pre>
 * UpdateViewCountJob 是一个定时任务，每小时的整点时刻执行一次，用于将 Redis 中存储的文章浏览量数据更新到数据库中。
 * 它通过批量更新操作将数据更新到数据库中，从而保持数据库中的浏览量与 Redis 中的数据同步
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/30 1:52
 */
@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0 0 * * * ?")
    public void updateViewCount(){
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
//        System.out.println(viewCountMap);
//        List<Article> articles = viewCountMap.entrySet()
//                .stream()
//                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
//                .collect(Collectors.toList());
//        //更新到数据库中
//        System.out.println(articles);
//        //articleService.updateBatchById(articles);
        articleService.updateViewCountInDatabase(viewCountMap);
    }
}
