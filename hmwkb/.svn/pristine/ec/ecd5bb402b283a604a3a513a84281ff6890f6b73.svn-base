package com.heima.service;

import com.heima.dao.model.WkbTaskQuery;
import com.heima.dao.model.WkbTaskShow;
import com.heima.model.WkbTask;
import com.heima.model.WkbTaskdetail;
import com.heima.service.biz.TaskFile;
import com.heima.json.TaskStatistics;
import com.heima.service.biz.WkbTaskExt;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-15
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbTaskService {
    //int saveTask(String name,InputStream data);
    InputStream getData(Integer id);

    void createTask(WkbTask wkbTask);

    List<WkbTaskShow> queryUserTasks(WkbTaskQuery wkbTaskQuery);

    WkbTaskExt queryWkbTaskDetail(Integer uId, Integer taskId);

    /**
     *  任务置成已读
     * @param taskId
     * @param uId
     * @return 0-已读 1-更新 2-回复更新
     */
    Integer readTask(Integer taskId, Integer uId);

    //任务回复
    void addTaskDetail(WkbTaskdetail wkbTaskdetail);

    void completeTask(Integer taskId,Integer uId, Map<Integer,List<Integer>> mapLevel);

    //-获取下属任务统计
    //目前的想法是获取所有部门，然后按照部门用户来读取
    List<TaskStatistics> queryUserStatistics(WkbTaskQuery wkbTaskQuery);

    //15-获取下属任务详情

    //-回复任务接口

    WkbTask getWkbTask(Integer taskId);

    //删除任务
    void deleteTask(Integer taskId,Integer uId);

    TaskFile fetchTaskFile(Integer taskId,Integer fileId,Integer uId,String fileType);
}
