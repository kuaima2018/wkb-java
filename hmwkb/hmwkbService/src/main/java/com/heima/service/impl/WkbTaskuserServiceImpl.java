package com.heima.service.impl;

import com.heima.dao.WkbTaskuserDao;
import com.heima.model.WkbTask;
import com.heima.model.WkbTaskuser;
import com.heima.service.WkbTaskuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class WkbTaskuserServiceImpl implements WkbTaskuserService {

    @Autowired
    private WkbTaskuserDao wkbTaskuserDao;

    @Override
    public void updateTaskUserPerfmLevel(Integer taskId, Map<Integer, List<Integer>> mapLevel) {
        //
        for(Map.Entry<Integer, List<Integer>> entry:mapLevel.entrySet())
        {
            wkbTaskuserDao.updateRevnPerfms(taskId,entry.getValue(),new Date(), entry.getKey());
        }
    }

    public void clearTaskUserPerfmLevel(Integer taskId)
    {
        wkbTaskuserDao.clearRevnPerfms(taskId);
    }

    @Override
    public void saveTaskUsers(List<WkbTaskuser> wkbTaskuserList) {
        for(WkbTaskuser wkbTaskuser:wkbTaskuserList)
        {
            wkbTaskuserDao.insertData(wkbTaskuser);
        }
    }

    @Override
    public void updateRecvReadByTask(Integer taskId, List<Integer> uIdList, Integer readFlag) {
        wkbTaskuserDao.updateRecvReadByTask(taskId,uIdList,new Date(), readFlag);
    }

    @Override
    public List<WkbTaskuser> fetchTaskUsers(Integer taskId) {
        return wkbTaskuserDao.selectTaskUsers(taskId);
    }

    @Override
    public Map<Integer,List<WkbTaskuser>> fetchTaskUserList(List<Integer> taskIdList) {

        Map<Integer,List<WkbTaskuser>> map=new HashMap<Integer, List<WkbTaskuser>>();
        if(taskIdList==null||taskIdList.size()<=0)
            return map;
        List<WkbTaskuser> wkbTaskuserList=wkbTaskuserDao.selectTaskUserList(taskIdList);
        for(WkbTaskuser wkbTaskuser:wkbTaskuserList)
        {
            List<WkbTaskuser> wkbTaskusers=null;
            if(map.containsKey(wkbTaskuser.gettId()))
            {
                wkbTaskusers=map.get(wkbTaskuser.gettId());
            }
            else
            {
                wkbTaskusers=new ArrayList<WkbTaskuser>();
                map.put(wkbTaskuser.gettId(),wkbTaskusers);
            }
            wkbTaskusers.add(wkbTaskuser);
        }

        return map;
    }

    @Override
    public void updateRecvDelFlagByTask(Integer taskId, List<Integer> uIdList, Integer delFlag) {
        wkbTaskuserDao.updateRecvDelFlagByTask(taskId, uIdList, new Date(), delFlag);
    }
}
