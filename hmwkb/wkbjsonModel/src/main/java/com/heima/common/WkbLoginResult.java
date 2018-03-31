package com.heima.common;

import com.heima.json.UserBaseInfo;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-9
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class WkbLoginResult extends WkbResult {
    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    private String sessionid;

    public UserBaseInfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserBaseInfo userinfo) {
        this.userinfo = userinfo;
    }

    private UserBaseInfo userinfo;

}
