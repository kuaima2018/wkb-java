package com.heima.service.impl;

import com.heima.common.WkbMessageEnum;
import com.heima.dao.WkbFriendsDao;
import com.heima.model.WkbFriends;
import com.heima.model.WkbUser;
import com.heima.service.WkbFriendsService;
import com.heima.service.biz.WkbBizException;
import com.heima.service.biz.WkbBizResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-10
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class WkbFriendsServiceImpl implements WkbFriendsService {

    @Autowired
    private WkbFriendsDao wkbFriendsDao;

    @Override
    public WkbBizResult saveFriends(WkbFriends wkbFriends) {
        //需要同时插入
        WkbBizResult wkbBizResult=new WkbBizResult();

        if(wkbFriends.getuId()==null||wkbFriends.getuId().intValue()<=0
                ||wkbFriends.getfUid()==null||wkbFriends.getfUid().intValue()<=0)
        {
            wkbBizResult.setCode(WkbMessageEnum.USER_NO_ID.getCode());
            return wkbBizResult;
        }
        wkbFriendsDao.insertData(wkbFriends);

        WkbFriends wkbFriends1=new WkbFriends();
        BeanUtils.copyProperties(wkbFriends,wkbFriends1);
        wkbFriends1.setfUid(wkbFriends.getuId());
        wkbFriends1.setuId(wkbFriends.getfUid());

        wkbFriendsDao.insertData(wkbFriends1);

        return wkbBizResult;
    }

    @Override
    public boolean isWkbFriends(Integer uId1, Integer uId2) {
        if(uId1==null||uId1.intValue()<=0||uId2==null||uId2.intValue()<=0)
            return false;
        WkbFriends wkbFriends=new WkbFriends();
        wkbFriends.setuId(uId1);
        wkbFriends.setfUid(uId2);
        List<WkbFriends> wkbFriendsList=wkbFriendsDao.queryFriends(wkbFriends);
        if(wkbFriendsList!=null&&wkbFriendsList.size()>0)
            return true;
        return false;
    }

    @Override
    public List<WkbUser> queryFriends(Integer uId) {
        return wkbFriendsDao.queryFriendUsers(uId);
    }

    @Override
    public int removeFriend(Integer uId, Integer fUId) {
        return wkbFriendsDao.removeFriend(uId,fUId);
    }
}
