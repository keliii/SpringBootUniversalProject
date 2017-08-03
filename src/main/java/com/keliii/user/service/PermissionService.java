package com.keliii.user.service;

import com.keliii.user.repository.ControllerUrlRepository;
import com.keliii.user.repository.RoleRepository;
import com.keliii.user.repository.UserRepository;
import com.keliii.user.entity.ControllerUrl;
import com.keliii.user.entity.Role;
import com.keliii.user.entity.User;
import com.keliii.user.shiro.interfaces.BasePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by keliii on 2017/6/20.
 */
@Service
public class PermissionService implements BasePermissionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ControllerUrlRepository controllerUrlRepository;


    public Set<Role> findRoles(String username) {
        User user = userRepository.findByUsername(username);
        user.getRoleList();
        return new HashSet<Role>(user.getRoleList());
    }

    @Override
    public ControllerUrl findControllerUrlByUrl(String url) {
        return controllerUrlRepository.findByUrl(url);
    }

    @Override
    public ControllerUrl saveControllerUrl(ControllerUrl url) {
        return controllerUrlRepository.saveAndFlush(url);
    }

    @Override
    public List<ControllerUrl> findAllControllerUrl() {
        return controllerUrlRepository.findAll();
    }

    @Override
    public int updatePublic(Integer id, Boolean isPublic) {
        return controllerUrlRepository.updatePublic(id,isPublic);
    }

    @Override
    public Set<ControllerUrl> findPermissions(String username) {
        Set<Role> roles = findRoles(username);
        Set<ControllerUrl> urls = new HashSet<>();
        for(Role role : roles) {
            urls.addAll(role.getControllerUrlList());
        }
        return urls;
    }

    @Override
    public int deleteNoExists(List<String> urls) {
        return controllerUrlRepository.deleteNoExists(urls);
    }


}
