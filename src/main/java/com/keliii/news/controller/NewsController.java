package com.keliii.news.controller;

import com.keliii.common.config.AMQConfig;
import com.keliii.common.service.AMQService;
import com.keliii.common.service.RedisService;
import com.keliii.news.mapper.NewsMapper;
import com.keliii.news.repository.NewsRepository;
import com.keliii.news.entity.News;
import com.keliii.news.service.NewsService;
import com.keliii.user.annotation.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by keliii on 2017/6/5.
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private NewsService newsService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AMQService amqService;

    @RequestMapping("/news")
    @Permission(isPublic = true)
    public Map<String, String> news() {
        Map<String, String> result = new HashMap<>();
        result.put("品三国", "易中天");
        result.put("舌尖上的中国", "CCTV-10");

//        List<News> jpaResult = newsRepository.findAll();
        List<News> jpaResult = newsService.getAll();
        for (News news : jpaResult) {
            result.put(news.getTitle(), news.getWriter());
        }
        return result;
    }

    @RequestMapping("/news/save")
    @Permission(isPublic = true)
    public News news_add() {
        News news = new News();
        int i = 0;
        Random random = new Random(System.currentTimeMillis());
        i = random.nextInt();
        news.setTitle("title:"+i);
        news.setWriter("writer:"+i);
        return newsService.add(news);
    }

    @RequestMapping("/redis/set")
    @Permission(isPublic = true)
    public String redis_set(String key, String value) {
        boolean result = redisService.set(key,value);
        return "set "+key+":"+value+"  "+result;
    }

    @RequestMapping("/redis/get")
    @Permission(isPublic = true)
    public String redis_get(String key) {
        return redisService.get(key);
    }

    @RequestMapping("/mq/send")
    @Permission(isPublic = true)
    public String mq_send(String message) {
        amqService.sendMessage(AMQConfig.UNIVERSAL_TEST_MQ,message);
        return message;
    }


}
