package com.heima.service.impl;

import com.heima.common.WkbMessageEnum;
import com.heima.dao.WkbOrgpostDao;
import com.heima.dao.WkbUserDao;
import com.heima.dao.model.OrgRight;
import com.heima.dto.WkbUserQueryDto;
import com.heima.json.JsonUserNameId;
import com.heima.json.WkbOrgUser;
import com.heima.model.WkbOrganization;
import com.heima.model.WkbOrgpost;
import com.heima.model.WkbPostright;
import com.heima.model.WkbUser;
import com.heima.service.*;
import com.heima.service.biz.TaskRight;
import com.heima.service.biz.WkbBizException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserRoleServiceImpl.class);

    @Autowired
    private WkbOrganizationService wkbOrganizationService;

    @Autowired
    private WkbUserService wkbUserService;

    @Autowired
    private WkbFriendsService wkbFriendsService;

    @Autowired
    private WkbPostrightService wkbPostrightService;

    @Autowired
    private WkbOrgpostDao wkbOrgpostDao;

    @Autowired
    private WkbUserDao wkbUserDao;

    private Integer searchFlag=1;

    private final static String friendName="好友";

    private final static String friendCompanyName="外协";

    @Override
    public List<WkbOrgUser> queryTaskReceivers(Integer uId, boolean bFriend) {

        WkbUser wkbUser=wkbUserService.getUser(uId);
        if(wkbUser==null)
            throw new WkbBizException(WkbMessageEnum.USER_NO_EXIST.getCode(),"");

        boolean isInCompany=true;
        if(StringUtils.isBlank(wkbUser.getcId()))
        {
            isInCompany=false;
        }
        if(wkbUser.getOkcFlag()==null||wkbUser.getOkcFlag().intValue()!=1)
        {
            isInCompany=false;
        }
        //throw new WkbBizException(WkbMessageEnum.USER_NO_COMPANY.getCode(),"");

        List<WkbOrgUser> wkbOrgUserList=null;
        List<WkbUser> friendUserList=null;
        if(bFriend==true)
            friendUserList=wkbFriendsService.queryFriends(wkbUser.getuId());
        else
            friendUserList=new ArrayList<WkbUser>();

        if(isInCompany==false)
        {
            //直接查询好友返回
            wkbOrgUserList=new ArrayList<WkbOrgUser>();
            WkbOrgUser wkbOrgUser=new WkbOrgUser(0, friendName);
            for(WkbUser friend:friendUserList)
            {
               wkbOrgUser.getDetails().add(this.convertFromUser(friend));
            }

            wkbOrgUserList.add(wkbOrgUser);
            return wkbOrgUserList;
        }

        OrgRight orgRight=wkbOrganizationService.queryRight(wkbUser);
        TaskRight taskRight = TaskRight.getTaskRight(orgRight.getpRight());


        Map<Integer,WkbOrganization> wkbOrganizationMap=new HashMap<Integer, WkbOrganization>();
        switch (taskRight)
        {
            case ALL:
                wkbOrgUserList=this.getAllUsers(wkbOrganizationMap, orgRight.getcId(),searchFlag);
                filterSelf(wkbOrgUserList,wkbUser);
                break;
            case UPLEVEL:
                if(orgRight.getoFatherId()==null)
                {
                    wkbOrgUserList= this.getAllUsers(wkbOrganizationMap, orgRight.getcId(),searchFlag);
                }
                else
                {
                    wkbOrgUserList=new ArrayList<WkbOrgUser>();
                    List<WkbUser> wkbUserList=wkbOrganizationService.queryOrganizationUsers(orgRight.getoFatherId());
                    WkbOrganization parentOrganization=wkbOrganizationService.queryOrganization(orgRight.getoFatherId());
                    WkbOrgUser wkbOrgUser=this.convertReceivers(wkbOrganizationMap, parentOrganization, wkbUserList);
                    if(wkbOrgUser!=null)
                        wkbOrgUserList.add(wkbOrgUser);

                    List<WkbOrganization> tempOrgList=new ArrayList<WkbOrganization>();
                    tempOrgList.add(orgRight);
                    List<WkbOrgUser> wkbOrgUsers=this.getUserAndChild(wkbOrganizationMap, tempOrgList,searchFlag);
                    if(wkbOrgUsers!=null&&wkbOrgUsers.size()>0)
                        wkbOrgUserList.addAll(wkbOrgUsers);
                }

                filterSelf(wkbOrgUserList,wkbUser);
                break;
            case SELF:
                List<WkbUser> wkbUserList=wkbOrganizationService.queryOrganizationUsers(orgRight.getId());
                WkbOrgUser wkbOrgUser=this.convertReceivers(wkbOrganizationMap, orgRight, wkbUserList);
                wkbOrgUserList=new ArrayList<WkbOrgUser>();
                if(wkbOrgUser!=null)
                    wkbOrgUserList.add(wkbOrgUser);
                List<WkbOrgUser> wkbOrgUsers=this.getLowUsers(wkbOrganizationMap, orgRight,searchFlag);
                if(wkbOrgUsers!=null&&wkbOrgUsers.size()>0)
                    wkbOrgUserList.addAll(wkbOrgUsers);

                filterSelf(wkbOrgUserList,wkbUser);
                break;
            default:
                wkbOrgUserList=getLowUsers(wkbOrganizationMap, orgRight, searchFlag);
                break;
        }
        //公司好友从组织架构中去重
        if(friendUserList!=null&&friendUserList.size()>0)
            return filterCompanyFriends(wkbOrganizationMap, wkbOrgUserList, friendUserList, wkbUser);
        else
            return wkbOrgUserList;
    }

    @Override
    public List<WkbUser> queryUsersByRoleId(Integer roleId) {
        List<WkbOrgpost> wkbOrgpostList=wkbPostrightService.queryUserRolesByRoleId(roleId);

        if(wkbOrgpostList!=null&&wkbOrgpostList.size()>0)
        {
            List<Integer> uIdList=new ArrayList<Integer>();
            for(WkbOrgpost wkbOrgpost:wkbOrgpostList)
            {
                uIdList.add(wkbOrgpost.getuId());
            }

            return wkbUserService.queryUserList(uIdList);
        }
        return null;
    }

    @Override
    public List<WkbUser> queryPageUsersByRoleId(WkbUserQueryDto wkbUserQueryDto, Integer startPos, Integer endPos) {
        List<WkbUser> wkbUserList=wkbPostrightService.queryPageUserRolesByRoleId(wkbUserQueryDto,startPos,endPos);
        if(wkbUserList==null)
            wkbUserList=new ArrayList<WkbUser>();
        return wkbUserList;
    }

    public Integer queryCountUsersByRoleId(WkbUserQueryDto wkbUserQueryDto)
    {
        return wkbPostrightService.queryCountUserRolesByRoleId(wkbUserQueryDto);
    }

    @Override
    public List<WkbUser> queryPageCompanyApplyUsers(WkbUserQueryDto wkbUserQueryDto, Integer startPos, Integer endPos) {
        //mysql
        Integer pos=startPos-1;
        Integer size= endPos-pos;
        List<WkbUser> wkbUserList=wkbUserDao.getPageCompanyApplyUsers(wkbUserQueryDto,pos,size);
        if(wkbUserList==null)
            wkbUserList=new ArrayList<WkbUser>();
        return wkbUserList;
    }

    @Override
    public Integer queryCountCompanyApplyUsers(WkbUserQueryDto wkbUserQueryDto) {
        return wkbUserDao.getCountCompanyApplyUsers(wkbUserQueryDto);
    }

    @Override
    public void addRoleUsers(Integer roleId, List<Integer> uIdList, Integer userId) {
        WkbPostright role = wkbPostrightService.getRole(roleId);
        if(role==null)
        {
            throw new WkbBizException(WkbMessageEnum.ROLE_NOT_EXIST.getCode(),"");
        }
        WkbOrganization wkbOrganization=wkbOrganizationService.queryOrganization(role.getoId());
        if(wkbOrganization==null)
            throw new WkbBizException(WkbMessageEnum.ORA_NOT_EXIST.getCode(),"");
        List<WkbUser> wkbUserList=wkbUserService.queryUserList(uIdList);
        for(WkbUser wkbUser:wkbUserList)
        {
            boolean bJoint=(wkbUser.getOkcFlag()!=null&&wkbUser.getOkcFlag().intValue()==1)?true:false;
            if(!wkbOrganization.getcId().equals(wkbUser.getcId()))
            {
                logger.error("joint company:"+wkbOrganization.getcId()+"user:"+wkbUser.getuId()+"-company:"+wkbUser.getcId()+"-flag:"+wkbUser.getOkcFlag());
                throw new RuntimeException("用户"+wkbUser.getuName()+"未加入该公司");
            }
            if(bJoint==true)
            {
                logger.error("user:"+wkbUser.getuId()+"-flag:"+wkbUser.getOkcFlag());
                throw new RuntimeException("用户"+wkbUser.getuName()+"已在公司岗位中");
            }
        }

        for(WkbUser wkbUser:wkbUserList)
        {
            WkbOrgpost userRole=new WkbOrgpost();
            userRole.setCreator(userId!=null?userId.toString():"0");
            userRole.setCrtdatetime(new Date());
            userRole.setpId(roleId);
            userRole.setuId(wkbUser.getuId());

            wkbOrgpostDao.insertData(userRole);
        }
        //更新用户表
        wkbUserDao.updateUsersByJoint(wkbOrganization.getcId(),uIdList,new Date());
    }

    @Override
    public void remvoeRoleUsers(Integer roleId, List<Integer> uIdList) {
        wkbOrgpostDao.removeUserRolesByUsers(uIdList, roleId);
        wkbOrgpostDao.resetCompanyByUsers(uIdList);
    }

    @Override
    public String getCompanyFromRoleId(Integer roleId) {
        WkbPostright role=wkbPostrightService.getRole(roleId);
        if(role!=null)
        {
            WkbOrganization wkbOrganization=wkbOrganizationService.queryOrganization(role.getoId());
            if(wkbOrganization!=null)
                return wkbOrganization.getcId();
        }

        return null;
    }

    @Override
    public void removeCompanyUsers(String companyId, Integer userId) {
        wkbOrgpostDao.removeUserRoles(userId);
    }


    private void filterSelf(List<WkbOrgUser> wkbOrgUserList,WkbUser wkbUser)
    {
        if(wkbOrgUserList!=null)
        {
            for(WkbOrgUser wkbOrgUser:wkbOrgUserList)
            {
                int pos=-1;
                for(int index=0;index<wkbOrgUser.getDetails().size();index++)
                {
                    if(wkbOrgUser.getDetails().get(index).getUserId().equals(wkbUser.getuId()))
                    {
                        pos=index;
                        break;
                    }
                }

                if(pos>=0)
                {
                    wkbOrgUser.getDetails().remove(pos);
                    return;
                }
            }
        }
    }
    private List<WkbOrgUser> filterCompanyFriends(Map<Integer, WkbOrganization> wkbOrganizationMap,List<WkbOrgUser> wkbOrgUserList,  List<WkbUser> friendUserList,WkbUser selfUser)
    {
        List<WkbUser> outsourceUserList=new ArrayList<WkbUser>();
        List<WkbUser> companyUserList=new ArrayList<WkbUser>();
        for(WkbUser wkbUser:friendUserList)
        {
            if(selfUser.getcId().equals( wkbUser.getcId()))
            {
                companyUserList.add(wkbUser);
            }
            else
            {
                outsourceUserList.add(wkbUser);
            }
        }

        if(companyUserList.size()>0)
            companyUserList=this.removeDuplicate(wkbOrgUserList,companyUserList);

        if(companyUserList.size()>0)
        {
            Map<Integer, WkbOrganization> map = wkbOrganizationService.queryCompanyUserOrganizations(companyUserList);
            for(Map.Entry<Integer, WkbOrganization> entry:map.entrySet())
            {
                if(!wkbOrganizationMap.containsKey(entry.getValue().getId()))
                {
                    wkbOrganizationMap.put(entry.getValue().getId(),entry.getValue());
                }
            }

            //在同一个公司，但没有配置到岗位组织的好友，如何处理？
            List<WkbUser> companyFriendUserList=new ArrayList<WkbUser>();
            for(WkbUser wkbUser:companyUserList)
            {
                //找到组织
                if(!map.containsKey(wkbUser.getuId()))
                {
                    outsourceUserList.add(wkbUser);
                    logger.warn("user not in organization:"+wkbUser.getuId());
                }
                else
                {
                    companyFriendUserList.add(wkbUser);

                }
            }
            if(companyFriendUserList.size()>0)
            {
                //暂时不合并到公司组织架构里面
                Map<WkbOrganization,  List<WkbUser>> mapOrgUsers=formatOrgUsers(map,companyFriendUserList);
                //格式输出
                for(Map.Entry<WkbOrganization,  List<WkbUser>> entry:mapOrgUsers.entrySet())
                {
                    WkbOrgUser wkbOrgUser=this.convertReceivers(wkbOrganizationMap,entry.getKey(),entry.getValue());
                    wkbOrgUserList.add(wkbOrgUser);
                }
            }
        }


        //最后外协
        if(outsourceUserList.size()>0)
        {
            WkbOrgUser wkbOrgUser=new WkbOrgUser(0,friendCompanyName);
            for(WkbUser wkbUser:outsourceUserList)
            {
                wkbOrgUser.getDetails().add(this.convertFromUser(wkbUser));
            }

            wkbOrgUserList.add(wkbOrgUser);
        }

        return wkbOrgUserList;
    }

    private Map<WkbOrganization,  List<WkbUser>> formatOrgUsers(Map<Integer, WkbOrganization> mapUserOrg, List<WkbUser> wkbUserList)
    {
        Map<WkbOrganization,  List<WkbUser>> mapOrgUsers=new HashMap<WkbOrganization, List<WkbUser>>();
        for(WkbUser wkbUser:wkbUserList)
        {
            if(mapUserOrg.containsKey(wkbUser.getuId()))
            {
                WkbOrganization wkbOrganization=mapUserOrg.get(wkbUser.getuId());
                if(mapOrgUsers.containsKey(wkbOrganization))
                {
                    mapOrgUsers.get(wkbOrganization).add(wkbUser);
                }
                else
                {
                    List<WkbUser> wkbUsers=new ArrayList<WkbUser>();
                    wkbUsers.add(wkbUser);
                    mapOrgUsers.put(wkbOrganization,wkbUsers);
                }
            }
        }
        return mapOrgUsers;
    }

    private List<WkbUser> removeDuplicate(List<WkbOrgUser> wkbOrgUserList ,List<WkbUser> wkbUserList)
    {
        List<WkbUser> newWkbUserList=new ArrayList<WkbUser>();
        for(WkbUser wkbUser:wkbUserList)
        {
            if(!isMatchSearch(wkbOrgUserList,wkbUser))
            {
                newWkbUserList.add(wkbUser);
            }
        }

        return newWkbUserList;
    }

    private boolean isMatchSearch(List<WkbOrgUser> wkbOrgUserList, WkbUser wkbUser)
    {
        for(WkbOrgUser wkbOrgUser:wkbOrgUserList)
        {
            for(JsonUserNameId userName:wkbOrgUser.getDetails())
            {
                if(userName.getUserId().equals(wkbUser.getuId()))
                {
                    return true;
                }
            }
        }

        return false;
    }

    private List<WkbOrgUser> getAllUsers(Map<Integer,WkbOrganization> wkbOrganizationMap, String companyId, int flag)
    {
        //获取所有顶级部门
        //对于顶级部门，获取用户，然后每个下层，分别获取
        List<WkbOrganization> wkbOrganizationList = wkbOrganizationService.getTopOrganizations(companyId);
        return this.getUserAndChild(wkbOrganizationMap, wkbOrganizationList,flag);
    }

    /**
     *
     * @param
     * @param flag  0-先父节点后子节点 1-按节点顺序查找
     * @return
     */
    private List<WkbOrgUser> getLowUsers(Map<Integer,WkbOrganization> wkbOrganizationMap, WkbOrganization wkbOrganization, int flag)
    {
        List<WkbOrganization> wkbOrganizationList = wkbOrganizationService.getChilds(wkbOrganization.getId());

        return this.getUserAndChild(wkbOrganizationMap, wkbOrganizationList,flag);
    }

    private List<WkbOrgUser> getUserAndChild(Map<Integer,WkbOrganization> wkbOrganizationMap, List<WkbOrganization> wkbOrganizationList,int flag)
    {
        List<WkbOrgUser> wkbOrgUserList=new ArrayList<WkbOrgUser>();
        if(wkbOrganizationList!=null)
        {
            for(WkbOrganization wkbOrganization:wkbOrganizationList)
            {
                if(!wkbOrganizationMap.containsKey(wkbOrganization.getId()))
                {
                    wkbOrganizationMap.put(wkbOrganization.getId(),wkbOrganization);
                }
                List<WkbUser> wkbUsers = wkbOrganizationService.queryOrganizationUsers(wkbOrganization.getId());
                if(wkbUsers!=null&&wkbUsers.size()>0)
                    wkbOrgUserList.add(this.convertReceivers(wkbOrganizationMap,wkbOrganization,wkbUsers));
                if(flag==1)
                {
                    List<WkbOrgUser> wkbOrgUsers=this.getLowUsers(wkbOrganizationMap, wkbOrganization,flag);
                    if(wkbOrgUsers!=null)
                        wkbOrgUserList.addAll(wkbOrgUsers);
                }
            }
            if(flag==0)
            {
                for(WkbOrganization wkbOrganization:wkbOrganizationList)
                {
                    List<WkbOrgUser> wkbOrgUsers=this.getLowUsers(wkbOrganizationMap, wkbOrganization,flag);
                    if(wkbOrgUsers!=null)
                        wkbOrgUserList.addAll(wkbOrgUsers);
                }
            }
        }
        return wkbOrgUserList;
    }

    private WkbOrgUser convertReceivers(Map<Integer,WkbOrganization> wkbOrganizationMap, WkbOrganization wkbOrganization,List<WkbUser> wkbUserList)
    {
        if(wkbUserList==null||wkbUserList.size()<=0)
            return null;

        WkbOrgUser wkbOrgUser=new WkbOrgUser(-1/*TODO:wkbOrganization.getoId()*/,this.getPrefixName(wkbOrganizationMap,wkbOrganization));
        for(WkbUser wkbUser:wkbUserList)
        {
            wkbOrgUser.getDetails().add(this.convertFromUser(wkbUser));
        }
        return wkbOrgUser;
    }

    private String getPrefixName(Map<Integer,WkbOrganization> wkbOrganizationMap, WkbOrganization wkbOrganization)
    {
        String name=wkbOrganization.getoName();
        Integer orgId=wkbOrganization.getoFatherId();
        while (orgId!=null&&wkbOrganizationMap.containsKey(orgId))
        {
            WkbOrganization wkbOrganizationParent=wkbOrganizationMap.get(orgId);
            name=wkbOrganizationParent.getoName()+":"+name;
            orgId=wkbOrganizationParent.getoFatherId();
        }
        return name;
    }

    private JsonUserNameId convertFromUser(WkbUser wkbUser)
    {
        JsonUserNameId jsonUserNameId=new JsonUserNameId();
        if(StringUtils.isNotBlank(wkbUser.getuName()))
            jsonUserNameId.setName(wkbUser.getuName());
        else
            jsonUserNameId.setName(wkbUser.getuIdentifier());
        jsonUserNameId.setUserId(wkbUser.getuId());
        jsonUserNameId.setImageUrl(wkbUser.getImageUrl());
        return jsonUserNameId;
    }

}
