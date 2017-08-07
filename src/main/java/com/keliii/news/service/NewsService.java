package com.keliii.news.service;

import com.keliii.news.entity.News;
import com.keliii.news.mapper.NewsMapper;
import com.keliii.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * redis示例
 * Created by keliii on 2017/6/26.
 */
@Service
@Transactional
public class NewsService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private NewsRepository newsRepository;

    //    @Cacheable(value = "newsAll", keyGenerator = "wiselyKeyGenerator")
    @Cacheable(value = "news", key = "'newsAll'")
    public List<News> getAll() {
        System.out.println("mybatis查询");
        List<News> result = newsMapper.getAll();
        return result;
    }

    //    @CacheEvict(value = "newsAll", keyGenerator = "wiselyKeyGenerator", allEntries = true)
    @CacheEvict(value = "news", key = "'newsAll'")
    public News add(News news) {
        return newsRepository.saveAndFlush(news);
    }
}
