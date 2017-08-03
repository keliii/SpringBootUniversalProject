package com.keliii.news.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by keliii on 2017/6/5.
 */
@Entity
public class News {

    @Id
    private String title;
    private String writer;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
