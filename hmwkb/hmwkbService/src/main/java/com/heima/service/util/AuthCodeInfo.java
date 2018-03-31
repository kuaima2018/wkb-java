package com.heima.service.util;

import com.google.code.ssm.api.CacheKeyMethod;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-1
 */
public class AuthCodeInfo implements Serializable {
    @CacheKeyMethod
    public String getKey() {
        return StringUtils.isEmpty(key)?" ":key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }

    private String authcode;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Date date;

}
