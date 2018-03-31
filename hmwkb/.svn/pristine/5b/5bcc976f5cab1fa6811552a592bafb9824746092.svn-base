package com.heima.service.test;

import com.heima.model.MapTrace;
import com.heima.service.MapTraceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-1-31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext*-test.xml")
@TransactionConfiguration(defaultRollback =false)
public class MapTraceServiceTest {
    @Autowired
    private MapTraceService mapTraceService;

    @Test
    public void testMapTrace() throws Exception
    {
        List<Integer> uIdList=new ArrayList<Integer>();
        uIdList.add(100054);
        uIdList.add(100055);
        uIdList.add(100056);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        List<MapTrace> mapTraceList=mapTraceService.findLastPathByUsers(uIdList, simpleDateFormat.parse("2014-03-21"));
        for(MapTrace mapTrace:mapTraceList)
        {
            System.out.println(mapTrace.getUid());
        }
    }
}
