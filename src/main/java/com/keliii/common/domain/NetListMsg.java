package com.keliii.common.domain;

import java.util.List;

/**
 * Created by keliii on 2017/6/22.
 */
public class NetListMsg<T> extends NetMsg {

    private List<T> rows;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 总记录数
     */
    private Long elements;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Long getElements() {
        return elements;
    }

    public void setElements(Long elements) {
        this.elements = elements;
    }
}
