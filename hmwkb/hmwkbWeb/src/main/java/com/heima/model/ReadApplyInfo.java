package com.heima.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-11
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReadApplyInfo implements Serializable {
    private Integer userid;
    private List<Integer> requestid;
    private String sessionid;

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public List<Integer> getRequestid() {
        return requestid;
    }

    public void setRequestid(List<Integer> requestid) {
        this.requestid = requestid;
    }
}
