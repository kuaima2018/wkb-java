package com.heima.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-10
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class WkbCompany implements Serializable {
    /**
     * 自动编号
     */
    private Integer id;

    /**
     * 公司编号
     */
    private String cId;

    /**
     * 公司名称
     */
    private String cName;

    /**
     * 公司联系人
     */
    private String cContact;

    /**
     * 公司手机
     */
    private String cMobile;

    /**
     * 公司电话
     */
    private String cTel;

    /**
     * 公司传真
     */
    private String cFax;

    /**
     * 公司邮箱
     */
    private String cEmail;

    /**
     * 公司地址
     */
    private String cAddr;

    /**
     * 公司邮编
     */
    private String cZipcode;

    /**
     * 公司行业
     */
    private String cIndustry;

    /**
     * 公司简介
     */
    private String cRemark;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date crtdatetime;

    /**
     * 更新者
     */
    private String updater;

    /**
     * 更新时间
     */
    private Date updatetime;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getcId() {
        return cId;
    }


    public void setcId(String cId) {
        this.cId = cId == null ? null : cId.trim();
    }


    public String getcName() {
        return cName;
    }


    public void setcName(String cName) {
        this.cName = cName == null ? null : cName.trim();
    }


    public String getcContact() {
        return cContact;
    }


    public void setcContact(String cContact) {
        this.cContact = cContact == null ? null : cContact.trim();
    }


    public String getcMobile() {
        return cMobile;
    }


    public void setcMobile(String cMobile) {
        this.cMobile = cMobile == null ? null : cMobile.trim();
    }


    public String getcTel() {
        return cTel;
    }


    public void setcTel(String cTel) {
        this.cTel = cTel == null ? null : cTel.trim();
    }


    public String getcFax() {
        return cFax;
    }


    public void setcFax(String cFax) {
        this.cFax = cFax == null ? null : cFax.trim();
    }


    public String getcEmail() {
        return cEmail;
    }


    public void setcEmail(String cEmail) {
        this.cEmail = cEmail == null ? null : cEmail.trim();
    }


    public String getcAddr() {
        return cAddr;
    }


    public void setcAddr(String cAddr) {
        this.cAddr = cAddr == null ? null : cAddr.trim();
    }


    public String getcZipcode() {
        return cZipcode;
    }


    public void setcZipcode(String cZipcode) {
        this.cZipcode = cZipcode == null ? null : cZipcode.trim();
    }

    public String getcIndustry() {
        return cIndustry;
    }

    public void setcIndustry(String cIndustry) {
        this.cIndustry = cIndustry;
    }

    public String getcRemark() {
        return cRemark;
    }

    public void setcRemark(String cRemark) {
        this.cRemark = cRemark;
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


    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}
