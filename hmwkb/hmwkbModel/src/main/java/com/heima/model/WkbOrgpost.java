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
public class WkbOrgpost implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 权限id（对应wkb_postright主键）
     */
    private Integer pId;

    /**
     * 用户id
     */
    private Integer uId;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建日期
     */
    private Date crtdatetime;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
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
