package com.heima.service.impl;

import com.heima.dao.AgeDao;
import com.heima.dao.ScheduleDao;
import com.heima.model.AgeInfo;
import com.heima.model.ScheduleModel;
import com.heima.service.AgeService;
import com.heima.service.ScheduleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-3-17
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleDao scheduleDao;

    @Override
    public int saveSchedule(ScheduleModel model) {
        if(model==null) return -1;
        if(model.getUid()==0 )
            return -1;
        return scheduleDao.insertData(model);
    }

    @Override
    public List<ScheduleModel> queryListByDate(String uid, String startDate, String endDate) {
       if(uid==null || StringUtils.isBlank(uid)) return null;
        return scheduleDao.queryListByDate(uid,startDate,endDate);

    }

    @Override
    public int updateSchedule(ScheduleModel model) {
        return scheduleDao.updateDataByPK(model);
    }
}
