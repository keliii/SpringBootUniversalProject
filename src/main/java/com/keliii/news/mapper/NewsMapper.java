package com.keliii.news.mapper;

import com.keliii.news.entity.News;

import java.util.List;

/**
 * Created by keliii on 2017/6/26.
 */
public interface NewsMapper {

    List<News> getAll();

    int save(News news);
}
