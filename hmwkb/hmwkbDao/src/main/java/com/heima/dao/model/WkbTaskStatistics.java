package com.heima.dao.model;

import java.io.Serializable;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-20
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class WkbTaskStatistics implements Serializable {
    private Integer uId;
    private Integer taskcount;

    public Integer getPlevel() {
        return plevel;
    }

    public void setPlevel(Integer plevel) {
        this.plevel = plevel;
    }

    private Integer plevel;

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getTaskcount() {
        return taskcount;
    }

    public void setTaskcount(Integer taskcount) {
        this.taskcount = taskcount;
    }
}
