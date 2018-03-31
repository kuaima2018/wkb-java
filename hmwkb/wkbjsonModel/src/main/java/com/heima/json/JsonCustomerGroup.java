package com.heima.json;

import java.io.Serializable;

/**
 * Created by xuzhikai on 2015/7/18.
 */
public class JsonCustomerGroup implements Serializable {
    private Integer groupId;
    private String groupName;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
