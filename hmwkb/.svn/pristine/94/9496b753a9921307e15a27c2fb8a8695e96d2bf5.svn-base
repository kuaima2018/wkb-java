package com.heima.controller;

import com.heima.common.WktResult;
import com.heima.json.JsonOrgQuery;
import com.heima.json.JsonUserNameId;
import com.heima.json.WkbOrgUser;
import com.heima.json.WkbOrgUserResult;
import com.heima.service.UserRoleService;
import com.heima.service.biz.WkbBizException;
import com.heima.web.service.SystemConfigure;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-17
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping({"/organization"})
public class OrganizationController extends BaseController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private SystemConfigure systemConfigure;

    @RequestMapping(value = "/getUsers")
    @ResponseBody
    public WktResult queryUsers(@RequestBody JsonOrgQuery jsonOrgQuery)
    {
        WktResult wktResult=new WktResult();
        boolean bFriend =false;
        if(jsonOrgQuery.getIncludeFriend()!=null&&jsonOrgQuery.getIncludeFriend().intValue()==1)
            bFriend=true;

        try
        {
            List<WkbOrgUser> wkbOrgUserList=userRoleService.queryTaskReceivers(jsonOrgQuery.getUserId(), bFriend);
            if(wkbOrgUserList!=null)
            {
                List<WkbOrgUser> retList=new ArrayList<WkbOrgUser>();
                for(WkbOrgUser wkbOrgUser:wkbOrgUserList)
                {
                    if(wkbOrgUser.getDetails()!=null&&wkbOrgUser.getDetails().size()>0)
                    {
                        for(JsonUserNameId jsonUserNameId:wkbOrgUser.getDetails())
                        {
                            if(StringUtils.isNotBlank(jsonUserNameId.getImageUrl()))
                            {
                                jsonUserNameId.setImageUrl(systemConfigure.getImageServer()+jsonUserNameId.getImageUrl());
                            }
                        }
                        retList.add(wkbOrgUser);
                    }
                }
                wkbOrgUserList=retList;
            }
            wktResult.setResult(wkbOrgUserList);
        }catch (WkbBizException wkbExp)
        {
            logger.error("query user error:"+wkbExp.getCode(), wkbExp);
            wktResult.getStatus().setErrorCode(-100);
            wktResult.getStatus().setErrorMessage(this.getBizError(wkbExp.getCode()));
        }
        catch (Exception exp)
        {
            logger.error("query user unkown error:",exp);
            wktResult.getStatus().setErrorCode(-200);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
        }


        return wktResult;
    }
}
