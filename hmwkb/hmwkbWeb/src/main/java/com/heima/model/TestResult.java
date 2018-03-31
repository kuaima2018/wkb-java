package com.heima.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-11
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class TestResult implements Serializable {
    private Integer uId;
    private List<Integer> targetids;

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public List<Integer> getTargetids() {
        return targetids;
    }

    public void setTargetids(List<Integer> targetids) {
        this.targetids = targetids;
    }
}
