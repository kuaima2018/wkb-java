package com.heima.json;

import com.heima.common.WkbResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-23
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class TaskUsersResult extends WkbResult implements Serializable {
    public TaskUsersResult()
    {
        this.users=new ArrayList<UserName>();
    }
    public List<UserName> getUsers() {
        return users;
    }

    public void setUsers(List<UserName> users) {
        this.users = users;
    }

    private List<UserName> users;
}
