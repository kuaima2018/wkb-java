package com.heima.controller;

import com.heima.common.WktResult;
import com.heima.common.WktStatus;
import com.heima.json.*;
import com.heima.model.WkbMessageGroup;
import com.heima.model.WkbUser;
import com.heima.service.WkbMessageGroupService;
import com.heima.service.WkbMessageGroupUserService;
import com.heima.web.service.SystemConfigure;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * IM相关配置信息控制器
 * User: xuzk
 * Date: 15-3-17
 */
@Controller
@RequestMapping({"/message"})
public class MessageController extends BaseController{
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private WkbMessageGroupService wkbMessageGroupService;
    @Autowired
    private WkbMessageGroupUserService wkbMessageGroupUserService;
    @Autowired
    private SystemConfigure systemConfigure;

    @RequestMapping(value = "/getGroup")
    @ResponseBody
    public WktResult getGroup(@RequestBody JsonMessageQuery jsonMessageQuery)
    {
        WktResult wktResult=new WktResult();
        if(jsonMessageQuery==null||jsonMessageQuery.getUserId()==null)
        {
            wktResult.getStatus().setErrorCode(1);
            wktResult.getStatus().setErrorMessage("未提供参数信息");
            return wktResult;
        }
        try{
            List<WkbMessageGroup> wkbMessageGroupList= wkbMessageGroupService.getUserGroups(jsonMessageQuery.getUserId());

            List<JsonMessageGroup> jsonMessageGroupList=new ArrayList<JsonMessageGroup>();
            boolean bHaveSystemCustomerGroup=false;
            boolean bHaveSystemCompanyGroup=false;
            boolean bHaveSystemFriendGroup=false;
            if(wkbMessageGroupList!=null||wkbMessageGroupList.size()>0)
            {
                for(WkbMessageGroup wkbMessageGroup:wkbMessageGroupList)
                {
                    if(wkbMessageGroup.getGroupId().intValue()==1)
                        bHaveSystemCustomerGroup=true;
                    else if(wkbMessageGroup.getGroupId().intValue()==2)
                        bHaveSystemCompanyGroup=true;
                    else if(wkbMessageGroup.getGroupId().intValue()==3)
                        bHaveSystemFriendGroup=true;
                }
            }

            if(bHaveSystemCustomerGroup==false)
            {
                //初始化系统组
                WkbMessageGroup wkbMessageGroup=new WkbMessageGroup();
                wkbMessageGroup.setGroupId(1);
                wkbMessageGroup.setGroupType(1);
                wkbMessageGroup.setUserId(jsonMessageQuery.getUserId());
                wkbMessageGroup.setGroupName("我的客户");
                wkbMessageGroup.setCreateBy("-1");
                wkbMessageGroupService.addMessageGroup(wkbMessageGroup);

                jsonMessageGroupList.add(this.formatJsonMessageGroup(wkbMessageGroup));
            }
            if(bHaveSystemCompanyGroup==false)
            {
                WkbMessageGroup wkbMessageGroup=new WkbMessageGroup();
                wkbMessageGroup.setGroupId(2);
                wkbMessageGroup.setGroupType(2);
                wkbMessageGroup.setUserId(jsonMessageQuery.getUserId());
                wkbMessageGroup.setGroupName("我的同事");
                wkbMessageGroup.setCreateBy("-1");
                wkbMessageGroupService.addMessageGroup(wkbMessageGroup);

                JsonMessageGroup jsonMessageGroup=this.formatJsonMessageGroup(wkbMessageGroup);
                //wkt-获取同事组的用户数
                jsonMessageGroup.setGroupCount(wkbMessageGroupUserService.getGroupCount(wkbMessageGroup.getGroupId(),wkbMessageGroup.getGroupType(),jsonMessageQuery.getUserId()));
                jsonMessageGroupList.add(jsonMessageGroup);
            }

            if(bHaveSystemFriendGroup==false)
            {
                WkbMessageGroup wkbMessageGroup=new WkbMessageGroup();
                wkbMessageGroup.setGroupId(3);
                wkbMessageGroup.setGroupType(3);
                wkbMessageGroup.setUserId(jsonMessageQuery.getUserId());
                wkbMessageGroup.setGroupName("我的朋友");
                wkbMessageGroup.setCreateBy("-1");
                wkbMessageGroupService.addMessageGroup(wkbMessageGroup);

                jsonMessageGroupList.add(this.formatJsonMessageGroup(wkbMessageGroup));

            }

            for(WkbMessageGroup wkbMessageGroup:wkbMessageGroupList)
            {
                JsonMessageGroup jsonMessageGroup=new JsonMessageGroup();
                jsonMessageGroup.setGroupId(wkbMessageGroup.getGroupId());
                jsonMessageGroup.setGroupType(wkbMessageGroup.getGroupType());
                jsonMessageGroup.setGroupName(wkbMessageGroup.getGroupName());

                //wkt-获取各个组的用户数
                jsonMessageGroup.setGroupCount(wkbMessageGroupUserService.getGroupCount(wkbMessageGroup.getId(),wkbMessageGroup.getGroupType(),jsonMessageQuery.getUserId()));
                jsonMessageGroupList.add(jsonMessageGroup);
            }

            wktResult.setResult(jsonMessageGroupList);

        }catch (Exception exp)
        {
            logger.error("query group error:",exp);
            wktResult.getStatus().setErrorCode(-100);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
        }
        return wktResult;
    }

