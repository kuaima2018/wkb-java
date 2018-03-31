package com.heima.service.impl;

import com.heima.dao.WkbCustomerGroupDao;
import com.heima.model.WkbCustomer;
import com.heima.model.WkbCustomerGroup;
import com.heima.service.WkbCustomerGroupService;
import com.heima.service.WkbCustomerService;
import com.heima.service.WkbSeqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by xuzhikai on 2015/7/18.
 */
@Service
public class WkbCustomerGroupServiceImpl implements WkbCustomerGroupService {

    @Autowired
    private WkbCustomerGroupDao wkbCustomerGroupDao;

    @Autowired
    private WkbCustomerService wkbCustomerService;

    @Autowired
    private WkbSeqService wkbSeqService;

    @Override
    public void addCustomerGroup(WkbCustomerGroup wkbCustomerGroup) {
        if(wkbCustomerGroup.getGroupId()==null)
            wkbCustomerGroup.setGroupId(wkbSeqService.getCusGroupId());
        if(wkbCustomerGroup.getCreateBy()==null)
            wkbCustomerGroup.setCreateBy(String.valueOf(wkbCustomerGroup.getUserId()));
        if(wkbCustomerGroup.getCreateDate()==null)
            wkbCustomerGroup.setCreateDate(new Date());

        wkbCustomerGroupDao.insertData(wkbCustomerGroup);
    }

    @Override
    public void deleteCustomerGroup(Integer groupId, Integer userId) {
        wkbCustomerGroupDao.deleteGroup(groupId,userId);
        wkbCustomerService.changeCustomersGroup(userId,groupId,1);
    }

    @Override
    public void updateCustomerGroup(WkbCustomerGroup wkbCustomerGroup) {
        if(wkbCustomerGroup.getGroupId()==null)
            return;
        wkbCustomerGroupDao.updateGroup(wkbCustomerGroup);
    }

    @Override
    public List<WkbCustomerGroup> queryCustomerGroups(Integer userId) {
        //如果没有系统分组，那么先创建
        List<WkbCustomerGroup> wkbCustomerGroupList=wkbCustomerGroupDao.queryAll(userId);
        boolean bSysGroup=false;
        if(wkbCustomerGroupList!=null)
        {
            for(WkbCustomerGroup wkbCustomerGroup:wkbCustomerGroupList)
            {
                if(wkbCustomerGroup.getGroupId().intValue()==1)
                {
                    bSysGroup=true;
                    break;
                }
            }
        }
        if(bSysGroup==false)
        {
            WkbCustomerGroup wkbCustomerGroup=new WkbCustomerGroup();
            wkbCustomerGroup.setGroupId(1);
            wkbCustomerGroup.setGroupName("我的客户");
            wkbCustomerGroupList.add(wkbCustomerGroup);
            //wkbCustomerGroup.setCreateBy("sys");
            //wkbCustomerGroup.setCreateDate();
        }
        return wkbCustomerGroupList;
    }
}
