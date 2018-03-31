package com.heima.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-11
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonCompanyQuery implements Serializable {
    private String token;
    private String companyId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
