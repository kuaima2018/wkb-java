package com.heima.service;

import com.heima.model.WkbMessage;

import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbMessageService {
    void addMessage(WkbMessage wkbMessage);
    List<WkbMessage> queryMessages(Date startDate,Date endDate,Integer startPos,Integer endPos);
}
