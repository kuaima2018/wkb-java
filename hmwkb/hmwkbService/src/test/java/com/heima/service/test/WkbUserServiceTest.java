package com.heima.service.test;

import com.heima.dto.WkbUserQueryDto;
import com.heima.model.SocialitySearch;
import com.heima.model.WkbUser;
import com.heima.service.WkbUserService;
import com.heima.service.biz.WkbBizResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext*-test.xml")
@TransactionConfiguration(defaultRollback =false)
//@Transactional()
public class WkbUserServiceTest {

    @Autowired
    private WkbUserService wkbUserService;

    //@Test
    public void testApply()
    {
        wkbUserService.applyJoinCompany(5,"11",false);
    }

    //@Test
    public void testRead()
    {
        List<Integer> list=new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(44);

        WkbUserQueryDto wkbUserQueryDto=new WkbUserQueryDto();
        wkbUserQueryDto.setcId("1");
        wkbUserQueryDto.setuAdmin((byte)0);
        wkbUserService.getPageCompanyUsers(wkbUserQueryDto,1,10);

        //List<WkbUser> wkbUserList=wkbUserService.queryUserList(list);
        wkbUserService.getCountCompanyUsers(wkbUserQueryDto);
        System.out.println("ok");
    }

    //@Test
    public void testSaveuser()
    {
        WkbUser wkbUser=new WkbUser();
        wkbUser.setuId(1000);
        wkbUser.setuName("lixue");
        wkbUser.setCreator("sys");
        wkbUser.setuPwd("xxx");
        wkbUser.setCrtdatetime(new Date());
        wkbUser.setuBrithday(new Date(wkbUser.getCrtdatetime().getTime()-3600));
        WkbBizResult wkbBizResult=wkbUserService.saveUser(wkbUser);

        System.out.println("ok");
    }

    //@Test
    public void testCount()
    {
        Integer count=1;//userRoleService.queryCountUsersByRoleId(3);
        //WkbUserQueryDto wkbUserQueryDto=new WkbUserQueryDto();
        //wkbUserQueryDto.setRoleId(5);
        //List<WkbUser> wkbUserList=wkbPostrightService.queryPageUserRolesByRoleId(wkbUserQueryDto, 1, 10);
        //System.out.println(wkbUserList.size());


        WkbUserQueryDto wkbUserQueryDto=new WkbUserQueryDto();
        wkbUserQueryDto.setcId("100181");
        List<WkbUser> wkbUserList=wkbUserService.getPageCompanyUsers(wkbUserQueryDto,1,10);
        System.out.println(wkbUserList.size());
    }

    @Test
    public void testSearch()
    {
        SocialitySearch socialitySearch=new SocialitySearch();
        socialitySearch.setSearchType("1");
        socialitySearch.setSearchValue("快马");
        List<WkbUser> wkbUsers=wkbUserService.searchUsers(socialitySearch, 1, 2);
        if(wkbUsers!=null)
        {
            for(WkbUser wkbUser:wkbUsers)
            {
                System.out.println(wkbUser.getuName()+"  ***********************************");
            }
        }

        wkbUsers=wkbUserService.searchUsers(socialitySearch, 2, 2);
        if(wkbUsers!=null)
        {
            for(WkbUser wkbUser:wkbUsers)
            {
                System.out.println(wkbUser.getuName()+"  ***********************************");
            }
        }

        wkbUsers=wkbUserService.searchUsers(socialitySearch, 3, 2);
        if(wkbUsers!=null)
        {
            for(WkbUser wkbUser:wkbUsers)
            {
                System.out.println(wkbUser.getuName()+"  ***********************************");
            }
        }
    }
}
