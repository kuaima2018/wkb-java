package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.model.WkbTaskuser;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-16
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbTaskuserDao extends BaseDao<WkbTaskuser> {
    void updateRecvReadByTask(@Param(value="tId")Integer taskId,@Param(value="uIdList")List<Integer> uIdList,@Param(value="readdate")Date readdate,@Param(value="tIsread")Integer readFlag);
    void updateRevnPerfms(@Param(value="tId")Integer taskId,@Param(value="uIdList")List<Integer> uIdList,@Param(value="perfmdate")Date perfmdate,@Param(value="tPerfm")Integer perfmFlag);
    List<WkbTaskuser> selectTaskUsers(Integer tId);
    List<WkbTaskuser> selectTaskUserList(List<Integer> tIdList);
    void updateRecvDelFlagByTask(@Param(value="tId")Integer taskId,@Param(value="uIdList")List<Integer> uIdList,@Param(value="deldate")Date deldate,@Param(value="tDel")Integer delFlag);
    void clearRevnPerfms(Integer tId);
}
