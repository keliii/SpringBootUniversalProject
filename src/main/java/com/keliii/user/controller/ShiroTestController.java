package com.keliii.user.controller;

import com.keliii.user.annotation.Permission;
import com.keliii.user.domain.RequestToMethodItem;
import com.keliii.user.domain.ShiroUrlFilter;
import com.keliii.user.entity.User;
import com.keliii.user.service.PermissionService;
import com.keliii.user.service.UserService;
import com.keliii.user.shiro.MShiroFilterFactoryBean;
import com.keliii.user.shiro.ShiroUtil;
import org.apache.commons.collections.SetUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by keliii on 2017/6/20.
 */
@RestController
@RequestMapping("/shiro")
public class ShiroTestController {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @RequestMapping("/login")
    @Permission(isPublic = true)
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        String result = "";
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException ice) {
            // 捕获密码错误异常
            return "password error!";
        } catch (UnknownAccountException uae) {
            // 捕获未知用户名异常
            return "username error";
        } catch (ExcessiveAttemptsException eae) {
            // 捕获错误登录过多的异常
            return "times error";
        }
        User user = userService.findByUsername(username);
        subject.getSession().setAttribute("user", user);
        return "success";
    }

    @RequestMapping("/test1")
    @Permission(isPublic = true)
    public String test1() {
        return "test1";
    }

    @RequestMapping("/test2")
    public String test2() {
        return "test2";
    }

    @RequestMapping("/test3")
    public String test3() {
        return "test3";
    }


    @RequestMapping("/403")
    @Permission(isPublic = true)
    public String error_403() {
        return "无权限访问";
    }


    @RequestMapping("/updateShiro")
    @Permission(isPublic = true)//这里时为了方便测试开放出来
    public String updateShiro() {
        try {
            ShiroUtil.updateFilterChain(shiroFilterFactoryBean,permissionService);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "shiro filterchain update success";
    }


    @RequestMapping("/getAllUrl")
    public List<RequestToMethodItem> getAllUrl(HttpServletRequest request) {
        ServletContext servletContext = request.getSession().getServletContext();
        if (servletContext == null)
        {
            return null;
        }
//        WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//        //请求url和处理方法的映射
//        List<RequestToMethodItem> requestToMethodItemList = new ArrayList<RequestToMethodItem>();
//        //获取所有的RequestMapping
//        Map<String, HandlerMapping> allRequestMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(appContext,
//                HandlerMapping.class, true, false);
//        for (HandlerMapping handlerMapping : allRequestMappings.values())
//        {
//            //本项目只需要RequestMappingHandlerMapping中的URL映射
//            if (handlerMapping instanceof RequestMappingHandlerMapping)
//            {
//                RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
//                Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
//                for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet())
//                {
//                    RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
//                    HandlerMethod mappingInfoValue = requestMappingInfoHandlerMethodEntry.getValue();
//
//                    RequestMethodsRequestCondition methodCondition = requestMappingInfo.getMethodsCondition();
//
////                    String requestType = SetUtils.first(methodCondition.getMethods()).name();
//                    Set<RequestMethod> methods = methodCondition.getMethods();
//                    String requestType = "";
//                    if(methods.size() > 0)
//                        requestType = methodCondition.getMethods().iterator().next().name();
//                    PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
////                    String requestUrl = SetUtils.first(patternsCondition.getPatterns());
//                    String requestUrl = patternsCondition.getPatterns().iterator().next();
//
//                    String controllerName = mappingInfoValue.getBeanType().toString();
//                    String requestMethodName = mappingInfoValue.getMethod().getName();
//                    Class<?>[] methodParamTypes = mappingInfoValue.getMethod().getParameterTypes();
//                    RequestToMethodItem item = new RequestToMethodItem(requestUrl, requestType, controllerName, requestMethodName,
//                            methodParamTypes);
//
//                    requestToMethodItemList.add(item);
//                }
//                break;
//            }
//        }
//
//        return requestToMethodItemList;
        return null;
    }


}
