package com.senpure.base.model;

import java.io.Serializable;

/**
 * @author senpure-generator
 * @version 2018-1-16 16:02:36
 */
public class URIPermission implements Serializable {
    private static final long serialVersionUID = 274982358L;

    //主键
    private Long id;
    //乐观锁，版本控制
    private Integer version;
    private String uriAndMethod;
    //外键,modelName:Permission,tableName:senpure_permission
    private Long permissionId;

    /**
     * get 主键
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * set 主键
     *
     * @return
     */
    public URIPermission setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUriAndMethod() {
        return uriAndMethod;
    }


    public URIPermission setUriAndMethod(String uriAndMethod) {
        this.uriAndMethod = uriAndMethod;
        return this;
    }

    /**
     * get 外键,modelName:Permission,tableName:senpure_permission
     *
     * @return
     */
    public Long getPermissionId() {
        return permissionId;
    }

    /**
     * set 外键,modelName:Permission,tableName:senpure_permission
     *
     * @return
     */
    public URIPermission setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
        return this;
    }

    /**
     * get 乐观锁，版本控制
     *
     * @return
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * set 乐观锁，版本控制
     *
     * @return
     */
    public URIPermission setVersion(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "URIPermission{"
                + "id=" + id
                + ",version=" + version
                + ",uriAndMethod=" + uriAndMethod
                + ",permissionId=" + permissionId
                + "}";
    }

}