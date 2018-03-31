package com.heima.service.test;

import com.heima.dao.model.WkbTaskQuery;
import com.heima.dao.model.WkbTaskShow;
import com.heima.model.*;
import com.heima.service.WkbTaskService;
import com.heima.service.WkbTaskdetailService;
import com.heima.service.WkbTaskuserService;
import com.heima.service.biz.TaskFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.URLDecoder;
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
public class WkbTaskServiceTest {

    @Autowired
    private WkbTaskService wkbTaskService;

    @Autowired
    private WkbTaskdetailService wkbTaskdetailService;

    @Autowired
    private WkbTaskuserService wkbTaskuserService;

    //@Test
    public void testQuery()
    {
        //List<WkbTask> wkbTaskList= wkbTaskService.queryUserEndTasks(1, 1, 3);
        //System.out.println(wkbTaskList.size());
        //List<WkbTaskdetail> wkbTaskdetailList= wkbTaskdetailService.queryTaskDetails(1);
        //System.out.println(wkbTaskdetailList.size());

        WkbTaskQuery wkbTaskQuery =new WkbTaskQuery();
        wkbTaskQuery.setQueryType(0);
        //wkbTaskQuery.setuId(100047);
        wkbTaskQuery.getuIdList().add(100047);
        //wkbTaskQuery
        wkbTaskQuery.setStartPos(6);
        wkbTaskQuery.setEndPos(10);
        List<WkbTaskShow> wkbTaskShowList=wkbTaskService.queryUserTasks(wkbTaskQuery);
        System.out.println(wkbTaskShowList.size());
    }

    @Test
    public void testSave() throws Exception
    {
        //FileInputStream fileReader=new FileInputStream("d:\\data.log");
        //int count=wkbTaskService.saveTask("xxx",fileReader);
        WkbTask wkbTask=new WkbTask();
        //wkbTask.settIsimportt((byte)0);
        wkbTask.settName("yyy");
        wkbTask.settText(URLDecoder.decode("%F0%9F%98%80%F0%9F%91%8D%F0%9F%8F%BB"));
        System.out.println(wkbTask.gettText()+"!xxxxxxxx");
        /*if(wkbTask.gettText()!=null)
            return;*/
        wkbTask.settVoice(null);
        wkbTask.settVoicetime(0);
        wkbTask.settVoicesize(0);
        wkbTask.setuId(1);

        WkbTaskuser wkbTaskuser=new WkbTaskuser();
        wkbTaskuser.setuId(5);
        wkbTask.getWkbTaskuserList().add(wkbTaskuser);

        wkbTaskuser=new WkbTaskuser();
        wkbTaskuser.setuId(4);
        wkbTask.getWkbTaskuserList().add(wkbTaskuser);

        /*FileInputStream fileReader2=new FileInputStream("d:\\server.xml");

        WkbTaskfile wkbTaskfile=new WkbTaskfile();
        wkbTaskfile.settFile(fileReader2);
        wkbTaskfile.settFilename("server.xml");
        wkbTaskfile.settFilesize(2000);
        wkbTaskfile.settFiletype("yyy1");
        wkbTask.getWkbTaskfileList().add(wkbTaskfile);

        FileInputStream fileReader3=new FileInputStream("d:\\settings.xml");

        wkbTaskfile=new WkbTaskfile();
        wkbTaskfile.settFile(fileReader3);
        wkbTaskfile.settFilename("settings.xml");
        wkbTaskfile.settFilesize(2000);
        wkbTaskfile.settFiletype("yyy2");

        wkbTask.getWkbTaskfileList().add(wkbTaskfile);*/

        wkbTaskService.createTask(wkbTask);
        System.out.println("ok");
    }

    //@Test
    public void testReply()  throws Exception
    {
        WkbTaskdetail wkbTaskdetail=new WkbTaskdetail();
        wkbTaskdetail.settId(4);
        wkbTaskdetail.settText("任务44明细44");
        FileInputStream fileReader=new FileInputStream("d:\\data.log");
        wkbTaskdetail.settVoice(fileReader);
        wkbTaskdetail.settVoicetime(20);
        wkbTaskdetail.settVoicesize(20);
        wkbTaskdetail.setuId(2);

        WkbTaskdetailuser wkbTaskdetailuser=new WkbTaskdetailuser();
        wkbTaskdetailuser.settId(4);
        wkbTaskdetailuser.setuId(1);

        wkbTaskdetail.getWkbTaskdetailuserList().add(wkbTaskdetailuser);

        FileInputStream fileReader2=new FileInputStream("d:\\server.xml");

        WkbTaskfile wkbTaskfile=new WkbTaskfile();
        wkbTaskfile.settFile(fileReader2);
        wkbTaskfile.settFilename("server.xml");
        wkbTaskfile.settFilesize(2000);
        wkbTaskfile.settFiletype("yyy1");
        wkbTaskdetail.getWkbTaskfileList().add(wkbTaskfile);

        wkbTaskService.addTaskDetail(wkbTaskdetail);

        System.out.println("ok");
    }

    //@Test
    public void testReadFile()
    {
        TaskFile taskFile=wkbTaskService.fetchTaskFile(17, 22, 1, "voicemp3");
        /*taskFile=wkbTaskService.fetchTaskFile(2, 10, 1, "voicemp3");
        taskFile=wkbTaskService.fetchTaskFile(2, 8, 1, "mp3");*/
        System.out.println("ok");
    }
    //@Test
    public void testRead() throws Exception
    {
        //FileInputStream fileReader=new FileInputStream("d:\\data.log");
        InputStream inputStream=wkbTaskService.getData(3);
        //FileWriter fileWriter=new FileWriter("d:\\data_bak.log");
        //fileWriter.write(inputStream);
        /*FileOutputStream fileOutputStream=new FileOutputStream("d:\\data_bak.log");
        fileOutputStream.write(inputStream);*/

        this.inputStream2File(inputStream,"d:\\data_bak.log");
    }

    private File inputStream2File(InputStream inputStream, String savePath)
            throws Exception {
        File file = new File(savePath);
        BufferedInputStream fis = new BufferedInputStream(inputStream);
        FileOutputStream fos = new FileOutputStream(file);
        int f;
        while ((f = fis.read()) != -1) {
            fos.write(f);
        }
        fos.flush();
        fos.close();
        fis.close();
        inputStream.close();
        return file;
    }

}
