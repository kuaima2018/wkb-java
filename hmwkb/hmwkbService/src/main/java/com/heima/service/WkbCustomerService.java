package com.heima.service;

import com.heima.model.WkbCustomer;

import java.util.List;

/**
 * Created by xuzhikai on 2015/7/18.
 */
public interface WkbCustomerService {
    public void addCustomer(WkbCustomer wkbCustomer);
    public void deleteCustomer(Integer customerId,Integer userId);
    public void updateCustomer(WkbCustomer wkbCustomer);
    public List<WkbCustomer> queryCustomer(Integer groupId, Integer userId,Integer index, Integer pageSize);
    public void changeCustomersGroup(Integer userId,Integer orgGroupId,Integer newGroupId);
}
