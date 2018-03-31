package com.heima.json;

import com.heima.common.WkbResult;

import java.io.Serializable;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-10
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class SearchResultInfo extends WkbResult implements Serializable {
    public UserBaseInfo getUser() {
        return user;
    }

    public void setUser(UserBaseInfo user) {
        this.user = user;
    }

    public CompanyBaseInfo getCompany() {
        return company;
    }

    public void setCompany(CompanyBaseInfo company) {
        this.company = company;
    }

    private UserBaseInfo user;
    private CompanyBaseInfo company;
}
