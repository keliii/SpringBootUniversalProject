package com.keliii.user.shiro.interfaces;

/**
 * Created by keliii on 2017/6/22.
 */

import com.keliii.user.entity.ControllerUrl;

import java.util.List;
import java.util.Set;

/**
 * 初始化时需要的permission接口
 */
public interface BasePermissionService {

    /**
     * 根据Url查找ControllerUrl记录
     * @param url
     * @return
     */
    ControllerUrl findControllerUrlByUrl(String url);

    /**
     * 保存ControllerUrl
     * @param controllerUrl
     * @return
     */
    ControllerUrl saveControllerUrl(ControllerUrl controllerUrl);

    /**
     * 查找所有
     * @return
     */
    List<ControllerUrl> findAllControllerUrl();

    /**
     * 更新Url的公共访问权限
     * @param id
     * @param isPublic
     * @return
     */
    int updatePublic(Integer id, Boolean isPublic);

    /**
     * 查找用户可访问的Url
     * @param username
     * @return
     */
    Set<ControllerUrl> findPermissions(String username);

    /**
     * 删除数据库中不存在于UrlList参数的ControllerUrl
     * @param urls
     * @return
     */
    int deleteNoExists(List<String> urls);
}
