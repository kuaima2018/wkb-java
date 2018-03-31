package com.heima.dao;

import com.heima.model.WkbMessage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbMessageDao {
    int addMessage(WkbMessage wkbMessage);
    List<WkbMessage> queryMessages(@Param(value="startDate")Date startDate,@Param(value="endDate")Date endDate,@Param(value="startPos")Integer startPos,@Param(value="endPos")Integer endPos);
}
