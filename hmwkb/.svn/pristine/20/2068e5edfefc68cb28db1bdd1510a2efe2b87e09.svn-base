package com.heima.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.heima.model.WkbFeedback;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 2015/4/8
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonFeedbackQuery extends WkbFeedback implements Serializable {

    private String token;
    private Integer userId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
