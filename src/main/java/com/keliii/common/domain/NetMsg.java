package com.keliii.common.domain;

import java.util.Date;

/**
 * Created by keliii on 2017/6/22.
 */
public class NetMsg {

    private Integer result;
    private String msg;
    private Object data;
    private Object extraData;
    private Date serverTime;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getExtraData() {
        return extraData;
    }

    public void setExtraData(Object extraData) {
        this.extraData = extraData;
    }

    public Date getServerTime() {
        return new Date();
    }
}
