package com.heima.controller;

import com.heima.common.WkbResult;
import com.heima.json.UserBaseInfo;
import com.heima.model.AgeInfo;
import com.heima.model.FileInfo;
import com.heima.model.PayTransDto;
import com.heima.model.ReturnInfo;
import com.heima.service.AgeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-3-17
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping({"/test"})
public class TestController extends BaseController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TestController.class);

    public TestController()
    {
        logger.warn("test controller is created!!!");
    }
    @Autowired
    private AgeService ageService;

    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ResponseBody
    public String add(@RequestBody AgeInfo ageInfo) {
        logger.info("beign add");


        try {
            ageService.addAgeInfo(ageInfo);
            //System.out.println("ok");
            return "成功";
        } catch (Exception exp) {
            logger.error("ssss",exp);
            return exp.getMessage();
        }
    }

    @RequestMapping(value = "/xxx", method = {RequestMethod.POST})
    @ResponseBody
    public WkbResult test(
            @RequestParam String aaa,
            @RequestParam String bbb) {
        logger.info("beign test");
        List<UserBaseInfo> list=new ArrayList<UserBaseInfo>();
        UserBaseInfo userBaseInfo=new UserBaseInfo();
        userBaseInfo.setUserId(1);
        userBaseInfo.setName("aaa");
        list.add(userBaseInfo);
        System.out.println(aaa);
        System.out.println(bbb);

        userBaseInfo=new UserBaseInfo();
        userBaseInfo.setUserId(2);
        userBaseInfo.setName("bbb");
        list.add(userBaseInfo);

        WkbResult wkbResult=new WkbResult();
        wkbResult.setResult(list);
        return wkbResult;
    }

    @RequestMapping(value = "/testFile",method = {RequestMethod.POST})
    @ResponseBody
    public String test(@RequestBody FileInfo fileInfo)
    {

        try{
            String path= "/acorn/data/pics/";

            if(StringUtils.isNotBlank(fileInfo.getPath()))
            {
                //检查目录是否存在
                path+=fileInfo.getPath();
                path+="/";
            }
            File file=new File(path);
            if(!file.exists())
            {
                file.mkdir();
            }
            FileWriter writer = new FileWriter(path+fileInfo.getName()+".txt", false);
            writer.write(fileInfo.getText());
            writer.close();
        }catch (Exception exp)
        {
            logger.error("test file error:", exp);
            return exp.getMessage();
        }
        //File tempFile=new File("/static/test.txt");
        return "succ";
    }

    @RequestMapping(value = "/voice/save",method = {RequestMethod.POST})
    @ResponseBody
    public ReturnInfo test(@RequestBody PayTransDto payTransDto)
    {

        ReturnInfo returnInfo=new ReturnInfo();
        returnInfo.setCode("000");
        returnInfo.setDesc("成功");
        try{
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(StringUtils.isNotBlank(payTransDto.getsDateCreated())){
                payTransDto.setDateCreated(simpleDateFormat.parse(payTransDto.getsDateCreated()));
            }
           System.out.println(payTransDto);
        }catch (Exception exp)
        {
            logger.error("test file error:", exp);
            returnInfo.setCode("999");
            returnInfo.setDesc(exp.getMessage());
        }
        //File tempFile=new File("/static/test.txt");
        return returnInfo;
    }
}
