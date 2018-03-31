package com.heima.dao.model;

import com.heima.model.WkbTask;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-16
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class WkbTaskShow extends WkbTask {
    private Integer recvUId;
    private Integer unReadCount;


    public WkbTaskShow()
    {
        unReadCount=0;
    }

    public Integer getRecvUId() {
        return recvUId;
    }

    public void setRecvUId(Integer recvUId) {
        this.recvUId = recvUId;
    }

    public Integer getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(Integer unReadCount) {
        this.unReadCount = unReadCount;
    }
}
