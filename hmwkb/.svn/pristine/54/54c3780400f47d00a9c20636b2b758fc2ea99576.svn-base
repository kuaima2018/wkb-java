package com.heima.service.impl;

import com.heima.dao.WkbMessageGroupUserDao;
import com.heima.model.WkbMessageGroupUser;
import com.heima.model.WkbUser;
import com.heima.service.WkbCompanyService;
import com.heima.service.WkbMessageGroupUserService;
import com.heima.service.WkbUserService;
import com.heima.service.util.BulkListSplitter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-17
 */
@Service
public class WkbMessageGroupUserServiceImpl implements WkbMessageGroupUserService {
    private final static int companyGroupId=2;
    private final static int companyGroupType=2;

    @Autowired
    private WkbUserService wkbUserService;
    @Autowired
    private WkbMessageGroupUserDao wkbMessageGroupUserDao;

    @Override
    public int getGroupCount(Integer groupId, Integer groupType, Integer userId) {
        if(groupType.intValue()==companyGroupType)
        {
            String companyId=this.getCompanyFromUser(userId);
            if(StringUtils.isBlank(companyId))
                return 0;
            int count=0;
            if(groupId.intValue()==companyGroupId)
            {
                count=wkbUserService.getCountCompanyFormalUsers(companyId);
                if(count>0)
                    return count-1;
                else
                    return count;
            }
            else
            {
                //TODO:wkt-后期需要维护公司人员的变动
                return wkbMessageGroupUserDao.queryCountByGroup(groupId);
            }

        }
        else
        {
            //直接从组中获取
            return wkbMessageGroupUserDao.queryCountByGroup(groupId);
        }
    }

    private String getCompanyFromUser(Integer userId)
    {
        WkbUser wkbUser=wkbUserService.getUser(userId);
        if(wkbUser==null|| StringUtils.isBlank(wkbUser.getcId()))
            return null;
        if(wkbUser.getOkcFlag()==null||wkbUser.getOkcFlag().intValue()!=1)
            return null;
        return wkbUser.getcId();
    }

    @Override
    public List<WkbUser> queryGroupUsers(Integer groupId, Integer groupType, Integer userId, Integer index, Integer pageSize) {
        int pos=(index-1)*pageSize;
        if(groupType.intValue()==companyGroupType)
        {
            String companyId=this.getCompanyFromUser(userId);
            if(StringUtils.isBlank(companyId))
                return new ArrayList<WkbUser>();
            else
            {
                if(groupId.intValue()==companyGroupId)
                    return wkbUserService.getPageCompanyFormalUsers(companyId, userId, pos, pageSize);
                else
                    //TODO:wkt-后期需要维护公司人员的变动
                    return wkbMessageGroupUserDao.queryUserByGroup(groupId,pos,pageSize);
            }
        }
        else
        {
            return wkbMessageGroupUserDao.queryUserByGroup(groupId,pos,pageSize);
        }
    }

    @Override
    public void deleteUserByGroup(Integer groupId) {
        //目前只是简单打删除标记
        wkbMessageGroupUserDao.modifyUserValidByGroup(groupId,(byte)0,new Date());
    }

    @Override
    public void addUser(Integer groupId, List<Integer> userIdList,Integer userId) {
        //首先检查用户是否已经添加过了
        //分批次查找
        BulkListSplitter<Integer> bulkListSplitter=new BulkListSplitter<Integer>();
        List<List<Integer>> listList =bulkListSplitter.splitList(userIdList,50);
        List<WkbMessageGroupUser> wkbMessageGroupUserList=null;
        for(List<Integer> itemList:listList)
        {
            if(wkbMessageGroupUserList==null||wkbMessageGroupUserList.size()<=0)
            {
                wkbMessageGroupUserList=wkbMessageGroupUserDao.queryUser(groupId,itemList);
            }
            else
            {
                List<WkbMessageGroupUser> itemWkbMessageGroupUserList=wkbMessageGroupUserDao.queryUser(groupId,itemList);
                if(itemWkbMessageGroupUserList!=null&&itemWkbMessageGroupUserList.size()>0)
                    wkbMessageGroupUserList.addAll(itemWkbMessageGroupUserList);
            }
        }

        if(wkbMessageGroupUserList==null)
        {
            wkbMessageGroupUserList=new ArrayList<WkbMessageGroupUser>();
        }
        List<WkbMessageGroupUser> addWkbMessageGroupUserList=new ArrayList<WkbMessageGroupUser>();
        for(Integer uId:userIdList)
        {
            boolean bFind=false;
            for(WkbMessageGroupUser wkbMessageGroupUser:wkbMessageGroupUserList)
            {
                if(wkbMessageGroupUser.getUserId().equals(uId))
                {
                    bFind=true;
                    break;
                }
            }
            if(bFind==false)
            {
                WkbMessageGroupUser wkbMessageGroupUser=new WkbMessageGroupUser();
                wkbMessageGroupUser.setCreateBy(String.valueOf(userId));
                wkbMessageGroupUser.setCreateDate(new Date());
                wkbMessageGroupUser.setGroupId(groupId);
                wkbMessageGroupUser.setUserId(uId);
                wkbMessageGroupUser.setValid((byte)1);
                wkbMessageGroupUser.setValidDate(new Date());

                addWkbMessageGroupUserList.add(wkbMessageGroupUser);
            }
        }
        //增加新的用户
        for(WkbMessageGroupUser wkbMessageGroupUser:addWkbMessageGroupUserList)
        {
            //TODO:wkt-批量插入
            wkbMessageGroupUserDao.insertData(wkbMessageGroupUser);
        }
    }

    @Override
    public void deleteUser(Integer groupId, List<Integer> userIdList) {
        //分批次删除
        BulkListSplitter<Integer> bulkListSplitter=new BulkListSplitter<Integer>();
        List<List<Integer>> listList =bulkListSplitter.splitList(userIdList,50);
        for(List<Integer> itemList:listList)
        {
            wkbMessageGroupUserDao.modifyUserValid(groupId,itemList,(byte)0,new Date());
        }
    }

}
