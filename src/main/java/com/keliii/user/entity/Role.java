package com.keliii.user.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by keliii on 2017/6/20.
 */
@Entity
@Table(name = "t_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String rolename;

    private String description;

//    @OneToMany(mappedBy = "role", fetch=FetchType.EAGER)
//    private List<ControllerUrl> controllerUrlList;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "t_role_url", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
            @JoinColumn(name = "url_id") })
    private List<ControllerUrl> controllerUrlList;

    @ManyToMany
    @JoinTable(name = "t_user_role", joinColumns = {@JoinColumn(name = "rolename")}, inverseJoinColumns = {
            @JoinColumn(name = "user_id")})
    private List<User> userList;// 一个角色对应多个用户

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ControllerUrl> getControllerUrlList() {
        return controllerUrlList;
    }

    public void setControllerUrlList(List<ControllerUrl> controllerUrlList) {
        this.controllerUrlList = controllerUrlList;
    }
}