    private JsonMessageGroup formatJsonMessageGroup(WkbMessageGroup wkbMessageGroup)
    {
        JsonMessageGroup jsonMessageGroup=new JsonMessageGroup();
        jsonMessageGroup.setGroupName(wkbMessageGroup.getGroupName());
        jsonMessageGroup.setGroupType(wkbMessageGroup.getGroupType());
        jsonMessageGroup.setGroupId(wkbMessageGroup.getGroupId());
        return jsonMessageGroup;
    }


    @RequestMapping(value = "/addGroup")
    @ResponseBody
    public WktResult addGroup(@RequestBody JsonMessageQuery jsonMessageQuery)
    {
        WktResult wktResult=new WktResult();
        if(jsonMessageQuery==null||jsonMessageQuery.getGroupType()==null
                || StringUtils.isBlank(jsonMessageQuery.getGroupName())||jsonMessageQuery.getUserId()==null)
        {
            wktResult.getStatus().setErrorCode(1);
            wktResult.getStatus().setErrorMessage("未提供参数信息");
            return wktResult;
        }
        WkbMessageGroup wkbMessageGroup=new WkbMessageGroup();
        wkbMessageGroup.setUserId(jsonMessageQuery.getUserId());
        wkbMessageGroup.setGroupName(jsonMessageQuery.getGroupName());
        wkbMessageGroup.setGroupType(jsonMessageQuery.getGroupType());
        try{
            wkbMessageGroupService.addMessageGroup(wkbMessageGroup);
            HashMap<String,Integer> retMap=new HashMap<String, Integer>();
            retMap.put("groupId",wkbMessageGroup.getGroupId());
            wktResult.setResult(retMap);
        }catch (Exception exp)
        {
            logger.error("add group error:",exp);
            wktResult.getStatus().setErrorCode(-100);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
        }
        return wktResult;
    }

    @RequestMapping(value = "/delGroup")
    @ResponseBody
    public WktStatusResult deleteGroup(@RequestBody JsonMessageQuery jsonMessageQuery)
    {
        WktStatus wktStatus=new WktStatus();
        if(jsonMessageQuery==null||jsonMessageQuery.getGroupId()==null
                || jsonMessageQuery.getUserId()==null)
        {
            wktStatus.setErrorCode(1);
            wktStatus.setErrorMessage("未提供参数信息");
            return new WktStatusResult(wktStatus);
        }


        try{
            wkbMessageGroupService.deleteMessageGroup(jsonMessageQuery.getGroupId(),jsonMessageQuery.getUserId());
        }catch (Exception exp)
        {
            logger.error("delete group error:",exp);
            wktStatus.setErrorCode(1);
            wktStatus.setErrorMessage(exp.getMessage());
        }
        return new WktStatusResult(wktStatus);
    }


