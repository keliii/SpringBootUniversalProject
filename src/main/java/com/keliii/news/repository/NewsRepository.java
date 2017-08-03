package com.keliii.news.repository;

import com.keliii.news.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by keliii on 2017/6/5.
 */
public interface NewsRepository extends JpaRepository<News, String> {

}
