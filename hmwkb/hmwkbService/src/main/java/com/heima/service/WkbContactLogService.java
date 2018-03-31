package com.heima.service;

import com.heima.model.WkbContactLog;

import java.util.List;

/**
 * Created by xuzhikai on 2015/7/18.
 */
public interface WkbContactLogService {
    public void addContactLog(WkbContactLog wkbContactLog);
    public void deleteContactLog(WkbContactLog wkbContactLog);
    public List<WkbContactLog> queryContactLogs(Integer customerId,Integer userId,Integer index,Integer pageSize);
}
