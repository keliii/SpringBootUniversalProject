package com.keliii.user.shiro;

import com.keliii.user.service.PermissionService;
import com.keliii.user.shiro.interfaces.BasePermissionService;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by keliii on 2017/6/20.
 */
@Configuration
public class ShiroConfig {

    //@Qualifier

    /**
     * 缓存管理
     * @return
     */
    @Bean
    public EhCacheManager getEhCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return cacheManager;
    }

    /**
     * 凭证匹配器
     * @param cacheManager
     * @return
     */
    @Bean
    public RetryLimitHashedCredentialsMatcher getRetryLimitHashedCredentialsMatcher(EhCacheManager cacheManager) {
        RetryLimitHashedCredentialsMatcher matcher = new RetryLimitHashedCredentialsMatcher(cacheManager);
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(2);
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }

    /**
     * Realm实现
     * @param matcher
     * @return
     */
    @Bean
    public UserRealm getUserRealm(RetryLimitHashedCredentialsMatcher matcher) {
        UserRealm ur = new UserRealm();
        ur.setCredentialsMatcher(matcher);
        return ur;
    }

    /**
     * 安全管理器
     * @param realm
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(UserRealm realm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm);
        return manager;
    }

    /**
     * LifecycleBeanPostProcessor用于在实现了Initializable接口的Shiro bean初始化时调用Initializable接口回调，
     * 在实现了Destroyable接口的Shiro bean销毁时调用 Destroyable接口回调。
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

    /**
     * 加载shiroFilter权限控制规则（从数据库读取然后配置）
     *
     * @author SHANHY
     * @create  2016年1月14日
     */
    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean){
        /////////////////////// 下面这些规则配置最好配置到配置文件中 ///////////////////////
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
//        // authc：该过滤器下的页面必须验证后才能访问，它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
//        filterChainDefinitionMap.put("/shiro/test1", "authc");// 这里为了测试，只限制/user，实际开发中请修改为具体拦截的请求规则
//        // anon：它对应的过滤器里面是空的,什么都没做
//        // logger.info("##################从数据库读取权限规则，加载到shiroFilter中##################");
//        filterChainDefinitionMap.put("/shiro/test2", "authc,perms[shiro:test2]");// 这里为了测试，固定写死的值，也可以从数据库或其他配置中读取
//        filterChainDefinitionMap.put("/shiro/login", "anon");
//        filterChainDefinitionMap.put("/**", "anon");//anon 可以理解为不拦截

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager, BasePermissionService permissionService) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new MShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/shiro/login");
        // 登录成功后要跳转的连接
        shiroFilterFactoryBean.setSuccessUrl("/keliii/news");
        shiroFilterFactoryBean.setUnauthorizedUrl("/shiro/403");
        loadShiroFilterChain(shiroFilterFactoryBean);
        return shiroFilterFactoryBean;
    }


}
