package com.heima.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonOrgQuery implements Serializable {
    private String token;
    private Integer includeFriend;
    private Integer userId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getIncludeFriend() {
        return includeFriend;
    }

    public void setIncludeFriend(Integer includeFriend) {
        this.includeFriend = includeFriend;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
