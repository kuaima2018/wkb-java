package com.heima.controller;

//import cn.jpush.api.ErrorCodeEnum;
//import cn.jpush.api.JPushClient;
//import cn.jpush.api.MessageResult;//
//import com.heima.common.WkbLoginResult;
//import com.heima.common.WkbMessageEnum;
//import com.heima.common.WkbResult;
//import com.heima.json.AddUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heima.json.ScheduleJson;
import com.heima.model.ScheduleDefaultModel;
import com.heima.model.ScheduleModel;
import com.heima.service.ScheduleDefaultService;
import com.heima.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

//import com.heima.service.biz.WkbBizException;
//import com.heima.service.biz.WkbBizResult;
//import com.heima.util.MD5Util;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping({"/scheduledefault"})
public class ScheduleDefaultController extends BaseController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ScheduleDefaultService scheduleDefaultService;


    //TestValue
    //   /session/{"creator":"ggch","crtdatetime":1397894891760,"uid":11,"fuid":22}
    @RequestMapping(value="",method = RequestMethod.POST)
    @ResponseBody
    public String add(@RequestParam(required = false, defaultValue = "") String session,
                      @RequestParam int uid,
                      @RequestParam int fuid,
                      @RequestParam(required = false, defaultValue = "") String creator)
    {
        ScheduleJson jsonModel=new ScheduleJson();
        ScheduleDefaultModel mode=new ScheduleDefaultModel();
        mode.setUid(uid);
        mode.setFuid(fuid);
        mode.setCreator(creator);

        //    WkbResult wkbResult=new WkbResult();
        //    wkbResult=this.checkSession(addUser.getSessionid(), mode.getUid());
        //    if(wkbResult.getSuccess().booleanValue()==false)
        //        return wkbResult;


        int iResult=scheduleDefaultService.saveScheduleDefault(mode);
        if(iResult==1)
        {
            jsonModel.setMessage("添加成功");
            jsonModel.setSuceess(true);
        }else
        {
            jsonModel.setMessage("添加失败");
        }
     //   jsonModel.setList(null);
        ObjectMapper objectMapper=new ObjectMapper();
        String strData="";
        try{
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            objectMapper.setDateFormat(simpleDateFormat);
            strData= objectMapper.writeValueAsString(jsonModel);
        }catch (Exception exp)
        {
            logger.error("init controller log error:",exp);
        }
        return strData;
    }


}
