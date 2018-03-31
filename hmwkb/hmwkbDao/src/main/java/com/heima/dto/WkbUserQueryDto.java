package com.heima.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heima.model.WkbUser;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-16
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class WkbUserQueryDto extends WkbUser implements Serializable {
    private Integer roleId;
    private Date applyBeginTime;
    private Date applyEndTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
    public Date getJointBeginTime() {
        return jointBeginTime;
    }

    public void setJointBeginTime(Date jointBeginTime) {
        this.jointBeginTime = jointBeginTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
    public Date getJointEndTime() {
        return jointEndTime;
    }

    public void setJointEndTime(Date jointEndTime) {
        this.jointEndTime = jointEndTime;
    }

    private Date jointBeginTime;
    private Date jointEndTime;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
    public Date getApplyBeginTime() {
        return applyBeginTime;
    }

    public void setApplyBeginTime(Date applyBeginTime) {
        this.applyBeginTime = applyBeginTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
    public Date getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(Date applyEndTime) {
        this.applyEndTime = applyEndTime;
    }
}
