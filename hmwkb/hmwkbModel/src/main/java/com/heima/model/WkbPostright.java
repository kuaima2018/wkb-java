package com.heima.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class WkbPostright implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 对应组织id
     */
    private Integer oId;

    /**
     * 岗位权限代码
     */
    private Integer pId;

    /**
     * 岗位权限名称
     */
    private String pName;

    /**
     * 权限值（1-全部,2-上级以及本部门,3-本部门,4-下级（默认值））
     */
    private Integer pRight;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date crtdatetime;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getoId() {
        return oId;
    }

    public void setoId(Integer oId) {
        this.oId = oId;
    }


    public Integer getpId() {
        return pId;
    }


    public void setpId(Integer pId) {
        this.pId = pId;
    }


    public String getpName() {
        return pName;
    }


    public void setpName(String pName) {
        this.pName = pName == null ? null : pName.trim();
    }


    public Integer getpRight() {
        return pRight;
    }


    public void setpRight(Integer pRight) {
        this.pRight = pRight;
    }


    public String getCreator() {
        return creator;
    }


    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }


    public Date getCrtdatetime() {
        return crtdatetime;
    }


    public void setCrtdatetime(Date crtdatetime) {
        this.crtdatetime = crtdatetime;
    }
}
