package com.heima.service.impl;

import com.heima.dao.WkbContactLogDao;
import com.heima.model.WkbContactLog;
import com.heima.service.WkbContactLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xuzhikai on 2015/7/18.
 */
@Service
public class WkbContactLogServiceImpl implements WkbContactLogService {
    @Autowired
    private WkbContactLogDao wkbContactLogDao;

    @Override
    public void addContactLog(WkbContactLog wkbContactLog) {
        if(wkbContactLog.getContactLogDate()==null)
            wkbContactLog.setContactLogDate(new Date());
        wkbContactLogDao.insertData(wkbContactLog);
    }

    @Override
    public void deleteContactLog(WkbContactLog wkbContactLog) {
        if(wkbContactLog.getContactLogId()!=null&&wkbContactLog.getUserId()!=null)
            wkbContactLogDao.deleteContactLog(wkbContactLog.getContactLogId(),wkbContactLog.getUserId());
    }

    @Override
    public List<WkbContactLog> queryContactLogs(Integer customerId, Integer userId, Integer index, Integer pageSize) {
        int pos=(index-1)*pageSize;
        if(customerId!=null)
            return wkbContactLogDao.queryLogsByPage(customerId,userId,pos,pageSize);
        return new ArrayList<WkbContactLog>();
    }
}
