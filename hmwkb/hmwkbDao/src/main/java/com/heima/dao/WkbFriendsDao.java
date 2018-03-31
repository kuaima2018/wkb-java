package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.model.WkbFriends;
import com.heima.model.WkbUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-10
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbFriendsDao extends BaseDao<WkbFriends> {
    List<WkbFriends> queryFriends(WkbFriends wkbFriends);
    List<WkbFriends> queryAllFriendList(Integer uId);
    List<WkbUser> queryFriendUsers(Integer uId);
    int removeFriend(@Param(value="uId")Integer uId,@Param(value="fUId")Integer fUId);
}
