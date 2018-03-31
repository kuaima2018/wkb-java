package com.heima.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class WkbOrganization implements Serializable {
    /**
     * 自动编号
     */
    private Integer id;

    /**
     * 组织标识
     */
    private String oId;

    /**
     * 公司标识
     */
    private String cId;

    /**
     * 组织名称
     */
    private String oName;

    /**
     * 父组织编号
     */
    private Integer oFatherId;

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

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }


    public String getcId() {
        return cId;
    }


    public void setcId(String cId) {
        this.cId = cId == null ? null : cId.trim();
    }


    public String getoName() {
        return oName;
    }


    public void setoName(String oName) {
        this.oName = oName == null ? null : oName.trim();
    }


    public Integer getoFatherId() {
        return oFatherId;
    }


    public void setoFatherId(Integer oFatherId) {
        this.oFatherId = oFatherId;
    }


    public String getCreator() {
        return creator;
    }


    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getCrtdatetime() {
        return crtdatetime;
    }

    public void setCrtdatetime(Date crtdatetime) {
        this.crtdatetime = crtdatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WkbOrganization that = (WkbOrganization) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
