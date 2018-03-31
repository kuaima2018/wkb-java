package com.heima.model;

import com.google.code.ssm.api.CacheKeyMethod;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-9
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class SessionRegister implements Serializable {
    @CacheKeyMethod
    public String getSessionId() {
        return StringUtils.isBlank(sessionId)?" ":sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    private String sessionId;
    private Date loginDate;
    private Integer uId;
}
