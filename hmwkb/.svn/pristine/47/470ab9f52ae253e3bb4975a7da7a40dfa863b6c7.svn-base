package com.heima.dao;

import com.heima.dao.model.WkbUnReadCount;
import com.heima.model.WkbTaskdetailuser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-17
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbTaskdetailuserDao {
    int saveTaskdetailuser(WkbTaskdetailuser wkbTaskdetailuser);
    int updateDetailRecvReadByTask(WkbTaskdetailuser wkbTaskdetailuser);
    List<WkbUnReadCount> queryTaskUnReadCount(@Param(value="taskIdList")List<Integer> taskIdList, @Param(value="userId")Integer userId);
}
