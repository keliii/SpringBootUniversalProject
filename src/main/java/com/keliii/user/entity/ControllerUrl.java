package com.keliii.user.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by keliii on 2017/6/21.
 */
@Entity
@Table(name = "t_controller_url")
public class ControllerUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true, nullable = false)
    private String url;

    private String clazz;

    private String method;

    private String requestType;

    private Boolean isPublic;

    /**
     * > 0 locked
     */
    private Integer state;

    private String description;

//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    private Role role;

    @ManyToMany
    @JoinTable(name = "t_role_url", joinColumns = {@JoinColumn(name = "url_id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id")})
    private List<Role> role;

    public ControllerUrl() {
    }

    public ControllerUrl(String url, String clazz, String method, String requestType) {
        this.url = url;
        this.clazz = clazz;
        this.method = method;
        this.requestType = requestType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Boolean isPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }
}
