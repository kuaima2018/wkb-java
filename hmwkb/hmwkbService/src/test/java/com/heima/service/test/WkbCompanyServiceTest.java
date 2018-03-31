package com.heima.service.test;

import com.heima.dto.WkbCompanyapplyQueryDto;
import com.heima.model.WkbCompany;
import com.heima.model.WkbCompanyapply;
import com.heima.service.WkbCompanyService;
import com.heima.service.WkbCompanyapplyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-1-31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext*-test.xml")
@TransactionConfiguration(defaultRollback =false)
public class WkbCompanyServiceTest {
    @Autowired
    private WkbCompanyService wkbCompanyService;

    @Autowired
    private WkbCompanyapplyService wkbCompanyapplyService;


    @Test
    public void testQueryCompany()
    {
        WkbCompanyapplyQueryDto wkbCompanyapplyQueryDto=new WkbCompanyapplyQueryDto();
        wkbCompanyapplyQueryDto.setStartPos(1);
        wkbCompanyapplyQueryDto.setEndPos(5);
        List<WkbCompanyapply> wkbCompanyapplyList=wkbCompanyapplyService.queryCompanyapply(wkbCompanyapplyQueryDto);
        System.out.println(wkbCompanyapplyList.size());
        //List<WkbCompany> wkbCompanyList= wkbCompanyService.queryPageCompany(wkbCompanyapplyQueryDto);
        //System.out.println(wkbCompanyList.size());
    }
}
