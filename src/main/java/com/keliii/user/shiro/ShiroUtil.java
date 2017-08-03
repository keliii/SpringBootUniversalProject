package com.keliii.user.shiro;

import com.keliii.common.util.StringUtil;
import com.keliii.user.domain.ShiroUrlFilter;
import com.keliii.user.entity.ControllerUrl;
import com.keliii.user.shiro.interfaces.BasePermissionService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by keliii on 2017/6/21.
 */
public class ShiroUtil {

    public static void updateFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean,
                                         List<ShiroUrlFilter> shiroUrlFilterList) throws Exception {
        AbstractShiroFilter abstractShiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
        FilterChainResolver filterChainResolver = abstractShiroFilter.getFilterChainResolver();
        PathMatchingFilterChainResolver pathMatchingFilterChainResolver = (PathMatchingFilterChainResolver) filterChainResolver;
        DefaultFilterChainManager defaultFilterChainManager = (DefaultFilterChainManager) pathMatchingFilterChainResolver.getFilterChainManager();

        Map<String, NamedFilterList> defaultFilterChains = defaultFilterChainManager.getFilterChains();
        defaultFilterChainManager.getFilterChains().clear();
        if (defaultFilterChainManager != null) {
            defaultFilterChainManager.getFilterChains().putAll(defaultFilterChains);
        }
        for (ShiroUrlFilter urlFilter : shiroUrlFilterList) {
            if(urlFilter.getAnon() != null && urlFilter.getAnon()) {
                defaultFilterChainManager.addToChain(urlFilter.getUrl(), "anon");
            } else {
                if (!StringUtil.nil(urlFilter.getPermissions())) {
                    defaultFilterChainManager.addToChain(urlFilter.getUrl(), "perms", urlFilter.getPermissions());
                }
            }
        }
    }

    public static void updateFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean,
                                         BasePermissionService service) throws Exception {
        List<ControllerUrl> urlList = service.findAllControllerUrl();
        List<ShiroUrlFilter> shiroUrlFilterList = new ArrayList<>();
        for (ControllerUrl url : urlList) {
            if(url.isPublic()) {
                shiroUrlFilterList.add(new ShiroUrlFilter(url.getUrl(),true));
            } else {
                shiroUrlFilterList.add(new ShiroUrlFilter(url.getUrl(), url.getId() + ""));
            }
        }

        //非controller配置的放通
        shiroUrlFilterList.add(new ShiroUrlFilter("/**", true));
        updateFilterChain(shiroFilterFactoryBean,shiroUrlFilterList);
    }


}
