package com.heima.service.impl;

import com.heima.dao.ScheduleDao;
import com.heima.dao.ScheduleDefaultDao;
import com.heima.model.ScheduleDefaultModel;
import com.heima.model.ScheduleModel;
import com.heima.service.ScheduleDefaultService;
import com.heima.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-3-17
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class ScheduleDefaultServiceImpl implements ScheduleDefaultService {

    @Autowired
    private ScheduleDefaultDao scheduleDefaultDao;

    @Override
    public int saveScheduleDefault(ScheduleDefaultModel model) {
        if(model==null) return -1;
        if(model.getUid()==0 )
            return -1;
        return scheduleDefaultDao.insertData(model);
    }
}
