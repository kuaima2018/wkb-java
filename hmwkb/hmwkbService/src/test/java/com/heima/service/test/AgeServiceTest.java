package com.heima.service.test;

import com.heima.model.AgeInfo;
import com.heima.model.WkbMessage;
import com.heima.model.WkbVersion;
import com.heima.service.AgeService;
import com.heima.service.WkbMessageService;
import com.heima.service.WkbVersionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-3-17
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext*-test.xml")
@TransactionConfiguration(defaultRollback=false)
@Transactional()
public class AgeServiceTest {

    @Autowired
    private AgeService ageService;

    @Autowired
    private WkbMessageService wkbMessageService;

    //@Test
    public void testAgeAdd()
    {
        String strType="mp3_xxx.txt";
        int index=strType.indexOf("_");
        if(index>0)
        {
            String fileType=strType.substring(0,index);
            String fileName=strType.substring(index+1);
            System.out.println(fileType);
        }
        /*AgeInfo ageInfo=new AgeInfo();
        ageInfo.setAge(12);
        ageInfo.setName("test");
        ageService.addAgeInfo(ageInfo);*/
    }


    //@Test
    public void testAddInfo() throws Exception
    {
        WkbMessage wkbMessage=new WkbMessage();
        wkbMessage.setCreatetime(new Date());
        wkbMessage.setMessage("java.lang.NullPointerExceptionat com.heima.ui.MainActivity$ClickListener.onClick(MainActivity.java:219)at android.view.View.performClick(View.java:4249)at android.view.View$PerformClick.run(View.java:17764)at android.os.Handler.handleCallback(Handler.java:730)at android.os.Handler.dispatchMessage(Handler.java:92)at android.os.Looper.loop(Looper.java:137)at android.app.ActivityThread.main(ActivityThread.java:5137)at java.lang.reflect.Method.invokeNative(Native Method)at java.lang.reflect.Method.invoke(Method.java:525)at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:756)at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:572)at miui.dexspy.DexspyInstaller.main(DexspyInstaller.java:171)at dalvik.system.NativeStart.main(Native Method)\n" +
                "(String)");

        //wkbMessage.setMessage(wkbMessage.getMessage().replace("\n","CHAR(10)CHAR(13)"));
        wkbMessageService.addMessage(wkbMessage);
    }

    @Autowired
    private WkbVersionService wkbVersionService;

    @Test
    public void testVersion(){
        WkbVersion wkbVersion=wkbVersionService.queryByAppType("ios");
        if(wkbVersion!=null){
            System.out.println(wkbVersion.getAppDesc());
        }
        System.out.println(String.valueOf(new Date().getTime()));
    }
}
