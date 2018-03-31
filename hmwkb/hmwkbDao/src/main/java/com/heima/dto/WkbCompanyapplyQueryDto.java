package com.heima.dto;

import com.heima.model.WkbCompanyapply;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-19
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class WkbCompanyapplyQueryDto extends WkbCompanyapply implements Serializable {
    private Date begincrttime;
    private Date endcrttime;
    private Integer startPos;
    private Integer endPos;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    public Date getBegincrttime() {
        return begincrttime;
    }

    public void setBegincrttime(Date begincrttime) {
        this.begincrttime = begincrttime;
    }

    public Date getEndcrttime() {
        return endcrttime;
    }

    public void setEndcrttime(Date endcrttime) {
        this.endcrttime = endcrttime;
    }

    public Integer getStartPos() {
        return startPos;
    }

    public void setStartPos(Integer startPos) {
        this.startPos = startPos;
    }

    public Integer getEndPos() {
        return endPos;
    }

    public void setEndPos(Integer endPos) {
        this.endPos = endPos;
    }
}
