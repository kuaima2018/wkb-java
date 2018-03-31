package com.heima.service.test;

import com.heima.model.WkbCustomer;
import com.heima.service.WkbCustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xuzhikai on 2015/7/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext*-test.xml")
@TransactionConfiguration(defaultRollback =false)
@Transactional()
public class WkbCustomerServiceTest {

    @Autowired
    private WkbCustomerService wkbCustomerService;

    @Test
    public void testQuery()
    {
        List<WkbCustomer> wkbCustomerList= wkbCustomerService.queryCustomer(2, 1, 1, 2);
        if(wkbCustomerList!=null)
        {
            System.out.println("**************************");
            for(WkbCustomer wkbCustomer:wkbCustomerList)
            {
                System.out.println(wkbCustomer.getCustomerName());
            }
        }

        wkbCustomerList= wkbCustomerService.queryCustomer(2, 1, 2, 2);
        if(wkbCustomerList!=null)
        {
            System.out.println("**************************");
            for(WkbCustomer wkbCustomer:wkbCustomerList)
            {
                System.out.println(wkbCustomer.getCustomerName());
            }
        }
    }

    //@Test
    public void testGroupChange()
    {
        //wkbCustomerService.changeCustomersGroup(1,1,2);
        WkbCustomer wkbCustomer=new WkbCustomer();
        wkbCustomer.setAddress("ttt");
        wkbCustomer.setCustomerName("测试名");
        wkbCustomer.setMobile("123434");
        wkbCustomer.setUserId(2);
        wkbCustomer.setGroupId(22);
        wkbCustomer.setCustomerId(6);
        //wkbCustomerService.updateCustomer(wkbCustomer);
        wkbCustomerService.deleteCustomer(2,1);
    }
}