    @RequestMapping(value = "/getUser")
    @ResponseBody
    public WktResult getUser(@RequestBody JsonMessageUserQuery jsonMessageUserQuery)
    {
        WktResult wktResult=new WktResult();
        if(jsonMessageUserQuery==null||jsonMessageUserQuery.getGroupId()==null
                ||jsonMessageUserQuery.getUserId()==null)
        {
            wktResult.getStatus().setErrorCode(1);
            wktResult.getStatus().setErrorMessage("未提供参数信息");
            return wktResult;
        }
        if(jsonMessageUserQuery.getIndex()==null||jsonMessageUserQuery.getIndex().intValue()<=0
                ||jsonMessageUserQuery.getPageSize()==null||jsonMessageUserQuery.getPageSize().intValue()<=0)
        {
            wktResult.getStatus().setErrorCode(2);
            wktResult.getStatus().setErrorMessage("请求的分页信息不正确");
            return wktResult;
        }

        //
        try{
            WkbMessageGroup wkbMessageGroup=wkbMessageGroupService.getGroup(jsonMessageUserQuery.getGroupId(),jsonMessageUserQuery.getUserId());
            if(wkbMessageGroup!=null)
            {
                List<WkbUser> wkbUserList=wkbMessageGroupUserService.queryGroupUsers(wkbMessageGroup.getId(), wkbMessageGroup.getGroupType(), wkbMessageGroup.getUserId(),
                    jsonMessageUserQuery.getIndex(), jsonMessageUserQuery.getPageSize());
                if(wkbUserList!=null)
                {
                    List<JsonUserNameId> jsonUserNameIdList=new ArrayList<JsonUserNameId>();
                    for(WkbUser wkbUser:wkbUserList)
                    {
                        JsonUserNameId jsonUserNameId=new JsonUserNameId();
                        jsonUserNameId.setUserId(wkbUser.getuId());
                        if(StringUtils.isNotBlank(wkbUser.getuName()))
                            jsonUserNameId.setName(wkbUser.getuName());
                        else
                            jsonUserNameId.setName(wkbUser.getuIdentifier());
                        if(StringUtils.isNotBlank(wkbUser.getImageUrl()))
                            jsonUserNameId.setImageUrl(systemConfigure.getImageServer() + wkbUser.getImageUrl());

                        jsonUserNameIdList.add(jsonUserNameId);
                    }

                    wktResult.setResult(jsonUserNameIdList);
                }
            }
        }catch (Exception exp)
        {
            logger.error("query group user error:",exp);
            wktResult.getStatus().setErrorCode(-100);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
        }
        return wktResult;
    }


    @RequestMapping(value = "/addUser")
    @ResponseBody
    public WktStatusResult addUser(@RequestBody JsonMessageUserQuery jsonMessageUserQuery)
    {
        WktStatus wktStatus=new WktStatus();
        if(jsonMessageUserQuery==null||jsonMessageUserQuery.getGroupId()==null
                ||jsonMessageUserQuery.getUserId()==null||jsonMessageUserQuery.getUserIds()==null)
        {
            wktStatus.setErrorCode(1);
            wktStatus.setErrorMessage("未提供参数信息");
            return new WktStatusResult(wktStatus);
        }
        if(jsonMessageUserQuery.getUserIds().size()<=0)
            return new WktStatusResult(wktStatus);

        try
        {
            WkbMessageGroup wkbMessageGroup=wkbMessageGroupService.getGroup(jsonMessageUserQuery.getGroupId(),jsonMessageUserQuery.getUserId());
            if(wkbMessageGroup==null)
            {
                wktStatus.setErrorCode(2);
                wktStatus.setErrorMessage("添加的组不存在");
                return new WktStatusResult(wktStatus);
            }

            wkbMessageGroupUserService.addUser(wkbMessageGroup.getId(), jsonMessageUserQuery.getUserIds(),jsonMessageUserQuery.getUserId());
        }catch (Exception exp)
        {
            logger.error("add user error:",exp);
            wktStatus.setErrorCode(-100);
            wktStatus.setErrorMessage(exp.getMessage());
        }
        return new WktStatusResult(wktStatus);
    }


    @RequestMapping(value = "/delUser")
    @ResponseBody
    public WktStatusResult deleteUser(@RequestBody JsonMessageUserQuery jsonMessageUserQuery)
    {
        WktStatus wktStatus=new WktStatus();
        if(jsonMessageUserQuery==null||jsonMessageUserQuery.getGroupId()==null
                ||jsonMessageUserQuery.getUserId()==null||jsonMessageUserQuery.getUserIds()==null)
        {
            wktStatus.setErrorCode(1);
            wktStatus.setErrorMessage("未提供参数信息");
            return new WktStatusResult(wktStatus);
        }
        if(jsonMessageUserQuery.getUserIds().size()<=0)
            return new WktStatusResult(wktStatus);

        try
        {
            WkbMessageGroup wkbMessageGroup=wkbMessageGroupService.getGroup(jsonMessageUserQuery.getGroupId(),jsonMessageUserQuery.getUserId());
            if(wkbMessageGroup==null)
            {
                wktStatus.setErrorCode(2);
                wktStatus.setErrorMessage("删除的组不存在");
                return new WktStatusResult(wktStatus);
            }

            wkbMessageGroupUserService.deleteUser(wkbMessageGroup.getId(), jsonMessageUserQuery.getUserIds());
        }catch (Exception exp)
        {
            logger.error("add user error:",exp);
            wktStatus.setErrorCode(-100);
            wktStatus.setErrorMessage(exp.getMessage());
        }

        return new WktStatusResult(wktStatus);
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public WktResult test(@RequestBody JsonMessageUserQuery jsonMessageUserQuery)
    {
        WktResult wktResult=new WktResult();
        List<Integer> userIdList=new ArrayList<Integer>();
        userIdList.add(1);
        userIdList.add(2);
        wktResult.setResult(userIdList);
        return wktResult;
    }


}
