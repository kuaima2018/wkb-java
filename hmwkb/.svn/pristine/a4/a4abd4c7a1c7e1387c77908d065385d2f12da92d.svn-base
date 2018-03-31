package com.heima.service.test;

import com.heima.model.WkbFriendapply;
import com.heima.service.WkbFriendapplyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-11
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext*-test.xml")
@TransactionConfiguration (defaultRollback =false)
@Transactional()
public class WkbFriendSeviceTest {

    @Autowired
    private WkbFriendapplyService wkbFriendapplyService;

    @Test
    public void testRead()
    {
        List<Integer> list=new ArrayList<Integer>();
        list.add(21);
        list.add(22);
        //wkbFriendapplyService.readApplyByUserIds(11,list);
        WkbFriendapply wkbFriendapply=new WkbFriendapply();
        wkbFriendapply.setuId(100047);
        List<WkbFriendapply> wkbFriendapplyList=wkbFriendapplyService.queryApplyList(wkbFriendapply, true, true, null, 1, 10);
        System.out.println(wkbFriendapplyList.size());
    }
}
