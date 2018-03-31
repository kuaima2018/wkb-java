package com.heima.dao.model;

import com.heima.model.WkbOrganization;

import java.io.Serializable;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrgRight extends WkbOrganization implements Serializable {

    private Integer pRight;
    private Integer uId;

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getpRight() {
        return pRight;
    }

    public void setpRight(Integer pRight) {
        this.pRight = pRight;
    }
}
