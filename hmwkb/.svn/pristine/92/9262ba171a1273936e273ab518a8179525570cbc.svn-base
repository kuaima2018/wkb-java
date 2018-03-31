package com.heima.service.impl;

import com.heima.dao.WkbMessageGroupDao;
import com.heima.model.WkbMessageGroup;
import com.heima.service.WkbMessageGroupService;
import com.heima.service.WkbMessageGroupUserService;
import com.heima.service.WkbSeqService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-16
 */
@Service
public class WkbMessageGroupServiceImpl implements WkbMessageGroupService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WkbMessageGroupServiceImpl.class);

    @Value("${com.heima.message.system.groupid:10}")
    private Integer systemGroupId;
    @Autowired
    private WkbSeqService wkbSeqService;
    @Autowired
    private WkbMessageGroupDao wkbMessageGroupDao;
    @Autowired
    private WkbMessageGroupUserService wkbMessageGroupUserService;

    @Override
    public void addMessageGroup(WkbMessageGroup wkbMessageGroup) {
        if(wkbMessageGroup.getCreateDate()==null)
            wkbMessageGroup.setCreateDate(new Date());
        if(wkbMessageGroup.getValid()==null)
            wkbMessageGroup.setValid((byte)1);
        if(wkbMessageGroup.getValidDate()==null)
            wkbMessageGroup.setValidDate(new Date());
        if(wkbMessageGroup.getGroupId()==null)
            wkbMessageGroup.setGroupId(wkbSeqService.getMessageGroupId());
        if(wkbMessageGroup.getUserId()==null)
            throw new RuntimeException("用户组未设置所属用户");
        if(StringUtils.isBlank(wkbMessageGroup.getCreateBy()))
            wkbMessageGroup.setCreateBy(String.valueOf(wkbMessageGroup.getUserId()));

        int count=wkbMessageGroupDao.insertData(wkbMessageGroup);
        if(count<=0)
            throw new RuntimeException("增加用户组失败");
    }

    @Override
    public void deleteMessageGroup(Integer groupId,Integer userId) {
        if(groupId.intValue()<systemGroupId.intValue())
        {
            throw new RuntimeException("系统组不能删除");
        }
        //首先根据编号，查询出组信息
        WkbMessageGroup wkbMessageGroup=this.getGroup(groupId,userId);
        if(wkbMessageGroup==null)
            throw new RuntimeException("未找到对应组信息");
        int count=wkbMessageGroupDao.modifyMessageGroupValid(wkbMessageGroup.getId(),(byte)0, new Date());
        if(count<=0)
            throw new RuntimeException("删除用户组失败");
        //wkt-删除组用户
        wkbMessageGroupUserService.deleteUserByGroup(wkbMessageGroup.getId());
    }

    @Override
    public List<WkbMessageGroup> getUserGroups(Integer userId) {
        WkbMessageGroup wkbMessageGroup=new WkbMessageGroup();
        wkbMessageGroup.setUserId(userId);
        return wkbMessageGroupDao.queryByMessageGroup(wkbMessageGroup);
    }

    @Override
    public WkbMessageGroup getGroup(Integer groupId, Integer userId) {
        //首先根据编号，查询出组信息
        WkbMessageGroup wkbMessageGroup=new WkbMessageGroup();
        wkbMessageGroup.setGroupId(groupId);
        wkbMessageGroup.setUserId(userId);
        List<WkbMessageGroup> wkbMessageGroupList=wkbMessageGroupDao.queryByMessageGroup(wkbMessageGroup);
        if(wkbMessageGroupList==null||wkbMessageGroupList.size()<=0)
            //throw new RuntimeException("未找到对应组信息");
            return null;
        else if(wkbMessageGroupList.size()>1)
            throw new RuntimeException("找到多个组信息，系统错误");
        return wkbMessageGroupList.get(0);
    }
}
