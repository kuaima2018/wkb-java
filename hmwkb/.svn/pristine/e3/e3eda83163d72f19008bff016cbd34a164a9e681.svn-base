package com.heima.service.impl;

import com.heima.common.WkbMessageEnum;
import com.heima.dao.WkbTaskdetailDao;
import com.heima.dao.WkbTaskdetailuserDao;
import com.heima.dao.WkbTaskfileDao;
import com.heima.model.WkbTaskdetail;
import com.heima.model.WkbTaskdetailuser;
import com.heima.model.WkbTaskfile;
import com.heima.service.WkbTaskdetailService;
import com.heima.service.biz.WkbBizException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-17
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class WkbTaskdetailServiceImpl implements WkbTaskdetailService {

    @Autowired
    private WkbTaskdetailDao wkbTaskdetailDao;

    @Autowired
    private WkbTaskfileDao wkbTaskfileDao;

    @Autowired
    private WkbTaskdetailuserDao wkbTaskdetailuserDao;

    @Override
    public List<WkbTaskdetail> queryTaskDetails(Integer taskId) {
        List<WkbTaskfile> wkbTaskfileList=wkbTaskfileDao.queryFilesByTask(taskId);
        return this.queryTaskDetailsNoFile(taskId,wkbTaskfileList);
    }

    @Override
    public List<WkbTaskdetail> queryTaskDetailsNoFile(Integer taskId, List<WkbTaskfile> wkbTaskfileList) {
        List<WkbTaskdetail> wkbTaskdetailList = wkbTaskdetailDao.queryDetailByTaskId(taskId);
        if(wkbTaskfileList!=null&&wkbTaskdetailList.size()>0)
        {
            for(WkbTaskdetail wkbTaskdetail:wkbTaskdetailList)
            {
                for(WkbTaskfile wkbTaskfile:wkbTaskfileList)
                {
                    if(wkbTaskfile.gettDid()!=null&&wkbTaskfile.gettDid().equals(wkbTaskdetail.getId()))
                    {
                        wkbTaskdetail.getWkbTaskfileList().add(wkbTaskfile);
                    }
                }
            }
        }
        return wkbTaskdetailList;
    }

    @Override
    public void saveTaskdetail(WkbTaskdetail wkbTaskdetail) {
        if(StringUtils.isBlank(wkbTaskdetail.getCreator()))
            wkbTaskdetail.setCreator(wkbTaskdetail.getuId().toString());
        if(wkbTaskdetail.getCrtdatetime()==null)
            wkbTaskdetail.setCrtdatetime(new Date());

        int count=wkbTaskdetailDao.saveTaskdetail(wkbTaskdetail);
        for(WkbTaskdetailuser wkbTaskdetailuser:wkbTaskdetail.getWkbTaskdetailuserList())
        {
            wkbTaskdetailuser.settDid(wkbTaskdetail.getId());
            wkbTaskdetailuser.settId(wkbTaskdetail.gettId());
            wkbTaskdetailuser.settIsread((byte)0);

            count=wkbTaskdetailuserDao.saveTaskdetailuser(wkbTaskdetailuser);
        }
        if(wkbTaskdetail.getWkbTaskfileList()!=null)
        {
            for(WkbTaskfile wkbTaskfile:wkbTaskdetail.getWkbTaskfileList())
            {
                wkbTaskfile.setCreator(wkbTaskdetail.getCreator());
                wkbTaskfile.setCrtdatetime(wkbTaskdetail.getCrtdatetime());
                wkbTaskfile.settId(wkbTaskdetail.gettId());
                wkbTaskfile.settDid(wkbTaskdetail.getId());

                wkbTaskfileDao.insertData(wkbTaskfile);
            }
        }


    }

    @Override
    public WkbTaskdetail fetchVoiceData(Integer id) {
        return wkbTaskdetailDao.getVoiceData(id);
    }
}
