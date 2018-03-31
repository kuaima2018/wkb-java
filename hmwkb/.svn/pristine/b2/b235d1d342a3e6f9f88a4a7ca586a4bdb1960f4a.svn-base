package com.heima.service;

import com.heima.model.WkbFriendapply;
import com.heima.service.biz.WkbBizResult;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-11
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbFriendapplyService {
    WkbFriendapply findWkbFriendapply(Integer requestId);
    WkbBizResult saveFriendapply(WkbFriendapply wkbFriendapply);
    WkbBizResult agreeApply(Integer id, Integer uId, Integer applyId);
    WkbBizResult rejectApply(Integer id, Integer uId,Integer applyId);
    WkbBizResult readApplyByIds(Integer uId, List<Integer> idList);
    WkbBizResult readApplyByUserIds(Integer uId, List<Integer> applyIdList);
    WkbBizResult saveApplySend(Integer id,Integer succ);
    List<WkbFriendapply> queryApplyList(WkbFriendapply wkbFriendapply, Boolean sortApplydate, Boolean sortApplyStatus,Boolean sortReadStatus, int index, int pageSize);
    WkbBizResult removeApplyByIds(Integer uId, List<Integer> idList);
}
