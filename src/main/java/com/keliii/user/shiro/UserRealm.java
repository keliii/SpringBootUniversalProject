package com.keliii.user.shiro;

import com.keliii.user.entity.ControllerUrl;
import com.keliii.user.entity.Role;
import com.keliii.user.entity.User;
import com.keliii.user.service.PermissionService;
import com.keliii.user.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by keliii on 2017/6/20.
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;

    /**
     * 提供用户信息返回权限信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 根据用户名查询当前用户拥有的角色
        Set<Role> roles = permissionService.findRoles(username);
        Set<String> roleNames = new HashSet<String>();
        for (Role role : roles) {
            roleNames.add(role.getRolename());
        }
        // 将角色名称提供给info
        authorizationInfo.setRoles(roleNames);
        // 根据用户名查询当前用户权限
        Set<ControllerUrl> permissions = permissionService.findPermissions(username);
        Set<String> permissionNames = new HashSet<String>();
        for (ControllerUrl url : permissions) {
            permissionNames.add(url.getId()+"");
        }
        // 将权限名称提供给info
        authorizationInfo.setStringPermissions(permissionNames);

        return authorizationInfo;
    }

    /**
     * 提供账户信息返回认证信息
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        User user = userService.findByUsername(username);
        if (user == null) {
            // 用户名不存在抛出异常
            throw new UnknownAccountException();
        }
        if (user.getState() == 0) {
            // 用户被管理员锁定抛出异常
            throw new LockedAccountException();
        }

        System.out.println("getName() -> " + getName());
        System.out.println("username -> " + username);
        System.out.println("password -> " + user.getPassword());
        System.out.println("salt -> " + user.getSalt());

        System.out.println("#####加密后#####");
        PasswordUtil pUtil = new PasswordUtil();
        String salt = pUtil.getSalt();
        System.out.println("salt -> " + salt);
        System.out.println("password -> " + pUtil.encryptPassword(user.getPassword(),salt));


        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(),
                user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());


        return authenticationInfo;
    }
}
