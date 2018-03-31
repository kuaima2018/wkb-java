package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.dao.model.WkbTaskQuery;
import com.heima.dao.model.WkbTaskShow;
import com.heima.dao.model.WkbTaskStatistics;
import com.heima.model.WkbTask;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.type.ObjectTypeHandler;

import java.io.InputStream;
import java.util.Date;
import java.util.List;


/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-15
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbTaskDao extends BaseDao<WkbTask> {
    int saveTask(@Param(value="tName")String name,@Param(value="tVoice")InputStream voice);
    WkbTask getData(Integer id);
    int insertTask(WkbTask wkbTask);

    List<WkbTaskShow> queryWkbTasks(@Param(value="taskQuery")WkbTaskQuery wkbTaskQuery, @Param(value="orderBy")List<String> orderBy);

    List<WkbTaskShow> querySendWkbTasks(@Param(value="taskQuery")WkbTaskQuery wkbTaskQuery, @Param(value="orderBy")List<String> orderBy);

    List<WkbTaskShow> queryRecvWkbTasks(@Param(value="taskQuery")WkbTaskQuery wkbTaskQuery, @Param(value="orderBy")List<String> orderBy);

    WkbTask getTask(Integer taskId);

    int completeTask(@Param(value="taskId")Integer taskId,@Param(value="updatetime")Date completedate,@Param(value="uId")Integer uId);

    int updateReadFlag(@Param(value="taskId")Integer taskId,@Param(value="updatetime")Date updatetime,@Param(value="tIsread")Integer readFlag);

    int updateDelFlag(@Param(value="taskId")Integer taskId,@Param(value="deldate")Date deltime,@Param(value="tDel")Integer delFlag);


    List<WkbTaskStatistics> selectSendTaskCount(@Param(value="taskQuery")WkbTaskQuery wkbTaskQuery);
    List<WkbTaskStatistics> selectRecvTaskCount(@Param(value="taskQuery")WkbTaskQuery wkbTaskQuery);


    int uncompleteTask(@Param(value="taskId")Integer taskId,@Param(value="updatetime")Date completedate);
}
