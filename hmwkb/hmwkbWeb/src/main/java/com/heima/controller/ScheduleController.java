package com.heima.controller;

import com.heima.common.WktResult;
import com.heima.common.WktStatus;
import com.heima.json.*;
import com.heima.model.ScheduleModel;
import com.heima.model.WkbNotation;
import com.heima.model.WkbSchedule;
import com.heima.model.WkbUser;
import com.heima.service.ScheduleService;
import com.heima.service.WkbNotationService;
import com.heima.service.WkbScheduleService;
import com.heima.service.WkbUserService;
import com.heima.web.service.SystemConfigure;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping({"/schedule"})
public class ScheduleController extends BaseController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    private WkbScheduleService wkbScheduleService;
    @Autowired
    private WkbUserService wkbUserService;
    @Autowired
    private SystemConfigure systemConfigure;
    @Autowired
    private WkbNotationService wkbNotationService;

    @RequestMapping(value = "query", method = RequestMethod.POST)
    @ResponseBody
    public WktResult query(@RequestBody JsonScheduleQuery jsonScheduleQuery)
    {
        WktResult wktResult=new WktResult();
        if(jsonScheduleQuery==null||jsonScheduleQuery.getUserId()==null
                ||jsonScheduleQuery.getBeginDate()==null||jsonScheduleQuery.getEndDate()==null)
        {
            wktResult.getStatus().setErrorCode(1);
            wktResult.getStatus().setErrorMessage("未提供查询信息");
            return wktResult;
        }
        try
        {
            //TODO:wkt-校验是否有权限查看
            Integer userId=jsonScheduleQuery.getTargetId();
            if(userId==null)
                userId=jsonScheduleQuery.getUserId();
            List<WkbSchedule> wkbScheduleList=wkbScheduleService.queryScheduleByUser(userId,jsonScheduleQuery.getBeginDate(),jsonScheduleQuery.getEndDate());
            if(wkbScheduleList!=null&&wkbScheduleList.size()>0)
            {
                List<JsonSchedule> jsonScheduleList=new ArrayList<JsonSchedule>();
                List<Integer> userIdList=new ArrayList<Integer>();

                List<Integer> scheduleIdList=new ArrayList<Integer>();
                for(WkbSchedule wkbSchedule:wkbScheduleList)
                {
                    if(!scheduleIdList.contains(wkbSchedule.getId()))
                        scheduleIdList.add(wkbSchedule.getId());
                }

                Map<Integer,List<WkbNotation>> mapNotations=this.fetchNotations(scheduleIdList);

                for(WkbSchedule wkbSchedule:wkbScheduleList)
                {
                    JsonSchedule jsonSchedule=new JsonSchedule();
                    jsonSchedule.setDate(wkbSchedule.getScheduleDate());
                    jsonSchedule.setSchedule(wkbSchedule.getSchedule());
                    jsonSchedule.setDaily(wkbSchedule.getDaily());
                    jsonSchedule.setRemind(wkbSchedule.getRemind());
                    jsonSchedule.setRemindTime(wkbSchedule.getRemindTime());
                    jsonSchedule.setUserId(wkbSchedule.getUserId());

                    //获取批注
                    if(mapNotations.containsKey(wkbSchedule.getId()))
                    {
                        List<WkbNotation> wkbNotationList=mapNotations.get(wkbSchedule.getId());
                        for(WkbNotation wkbNotation:wkbNotationList)
                        {
                            if(wkbNotation.getType()!=null)
                            {
                                JsonNotation jsonNotation=new JsonNotation();
                                jsonNotation.setId(wkbNotation.getId());
                                jsonNotation.setNotationTime(wkbNotation.getCreateTime());
                                jsonNotation.setUserId(wkbNotation.getUserId());
                                jsonNotation.setNotation(wkbNotation.getNotation());

                                if(!userIdList.contains(jsonNotation.getUserId()))
                                    userIdList.add(jsonNotation.getUserId());

                                if(wkbNotation.getType().intValue()==1)
                                {
                                    //计划
                                    jsonSchedule.getScheduleNotations().add(jsonNotation);
                                }
                                else if(wkbNotation.getType().intValue()==2)
                                {
                                    //日报
                                    jsonSchedule.getDailyNotations().add(jsonNotation);
                                }
                            }
                        }
                    }

                    jsonScheduleList.add(jsonSchedule);
                }

                //设置用户信息
                if(userIdList.size()>0)
                {
                    List<WkbUser> wkbUserList=wkbUserService.queryUserList(userIdList);
                    if(wkbUserList!=null)
                    {
                        for(JsonSchedule jsonSchedule:jsonScheduleList)
                        {
                            this.formatScheduleUser(wkbUserList,jsonSchedule.getScheduleNotations());
                            this.formatScheduleUser(wkbUserList,jsonSchedule.getDailyNotations());
                        }
                    }
                }

                wktResult.setResult(jsonScheduleList);
            }

        }catch (Exception exp)
        {
            logger.error("query user schedule error:",exp);
            wktResult.getStatus().setErrorCode(-100);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
        }
        return wktResult;
    }

    private Map<Integer,List<WkbNotation>> fetchNotations(List<Integer> scheduleIdList)
    {
        List<WkbNotation> wkbNotationList=wkbNotationService.queryNotation(scheduleIdList, null);
        Map<Integer,List<WkbNotation>> map=new HashMap<Integer, List<WkbNotation>>();
        if(wkbNotationList!=null) {
            for (WkbNotation wkbNotation : wkbNotationList) {
                if (map.containsKey(wkbNotation.getSchId())) {
                    map.get(wkbNotation.getSchId()).add(wkbNotation);
                } else {
                    List<WkbNotation> wkbNotations = new ArrayList<WkbNotation>();
                    wkbNotations.add(wkbNotation);
                    map.put(wkbNotation.getSchId(), wkbNotations);
                }
            }
        }
        return map;
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public WktStatusResult edit(@RequestBody JsonScheduleAddQuery jsonScheduleAddQuery)
    {
        WktStatus wktStatus=new WktStatus();

        if(jsonScheduleAddQuery==null||jsonScheduleAddQuery.getUserId()==null
                ||jsonScheduleAddQuery.getDate()==null)
        {
            wktStatus.setErrorMessage("未提供参数");
            wktStatus.setErrorCode(1);
            return new WktStatusResult(wktStatus);
        }

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String str=simpleDateFormat.format(new Date());
        simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(jsonScheduleAddQuery.getRemind()!=null&&jsonScheduleAddQuery.getRemind().intValue()==1) {
            if(jsonScheduleAddQuery.getRemindTime()!=null)
            {
                if(jsonScheduleAddQuery.getRemindTime().compareTo(new Date())<0)
                {
                    wktStatus.setErrorMessage("提醒时间设置不合法");
                    wktStatus.setErrorCode(1);
                    return new WktStatusResult(wktStatus);
                }
            }
        }

        try{
            Date date=simpleDateFormat.parse(str+" 00:00:00");
            if(jsonScheduleAddQuery.getDate().compareTo(date)<0)
            {
                wktStatus.setErrorMessage("过去的计划日报不可编辑");
                wktStatus.setErrorCode(2);
                return new WktStatusResult(wktStatus);
            }

            WkbSchedule wkbSchedule=new WkbSchedule();
            wkbSchedule.setUserId(jsonScheduleAddQuery.getUserId());
            wkbSchedule.setSchedule(jsonScheduleAddQuery.getSchedule());
            wkbSchedule.setDaily(jsonScheduleAddQuery.getDaily());
            wkbSchedule.setRemind(jsonScheduleAddQuery.getRemind());
            wkbSchedule.setRemindTime(jsonScheduleAddQuery.getRemindTime());
            wkbSchedule.setScheduleDate(jsonScheduleAddQuery.getDate());

            wkbScheduleService.editSchedule(wkbSchedule);

        }catch (Exception exp)
        {
            logger.error("edit user schedule error:",exp);
            wktStatus.setErrorCode(-100);
            wktStatus.setErrorMessage(exp.getMessage());
        }
        return new WktStatusResult(wktStatus);
    }

    @RequestMapping(value = "notation", method = RequestMethod.POST)
    @ResponseBody
    public WktStatusResult notation(@RequestBody JsonScheduleNotationQuery jsonScheduleNotationQuery)
    {
        WktStatus wktStatus=new WktStatus();

        if(jsonScheduleNotationQuery==null||jsonScheduleNotationQuery.getUserId()==null
                ||jsonScheduleNotationQuery.getTargetId()==null||jsonScheduleNotationQuery.getDate()==null
                ||jsonScheduleNotationQuery.getType()==null)
        {
            wktStatus.setErrorMessage("未提供参数");
            wktStatus.setErrorCode(1);
            return new WktStatusResult(wktStatus);
        }

        //TODO:wkt-校验是否有权限批注
        /*if(jsonScheduleNotationQuery.getDate().compareTo(new Date())<=0)
        {
            wktStatus.setErrorMessage("过去的计划日报不可批注");
            wktStatus.setErrorCode(2);
            return new WktStatusResult(wktStatus);
        }*/
        if(jsonScheduleNotationQuery.getType().intValue()!=1&&jsonScheduleNotationQuery.getType().intValue()!=2)
        {
            wktStatus.setErrorMessage("批注类型不正确");
            wktStatus.setErrorCode(3);
            return new WktStatusResult(wktStatus);
        }

        try {
            /*WkbSchedule wkbSchedule = new WkbSchedule();
            wkbSchedule.setUserId(jsonScheduleNotationQuery.getTargetId());
            wkbSchedule.setScheduleDate(jsonScheduleNotationQuery.getDate());
            if (jsonScheduleNotationQuery.getType().intValue() == 1) {
                wkbSchedule.setScheduleNotation(jsonScheduleNotationQuery.getNotation());
                wkbSchedule.setScheduleNotationId(jsonScheduleNotationQuery.getUserId());
                wkbSchedule.setScheduleNotationTime(new Date());
            } else {
                wkbSchedule.setDailyNotation(jsonScheduleNotationQuery.getNotation());
                wkbSchedule.setDailyNotationId(jsonScheduleNotationQuery.getUserId());
                wkbSchedule.setDailyNotationTime(new Date());
            }*/

            WkbNotation wkbNotation=new WkbNotation();
            wkbNotation.setNotation(jsonScheduleNotationQuery.getNotation());
            wkbNotation.setUserId(jsonScheduleNotationQuery.getUserId());
            wkbNotation.setType(jsonScheduleNotationQuery.getType());

            wkbScheduleService.editNotation(wkbNotation,jsonScheduleNotationQuery.getTargetId(),jsonScheduleNotationQuery.getDate());

        }catch (Exception exp)
        {
            logger.error("edit user schedule notaion error:",exp);
            wktStatus.setErrorCode(-100);
            wktStatus.setErrorMessage(exp.getMessage());
        }
        return new WktStatusResult(wktStatus);
    }


    @RequestMapping(value = "remind", method = RequestMethod.POST)
    @ResponseBody
    public WktResult queryRemind(@RequestBody JsonScheduleRemindQuery jsonScheduleRemindQuery)
    {
        WktResult wktResult=new WktResult();
        if(jsonScheduleRemindQuery==null||jsonScheduleRemindQuery.getUserId()==null
                ||jsonScheduleRemindQuery.getDuration()==null)
        {
            wktResult.getStatus().setErrorCode(1);
            wktResult.getStatus().setErrorMessage("未提供查询信息");
            return wktResult;
        }
        if(jsonScheduleRemindQuery.getDuration().intValue()<=0)
        {
            wktResult.getStatus().setErrorCode(2);
            wktResult.getStatus().setErrorMessage("提醒天数不正确");
            return wktResult;
        }
        try
        {
            //根据天数来确定结束时间
            List<WkbSchedule> wkbScheduleList=wkbScheduleService.queryUserRemind(jsonScheduleRemindQuery.getUserId(),jsonScheduleRemindQuery.getDuration());
            if(wkbScheduleList!=null)
            {
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List<String>  dateList=new ArrayList<String>();
                for(WkbSchedule wkbSchedule:wkbScheduleList)
                {
                    dateList.add(simpleDateFormat.format(wkbSchedule.getRemindTime()));
                }

                wktResult.setResult(dateList);
            }
        }catch (Exception exp)
        {
            logger.error("query user schedule error:",exp);
            wktResult.getStatus().setErrorCode(-100);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
        }
        return wktResult;
    }


    private void formatScheduleUser(List<WkbUser> wkbUserList,List<JsonNotation> jsonNotationList)
    {
        if(jsonNotationList!=null&&jsonNotationList.size()>0)
        {
            for(JsonNotation jsonNotation:jsonNotationList) {
                WkbUser wkbUser = this.locateUser(wkbUserList, jsonNotation.getUserId());
                if (wkbUser != null) {
                    if (StringUtils.isBlank(wkbUser.getuName()))
                        jsonNotation.setName(wkbUser.getuIdentifier());
                    else
                        jsonNotation.setName(wkbUser.getuName());

                    if (StringUtils.isNotBlank(wkbUser.getImageUrl()))
                        jsonNotation.setImageUrl(systemConfigure.getImageServer() + wkbUser.getImageUrl());
                }
            }
        }
    }

    private WkbUser locateUser(List<WkbUser> wkbUserList,Integer userId)
    {
        for(WkbUser wkbUser:wkbUserList)
        {
            if(wkbUser.getuId().equals(userId))
                return wkbUser;
        }
        return null;
    }



    /******************************************************************************************************************/
    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ScheduleJson add(@RequestParam(required = false, defaultValue = "") String session,
                      @RequestParam int uid,
                      @RequestParam String sdate,
                      @RequestParam(required = false, defaultValue = "") String stext,
                      @RequestParam(required = false, defaultValue = "") String dtext,
                      @RequestParam(required = false, defaultValue = "") int rule,
                      @RequestParam(required = false, defaultValue = "") String remind,
                      @RequestParam(required = false, defaultValue = "") String creator
    ) throws Exception {
        ScheduleJson jsonModel = new ScheduleJson();
        ScheduleModel mode = new ScheduleModel();
        mode.setUid(uid);
        mode.setSschdate(setStringToDate(sdate));
        mode.setSschtext(URLDecoder.decode(stext, "utf-8"));
        mode.setSdailytext(URLDecoder.decode(dtext,"utf-8"));
        mode.setSremindrule(rule);
     mode.setSremindtime(setStringToDate(remind));


        mode.setCreator(creator);
        mode.setCrtdatetime(new java.util.Date());
        int iResult = scheduleService.saveSchedule(mode);
        if (iResult == 1) {
            jsonModel.setMessage("添加成功");
            jsonModel.setSuceess(true);

        } else {
            jsonModel.setMessage("添加失败");

        }
        //   jsonModel.setList(null);
        return jsonModel;
    }


    //TestValue
    //  /Session/1/2014-04-18/2014-04-20
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<ScheduleModel> get(@RequestParam(required = false, defaultValue = "") String session,
                      @RequestParam String uid,
                      @RequestParam(required = false, defaultValue = "") String bDate,
                      @RequestParam(required = false, defaultValue = "") String eDate) {
        //    WkbResult wkbResult=new WkbResult();
        //    wkbResult=this.checkSession(addUser.getSessionid(), uid);
        //    if(wkbResult.getSuccess().booleanValue()==false)
        //        return wkbResult;
        List<ScheduleModel> modelList = new ArrayList<ScheduleModel>();
        modelList = scheduleService.queryListByDate(uid, bDate, eDate);
        return modelList;// JSON.toJSONString(modelList);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @ResponseBody
    public ScheduleJson update(@RequestParam(required = false, defaultValue = "") String session,
                      @RequestParam int id,
                      @RequestParam int uid,
                      @RequestParam String sdate,
                      @RequestParam(required = false, defaultValue = "") String stext,
                      @RequestParam(required = false, defaultValue = "") String dtext,
                      @RequestParam(required = false, defaultValue = "") int rule,
                      @RequestParam(required = false, defaultValue = "") String remind,
                      @RequestParam(required = false, defaultValue = "") String creator
    ) throws Exception {
        ScheduleJson jsonModel = new ScheduleJson();
        ScheduleModel mode = new ScheduleModel();
        mode.setId(id);
        mode.setUid(uid);
        mode.setSschdate(setStringToDate(sdate));
        mode.setSschtext(URLDecoder.decode(stext,"utf-8"));
        mode.setSdailytext(URLDecoder.decode(dtext,"utf-8"));
        mode.setSremindrule(rule);
        mode.setSremindtime(setStringToDate(remind));

        mode.setCreator(creator);
        mode.setCrtdatetime(new java.util.Date());

        //    WkbResult wkbResult=new WkbResult();
        //    wkbResult=this.checkSession(addUser.getSessionid(), mode.getUid());
        //    if(wkbResult.getSuccess().booleanValue()==false)
        //        return wkbResult;


        int iResult = scheduleService.updateSchedule(mode);
        if (iResult == 1) {
            jsonModel.setMessage("更新成功");
            jsonModel.setSuceess(true);
        } else {
            jsonModel.setMessage("更新失败");
        }
        //   jsonModel.setList(null);
        return jsonModel;
    }

    /**
     * 字符串转日期
     *
     * @param date
     * @return
     */
    public static Date setStringToDate(String date) {
        Date formatDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatDate = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatDate;

    }

}
