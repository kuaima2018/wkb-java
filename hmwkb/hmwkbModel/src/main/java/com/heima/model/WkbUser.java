package com.heima.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class WkbUser implements Serializable {
    /**
     * 用户编号(系统内部使用)
     */
    private Integer uId;

    /**
     * 用户账号（目前只支持手机号码）
     */
    private String uIdentifier;

    /**
     * 用户名称
     */
    private String uName;

    /**
     * 密码
     */
    private String uPwd;

    /**
     * 性别 0-女 1-男
     */
    private Byte uSex;

    /**
     * 生日
     */
    private Date uBrithday;

    /**
     * 用户职位
     */
    private String uTitle;

    /**
     * 手机
     */
    private String uMobile;

    /**
     * 电话
     */
    private String uTel;

    /**
     * 传真
     */
    private String uFax;

    /**
     * 邮箱
     */
    private String uEmail;

    /**
     * 地址
     */
    private String uAddr;

    /**
     * 编码
     */
    private String uZipcode;

    /**
     * 任务发送默认人
     */
    private Integer uDefault;

    /**
     * 公司代码
     */
    private String cId;

    /**
     * 用户公司（用户注册时，自己维护，等加入公司后，自动更新）
     */
    private String uCompany;

    /**
     * 用户收入
     */
    private String uIncome;

    @Deprecated
    /**
     *
     */
    private Integer oId;

    @Deprecated
    /**
     *
     */
    private Integer pId;

    /**
     * 公司标记 0-申请(未正式在岗位中) 1-正式（肯定已在岗位中了） 2-被退出岗位（其实等效与0）
     */
    private Byte okcFlag;

    /**
     * 管理员标记 0-普通员工 1-公司管理员 2-超级管理员
     */
    private Byte uAdmin;

    /**
     * 用户是否有效（1-有效 0或者其他情况都无效）
     */
    private Byte uOnoff;

    /**
     *
     */
    private String taskShare;

    /**
     *
     */
    private String uAppkey;

    /**
     *
     */
    private String uAppsecret;

    /**
     *
     */
    private String creator;

    /**
     *
     */
    private Date crtdatetime;

    /**
     *
     */
    private Date logintime;

    /**
     * 申请时间
     */
    private Date applytime;

    /**
     * 加入日期
     */
    private Date jointime;

    /**
     * 头像url
     */
    private String imageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getApplytime() {
        return applytime;
    }

    public void setApplytime(Date applytime) {
        this.applytime = applytime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getJointime() {
        return jointime;
    }

    public void setJointime(Date jointime) {
        this.jointime = jointime;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }


    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName == null ? null : uName.trim();
    }

    public String getuPwd() {
        return uPwd;
    }

    public void setuPwd(String uPwd) {
        this.uPwd = uPwd == null ? null : uPwd.trim();
    }

    public Byte getuSex() {
        return uSex;
    }


    public void setuSex(Byte uSex) {
        this.uSex = uSex;
    }


    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
    public Date getuBrithday() {
        return uBrithday;
    }


    public void setuBrithday(Date uBrithday) {
        this.uBrithday = uBrithday;
    }


    public String getuTitle() {
        return uTitle;
    }


    public void setuTitle(String uTitle) {
        this.uTitle = uTitle == null ? null : uTitle.trim();
    }


    public String getuMobile() {
        return uMobile;
    }


    public void setuMobile(String uMobile) {
        this.uMobile = uMobile == null ? null : uMobile.trim();
    }

    public String getuTel() {
        return uTel;
    }

    public void setuTel(String uTel) {
        this.uTel = uTel == null ? null : uTel.trim();
    }

    public String getuFax() {
        return uFax;
    }

    public void setuFax(String uFax) {
        this.uFax = uFax == null ? null : uFax.trim();
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail == null ? null : uEmail.trim();
    }

    public String getuAddr() {
        return uAddr;
    }

    public void setuAddr(String uAddr) {
        this.uAddr = uAddr == null ? null : uAddr.trim();
    }

    public String getuZipcode() {
        return uZipcode;
    }

    public void setuZipcode(String uZipcode) {
        this.uZipcode = uZipcode == null ? null : uZipcode.trim();
    }

    public Integer getuDefault() {
        return uDefault;
    }

    public void setuDefault(Integer uDefault) {
        this.uDefault = uDefault;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId == null ? null : cId.trim();
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

    public Byte getOkcFlag() {
        return okcFlag;
    }

    public void setOkcFlag(Byte okcFlag) {
        this.okcFlag = okcFlag;
    }

    public Byte getuAdmin() {
        return uAdmin;
    }

    public void setuAdmin(Byte uAdmin) {
        this.uAdmin = uAdmin;
    }

    public Byte getuOnoff() {
        return uOnoff;
    }

    public void setuOnoff(Byte uOnoff) {
        this.uOnoff = uOnoff;
    }

    public String getTaskShare() {
        return taskShare;
    }

    public void setTaskShare(String taskShare) {
        this.taskShare = taskShare == null ? null : taskShare.trim();
    }

    public String getuAppkey() {
        return uAppkey;
    }

    public void setuAppkey(String uAppkey) {
        this.uAppkey = uAppkey == null ? null : uAppkey.trim();
    }


    public String getuAppsecret() {
        return uAppsecret;
    }

    public void setuAppsecret(String uAppsecret) {
        this.uAppsecret = uAppsecret == null ? null : uAppsecret.trim();
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

    public Date getLogintime() {
        return logintime;
    }

    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }

    public String getuCompany() {
        return uCompany;
    }

    public void setuCompany(String uCompany) {
        this.uCompany = uCompany;
    }

    public String getuIdentifier() {
        return uIdentifier;
    }

    public void setuIdentifier(String uIdentifier) {
        this.uIdentifier = uIdentifier;
    }

    public String getuIncome() {
        return uIncome;
    }

    public void setuIncome(String uIncome) {
        this.uIncome = uIncome;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
