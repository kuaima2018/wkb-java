package com.heima.service;

import com.heima.model.WkbTaskdetail;
import com.heima.model.WkbTaskfile;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-17
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbTaskdetailService {
    List<WkbTaskdetail> queryTaskDetails(Integer taskId);
    List<WkbTaskdetail> queryTaskDetailsNoFile(Integer taskId, List<WkbTaskfile> wkbTaskfileList);
    void saveTaskdetail(WkbTaskdetail wkbTaskdetail);
    WkbTaskdetail fetchVoiceData(Integer id);
}
