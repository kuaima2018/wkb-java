package com.heima.service;

import com.heima.model.WkbTask;
import com.heima.model.WkbTaskuser;

import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbTaskuserService {
    void updateTaskUserPerfmLevel(Integer taskId, Map<Integer, List<Integer>> mapLevel);
    void saveTaskUsers(List<WkbTaskuser> wkbTaskuserList);
    void updateRecvReadByTask(Integer taskId,List<Integer> uIdList,Integer readFlag);
    List<WkbTaskuser> fetchTaskUsers(Integer taskId);
    Map<Integer,List<WkbTaskuser>> fetchTaskUserList(List<Integer> taskIdList);
    void updateRecvDelFlagByTask(Integer taskId,List<Integer> uIdList,Integer delFlag);

    void clearTaskUserPerfmLevel(Integer taskId);
}
