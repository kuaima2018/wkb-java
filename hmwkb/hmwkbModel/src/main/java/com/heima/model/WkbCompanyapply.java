package com.heima.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class WkbCompanyapply implements Serializable {
    /**
     */
    private Integer id;

    /**
     */
    private String cId;

    /**
     */
    private String cName;

    /**
     */
    private String cContact;

    /**
     */
    private String cMobile;

    /**
     */
    private String cTel;

    /**
     */
    private String cFax;

    /**
     */
    private String cEmail;

    /**
     */
    private String cAddr;

    /**
     */
    private String cZipcode;

    /**
     */
    private Integer creator;

    /**
     */
    private Date crtdatetime;

    /**
     * 申请状态 0-申请 1-批准 2-拒绝
     */
    private String status;

    /**
     */
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


    public Integer getCreator() {
        return creator;
    }


    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getCrtdatetime() {
        return crtdatetime;
    }


    public void setCrtdatetime(Date crtdatetime) {
        this.crtdatetime = crtdatetime;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}
