package com.heima.json;

import com.heima.common.WkbResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-20
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class SubordinateTaskResult extends WkbResult implements Serializable {
    public SubordinateTaskResult()
    {
        this.tasks=new ArrayList<SubordinateTask>();
    }
    private List<SubordinateTask> tasks;

    public List<SubordinateTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<SubordinateTask> tasks) {
        this.tasks = tasks;
    }
}
