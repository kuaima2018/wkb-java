package com.heima.model;

import com.heima.common.WkbResult;

import java.io.Serializable;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class AddUserResult extends WkbResult implements Serializable {
    private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
