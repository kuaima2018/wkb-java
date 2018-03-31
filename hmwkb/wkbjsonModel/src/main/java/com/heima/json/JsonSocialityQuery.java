package com.heima.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.heima.model.SocialitySearch;

import java.io.Serializable;

/**
 * Created by xuzhikai on 2015/7/20.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonSocialityQuery extends SocialitySearch implements Serializable {
    private Integer index;
    private Integer pageSize;
    private Integer userId;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
