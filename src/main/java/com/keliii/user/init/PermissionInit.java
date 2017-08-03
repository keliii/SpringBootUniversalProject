package com.keliii.user.init;

import com.keliii.common.util.StringUtil;
import com.keliii.user.annotation.Permission;
import com.keliii.user.domain.RequestToMethodItem;
import com.keliii.user.domain.ShiroUrlFilter;
import com.keliii.user.entity.ControllerUrl;
import com.keliii.user.service.PermissionService;
import com.keliii.user.shiro.MShiroFilterFactoryBean;
import com.keliii.user.shiro.ShiroUtil;
import com.keliii.user.shiro.interfaces.BasePermissionService;
import netscape.security.Privilege;
import org.apache.shiro.web.filter.mgt.*;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by keliii on 2017/6/21.
 */
public class PermissionInit implements ApplicationListener<ContextRefreshedEvent>, ServletContextListener {

    private List<RequestToMethodItem> requestList;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("####################################");
        System.out.println("#          contextInitialized      #");
        System.out.println("####################################");

        requestList = new ArrayList<>();
        WebApplicationContext appContext = WebApplicationContextUtils
                .getWebApplicationContext(servletContextEvent.getServletContext());

        //获取所有的RequestMapping
        Map<String, HandlerMapping> allRequestMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(appContext,
                HandlerMapping.class, true, false);

        for (HandlerMapping handlerMapping : allRequestMappings.values()) {
            RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
            for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet()) {
                RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
                HandlerMethod mappingInfoValue = requestMappingInfoHandlerMethodEntry.getValue();
                RequestMethodsRequestCondition methodCondition = requestMappingInfo.getMethodsCondition();
                Set<RequestMethod> methods = methodCondition.getMethods();
                String requestType = "";
                if (methods.size() > 0)
                    requestType = methodCondition.getMethods().iterator().next().name();
                PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
                String requestUrl = patternsCondition.getPatterns().iterator().next();
                String controllerName = mappingInfoValue.getBeanType().getName();
                String requestMethodName = mappingInfoValue.getMethod().getName();
                Class<?>[] methodParamTypes = mappingInfoValue.getMethod().getParameterTypes();
                RequestToMethodItem item = new RequestToMethodItem(requestUrl, requestType, controllerName,
                        requestMethodName, methodParamTypes);
                requestList.add(item);
            }
            break;
        }
        // 以上获取Spring MCV中所有URL参考于http://www.cnblogs.com/yuananyun/archive/2014/08/25/3934371.html
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("####################################");
        System.out.println("#          onApplicationEvent      #");
        System.out.println("####################################");

        if (requestList != null) {
            ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();

            BasePermissionService permissionService = applicationContext.getBean(BasePermissionService.class);

            List<String> requestUrls = new ArrayList<>();
            for (RequestToMethodItem item : requestList) {
                boolean isPublic = false;
                try {
                    Class cls = Class.forName(item.controllerName);
                    Method method = cls.getMethod(item.methodName,item.methodParmaTypes);
                    if (method.isAnnotationPresent(Permission.class)) {
                        Permission permission = method.getAnnotation(Permission.class);
                        isPublic = permission.isPublic();
                        if(!StringUtil.nil(permission.url()))
                            item.requestUrl = permission.url();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                ControllerUrl url = permissionService.findControllerUrlByUrl(item.requestUrl);
                if (url == null) {
//                    System.out.println(item.requestUrl + ":" + item.controllerName + ":" + item.methodName);
                    url = new ControllerUrl(item.requestUrl, item.controllerName, item.methodName, item.requestType);
                    url.setPublic(isPublic);
                    permissionService.saveControllerUrl(url);
                } else {
                    if(url.getState() == null || url.getState() <= 0) {
                        permissionService.updatePublic(url.getId(),isPublic);
                    }
                }

                requestUrls.add(item.requestUrl);
            }

            //删除不存在的URL
            permissionService.deleteNoExists(requestUrls);

            MShiroFilterFactoryBean shiroFilterFactoryBean = applicationContext.getBean(MShiroFilterFactoryBean.class);
            List<ControllerUrl> urlList = permissionService.findAllControllerUrl();
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

//            System.out.println("####################################");
//            for (ShiroUrlFilter filter : shiroUrlFilterList) {
//                System.out.println(filter.getUrl()+":"+filter.getPermissions());
//            }

            try {
                ShiroUtil.updateFilterChain(shiroFilterFactoryBean, shiroUrlFilterList);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("权限配置失败。");
            }


        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("####################################");
        System.out.println("#          contextDestroyed        #");
        System.out.println("####################################");
    }
}
