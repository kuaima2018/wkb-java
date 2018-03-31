package com.heima.service;

import com.heima.model.WkbFriends;
import com.heima.model.WkbUser;
import com.heima.service.biz.WkbBizResult;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-10
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbFriendsService {
    WkbBizResult saveFriends(WkbFriends wkbFriends);
    boolean isWkbFriends(Integer uId1, Integer uId2);
    List<WkbUser> queryFriends(Integer uId);
    int removeFriend(Integer uId,Integer fUId);
}
