package com.heima.service.impl;

import com.heima.common.WkbMessageEnum;
import com.heima.dao.WkbTaskDao;
import com.heima.dao.WkbTaskdetailuserDao;
import com.heima.dao.WkbTaskfileDao;
import com.heima.dao.model.WkbTaskQuery;
import com.heima.dao.model.WkbTaskShow;
import com.heima.dao.model.WkbTaskStatistics;
import com.heima.dao.model.WkbUnReadCount;
import com.heima.model.*;
import com.heima.service.WkbSeqService;
import com.heima.service.WkbTaskService;
import com.heima.service.WkbTaskdetailService;
import com.heima.service.WkbTaskuserService;
import com.heima.service.biz.TaskFile;
import com.heima.json.TaskStatistics;
import com.heima.service.biz.WkbBizException;
import com.heima.service.biz.WkbTaskExt;
//import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-15
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class WkbTaskServiceImpl implements WkbTaskService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WkbTaskServiceImpl.class);

    @Autowired
    private WkbTaskDao wkbTaskDao;

    @Autowired
    private WkbTaskuserService wkbTaskuserService;



    @Autowired
    private WkbTaskfileDao wkbTaskfileDao;

    @Autowired
    private WkbTaskdetailService wkbTaskdetailService;

    @Autowired
    private WkbTaskdetailuserDao wkbTaskdetailuserDao;

    //@Override
    public int saveTask(String name, InputStream data) {
        return wkbTaskDao.saveTask(name,data);
    }

    @Override
    public InputStream getData(Integer id) {
        WkbTask wkbTask=wkbTaskDao.getData(id);
        return wkbTask.gettVoice();
    }

    @Autowired
    private WkbSeqService wkbSeqService;

    @Override
    public void createTask(WkbTask wkbTask) {
        if(wkbTask==null)
            return;
        if(wkbTask.getuId()==null)
            throw new WkbBizException(WkbMessageEnum.USER_NO_ID.getCode(),"");
        if(wkbTask.getCrtdatetime()==null)
            wkbTask.setCrtdatetime(new Date());
        wkbTask.setUpdatetime(wkbTask.getCrtdatetime());

        if(StringUtils.isBlank(wkbTask.getCreator()))
            wkbTask.setCreator(wkbTask.getuId().toString());

        if(wkbTask.getWkbTaskuserList()==null||wkbTask.getWkbTaskuserList().size()<=0)
            throw new WkbBizException(WkbMessageEnum.TASK_NO_RECEIVER.getCode(),"");

        for(WkbTaskuser wkbTaskuser:wkbTask.getWkbTaskuserList())
        {
            if(wkbTaskuser.getuId()==null)
                throw new WkbBizException(WkbMessageEnum.TASK_NO_RECEIVER.getCode(),"");
        }
        //首先保存主表
        wkbTask.settIsread((byte)1);//发送任务，发送者默认已读
        wkbTask.settIscomplete((byte)0);
        if(wkbTask.gettVoice()!=null&&wkbTask.gettVoicetime()!=null&&wkbTask.gettVoicetime().intValue()>0)
        {
            wkbTask.settVoiceid(wkbSeqService.getVoiceNo());
        }
        int count=wkbTaskDao.insertTask(wkbTask);
        if(wkbTask.getId()==null)
        {
            logger.error("create task get no id");
            throw new WkbBizException(WkbMessageEnum.SYSTEM_ERROR.getCode(),"");
        }
        //保存接收人
        for(WkbTaskuser wkbTaskuser:wkbTask.getWkbTaskuserList())
        {
            wkbTaskuser.settIsread((byte)0);
            wkbTaskuser.settId(wkbTask.getId());
        }
        count=0;
        /*for(WkbTaskuser wkbTaskuser:wkbTask.getWkbTaskuserList())
            count+= wkbTaskuserDao.insertData(wkbTaskuser);*/
        wkbTaskuserService.saveTaskUsers(wkbTask.getWkbTaskuserList());
        /*if(count!=wkbTask.getWkbTaskuserList().size())
            throw new WkbBizException(WkbMessageEnum.SYSTEM_ERROR.getCode(),"");*/
        //保存附件
        if(wkbTask.getWkbTaskfileList()!=null&&wkbTask.getWkbTaskfileList().size()>0)
        {
            for(WkbTaskfile wkbTaskfile:wkbTask.getWkbTaskfileList())
            {
                if(wkbTaskfile.getCreator()==null)
                    wkbTaskfile.setCreator(wkbTask.getCreator());
                if(wkbTaskfile.getCrtdatetime()==null)
                    wkbTaskfile.setCrtdatetime(wkbTask.getCrtdatetime());
                wkbTaskfile.settId(wkbTask.getId());

                count=wkbTaskfileDao.insertData(wkbTaskfile);

            }
        }
    }

    @Override
    public List<WkbTaskShow> queryUserTasks(WkbTaskQuery wkbTaskQuery) {
        //mysql
        wkbTaskQuery.setStartPos(wkbTaskQuery.getStartPos()-1);
        wkbTaskQuery.setEndPos(wkbTaskQuery.getEndPos()-wkbTaskQuery.getStartPos());

        List<String> sortList=new ArrayList<String>();
        boolean bComplete=true;
        if(wkbTaskQuery.gettIscomplete()==null || wkbTaskQuery.gettIscomplete().intValue()==0)
        {
            bComplete=false;
            sortList.add("t_isread");
        }
        sortList.add("crtdatetime");
        List<WkbTaskShow> wkbTaskShowList=null;
        if(wkbTaskQuery.getQueryType()!=null&&wkbTaskQuery.getQueryType().intValue()==1)
            wkbTaskShowList= wkbTaskDao.querySendWkbTasks(wkbTaskQuery, sortList);
        else  if(wkbTaskQuery.getQueryType()!=null&&wkbTaskQuery.getQueryType().intValue()==2)
            wkbTaskShowList= wkbTaskDao.queryRecvWkbTasks(wkbTaskQuery, sortList);
        else
            wkbTaskShowList= wkbTaskDao.queryWkbTasks(wkbTaskQuery, sortList);
        //获取任务附件
        List<Integer> taskIdList=new ArrayList<Integer>();
        for(WkbTaskShow wkbTaskShow:wkbTaskShowList)
        {
            if(!taskIdList.contains(wkbTaskShow.getId()))
                taskIdList.add(wkbTaskShow.getId());
        }
        if(taskIdList.size()>0)
        {
            //
            fetchTaskShowUnread(wkbTaskQuery, bComplete, wkbTaskShowList, taskIdList);
            List<WkbTaskfile> wkbTaskfileList=wkbTaskfileDao.queryMainFilesByTasks(taskIdList);
            if(wkbTaskfileList!=null)
            {
                for(WkbTaskfile wkbTaskfile:wkbTaskfileList)
                {
                    for(WkbTaskShow wkbTaskShow:wkbTaskShowList)
                    {
                        if(wkbTaskfile.gettId().equals(wkbTaskShow.getId()))
                            wkbTaskShow.getWkbTaskfileList().add(wkbTaskfile);
                    }
                }
            }
        }
        return wkbTaskShowList;
    }

    private void fetchTaskShowUnread(WkbTaskQuery wkbTaskQuery, boolean bComplete, List<WkbTaskShow> wkbTaskShowList, List<Integer> taskIdList) {
        if(wkbTaskQuery.getQueryNewCount()!=null&&wkbTaskQuery.getQueryNewCount().booleanValue()&&bComplete==false)
        {
            //查询未读数量
            if(wkbTaskQuery.getuId()!=null)
            {
                List<WkbUnReadCount> wkbUnReadCountList=wkbTaskdetailuserDao.queryTaskUnReadCount(taskIdList, wkbTaskQuery.getuId());
                if(wkbUnReadCountList!=null)
                {
                    //每个任务赋值未读数量
                    for(WkbTaskShow wkbTaskShow:wkbTaskShowList)
                    {
                        wkbTaskShow.setUnReadCount(this.getUnreadTaskCount(wkbUnReadCountList,wkbTaskShow,wkbTaskQuery.getuId()));
                    }
                }
            }
        }
    }

    private Integer getUnreadTaskCount(List<WkbUnReadCount> wkbUnReadCountList,WkbTaskShow wkbTaskShow, Integer userId)
    {
        Integer count=0;
        for(WkbUnReadCount wkbUnReadCount:wkbUnReadCountList)
        {
            if(wkbUnReadCount.getTaskId().intValue()==wkbTaskShow.getId().intValue())
            {
                count=wkbUnReadCount.getCount();
                break;
            }
        }
        //如果是接收任务，加上接收任务未读数量
        if(!wkbTaskShow.getuId().equals(userId))
        {
            if(wkbTaskShow.gettIsread()!=null&&wkbTaskShow.gettIsread().intValue()==1)
            {
                count+=1;
            }
        }
        return count;
    }

    @Override
    public Integer readTask(Integer taskId, Integer uId) {
        //将任务设置成已读，如果是任务发送者，那么直接更新任务主表
        //如果是任务接收者，那么更新任务接收者主表以及任务明细接收者表()
        WkbTask wkbTask= wkbTaskDao.getTask(taskId);
        if(wkbTask==null)
            throw new WkbBizException(WkbMessageEnum.TASK_NO_EXIST.getCode(),"");
        if(wkbTask.getuId().equals(uId))
        {
            wkbTaskDao.updateReadFlag(taskId,new Date(),1);
        }
        else
        {
            List<Integer> uIdList=new ArrayList<Integer>();
            uIdList.add(uId);
            //然后更新主表
            wkbTaskuserService.updateRecvReadByTask(taskId, uIdList, 1);
        }
        //首先更新明细
        WkbTaskdetailuser wkbTaskdetailuser=new WkbTaskdetailuser();
        wkbTaskdetailuser.settId(taskId);
        wkbTaskdetailuser.settIsread((byte) 1);
        wkbTaskdetailuser.setReaddate(new Date());
        wkbTaskdetailuser.setuId(uId);
        wkbTaskdetailuserDao.updateDetailRecvReadByTask(wkbTaskdetailuser);

        return null;
    }



    @Override
    public void addTaskDetail(WkbTaskdetail wkbTaskdetail) {
        //首先检查任务是否存在
        if(wkbTaskdetail==null)
            return;
        if(wkbTaskdetail.gettId()==null)
            throw new WkbBizException(WkbMessageEnum.TASK_NO_ID.getCode(),"");
        if(wkbTaskdetail.getWkbTaskdetailuserList()==null||wkbTaskdetail.getWkbTaskdetailuserList().size()==0)
            throw new WkbBizException(WkbMessageEnum.TASK_NO_RECEIVER.getCode(),"");
        if(wkbTaskdetail.getuId()==null)
            throw new WkbBizException(WkbMessageEnum.TASK_NO_SENDER.getCode(),"");

        for(WkbTaskdetailuser wkbTaskdetailuser:wkbTaskdetail.getWkbTaskdetailuserList())
        {
            if(wkbTaskdetailuser.getuId()==null)
                throw new WkbBizException(WkbMessageEnum.TASK_NO_RECEIVER.getCode(),"");
        }

        if(wkbTaskdetail.gettVoice()!=null&&wkbTaskdetail.gettVoicetime()!=null&&wkbTaskdetail.gettVoicetime().intValue()>0)
        {
            wkbTaskdetail.settVoiceid(wkbSeqService.getVoiceNo());
        }
        wkbTaskdetailService.saveTaskdetail(wkbTaskdetail);

        //修改任务未读状态
        //如果是任务发送者，那么更新任务接收人的未读标志
        WkbTask wkbTask= wkbTaskDao.getTask(wkbTaskdetail.gettId());
        if(wkbTask.getuId().equals(wkbTaskdetail.getuId()))
        {
            List<Integer> uIdList=new ArrayList<Integer>();
            for(WkbTaskdetailuser wkbTaskdetailuser:wkbTaskdetail.getWkbTaskdetailuserList())
            {
                if(!uIdList.contains(wkbTaskdetailuser.getuId()))
                {
                    uIdList.add(wkbTaskdetailuser.getuId());
                }
            }
            wkbTaskuserService.updateRecvReadByTask(wkbTask.getId(),uIdList,0);
            //更新任务删除状态
            wkbTaskuserService.updateRecvDelFlagByTask(wkbTask.getId(), uIdList, 0);
        }
        else
        {
            //只需要更新任务发送者的读取状态
            if(wkbTask.gettIsread().intValue()==1)
                wkbTaskDao.updateReadFlag(wkbTask.getId(),new Date(),0);
            if(wkbTask.gettDel().intValue()==1)
                wkbTaskDao.updateDelFlag(wkbTask.getId(),new Date(),0);
        }

        wkbTaskDao.uncompleteTask(wkbTask.getId(),new Date());
        wkbTaskuserService.clearTaskUserPerfmLevel(wkbTask.getId());

    }

    @Override
    public void completeTask(Integer taskId, Integer uId, Map<Integer,List<Integer>> mapLevel) {
        //只有任务发送者才能结束任务
        WkbTask wkbTask=wkbTaskDao.getTask(taskId);
        if(!uId.equals(wkbTask.getuId()))
        {
            throw new WkbBizException(WkbMessageEnum.TASK_COMPLETE_USER_ERROR.getCode(),"");
        }
        wkbTaskDao.completeTask(taskId,new Date(),uId);
        //评分
        if(mapLevel!=null&&mapLevel.size()>0)
        {
            wkbTaskuserService.updateTaskUserPerfmLevel(taskId,mapLevel);
        }
    }

    @Override
    public List<TaskStatistics> queryUserStatistics(WkbTaskQuery wkbTaskQuery) {
        //首先确定用户
        List<TaskStatistics> taskStatisticsList=new ArrayList<TaskStatistics>();
        List<Integer> uIdList=new ArrayList<Integer>();
        if(wkbTaskQuery.getStartPos().intValue()<=0)
            return taskStatisticsList;
        if(wkbTaskQuery.getEndPos().intValue()<=0)
            return taskStatisticsList;
        for(int i=wkbTaskQuery.getStartPos()-1;i<wkbTaskQuery.getuIdList().size()&&i<wkbTaskQuery.getEndPos().intValue();i++)
        {
            uIdList.add(wkbTaskQuery.getuIdList().get(i));
        }
        if(uIdList.size()>0)
        {
            wkbTaskQuery.setuIdList(uIdList);
            List<WkbTaskStatistics> wkbTaskStatisticsList= wkbTaskDao.selectSendTaskCount(wkbTaskQuery);
            if(wkbTaskStatisticsList==null)
                wkbTaskStatisticsList=new ArrayList<WkbTaskStatistics>();

            List<WkbTaskStatistics> wkbTaskRecvStatisticsList= wkbTaskDao.selectRecvTaskCount(wkbTaskQuery);
            if(wkbTaskRecvStatisticsList==null)
                wkbTaskRecvStatisticsList=new ArrayList<WkbTaskStatistics>();

            for(Integer uId:uIdList)
            {
                TaskStatistics taskStatistics=new TaskStatistics();
                taskStatistics.setUserId(uId);

                for(WkbTaskStatistics wkbTaskStatistics:wkbTaskStatisticsList)
                {
                    if(wkbTaskStatistics.getuId().equals(uId))
                    {
                        taskStatistics.setSend(wkbTaskStatistics.getTaskcount());
                        break;
                    }
                }

                int notFinishCount=0;
                int finishCount=0;
                for(WkbTaskStatistics wkbTaskStatistics:wkbTaskRecvStatisticsList)
                {
                    if(wkbTaskStatistics.getuId().equals(uId))
                    {
                        if(wkbTaskStatistics.getPlevel()!=null)
                        {
                           if(wkbTaskStatistics.getPlevel().intValue()==1)
                           {
                               taskStatistics.setLevel1(wkbTaskStatistics.getTaskcount());
                           }
                           else if(wkbTaskStatistics.getPlevel().intValue()==2)
                           {
                               taskStatistics.setLevel2(wkbTaskStatistics.getTaskcount());
                           }
                           else if(wkbTaskStatistics.getPlevel().intValue()==3)
                           {
                               taskStatistics.setLevel3(wkbTaskStatistics.getTaskcount());
                           }
                           else
                           {
                               finishCount=wkbTaskStatistics.getTaskcount();
                           }
                        }
                        else
                        {
                            notFinishCount=wkbTaskStatistics.getTaskcount();
                        }
                    }
                }

                taskStatistics.setReceive(notFinishCount+finishCount+taskStatistics.getLevel1()+taskStatistics.getLevel2()+taskStatistics.getLevel3());

                taskStatisticsList.add(taskStatistics);
            }
        }
        return taskStatisticsList;
    }


    @Override
    public WkbTask getWkbTask(Integer taskId) {
        return wkbTaskDao.getTask(taskId);
    }

    @Override
    public void deleteTask(Integer taskId, Integer uId) {
        //判断是任务发送者还是接收者
        WkbTask wkbTask=wkbTaskDao.getTask(taskId);
        if(wkbTask==null)
            throw new WkbBizException(WkbMessageEnum.TASK_NO_EXIST.getCode(),"");
        if(uId.equals(wkbTask.getuId()))
        {
            wkbTaskDao.updateDelFlag(taskId,new Date(),1);
        }
        else
        {
            boolean bUser=false;
            List<WkbTaskuser> wkbTaskuserList=wkbTaskuserService.fetchTaskUsers(taskId);
            if(wkbTaskuserList!=null)
            {
                for(WkbTaskuser wkbTaskuser:wkbTaskuserList)
                {
                    if(wkbTaskuser.getuId().equals(uId))
                    {
                        List<Integer> uIdList=new ArrayList<Integer>();
                        uIdList.add(uId);
                        wkbTaskuserService.updateRecvDelFlagByTask(taskId,uIdList,1);
                        bUser=true;
                        break;
                    }
                }
            }
            if(bUser==false)
                throw new WkbBizException(WkbMessageEnum.TASK_DELETE_USER_ERROR.getCode(),"");
        }
    }

    @Override
    public TaskFile fetchTaskFile(Integer taskId,Integer fileId, Integer uId, String fileType) {
        TaskFile taskFile=new TaskFile();

        WkbTask wkbTask=wkbTaskDao.getTask(taskId);
        if(wkbTask==null)
            throw new WkbBizException(WkbMessageEnum.TASK_NO_EXIST.getCode(),"");
        if("voicemp3".equals(fileType))
        {
            //从任务或者任务详细表中获取
            //if(fileId.equals(taskId))
            //{
                WkbTask wkbTaskFile=wkbTaskDao.getData(fileId);
                if(wkbTaskFile!=null)
                {
                taskFile.setFileId(taskId);
                taskFile.setData(wkbTaskFile.gettVoice());
                taskFile.setFileName("");
                taskFile.setFileSize(wkbTask.gettVoicesize());
                taskFile.setFileType(fileType);
            }
            if(wkbTaskFile==null)
            {
                //从明细中查找
                List<WkbTaskdetail> wkbTaskdetailList= wkbTaskdetailService.queryTaskDetails(taskId);
                if(wkbTaskdetailList!=null)
                {
                    for(WkbTaskdetail wkbTaskdetail:wkbTaskdetailList)
                    {
                         if(fileId.equals(wkbTaskdetail.gettVoiceid()))
                         {
                             WkbTaskdetail wkbTaskdetailData= wkbTaskdetailService.fetchVoiceData(wkbTaskdetail.gettVoiceid());
                             taskFile.setFileId(fileId);
                             taskFile.setData(wkbTaskdetailData.gettVoice());
                             taskFile.setFileName("");
                             taskFile.setFileSize(wkbTaskdetail.gettVoicesize());
                             taskFile.setFileType(fileType);
                             break;
                         }
                    }
                }

                if(taskFile.getFileId()==null)
                    throw new WkbBizException(WkbMessageEnum.TASK_FILE_NO_EXIST.getCode(),"");
            }
        }
        else
        {
            List<WkbTaskfile> wkbTaskfileList=wkbTaskfileDao.queryFilesByTask(taskId);
            if(wkbTaskfileList!=null)
            {
                for(WkbTaskfile wkbTaskfile:wkbTaskfileList)
                {
                    if(wkbTaskfile.getId().equals(fileId))
                    {
                        WkbTaskfile wkbTaskfileData=wkbTaskfileDao.getFileData(fileId);
                        taskFile.setData(wkbTaskfileData.gettFile());
                        taskFile.setFileName(wkbTaskfile.gettFilename());
                        taskFile.setFileType(wkbTaskfile.gettFiletype());
                        taskFile.setFileSize(wkbTaskfile.gettFilesize());
                        taskFile.setFileId(fileId);
                        break;
                    }
                }
            }
            if(taskFile.getFileId()==null)
                throw new WkbBizException(WkbMessageEnum.TASK_FILE_NO_EXIST.getCode(),"");
        }
        return taskFile;
    }

    public List<WkbTask> queryUserTasksxxx(Integer uId, int index, int pageSize) {
        //查找待办任务列表（发送的和接收的）
        List<String> sortList=new ArrayList<String>();
        sortList.add("t_isread");
        sortList.add("crtdatetime");


        WkbTaskQuery wkbTaskQuery=new WkbTaskQuery();
        wkbTaskQuery.getuIdList().add(uId);
        wkbTaskQuery.setStartPos((index - 1) * pageSize + 1);
        wkbTaskQuery.setEndPos(wkbTaskQuery.getStartPos() + pageSize - 1);
        wkbTaskQuery.settIscomplete((byte)1);

        /*SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
        wkbTaskQuery.setDateFrom(simpleDateFormat.parse("2014-04-09 10:10:10"));
            wkbTaskQuery.setDateEnd(simpleDateFormat.parse("2014-04-10 10:10:10"));
        }catch (Exception ex)
        {

        }*/
        return null;
    }

    @Override
    public WkbTaskExt queryWkbTaskDetail(Integer uId, Integer taskId) {
        //查询任务以及任务明细和附件（但不包含附件的内容-大数据）
        //如果是群任务的接收人，那么需要过滤掉非本人的回复

        WkbTask wkbTask=wkbTaskDao.getTask(taskId);
        List<WkbTaskfile> wkbTaskfileList=wkbTaskfileDao.queryFilesByTask(taskId);
        List<WkbTaskdetail> wkbTaskdetailList=wkbTaskdetailService.queryTaskDetailsNoFile(taskId, wkbTaskfileList);

        boolean bPatuser=false;
        //检查是否非任务的参与者，包括发送者，接收者
        if(uId.equals(wkbTask.getuId()))
        {
            bPatuser=true;
        }
        else
        {
            List<WkbTaskuser> wkbTaskuserList= wkbTaskuserService.fetchTaskUsers(taskId);
            if(wkbTaskuserList!=null)
            {
                for(WkbTaskuser wkbTaskuser:wkbTaskuserList)
                {
                    if(wkbTaskuser.getuId().equals(uId))
                    {
                        bPatuser=true;
                        break;
                    }
                }
            }
        }

        //如果是任务发起者，那么都可以看到
        //如果是任务接收人，那么只能看到发送者、自己发送的、自己接收的明细
        WkbTaskExt wkbTaskExt=new WkbTaskExt();
        if(wkbTaskdetailList!=null)
        {
            for(WkbTaskfile wkbTaskfile:wkbTaskfileList)
            {
                 if(wkbTaskfile.gettDid()==null||wkbTaskfile.gettDid().intValue()<=0)
                 {
                     wkbTask.getWkbTaskfileList().add(wkbTaskfile);
                 }
            }
        }
        try{
            BeanUtils.copyProperties(wkbTask,wkbTaskExt);
        }catch (Exception exp)
        {
            System.out.println("error");
        }
        if(uId.equals(wkbTask.getuId())||bPatuser==false)
        {
            wkbTaskExt.setWkbTaskdetailList(wkbTaskdetailList);
            return wkbTaskExt;
        }

        List<WkbTaskdetail> wkbRecvTaskdetailList=new ArrayList<WkbTaskdetail>();
        for(WkbTaskdetail wkbTaskdetail:wkbTaskdetailList)
        {
            if(wkbTaskdetail.getuId().equals(uId))
            {
                wkbRecvTaskdetailList.add(wkbTaskdetail);
            }
            else
            {
                for(WkbTaskdetailuser wkbTaskdetailuser:wkbTaskdetail.getWkbTaskdetailuserList())
                {
                    if(uId.equals(wkbTaskdetailuser.getuId()))
                    {
                        wkbRecvTaskdetailList.add(wkbTaskdetail);
                        break;
                    }
                }
            }
        }
        wkbTaskExt.setWkbTaskdetailList(wkbRecvTaskdetailList);
        return wkbTaskExt;
    }
}
