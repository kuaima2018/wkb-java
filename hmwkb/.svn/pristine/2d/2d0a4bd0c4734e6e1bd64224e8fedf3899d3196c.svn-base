package com.heima.service.test;

import com.heima.service.WkbSeqService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext*-test.xml")
@TransactionConfiguration// (defaultRollback =false)
@Transactional()
public class WkbSeqServiceTest {

    @Autowired
    private WkbSeqService wkbSeqService;

    @Test
    public void testGetnext()
    {
        int id=wkbSeqService.getNewUserId();
        System.out.println("userid:"+id);
    }
}
