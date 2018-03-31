package com.heima.tree;

import com.heima.common.WkbResult;

import java.io.Serializable;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class WkbAddNodeResult extends WkbOrgNode implements Serializable {
    public WkbAddNodeResult()
    {
        succ="succ";
    }
    public String getSucc() {
        return succ;
    }

    public void setSucc(String succ) {
        this.succ = succ;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String succ;
    private String msg;

}
