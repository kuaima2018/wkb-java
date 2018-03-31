package com.heima.security.model;

import com.google.code.ssm.api.CacheKeyMethod;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-15
 * Time: 下午5:32
 * To change this template use File | Settings | File Templates.
 */
public class MemServiceUser extends UserIdentity {
    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    @CacheKeyMethod
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 登录时间
     */
    private Date loginDate;
    /**
     * 登录IP
     */
    private String userIp;
    /**
     * 登录token
     */
    private String token;
    /**
     * 更新日期
     */
    private Date updateDate;
}
