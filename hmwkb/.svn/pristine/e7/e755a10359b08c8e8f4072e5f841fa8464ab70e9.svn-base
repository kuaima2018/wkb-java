package com.heima.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-15
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class WkbOrgUser implements Serializable {
    public WkbOrgUser(Integer orgId, String orgName)
    {
        this.orgId = orgId;
        this.orgName = orgName;
        this.details=new ArrayList<JsonUserNameId>();
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List<JsonUserNameId> getDetails() {
        return details;
    }

    public void setDetails(List<JsonUserNameId> details) {
        this.details = details;
    }

    private Integer orgId;
    private String orgName;
    private List<JsonUserNameId> details;
}
