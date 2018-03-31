package com.heima.service.test;

import com.heima.json.TrackPathTLExt;
import com.heima.service.MapTraceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext*-test.xml")
@TransactionConfiguration
@Transactional()
public class WkbPathServiceTest {

    @Autowired
    private MapTraceService mapTraceService;

    @Test
    public void testPachs() throws Exception
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<TrackPathTLExt> trackPathTLList=new ArrayList<TrackPathTLExt>();
        TrackPathTLExt trackPathTLExt1=new TrackPathTLExt();
        trackPathTLExt1.setId(1);
        trackPathTLExt1.setJingdu(new BigDecimal("21.435691"));
        trackPathTLExt1.setWeidu(new BigDecimal("31.355253"));
        trackPathTLExt1.setTime(simpleDateFormat.parse("2014-05-12 10:00:00"));

        trackPathTLList.add(trackPathTLExt1);


        TrackPathTLExt trackPathTLExt2=new TrackPathTLExt();
        trackPathTLExt2.setId(2);
        trackPathTLExt2.setJingdu(new BigDecimal("21.435691"));
        trackPathTLExt2.setWeidu(new BigDecimal("31.355253"));
        trackPathTLExt2.setTime(simpleDateFormat.parse("2014-05-12 10:00:01"));

        trackPathTLList.add(trackPathTLExt2);

        mapTraceService.calcTracePath(trackPathTLList);
    }
}
