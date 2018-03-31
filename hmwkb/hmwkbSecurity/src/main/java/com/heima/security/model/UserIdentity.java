package com.heima.security.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-6
 */
public class UserIdentity implements Serializable {
    private Integer userId;
    private String mobile;
    private String password;
    private String checkData;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getCheckData() {
        return checkData;
    }

    public void setCheckData(String checkData) {
        this.checkData = checkData;
    }
}
