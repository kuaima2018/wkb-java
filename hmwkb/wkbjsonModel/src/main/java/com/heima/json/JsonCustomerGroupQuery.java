package com.heima.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by xuzhikai on 2015/7/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonCustomerGroupQuery extends JsonCustomerGroup implements Serializable {
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private Integer userId;
    private String token;
}
