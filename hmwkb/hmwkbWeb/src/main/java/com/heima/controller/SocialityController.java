package com.heima.controller;

import com.heima.common.WktResult;
import com.heima.json.JsonSociality;
import com.heima.json.JsonSocialityQuery;
import com.heima.json.JsonUser;
import com.heima.model.WkbUser;
import com.heima.service.WkbUserService;
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
 * Created by xuzhikai on 2015/7/20.
 */
@Controller
@RequestMapping({"/sociality"})
public class SocialityController extends BaseController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SocialityController.class);

    @Autowired
    private WkbUserService wkbUserService;
    @Autowired
    private SystemConfigure systemConfigure;

    @RequestMapping(value = "/searchRecommandCustomers")
    @ResponseBody
    public WktResult search(@RequestBody JsonSocialityQuery jsonSocialityQuery)
    {
        WktResult wktResult=new WktResult();
        if(jsonSocialityQuery==null||jsonSocialityQuery.getUserId()==null)
        {
            wktResult.getStatus().setErrorCode(1);
            wktResult.getStatus().setErrorMessage("未提供完整的信息");
            return wktResult;
        }

        try{
            if(jsonSocialityQuery.getIndex()==null)
                jsonSocialityQuery.setIndex(1);
            if(jsonSocialityQuery.getIndex().intValue()<=0)
                return wktResult;
            if(jsonSocialityQuery.getPageSize()==null||jsonSocialityQuery.getPageSize().intValue()<=0)
                return wktResult;

            List<WkbUser> wkbUserList=wkbUserService.searchUsers(jsonSocialityQuery,jsonSocialityQuery.getIndex(),jsonSocialityQuery.getPageSize());

            List<JsonSociality> jsonSocialityList=new ArrayList<JsonSociality>();
            if(wkbUserList!=null){
                for(WkbUser wkbUser:wkbUserList)
                {
                    JsonSociality jsonSociality=new JsonSociality();
                    jsonSociality.setUserId(wkbUser.getuId());
                    jsonSociality.setUserName(wkbUser.getuName());
                    jsonSociality.setUserCompany(wkbUser.getuCompany());
                    jsonSociality.setUserPostion(wkbUser.getuTitle());
                    if(StringUtils.isNotBlank(wkbUser.getImageUrl()))
                        jsonSociality.setUserImageUrl(systemConfigure.getImageServer()+wkbUser.getImageUrl());

                    jsonSocialityList.add(jsonSociality);
                }
            }
            wktResult.setResult(jsonSocialityList);
        }catch (Exception exp){
            wktResult.getStatus().setErrorCode(-1);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
            logger.error("query customer group error:", exp);
        }
        return wktResult;
    }
}
