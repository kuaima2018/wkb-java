package com.heima.service;

import com.heima.model.WkbCustomerGroup;

import java.util.List;

/**
 * Created by xuzhikai on 2015/7/18.
 */
public interface WkbCustomerGroupService {
    public void addCustomerGroup(WkbCustomerGroup wkbCustomerGroup);
    public void deleteCustomerGroup(Integer groupId,Integer userId);
    public void updateCustomerGroup(WkbCustomerGroup wkbCustomerGroup);
    public List<WkbCustomerGroup> queryCustomerGroups(Integer userId);
}
