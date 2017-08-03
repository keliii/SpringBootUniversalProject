package com.keliii.user.domain;

/**
 * Created by keliii on 2017/6/21.
 */
public class ShiroUrlFilter {

    private String url;

    private Boolean anon;

    private String permissions;

    public ShiroUrlFilter(String url, Boolean anon) {
        this.url = url;
        this.anon = anon;
    }

    public ShiroUrlFilter(String url, String permissions) {
        this.url = url;
        this.permissions = permissions;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public Boolean getAnon() {
        return anon;
    }

    public void setAnon(Boolean anon) {
        this.anon = anon;
    }
}
