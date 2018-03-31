package com.heima.service.test;

import com.heima.dto.WkbUserQueryDto;
import com.heima.json.WkbOrgUser;
import com.heima.model.WkbUser;
import com.heima.service.UserRoleService;
import com.heima.service.WkbOrganizationService;
import com.heima.service.WkbPostrightService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-15
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext*-test.xml")
@TransactionConfiguration(defaultRollback=false)
@Transactional()
public class WkbOrgServiceTest {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private WkbOrganizationService wkbOrganizationService;

    @Autowired
    private WkbPostrightService wkbPostrightService;

    //@Test
    public void testUserRecv()
    {
        List<WkbOrgUser> wkbOrgUserList= userRoleService.queryTaskReceivers(1,false);
        System.out.println(wkbOrgUserList.size());
    }

    //@Test
    public void testDel()
    {
        boolean bSucc=wkbOrganizationService.deleteOrganization(2,false);
        if(bSucc==false)
        {
            wkbOrganizationService.deleteOrganization(2,true);
        }
    }

    @Test
    public void testCount()
    {
        Integer count=1;//userRoleService.queryCountUsersByRoleId(3);
        //WkbUserQueryDto wkbUserQueryDto=new WkbUserQueryDto();
        //wkbUserQueryDto.setRoleId(5);
        //List<WkbUser> wkbUserList=wkbPostrightService.queryPageUserRolesByRoleId(wkbUserQueryDto, 1, 10);
        //System.out.println(wkbUserList.size());
        WkbUserQueryDto wkbUserQueryDto=new WkbUserQueryDto();
        wkbUserQueryDto.setcId("100181");
        List<WkbUser> wkbUserList=userRoleService.queryPageCompanyApplyUsers(wkbUserQueryDto,1,10);
        System.out.println(wkbUserList.size());
    }
}
