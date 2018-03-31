package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.model.WkbFriendapply;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-11
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbFriendapplyDao extends BaseDao<WkbFriendapply> {
    WkbFriendapply getWkbFriendapply(Integer id);
    List<WkbFriendapply> findNewApply(@Param(value="uId")Integer uId, @Param(value="applyId")Integer applyId,@Param(value="lastDate")Date lastDate);
    int updateNewApplyById(WkbFriendapply wkbFriendapply);
    int updateNewApplysByUserId(WkbFriendapply wkbFriendapply);
    int updateApplySend(@Param(value="id")Integer id, @Param(value="sendSucc") Integer sendSucc,@Param(value="sendDate")Date sendDate);
    int updateApplyReadByIds(@Param(value="idList")List<Integer> idList, @Param(value="readStatusDate")Date readStatusDate);
    int updateApplyReadByUserIds(@Param(value="uId")Integer uId, @Param(value="applyIdList")List<Integer> applyIdList, @Param(value="readStatusDate")Date readStatusDate);

    List<WkbFriendapply> queryApplyPage(@Param(value="wkbFriendapply")WkbFriendapply wkbFriendapply,@Param(value="orderBy")List<String> orderBy,@Param(value="startPos")Integer startPos,@Param(value="endPos")Integer endPos);

    int deleteApplyByIds(@Param(value="idList")List<Integer> idList, @Param(value="uId")Integer uId);
}
