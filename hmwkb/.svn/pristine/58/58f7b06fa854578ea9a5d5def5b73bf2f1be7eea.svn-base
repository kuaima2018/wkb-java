package com.heima.service.impl;

import com.heima.dao.WkbMessageDao;
import com.heima.model.WkbMessage;
import com.heima.service.WkbMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class WkbMessageServiceImpl implements WkbMessageService {

    @Autowired
    private WkbMessageDao wkbMessageDao;

    @Override
    public void addMessage(WkbMessage wkbMessage) {
        if(wkbMessage.getCreatetime()==null)
        {
            wkbMessage.setCreatetime(new Date());
        }
        if(wkbMessage.getId()!=null)
            wkbMessage.setId(null);
        wkbMessageDao.addMessage(wkbMessage);
    }

    @Override
    public List<WkbMessage> queryMessages(Date startDate, Date endDate, Integer startPos, Integer endPos) {
        //mysql
        Integer pos=startPos-1;
        Integer size= endPos-pos;
        return wkbMessageDao.queryMessages(startDate,endDate,pos,size);
    }
}
