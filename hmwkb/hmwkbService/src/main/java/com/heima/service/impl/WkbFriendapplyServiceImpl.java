package com.heima.service.impl;

import com.heima.common.WkbMessageEnum;
import com.heima.dao.WkbFriendapplyDao;
import com.heima.dao.WkbUserDao;
import com.heima.model.WkbFriendapply;
import com.heima.model.WkbFriends;
import com.heima.model.WkbUser;
import com.heima.service.WkbFriendapplyService;
import com.heima.service.WkbFriendsService;
import com.heima.service.biz.WkbBizException;
import com.heima.service.biz.WkbBizResult;
import com.heima.service.util.BulkListSplitter;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-11
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class WkbFriendapplyServiceImpl implements WkbFriendapplyService {
    @Autowired
    private WkbFriendapplyDao wkbFriendapplyDao;

    @Autowired
    private WkbUserDao wkbUserDao;

    @Autowired
    private WkbFriendsService wkbFriendsService;

    @Value("${com.heima.service.impl.WkbFriendapplyServiceImpl.applyInterval}")
    private Integer friendApplyInterval;

    @Override
    public WkbFriendapply findWkbFriendapply(Integer requestId) {
        return wkbFriendapplyDao.getWkbFriendapply(requestId);
    }

    /**
     * 保存好友申请（避免重复多条）
     * @param wkbFriendapply
     * @return
     */
    @Override
    public WkbBizResult saveFriendapply(WkbFriendapply wkbFriendapply) {
        WkbBizResult wkbBizResult=new WkbBizResult();
        if(wkbFriendapply.getuId()==null||wkbFriendapply.getuId().intValue()<=0
            ||wkbFriendapply.getAppUid()==null||wkbFriendapply.getAppUid().intValue()<=0)
        {
            wkbBizResult.setCode(WkbMessageEnum.USER_NO_ID.getCode());
            return wkbBizResult;
        }

        if(wkbFriendsService.isWkbFriends(wkbFriendapply.getuId(),wkbFriendapply.getAppUid()))
        {
            wkbBizResult.setCode(WkbMessageEnum.USER_FRIEND_EXIST.getCode());
            return wkbBizResult;
        }
        //如果已经存在未处理的请求(一定时间内)，那么直接返回
        Date lastDate=DateUtils.addMinutes(new Date(),-(friendApplyInterval+1));
        List<WkbFriendapply> wkbFriendapplyList=wkbFriendapplyDao.findNewApply(wkbFriendapply.getuId(),wkbFriendapply.getAppUid(), lastDate);
        if(wkbFriendapplyList!=null&&wkbFriendapplyList.size()>0)
        {
            return wkbBizResult;
        }

        if(wkbFriendapply.getAppDate()==null)
            wkbFriendapply.setAppDate(new Date());

        wkbFriendapply.setSendSucc((byte)0);
        wkbFriendapply.setSendCount(0);

        WkbUser wkbUser=wkbUserDao.getUser(wkbFriendapply.getuId());
        WkbUser wkbApplyUser=wkbUserDao.getUser(wkbFriendapply.getAppUid());
        if(wkbUser==null||wkbApplyUser==null)
        {
            wkbBizResult.setCode(WkbMessageEnum.USER_NO_EXIST.getCode());
            return wkbBizResult;
        }

        wkbFriendapply.setReadStatus((byte)1);
        wkbFriendapply.setAppStatus((byte) 1);
        wkbFriendapply.setAppName(wkbApplyUser.getuName());
        wkbFriendapplyDao.insertData(wkbFriendapply);

        return wkbBizResult;
    }

    @Override
    public WkbBizResult agreeApply(Integer id, Integer uId, Integer applyId) {
        WkbBizResult wkbBizResult=new WkbBizResult();

        if(id==null&&applyId==null)
        {
            wkbBizResult.setCode(WkbMessageEnum.USER_NO_ID.getCode());
            return wkbBizResult;
        }
        WkbFriendapply wkbFriendapply=new WkbFriendapply();
        wkbFriendapply.setAppStatus((byte)0);
        wkbFriendapply.setAppStatusDate(new Date());
        wkbFriendapply.setReadStatus((byte)0);
        wkbFriendapply.setReadStatusDate(new Date());
        wkbFriendapply.setuId(uId);
        wkbFriendapply.setAppUid(applyId);
        wkbFriendapply.setId(id);
        int count=0;
        if(id!=null)
        {
            count = wkbFriendapplyDao.updateNewApplyById(wkbFriendapply);
            WkbFriendapply wkbFriendapplyNew=wkbFriendapplyDao.getWkbFriendapply(wkbFriendapply.getId());
            wkbFriendapply.setAppUid(wkbFriendapplyNew.getAppUid());

        }
        else
            count = wkbFriendapplyDao.updateNewApplysByUserId(wkbFriendapply);

        if(count==0)
        {
            throw new WkbBizException(WkbMessageEnum.USER_APPLY_NO_EXIST.getCode(),"");
        }

        WkbFriends wkbFriends=new WkbFriends();
        wkbFriends.setuId(wkbFriendapply.getuId());
        wkbFriends.setCreator(wkbFriendapply.getAppUid().toString());
        wkbFriends.setCrtdatetime(new Date());
        wkbFriends.setfUid(wkbFriendapply.getAppUid());

        wkbFriendsService.saveFriends(wkbFriends);

        return wkbBizResult;
    }

    @Override
    public WkbBizResult rejectApply(Integer id, Integer uId, Integer applyId) {
        WkbBizResult wkbBizResult=new WkbBizResult();

        if(id==null&&applyId==null)
        {
            wkbBizResult.setCode(WkbMessageEnum.USER_NO_ID.getCode());
            return wkbBizResult;
        }
        WkbFriendapply wkbFriendapply=new WkbFriendapply();
        wkbFriendapply.setAppStatus((byte) 0);
        wkbFriendapply.setAppStatusDate(new Date());
        wkbFriendapply.setReadStatus((byte) 0);
        wkbFriendapply.setReadStatusDate(new Date());
        wkbFriendapply.setuId(uId);
        wkbFriendapply.setAppUid(applyId);
        wkbFriendapply.setId(id);
        int count=0;
        if(id!=null)
            count = wkbFriendapplyDao.updateNewApplyById(wkbFriendapply);
        else
            count = wkbFriendapplyDao.updateNewApplysByUserId(wkbFriendapply);

        if(count==0)
        {
            throw new WkbBizException(WkbMessageEnum.USER_APPLY_NO_EXIST.getCode(),"");
        }

        return wkbBizResult;
    }

    private static final Integer batchSize=50;
    @Override
    public WkbBizResult readApplyByIds(Integer uId, List<Integer> idList) {
        WkbBizResult wkbBizResult=new WkbBizResult();

        BulkListSplitter<Integer> bulkListSplitter=new BulkListSplitter<Integer>();
        List<List<Integer>> listList =bulkListSplitter.splitList(idList,batchSize);
        for(List<Integer> itemList:listList)
        {
            int count=wkbFriendapplyDao.updateApplyReadByIds(itemList, new Date());
        }
        return wkbBizResult;
    }

    @Override
    public WkbBizResult readApplyByUserIds(Integer uId, List<Integer> applyIdList) {
        WkbBizResult wkbBizResult=new WkbBizResult();

        BulkListSplitter<Integer> bulkListSplitter=new BulkListSplitter<Integer>();
        List<List<Integer>> listList =bulkListSplitter.splitList(applyIdList,batchSize);
        for(List<Integer> itemList:listList)
        {
            int count=wkbFriendapplyDao.updateApplyReadByUserIds(uId, itemList, new Date());
            //System.out.println(count);
        }

        return wkbBizResult;
    }

    @Override
    public WkbBizResult saveApplySend(Integer id, Integer succ) {
        WkbBizResult wkbBizResult=new WkbBizResult();
        int count=wkbFriendapplyDao.updateApplySend(id,succ,new Date());
        return wkbBizResult;
    }

    @Override
    public List<WkbFriendapply> queryApplyList(WkbFriendapply wkbFriendapply, Boolean sortApplydate, Boolean sortApplyStatus, Boolean sortReadStatus, int index, int pageSize) {
        List<String> orderbyList=new ArrayList<String>();
        if(sortReadStatus!=null&&sortReadStatus.booleanValue()==true)
        {
            orderbyList.add("read_status");
        }

        if(sortApplyStatus!=null&&sortApplyStatus.booleanValue()==true)
        {
            orderbyList.add("app_status");
        }
        if(sortApplydate!=null&&sortApplydate.booleanValue()==true)
        {
            orderbyList.add("app_date");
        }



        if(orderbyList.isEmpty())
        {
            orderbyList.add("app_date");
        }
        int startPos=(index-1)*pageSize+1;
        int endPos=startPos+pageSize-1;

        //mysql
        Integer pos=startPos-1;
        Integer size= endPos-pos;
        return wkbFriendapplyDao.queryApplyPage(wkbFriendapply,orderbyList,pos,size);
    }

    @Override
    public WkbBizResult removeApplyByIds(Integer uId, List<Integer> idList) {
        WkbBizResult wkbBizResult=new WkbBizResult();
        BulkListSplitter<Integer> bulkListSplitter=new BulkListSplitter<Integer>();
        List<List<Integer>> listList =bulkListSplitter.splitList(idList,batchSize);
        for(List<Integer> itemList:listList)
        {
            if(itemList!=null&&itemList.size()>0)
                wkbFriendapplyDao.deleteApplyByIds(itemList,uId);
        }
        return wkbBizResult;
    }

}
