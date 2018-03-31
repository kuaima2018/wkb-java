package com.heima.json;

import java.io.Serializable;

/**
 * Created by xuzhikai on 2015/7/20.
 */
public class JsonSociality implements Serializable {
    private String userImageUrl;
    private String userCompany;
    private String userName;
    private String userPostion;
    private Integer userId;

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPostion() {
        return userPostion;
    }

    public void setUserPostion(String userPostion) {
        this.userPostion = userPostion;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
