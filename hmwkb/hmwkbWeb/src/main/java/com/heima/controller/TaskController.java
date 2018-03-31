package com.heima.controller;

import com.heima.common.WkbMessageEnum;
import com.heima.common.WkbResult;
import com.heima.common.WktResult;
import com.heima.common.WktStatus;
import com.heima.dao.model.WkbTaskQuery;
import com.heima.dao.model.WkbTaskShow;
import com.heima.json.*;
import com.heima.model.*;
import com.heima.security.model.ServiceResult;
import com.heima.security.service.ServiceTokenService;
import com.heima.security.util.ServiceResultUtils;
import com.heima.service.WkbTaskService;
import com.heima.service.WkbTaskuserService;
import com.heima.service.WkbUserService;
import com.heima.service.biz.TaskFile;
import com.heima.service.biz.WkbBizException;
import com.heima.service.biz.WkbTaskExt;
import com.heima.web.service.JPushService;
import com.heima.web.service.SystemConfigure;
import org.springframework.beans.BeanUtils;
//import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-16
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping({"/task"})
public class TaskController extends BaseController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private WkbTaskService wkbTaskService;

    @Autowired
    private WkbUserService wkbUserService;

    @Autowired
    private JPushService jPushService;

    @Autowired
    private SystemConfigure systemConfigure;

    @Autowired
    private ServiceTokenService serviceTokenService;

    @Autowired
    private WkbTaskuserService wkbTaskuserService;

    @RequestMapping(value = "/getTasks")
    @ResponseBody
    public WktResult getTasks(@RequestBody JsonTaskQuery jsonTaskQuery)
    {
        WktResult wktResult=new WktResult();
        if(jsonTaskQuery==null||jsonTaskQuery.getUserId()==null)
        {
            wktResult.getStatus().setErrorCode(1);
            wktResult.getStatus().setErrorMessage("未提供查询信息");
            return wktResult;
        }

        try
        {
            WkbTaskQuery wkbTaskQuery=new WkbTaskQuery();
            List<Integer> uIdList=new ArrayList<Integer>();
            uIdList.add(jsonTaskQuery.getUserId());
            wkbTaskQuery.setuIdList(uIdList);
            wkbTaskQuery.setuId(jsonTaskQuery.getUserId());
            if(jsonTaskQuery.getType()!=null)
            {
                if(jsonTaskQuery.getType().intValue()==0)
                    wkbTaskQuery.settIscomplete((byte) 0);
                else
                    wkbTaskQuery.settIscomplete((byte) 1);
            }

            if(jsonTaskQuery.getIndex()==null||jsonTaskQuery.getIndex().intValue()<=0)
                return wktResult;
            if(jsonTaskQuery.getPageSize()==null||jsonTaskQuery.getPageSize().intValue()<=0)
                return wktResult;
            int startPos=(jsonTaskQuery.getIndex()-1)*jsonTaskQuery.getPageSize()+1;
            int endPos=startPos+jsonTaskQuery.getPageSize()-1;

            wkbTaskQuery.setStartPos(startPos);
            wkbTaskQuery.setEndPos(endPos);
            wkbTaskQuery.setQueryNewCount(true);

            if(jsonTaskQuery.getDispatchType()!=null)
            {
                if(jsonTaskQuery.getDispatchType().intValue()==1
                        ||jsonTaskQuery.getDispatchType().intValue()==2)
                    wkbTaskQuery.setQueryType(jsonTaskQuery.getDispatchType());
            }
            List<WkbTaskShow> wkbTaskShowList = wkbTaskService.queryUserTasks(wkbTaskQuery);
            //获取用户名称
            if(wkbTaskShowList!=null)
            {
                List<Integer> taskIdList=new ArrayList<Integer>();
                List<JsonUserTask> jsonUserTaskList=new ArrayList<JsonUserTask>();

                for(WkbTaskShow wkbTaskShow:wkbTaskShowList)
                {
                    JsonUserTask jsonUserTask=new JsonUserTask();
                    jsonUserTask.setDate(wkbTaskShow.getCrtdatetime());
                    jsonUserTask.setDuration(wkbTaskShow.gettVoicetime());
                    jsonUserTask.setImportant(wkbTaskShow.gettIsimportt()!=null?wkbTaskShow.gettIsimportt().intValue():0);
                    jsonUserTask.setRead(wkbTaskShow.gettIsread().intValue());
                    jsonUserTask.setTaskId(wkbTaskShow.getId());
                    jsonUserTask.setSendUserId(wkbTaskShow.getuId());
                    jsonUserTask.setText(wkbTaskShow.gettText());
                    jsonUserTask.setCount(wkbTaskShow.getUnReadCount());
                    if(wkbTaskShow.gettVoicetime()!=null)
                        jsonUserTask.setVoiceId(wkbTaskShow.gettVoiceid());
                    if(wkbTaskShow.getWkbTaskfileList()!=null&&wkbTaskShow.getWkbTaskfileList().size()>0)
                    {
                        List<FileResult> fileids=new ArrayList<FileResult>();
                        for(WkbTaskfile wkbTaskfile:wkbTaskShow.getWkbTaskfileList())
                            fileids.add(new FileResult(wkbTaskfile.getId(),wkbTaskfile.gettFilename(),wkbTaskfile.gettFiletype()));
                        jsonUserTask.setFileInfos(fileids);
                    }

                    taskIdList.add(jsonUserTask.getTaskId());
                    jsonUserTaskList.add(jsonUserTask);
                }

                this.initUserTaskNames(jsonUserTaskList, wkbTaskuserService.fetchTaskUserList(taskIdList));
                wktResult.setResult(jsonUserTaskList);
            }
        }catch (WkbBizException wkbExp)
        {
            logger.error("query user tasks error:"+wkbExp.getCode(), wkbExp);
            wktResult.getStatus().setErrorCode(-100);
            wktResult.getStatus().setErrorMessage(this.getBizError(wkbExp.getCode()));
        }
        catch (Exception exp)
        {
            logger.error("query user tasks unkown error:",exp);
            wktResult.getStatus().setErrorCode(-200);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
        }
        return wktResult;
    }


    @RequestMapping(value = "/sendTask")
    @ResponseBody
    public WktResult createTask(
            HttpServletRequest request
    )
    {
        WktResult wktResult=new WktResult();

        logger.info("begin call createTask");
        WkbTaskResult wkbTaskResult=new WkbTaskResult();

        WkbTask wkbTask;
        try{
            wkbTask=this.getUploadTask(request);
            if(wkbTask.getId()!=null)
            {
                //回复任务
                wkbTaskResult=this.replyTask(wkbTask);
            }
            else
            {
                //发送任务
                wkbTaskService.createTask(wkbTask);
                wkbTaskResult.setTaskid(wkbTask.getId());
                if(wkbTask.gettVoiceid()==null)
                {
                    wkbTaskResult.setVoiceid(0);
                }
                else
                {
                    wkbTaskResult.setVoiceid(wkbTask.gettVoiceid());
                }
            }

            //推送消息提醒
            Map<String, Object> extra =new HashMap<String, Object>();
            extra.put("type", 1);
            for(WkbTaskuser wkbTaskuser:wkbTask.getWkbTaskuserList())
            {
                jPushService.pushMessage(wkbTaskuser.getuId(),"新任务","新任务",extra);
            }
            //
            HashMap<String,Integer> mapResult=new HashMap<String, Integer>();
            mapResult.put("taskId",wkbTaskResult.getTaskid());
            mapResult.put("voiceId",wkbTaskResult.getVoiceid());

            wktResult.setResult(mapResult);
        }
        catch (WkbBizException wkbExp)
        {
            logger.error("send task error:"+wkbExp.getCode(), wkbExp);
            wktResult.getStatus().setErrorCode(-100);
            wktResult.getStatus().setErrorMessage(this.getBizError(wkbExp.getCode()));
        }
        catch (Exception exp)
        {
            logger.error("send task unknown error:", exp);
            wktResult.getStatus().setErrorCode(-200);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
        }

        return wktResult;
    }


    private WkbTaskResult replyTask( WkbTask wkbTask)
    {
        WkbTaskResult wkbTaskResult=new WkbTaskResult();
        try{
            WkbTask wkbTaskMain=wkbTaskService.getWkbTask(wkbTask.getId());
            if(wkbTaskMain==null)
            {
                logger.error("error taskid:"+wkbTask.getId());
                throw new WkbBizException(WkbMessageEnum.TASK_NO_EXIST.getCode(),"");
            }

            WkbTaskdetail wkbTaskdetail=new WkbTaskdetail();
            BeanUtils.copyProperties(wkbTask, wkbTaskdetail);
            wkbTaskdetail.setId(null);
            wkbTaskdetail.settId(wkbTask.getId());
            for(WkbTaskuser wkbTaskuser:wkbTask.getWkbTaskuserList())
            {
                WkbTaskdetailuser wkbTaskdetailuser=new WkbTaskdetailuser();
                BeanUtils.copyProperties(wkbTaskuser, wkbTaskdetailuser);
                wkbTaskdetail.getWkbTaskdetailuserList().add(wkbTaskdetailuser);
            }
            wkbTaskService.addTaskDetail(wkbTaskdetail);

            wkbTaskResult.setTaskid(wkbTaskMain.getId());
            if(wkbTaskdetail.gettVoiceid()==null)
            {
                wkbTaskResult.setVoiceid(0);
            }
            else
                wkbTaskResult.setVoiceid(wkbTaskdetail.gettVoiceid());
        }
        catch (WkbBizException wkbExp)
        {
            logger.error("reply task error:"+wkbExp.getCode(), wkbExp);
            this.formatResult(wkbExp.getCode(),"",wkbTaskResult);
        }
        catch (Exception exp)
        {
            logger.error("reply task unknown error:", exp);
            wkbTaskResult.setSuccess(false);
            wkbTaskResult.setMessage(exp.getMessage());
        }

        return wkbTaskResult;
    }


    @RequestMapping(value = "/readTask")
    @ResponseBody
    public WktStatusResult readTask(@RequestBody JsonTask jsonTask)
    {
        WktStatus wktStatus=new WktStatus();
        if(jsonTask==null||jsonTask.getTaskId()==null)
        {
            wktStatus.setErrorMessage("未提供参数");
            wktStatus.setErrorCode(1);
            return new WktStatusResult(wktStatus);
        }

        try
        {
            Integer readFlag=wkbTaskService.readTask(jsonTask.getTaskId(),jsonTask.getUserId());
            //如果是任务接收者，那么推送消息给发送者
            //群发消息，只第一次推送
            //是否已读现在不推送消息
            //this.pushReadMessage(taskid,userid);
        }catch (WkbBizException wkbExp)
        {
            logger.error("read task error:"+wkbExp.getCode(),wkbExp);
            wktStatus.setErrorCode(-100);
            wktStatus.setErrorMessage(this.getBizError(wkbExp.getCode()));
        }catch (Exception exp)
        {
            logger.error("read task unkown error:", exp);
            wktStatus.setErrorCode(-200);
            wktStatus.setErrorMessage(exp.getMessage());
        }

        return new WktStatusResult(wktStatus);
    }

    @RequestMapping(value = "/getTaskDetail")
    @ResponseBody
    public WktResult getTaskDetail(@RequestBody JsonTaskQuery jsonTaskQuery)
    {
        WktResult wktResult=new WktResult();

        if(jsonTaskQuery==null||jsonTaskQuery.getTaskId()==null||jsonTaskQuery.getUserId()==null)
        {
            wktResult.getStatus().setErrorCode(1);
            wktResult.getStatus().setErrorMessage("未提供参数");
            return wktResult;
        }

        int index=1;
        int pagesize=10000;
        if(jsonTaskQuery.getIndex()!=null)
            index=jsonTaskQuery.getIndex();
        if(jsonTaskQuery.getPageSize()!=null)
            pagesize=jsonTaskQuery.getPageSize();

        if(index<=0||pagesize<=0)
            return wktResult;

        int startPos=(index-1)*pagesize;
        int endPos=startPos+pagesize;


        try
        {
            WkbTaskExt wkbTaskExt=wkbTaskService.queryWkbTaskDetail(jsonTaskQuery.getUserId(),jsonTaskQuery.getTaskId());
            //转换成json返回
            List<JsonUserTaskBase> jsonUserTaskList=new ArrayList<JsonUserTaskBase>();

            JsonUserTaskBase jsonUserTask=new JsonUserTask();
            if(wkbTaskExt.getWkbTaskfileList().size()>0)
            {
                List<FileResult> fileIdList=new ArrayList<FileResult>();
                for (WkbTaskfile wkbTaskfile:wkbTaskExt.getWkbTaskfileList())
                {
                    fileIdList.add(new FileResult(wkbTaskfile.getId(),wkbTaskfile.gettFilename(),wkbTaskfile.gettFiletype()));
                }
                jsonUserTask.setFileInfos(fileIdList);
            }
            jsonUserTask.setTaskId(wkbTaskExt.getId());
            jsonUserTask.setSendUserId(wkbTaskExt.getuId());
            //userTask.setName();
            jsonUserTask.setText(wkbTaskExt.gettText());
            if(wkbTaskExt.gettVoicetime()!=null)
                jsonUserTask.setVoiceId(wkbTaskExt.gettVoiceid());
            jsonUserTask.setDate(wkbTaskExt.getCrtdatetime());
            jsonUserTask.setImportant(wkbTaskExt.gettIsimportt().intValue());
            jsonUserTask.setDuration(wkbTaskExt.gettVoicetime());

            jsonUserTaskList.add(jsonUserTask);


            //明细
            for(WkbTaskdetail wkbTaskdetail:wkbTaskExt.getWkbTaskdetailList())
            {
                jsonUserTask=new JsonUserTask();
                if(wkbTaskdetail.getWkbTaskfileList().size()>0)
                {
                    List<FileResult> fileIdList=new ArrayList<FileResult>();
                    for (WkbTaskfile wkbTaskfile:wkbTaskdetail.getWkbTaskfileList())
                    {
                        fileIdList.add(new FileResult(wkbTaskfile.getId(),wkbTaskfile.gettFilename(),wkbTaskfile.gettFiletype()));
                    }
                    jsonUserTask.setFileInfos(fileIdList);
                }
                jsonUserTask.setTaskId(wkbTaskdetail.gettId());
                jsonUserTask.setSendUserId(wkbTaskdetail.getuId());
                //userTask.setName();
                jsonUserTask.setText(wkbTaskdetail.gettText());
                if(wkbTaskdetail.gettVoicetime()!=null)
                    jsonUserTask.setVoiceId(wkbTaskdetail.gettVoiceid());
                jsonUserTask.setDate(wkbTaskdetail.getCrtdatetime());
                jsonUserTask.setImportant(wkbTaskExt.gettIsimportt().intValue());
                jsonUserTask.setDuration(wkbTaskdetail.gettVoicetime());

                jsonUserTaskList.add(jsonUserTask);
            }

            initJsonUserNames(jsonUserTaskList);

            JsonRevertComparatorUserTask rcomparatorUserTask=new JsonRevertComparatorUserTask();
            Collections.sort(jsonUserTaskList, rcomparatorUserTask);

            //分页过滤
            List<JsonUserTaskBase> filterTaskList=new ArrayList<JsonUserTaskBase>();
            for(int i=startPos;i<endPos&&i<jsonUserTaskList.size();i++)
            {
                filterTaskList.add(jsonUserTaskList.get(i));
            }

            wktResult.setResult(filterTaskList);


        }catch (WkbBizException wkbExp)
        {
            logger.error("read task detail error:"+wkbExp.getCode(),wkbExp);
            wktResult.getStatus().setErrorCode(-100);
            wktResult.getStatus().setErrorMessage(this.getBizError(wkbExp.getCode()));
        }catch (Exception exp)
        {
            logger.error("read task detail unkown error:", exp);
            wktResult.getStatus().setErrorCode(-200);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
        }
        return wktResult;
    }


    @RequestMapping(value = "/searchUserTasks")
    @ResponseBody
    public WktResult searchUserTasks(@RequestBody JsonUserTaskQuery jsonUserTaskQuery)
    {
        WktResult wktResult=new WktResult();
        try
        {
            WkbTaskQuery wkbTaskQuery=new WkbTaskQuery();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try
            {
                if(StringUtils.isNotBlank(jsonUserTaskQuery.getDateFrom()))
                {
                    jsonUserTaskQuery.setDateFrom(jsonUserTaskQuery.getDateFrom()+" 00:00:00");
                    wkbTaskQuery.setDateFrom(simpleDateFormat.parse(jsonUserTaskQuery.getDateFrom()));
                }
                if(StringUtils.isNotBlank(jsonUserTaskQuery.getDateEnd()))
                {
                    jsonUserTaskQuery.setDateEnd(jsonUserTaskQuery.getDateEnd()+" 23:59:59");
                    wkbTaskQuery.setDateEnd(simpleDateFormat.parse(jsonUserTaskQuery.getDateEnd()));
                }
            }catch (Exception exp)
            {
                logger.error("parse date error:",exp);
            }
            if(jsonUserTaskQuery.getType()!=null)
            {
                if(jsonUserTaskQuery.getType().intValue()==0)
                {
                    wkbTaskQuery.settIscomplete((byte)0);
                }
                else
                {
                    wkbTaskQuery.settIscomplete((byte)1);
                }
            }
            if(jsonUserTaskQuery.getImportant()!=null)
            {
                 int flag=jsonUserTaskQuery.getImportant();
                 if(flag==1)
                    wkbTaskQuery.settIsimportt((byte)1);
                 else if(flag==0)
                    wkbTaskQuery.settIsimportt((byte)0);
            }

            List<Integer> uIdList=new ArrayList<Integer>();
            if(StringUtils.isNotBlank(jsonUserTaskQuery.getTargetIds()))
            {
                String[] items=jsonUserTaskQuery.getTargetIds().split(",");
                for(String item :items)
                {
                    if(StringUtils.isNotBlank(item))
                        uIdList.add(Integer.parseInt(item));
                }
            }
            if(uIdList.isEmpty())
                return wktResult;

            wkbTaskQuery.setuIdList(uIdList);

            if(jsonUserTaskQuery.getIndex()==null||jsonUserTaskQuery.getIndex().intValue()<=0)
                return wktResult;
            if(jsonUserTaskQuery.getPageSize()==null||jsonUserTaskQuery.getPageSize().intValue()<=0)
                return wktResult;
            int startPos=(jsonUserTaskQuery.getIndex()-1)*jsonUserTaskQuery.getPageSize()+1;
            int endPos=startPos+jsonUserTaskQuery.getPageSize()-1;

            wkbTaskQuery.setStartPos(startPos);
            wkbTaskQuery.setEndPos(endPos);
            if(jsonUserTaskQuery.getDispatchType()!=null)
            {
                if(jsonUserTaskQuery.getDispatchType().intValue()==1
                        ||jsonUserTaskQuery.getDispatchType().intValue()==2)
                    wkbTaskQuery.setQueryType(jsonUserTaskQuery.getDispatchType());
            }
            List<WkbTaskShow> wkbTaskShowList = wkbTaskService.queryUserTasks(wkbTaskQuery);
            //获取用户名称
            if(wkbTaskShowList!=null)
            {
                List<JsonUserTask> jsonUserTaskList=new ArrayList<JsonUserTask>();
                List<Integer> taskIdList=new ArrayList<Integer>();
                for(WkbTaskShow wkbTaskShow:wkbTaskShowList)
                {
                    JsonUserTask userTask=new JsonUserTask();

                    userTask.setDate(wkbTaskShow.getCrtdatetime());
                    userTask.setDuration(wkbTaskShow.gettVoicetime());
                    userTask.setImportant(wkbTaskShow.gettIsimportt()!=null?wkbTaskShow.gettIsimportt().intValue():0);
                    userTask.setRead(wkbTaskShow.gettIsread().intValue());
                    userTask.setTaskId(wkbTaskShow.getId());
                    userTask.setSendUserId(wkbTaskShow.getuId());
                    userTask.setText(wkbTaskShow.gettText());
                    if(wkbTaskShow.gettVoicetime()!=null)
                        userTask.setVoiceId(wkbTaskShow.gettVoiceid());
                    if(wkbTaskShow.getWkbTaskfileList()!=null&&wkbTaskShow.getWkbTaskfileList().size()>0)
                    {
                        List<FileResult> fileids=new ArrayList<FileResult>();
                        for(WkbTaskfile wkbTaskfile:wkbTaskShow.getWkbTaskfileList())
                            fileids.add(new FileResult(wkbTaskfile.getId(),wkbTaskfile.gettFilename(),wkbTaskfile.gettFiletype()));
                        userTask.setFileInfos(fileids);
                    }
                    jsonUserTaskList.add(userTask);
                    taskIdList.add(userTask.getTaskId());
                }

                initJsonXUserNames(jsonUserTaskList);

                initUserTaskNames(jsonUserTaskList, wkbTaskuserService.fetchTaskUserList(taskIdList));
                List<JsonUserTaskBase> jsonUserTaskBaseList=new ArrayList<JsonUserTaskBase>();
                for(JsonUserTask jsonUserTask:jsonUserTaskList)
                {
                    JsonUserTaskBase jsonUserTaskBase=new JsonUserTaskBase();
                    BeanUtils.copyProperties(jsonUserTask,jsonUserTaskBase);
                    jsonUserTaskBaseList.add(jsonUserTaskBase);
                }
                wktResult.setResult(jsonUserTaskBaseList);
            }
        }catch (WkbBizException wkbExp)
        {
            logger.error("query user tasks error:"+wkbExp.getCode(), wkbExp);
            wktResult.getStatus().setErrorCode(-100);
            wktResult.getStatus().setErrorMessage(this.getBizError(wkbExp.getCode()));
        }
        catch (Exception exp)
        {
            logger.error("query user tasks unkown error:",exp);
            wktResult.getStatus().setErrorCode(-200);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
        }
        return wktResult;
    }


    @RequestMapping(value = "/completeTask")
    @ResponseBody
    public WktStatusResult completeTask(@RequestBody JsonTaskQuery jsonTaskQuery)
    {
        WktStatus wktStatus=new WktStatus();

        if(jsonTaskQuery==null||jsonTaskQuery.getTaskId()==null||jsonTaskQuery.getType()==null)
        {
            wktStatus.setErrorMessage("未提供参数");
            wktStatus.setErrorCode(1);
            return new WktStatusResult(wktStatus);
        }

        if(jsonTaskQuery.getType().intValue()<0||jsonTaskQuery.getType().intValue()>4)
        {
            wktStatus.setErrorMessage("任务结束标识值不正确");
            wktStatus.setErrorCode(1);
            return new WktStatusResult(wktStatus);
        }

        try
        {
            if(jsonTaskQuery.getType().intValue()==4)
            {
                //删除任务
                wkbTaskService.deleteTask(jsonTaskQuery.getTaskId(),jsonTaskQuery.getUserId());
            }
            else
            {
                Integer perfFlag=null;
                if(jsonTaskQuery.getType().intValue()!=3)
                {
                    perfFlag=jsonTaskQuery.getType()+1;//1-优 2-良 3-差
                }
                else
                {
                    perfFlag=0;//没有评分 0-表示任务结束
                }
                Map<Integer,List<Integer>> mapLevel=new HashMap<Integer, List<Integer>>();
                //目前就只有设置全部
                if(perfFlag!=null)
                {
                    //获取所有人，然后设置
                    List<WkbTaskuser> wkbTaskuserList=wkbTaskuserService.fetchTaskUsers(jsonTaskQuery.getTaskId());
                    if(wkbTaskuserList!=null)
                    {
                        List<Integer> uIdList=new ArrayList<Integer>();
                        for(WkbTaskuser wkbTaskuser:wkbTaskuserList)
                        {
                            uIdList.add(wkbTaskuser.getuId());
                        }
                        mapLevel.put(perfFlag,uIdList);
                    }
                }
                wkbTaskService.completeTask(jsonTaskQuery.getTaskId(),jsonTaskQuery.getUserId(),mapLevel);
            }
        }catch (WkbBizException wkbExp)
        {
            logger.error("complete task error:"+wkbExp.getCode(),wkbExp);
            wktStatus.setErrorCode(-200);
            wktStatus.setErrorMessage(this.getBizError(wkbExp.getCode()));
        }catch (Exception exp)
        {
            logger.error("complete task unkown error:", exp);
            wktStatus.setErrorCode(-200);
            wktStatus.setErrorMessage(exp.getMessage());
        }

        return new WktStatusResult(wktStatus);
    }

    @RequestMapping(value = "/getUserStatistics", method = {RequestMethod.POST} )
    @ResponseBody
    public WktResult queryStatisticsTasks(@RequestBody JsonUserTaskQuery jsonUserTaskQuery)
    {
        WktResult wktResult=new WktResult();
        try
        {
            if(jsonUserTaskQuery==null||StringUtils.isBlank(jsonUserTaskQuery.getTargetIds()))
                return wktResult;

            WkbTaskQuery wkbTaskQuery=new WkbTaskQuery();
            wkbTaskQuery.settDel(null);
            List<Integer> uIdList=new ArrayList<Integer>();
            String[] items=jsonUserTaskQuery.getTargetIds().split(",");
            for(String item:items)
            {
                uIdList.add(Integer.parseInt(item));
            }
            wkbTaskQuery.getuIdList().addAll(uIdList);

            if(jsonUserTaskQuery.getIndex()==null||jsonUserTaskQuery.getIndex().intValue()<=0)
                return wktResult;
            if(jsonUserTaskQuery.getPageSize()==null||jsonUserTaskQuery.getPageSize().intValue()<=0)
                return wktResult;
            int startPos=(jsonUserTaskQuery.getIndex()-1)*jsonUserTaskQuery.getPageSize()+1;
            int endPos=startPos+jsonUserTaskQuery.getPageSize()-1;

            try
            {
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(StringUtils.isNotBlank(jsonUserTaskQuery.getDateFrom()))
                {
                    String str=jsonUserTaskQuery.getDateFrom();
                    str+=" 00:00:00";
                    wkbTaskQuery.setDateFrom(simpleDateFormat.parse(str));
                }
                if(StringUtils.isNotBlank(jsonUserTaskQuery.getDateEnd()))
                {
                    String str=jsonUserTaskQuery.getDateEnd();
                    str+=" 23:59:59";
                    wkbTaskQuery.setDateEnd(simpleDateFormat.parse(str));
                }
            }catch (Exception exp)
            {
                logger.error("parse date error:"+jsonUserTaskQuery.getDateFrom()+"-"+jsonUserTaskQuery.getDateEnd(),exp);
            }

            if(jsonUserTaskQuery.getImportant()!=null)
            {
                if(jsonUserTaskQuery.getImportant().intValue()==1)
                {
                    wkbTaskQuery.settIsimportt((byte)1);
                }
                else if(jsonUserTaskQuery.getImportant().intValue()==0)
                {
                    wkbTaskQuery.settIsimportt((byte)0);
                }
            }
            wkbTaskQuery.setStartPos(startPos);
            wkbTaskQuery.setEndPos(endPos);
            List<TaskStatistics> taskStatisticsList=wkbTaskService.queryUserStatistics(wkbTaskQuery);


            List<JsonTaskStatistics> jsonTaskStatisticsList=new ArrayList<JsonTaskStatistics>();
            //名称
            List<Integer> userIdList=new ArrayList<Integer>();
            for(TaskStatistics taskStatistics:taskStatisticsList)
            {
                JsonTaskStatistics jsonTaskStatistics=new JsonTaskStatistics();
                BeanUtils.copyProperties(taskStatistics,jsonTaskStatistics);
                jsonTaskStatistics.setUserId(taskStatistics.getUserId());

                if(!userIdList.contains(taskStatistics.getUserId()))
                {
                    userIdList.add(taskStatistics.getUserId());
                }

                jsonTaskStatisticsList.add(jsonTaskStatistics);
            }


            if(userIdList.size()>0)
            {
                List<WkbUser> wkbUserList=wkbUserService.queryUserList(userIdList);
                for(JsonTaskStatistics taskStatistics:jsonTaskStatisticsList)
                {
                    for(WkbUser wkbUser:wkbUserList)
                    {
                        if(wkbUser.getuId().equals(taskStatistics.getUserId()))
                        {
                            if(StringUtils.isNotBlank(wkbUser.getuName()))
                                taskStatistics.setName(wkbUser.getuName());
                            else
                                taskStatistics.setName(wkbUser.getuIdentifier());
                            if(StringUtils.isNotBlank(wkbUser.getImageUrl()))
                                taskStatistics.setImageUrl(systemConfigure.getImageServer()+wkbUser.getImageUrl());
                            break;
                        }
                    }
                }
            }
            wktResult.setResult(jsonTaskStatisticsList);

        }catch (WkbBizException wkbExp)
        {
            logger.error("query user statistics tasks error:"+wkbExp.getCode(), wkbExp);
            wktResult.getStatus().setErrorCode(-100);
            wktResult.getStatus().setErrorMessage(this.getBizError(wkbExp.getCode()));
        }
        catch (Exception exp)
        {
            logger.error("query user statistics tasks unkown error:",exp);
            wktResult.getStatus().setErrorCode(-200);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
        }
        return wktResult;
    }

    @RequestMapping(value = "/getSubordinateTasks")
    @ResponseBody
    public WktResult getSubordinateTasks(@RequestBody JsonSingleUserTaskQuery jsonSingleUserTaskQuery)
    {
        WktResult wktResult=new WktResult();
        if(jsonSingleUserTaskQuery==null||jsonSingleUserTaskQuery.getTargetId()==null)
        {
            wktResult.getStatus().setErrorCode(1);
            wktResult.getStatus().setErrorMessage("未提供查询参数信息");
            return wktResult;
        }
        if(jsonSingleUserTaskQuery.getType()!=null)
        {
            if(jsonSingleUserTaskQuery.getType().intValue()<0||jsonSingleUserTaskQuery.getType().intValue()>5)
            {
                wktResult.getStatus().setErrorCode(2);
                wktResult.getStatus().setErrorMessage("请求参数类型值不正确");
                return wktResult;
            }
        }
        try
        {
            WkbTaskQuery wkbTaskQuery=new WkbTaskQuery();
            wkbTaskQuery.settDel(null);
            List<Integer> uIdList=new ArrayList<Integer>();
            uIdList.add(jsonSingleUserTaskQuery.getTargetId());
            wkbTaskQuery.setuIdList(uIdList);
            if(jsonSingleUserTaskQuery.getType()!=null)
            {
                if(jsonSingleUserTaskQuery.getType().intValue()==0)
                {
                    wkbTaskQuery.setQueryType(1);
                }
                else if(jsonSingleUserTaskQuery.getType().intValue()==1)
                {
                    wkbTaskQuery.setQueryType(2);
                    wkbTaskQuery.settPerfm(null);
                }
                else if(jsonSingleUserTaskQuery.getType().intValue()==2)
                {
                    wkbTaskQuery.setQueryType(2);
                    wkbTaskQuery.settPerfm(null);
                    wkbTaskQuery.settIscomplete((byte)1);
                }
                else if(jsonSingleUserTaskQuery.getType().intValue()>2)
                {
                    wkbTaskQuery.setQueryType(2);
                    wkbTaskQuery.settIscomplete((byte)1);
                    wkbTaskQuery.settPerfm(jsonSingleUserTaskQuery.getType() - 2);
                }
            }

            if(jsonSingleUserTaskQuery.getIndex()==null||jsonSingleUserTaskQuery.getIndex().intValue()<=0)
                return wktResult;
            if(jsonSingleUserTaskQuery.getPageSize()==null||jsonSingleUserTaskQuery.getPageSize().intValue()<=0)
                return wktResult;
            int startPos=(jsonSingleUserTaskQuery.getIndex()-1)*jsonSingleUserTaskQuery.getPageSize()+1;
            int endPos=startPos+jsonSingleUserTaskQuery.getPageSize()-1;

            wkbTaskQuery.setStartPos(startPos);
            wkbTaskQuery.setEndPos(endPos);
            if(jsonSingleUserTaskQuery.getDispatchType()!=null)
            {
                if(jsonSingleUserTaskQuery.getDispatchType().intValue()==1||jsonSingleUserTaskQuery.getDispatchType().intValue()==2)
                    wkbTaskQuery.setQueryType(jsonSingleUserTaskQuery.getDispatchType());

            }
            if(jsonSingleUserTaskQuery.getImportant()!=null)
            {
                if(jsonSingleUserTaskQuery.getImportant().intValue()==1)
                {
                    wkbTaskQuery.settIsimportt((byte)1);
                }
                else if(jsonSingleUserTaskQuery.getImportant().intValue()==0)
                {
                    wkbTaskQuery.settIsimportt((byte)0);
                }
            }

            try
            {
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(StringUtils.isNotBlank(jsonSingleUserTaskQuery.getDateFrom()))
                {
                    String str=jsonSingleUserTaskQuery.getDateFrom();
                    str+=" 00:00:00";
                    wkbTaskQuery.setDateFrom(simpleDateFormat.parse(str));
                }
                if(StringUtils.isNotBlank(jsonSingleUserTaskQuery.getDateEnd()))
                {
                    String str=jsonSingleUserTaskQuery.getDateEnd();
                    str+=" 23:59:59";
                    wkbTaskQuery.setDateEnd(simpleDateFormat.parse(str));
                }
            }catch (Exception exp)
            {
                logger.error("parse date error:"+jsonSingleUserTaskQuery.getDateFrom()+"-"+jsonSingleUserTaskQuery.getDateEnd(),exp);
            }

            List<WkbTaskShow> wkbTaskShowList = wkbTaskService.queryUserTasks(wkbTaskQuery);
            //获取用户名称
            if(wkbTaskShowList!=null)
            {
                List<Integer> taskIdList=new ArrayList<Integer>();
                List<JsonUserTask> jsonUserTaskList=new ArrayList<JsonUserTask>();

                for(WkbTaskShow wkbTaskShow:wkbTaskShowList)
                {
                    JsonUserTask userTask=new JsonUserTask();
                    userTask.setDate(wkbTaskShow.getCrtdatetime());
                    userTask.setDuration(wkbTaskShow.gettVoicetime());
                    userTask.setImportant(wkbTaskShow.gettIsimportt()!=null?wkbTaskShow.gettIsimportt().intValue():0);
                    userTask.setRead(wkbTaskShow.gettIsread().intValue());
                    userTask.setTaskId(wkbTaskShow.getId());
                    userTask.setSendUserId(wkbTaskShow.getuId());
                    userTask.setText(wkbTaskShow.gettText());
                    if(wkbTaskShow.gettVoicetime()!=null)
                        userTask.setVoiceId(wkbTaskShow.gettVoiceid());
                    if(wkbTaskShow.getWkbTaskfileList()!=null&&wkbTaskShow.getWkbTaskfileList().size()>0)
                    {
                        List<FileResult> fileids=new ArrayList<FileResult>();
                        for(WkbTaskfile wkbTaskfile:wkbTaskShow.getWkbTaskfileList())
                            fileids.add(new FileResult(wkbTaskfile.getId(),wkbTaskfile.gettFilename(),wkbTaskfile.gettFiletype()));
                        userTask.setFileInfos(fileids);
                    }

                    taskIdList.add(wkbTaskShow.getId());

                    jsonUserTaskList.add(userTask);
                }

                initUserTaskNames(jsonUserTaskList,wkbTaskuserService.fetchTaskUserList(taskIdList)) ;
                List<JsonUserNameTask> jsonUserNameTaskList=new ArrayList<JsonUserNameTask>();
                for(JsonUserTask jsonUserTask:jsonUserTaskList)
                {
                    JsonUserNameTask jsonUserNameTask=new JsonUserNameTask();
                    BeanUtils.copyProperties(jsonUserTask,jsonUserNameTask);
                    jsonUserNameTaskList.add(jsonUserNameTask);
                }
                wktResult.setResult(jsonUserNameTaskList);
            }
        }catch (WkbBizException wkbExp)
        {
            logger.error("query subordinate tasks error:"+wkbExp.getCode(), wkbExp);
            wktResult.getStatus().setErrorCode(-100);
            wktResult.getStatus().setErrorMessage(this.getBizError(wkbExp.getCode()));
        }
        catch (Exception exp)
        {
            logger.error("query subordinate tasks unkown error:", exp);
            wktResult.getStatus().setErrorCode(-200);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
        }
        return wktResult;
    }

    @RequestMapping(value = "/getTaskUsers")
    @ResponseBody
    public WktResult getTaskUsers(@RequestBody JsonTaskQuery jsonTaskQuery)
    {
        WktResult wktResult=new WktResult();
        if(jsonTaskQuery==null||jsonTaskQuery.getUserId()==null||jsonTaskQuery.getTaskId()==null)
        {
            wktResult.getStatus().setErrorCode(1);
            wktResult.getStatus().setErrorMessage("未提供查询参数");
            return wktResult;
        }

        try
        {
            WkbTask wkbTask=wkbTaskService.getWkbTask(jsonTaskQuery.getTaskId());
            List<JsonUserTaskBase> jsonUserTaskBaseList=new ArrayList<JsonUserTaskBase>();
            if(wkbTask!=null)
            {
                if(jsonTaskQuery.getUserId().equals(wkbTask.getuId()))
                {
                    List<WkbTaskuser> wkbTaskuserList=wkbTaskuserService.fetchTaskUsers(jsonTaskQuery.getTaskId());
                    if(wkbTaskuserList!=null)
                    {
                        for(WkbTaskuser wkbTaskuser:wkbTaskuserList)
                        {
                            JsonUserTaskBase jsonUserTaskBase=new JsonUserTaskBase();
                            jsonUserTaskBase.setSendUserId(wkbTaskuser.getuId());
                            jsonUserTaskBaseList.add(jsonUserTaskBase);
                        }
                    }
                }
                else
                {
                    JsonUserTaskBase jsonUserTaskBase=new JsonUserTaskBase();
                    jsonUserTaskBase.setSendUserId(wkbTask.getuId());
                    jsonUserTaskBaseList.add(jsonUserTaskBase);
                }

                this.initJsonUserNames(jsonUserTaskBaseList);
                List<JsonTaskUserName> jsonTaskUserNameList=new ArrayList<JsonTaskUserName>();
                for(JsonUserTaskBase jsonUserTaskBase:jsonUserTaskBaseList)
                {
                    JsonTaskUserName jsonTaskUserName=new JsonTaskUserName();
                    jsonTaskUserName.setUserId(jsonUserTaskBase.getSendUserId());
                    jsonTaskUserName.setName(jsonUserTaskBase.getName());
                    jsonTaskUserName.setImageUrl(jsonUserTaskBase.getImageUrl());

                    if(!jsonTaskUserNameList.contains(jsonTaskUserName))
                        jsonTaskUserNameList.add(jsonTaskUserName);
                }

                wktResult.setResult(jsonTaskUserNameList);
            }
        }catch (WkbBizException wkbExp)
        {
            logger.error("query task users error:"+wkbExp.getCode(), wkbExp);
            wktResult.getStatus().setErrorCode(-100);
            wktResult.getStatus().setErrorMessage(this.getBizError(wkbExp.getCode()));
        }
        catch (Exception exp)
        {
            logger.error("query task users unkown error:",exp);
            wktResult.getStatus().setErrorCode(-200);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
        }

        return wktResult;
    }


    @RequestMapping(value = "/getTaskFile")
    @ResponseBody
    public void downloadFile(
            @RequestParam Integer userId,

            @RequestParam Integer taskId,
            @RequestParam String type,
            @RequestParam Integer fileId,
            @RequestParam(required = false, defaultValue = "") String token,
            HttpServletResponse response
    )
    {
        logger.info("begin call downloadFile");
        WkbResult wkbResult=new WkbResult();
        if(taskId ==null||type==null|| fileId ==null)
        {
            wkbResult.setMessage("未提供参数");
            wkbResult.setSuccess(false);
            logger.error(wkbResult.getMessage());
            return;
        }

        try
        {
            TaskFile taskFile=wkbTaskService.fetchTaskFile(taskId, fileId, userId,type);
            response.setContentType(taskFile.getFileType());
            response.setContentLength(taskFile.getFileSize());

            try{
            BufferedInputStream in = null;
            BufferedOutputStream out = null;
            OutputStream os;
            os = new BufferedOutputStream(response.getOutputStream());
            in = new BufferedInputStream(taskFile.getData());
            out = new BufferedOutputStream(os);
            byte[] buffer = new byte[1024 * 8];
            int j = -1;
            while ((j = in.read(buffer)) != -1) {
                out.write(buffer, 0, j);
            }
                out.flush();
            }catch (Exception exp)
            {
                logger.error("download file error:",exp);
            }
            //Streams.copy(taskFile.getData(),response.getOutputStream(),true);
            //response.getOutputStream().write();
            //org.apache.commons.io.IOUtil.copy(taskFile.getData(),response.getOutputStream());
            //response.flushBuffer();
        }catch (WkbBizException wkbExp)
        {
            logger.error("download task error:"+wkbExp.getCode(),wkbExp);
            this.formatResult(wkbExp.getCode(),"",wkbResult);
        }catch (Exception exp)
        {
            logger.error("download task unkown error:", exp);
            wkbResult.setSuccess(false);
            wkbResult.setMessage(exp.getMessage());
        }
        return;// wkbResult;
    }


    private void initJsonUserNames(List<JsonUserTaskBase> userTaskList)
    {
        if(userTaskList==null||userTaskList.size()<=0)
            return;
        List<Integer> uIdList=new ArrayList<Integer>();
        for(JsonUserTaskBase userTask:userTaskList)
        {
            if(!uIdList.contains(userTask.getSendUserId()))
                uIdList.add(userTask.getSendUserId());
        }
        if(uIdList.size()>0)
        {
            List<WkbUser> wkbUserList=wkbUserService.queryUserList(uIdList);
            for(JsonUserTaskBase userTask:userTaskList)
            {
                for(WkbUser wkbUser:wkbUserList)
                {
                    if(wkbUser.getuId().equals(userTask.getSendUserId()))
                    {
                        if(StringUtils.isNotBlank(wkbUser.getuName()))
                            userTask.setName(wkbUser.getuName());
                        else
                            userTask.setName(wkbUser.getuIdentifier());

                        if(StringUtils.isNotBlank(wkbUser.getImageUrl()))
                            userTask.setImageUrl(systemConfigure.getImageServer()+wkbUser.getImageUrl());

                        break;
                    }
                }
            }
        }
    }

    private void initJsonXUserNames(List<JsonUserTask> userTaskList)
    {
        if(userTaskList==null||userTaskList.size()<=0)
            return;
        List<Integer> uIdList=new ArrayList<Integer>();
        for(JsonUserTask userTask:userTaskList)
        {
            if(!uIdList.contains(userTask.getSendUserId()))
                uIdList.add(userTask.getSendUserId());
        }
        if(uIdList.size()>0)
        {
            List<WkbUser> wkbUserList=wkbUserService.queryUserList(uIdList);
            for(JsonUserTask userTask:userTaskList)
            {
                for(WkbUser wkbUser:wkbUserList)
                {
                    if(wkbUser.getuId().equals(userTask.getSendUserId()))
                    {
                        userTask.setName(wkbUser.getuName());
                        break;
                    }
                }
            }
        }
    }

    private void initUserTaskNames(List<JsonUserTask> userTaskList,Map<Integer,List<WkbTaskuser>> mapTaskRecvers)
    {
        if(userTaskList==null||userTaskList.size()<=0)
            return;

        List<Integer> uIdList=new ArrayList<Integer>();

        for(Map.Entry<Integer,List<WkbTaskuser>> entry:mapTaskRecvers.entrySet())
        {
            for(WkbTaskuser wkbTaskuser:entry.getValue())
            {
                if(!uIdList.contains(wkbTaskuser.getuId()))
                {
                    uIdList.add(wkbTaskuser.getuId());
                }
            }
        }
        for(JsonUserTask userTask:userTaskList)
        {
            if(!uIdList.contains(userTask.getSendUserId()))
                uIdList.add(userTask.getSendUserId());

        }
        if(uIdList.size()>0)
        {
            List<WkbUser> wkbUserList=wkbUserService.queryUserList(uIdList);
            for(JsonUserTask userTask:userTaskList)
            {
                for(WkbUser wkbUser:wkbUserList)
                {
                    if(wkbUser.getuId().equals(userTask.getSendUserId()))
                    {
                        //如果没有设置名称，那么使用账号代替
                        if(StringUtils.isBlank(wkbUser.getuName()))
                            userTask.setName(wkbUser.getuIdentifier());
                        else
                            userTask.setName(wkbUser.getuName());

                        if(StringUtils.isNotBlank(wkbUser.getImageUrl()))
                            userTask.setImageUrl(systemConfigure.getImageServer()+wkbUser.getImageUrl());
                        break;
                    }
                }

                //获取接收者名称
                if(mapTaskRecvers.containsKey(userTask.getTaskId()))
                {
                    List<WkbTaskuser> wkbTaskuserList=mapTaskRecvers.get(userTask.getTaskId());
                    for(WkbTaskuser wkbTaskuser:wkbTaskuserList)
                    {
                        for(WkbUser wkbUser:wkbUserList)
                        {
                            if(wkbUser.getuId().equals(wkbTaskuser.getuId()))
                            {
                                JsonUserName jsonUserName=new JsonUserName();
                                if(StringUtils.isNotBlank(wkbUser.getuName()))
                                    jsonUserName.setName(wkbUser.getuName());
                                else
                                    jsonUserName.setName(wkbUser.getuIdentifier());
                                if(StringUtils.isNotBlank(wkbUser.getImageUrl()))
                                    jsonUserName.setImageUrl(systemConfigure.getImageServer()+wkbUser.getImageUrl());
                                userTask.getReceiverNames().add(jsonUserName);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


    private boolean pushReadMessage(Integer taskId,Integer uId)
    {
        WkbTask wkbTask=wkbTaskService.getWkbTask(taskId);
        Map<String, Object> extra =new HashMap<String, Object>();
        extra.put("type", 1);
        return jPushService.pushMessage(wkbTask.getuId(),"任务已读","任务已读",extra);
    }

    private WkbTask getUploadTask(HttpServletRequest request) throws Exception {

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        logger.debug("multi part:"+String.valueOf(isMultipart));
        request.setCharacterEncoding("UTF-8");
        WkbTask wkbTask=new WkbTask();

        String sessionid=null;
        DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 1024 * 20, null);
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        servletFileUpload.setFileSizeMax(1024 * 1024 * 20);
        servletFileUpload.setHeaderEncoding("UTF-8");
        @SuppressWarnings("unchecked")
        List<FileItem> fileItems = servletFileUpload.parseRequest(request);
        if (null != fileItems && fileItems.size() > 0) {
            for (FileItem item : fileItems) {
                if (item.isFormField()) {
                    // 普通表单项目
                    logger.info("here1");
                    String fieldName = item.getFieldName();
                    logger.info(fieldName+"---"+item.getString());
                    if ("taskId".equalsIgnoreCase(fieldName)) {
                        String str = item.getString();
                        if(StringUtils.isNotBlank(str))
                            wkbTask.setId( Integer.parseInt(item.getString()));
                    }
                    else if("userId".equalsIgnoreCase(fieldName))
                    {
                        wkbTask.setuId(Integer.parseInt(item.getString()));
                    }
                    else if("text".equals(fieldName)) {
                        String temp=item.getString();
                        if(StringUtils.isNotBlank(temp)) {
                            String temp1 = URLDecoder.decode(temp,"UTF-8");
                            wkbTask.settText(temp1);
                        }
                    }
                    else if("duration".equals(fieldName))
                        wkbTask.settVoicetime(Integer.parseInt(item.getString()));
                    else if("important".equals(fieldName))
                        wkbTask.settIsimportt(Byte.parseByte(item.getString()));
                    else if("targetIds".equalsIgnoreCase(fieldName))
                    {
                        logger.info("send task targetids:"+item.getString());
                        String[] uIds=item.getString().split(",");
                        for(String uId:uIds)
                        {
                            if(StringUtils.isNotBlank(uId))
                            {
                                WkbTaskuser wkbTaskuser=new WkbTaskuser();
                                wkbTaskuser.setuId(Integer.parseInt(uId));
                                wkbTaskuser.settIsread((byte)0);
                                wkbTask.getWkbTaskuserList().add(wkbTaskuser);
                            }
                        }
                    }
                    else if("token".equals(fieldName))
                        sessionid=item.getString();

                } else {
                    logger.info(item.getName()+"-**-"+item.getFieldName());
                    // 文件
                    // 图片输入流
                    InputStream inputStream = item.getInputStream();
                    // 文件名 xxx.jpg
                    String fileName = item.getName();



                    String fileType = item.getFieldName(); //item.getFileName();  //获取android端设置的文件类别如 mp3，img，根据不同的类别，判断不同的文件
                    if("voicemp3".equals(fileType)/*||fileName.contains("test")*/)
                    {
                        wkbTask.settVoice(inputStream);
                        wkbTask.settVoicesize(new Integer(String.valueOf(item.getSize())));
                    }
                    else
                    {


                        WkbTaskfile wkbTaskfile=new WkbTaskfile();
                        wkbTaskfile.settFile(inputStream);
                        wkbTaskfile.settFilesize(new Integer(String.valueOf(item.getSize())));
                        wkbTaskfile.settFilename(fileName);
                        wkbTaskfile.settFiletype(fileType);

                        String strType=URLDecoder.decode(fileType,"utf-8");
                        logger.info("new file type and name:"+strType);
                        int index=strType.indexOf("_");
                        if(index>0)
                        {
                            fileType=strType.substring(0,index);
                            fileName=strType.substring(index+1);
                            wkbTaskfile.settFiletype(fileType);
                            wkbTaskfile.settFilename(fileName);
                        }
                        wkbTask.getWkbTaskfileList().add(wkbTaskfile);
                    }
                    //android端代码 entity.addPart("img", new FileBody(file));  entity.addPart("mp3", new FileBody(file));
                    //params.put("file", null);
                }
            }
        }
        else
        {
            logger.error("no task data");
        }

        //验证用户
        if(wkbTask.getuId()==null)
            throw new WkbBizException(WkbMessageEnum.USER_NO_ID.getCode(),"");
        if(wkbTask.getWkbTaskuserList().size()<=0)
            throw new WkbBizException(WkbMessageEnum.TASK_NO_RECEIVER.getCode(),"");
        ServiceResult serviceResult=serviceTokenService.verifyToken(sessionid);
        if(!ServiceResultUtils.isSucc(serviceResult))
        {
            String errorMsg=ServiceResultUtils.getDescFromCode(serviceResult.getCode());
            logger.error("sessionid:"+sessionid);
            logger.error("userid:"+wkbTask.getuId());
            logger.error("login info error:"+serviceResult.getCode()+"-"+errorMsg);
            throw new RuntimeException(errorMsg);
        }
        return wkbTask;
    }
}
