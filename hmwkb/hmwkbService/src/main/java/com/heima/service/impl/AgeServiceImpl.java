package com.heima.service.impl;

import com.heima.dao.AgeDao;
import com.heima.model.AgeInfo;
import com.heima.service.AgeService;
import org.apache.commons.lang.StringUtils;
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
public class AgeServiceImpl implements AgeService {

    @Autowired
    private AgeDao ageDao;

    @Override
    public void addAgeInfo(AgeInfo ageInfo) {
        if(ageInfo==null)
            return;
        if(ageInfo.getAge()==null|| StringUtils.isBlank(ageInfo.getName()))
        {
            return;
        }
        ageDao.insertData(ageInfo);
    }
}
