package com.heima.controller;

import com.heima.common.WktResult;
import com.heima.json.JsonVersionQuery;
import com.heima.json.JsonVersionResult;
import com.heima.model.WkbVersion;
import com.heima.service.WkbVersionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xuzhikai on 2015/10/7.
 */
@Controller
@RequestMapping({"/version"})
public class VersionController extends BaseController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VersionController.class);

    @Autowired
    private WkbVersionService wkbVersionService;

    @RequestMapping(value = "/verInfo", method = {RequestMethod.POST})
    @ResponseBody
    public WktResult verInfo(@RequestBody JsonVersionQuery jsonVersionQuery){
        WktResult wktResult=new WktResult();
        if(jsonVersionQuery==null|| StringUtils.isBlank(jsonVersionQuery.getPlatform()))
        {
            wktResult.getStatus().setErrorCode(1);
            wktResult.getStatus().setErrorMessage("未提供平台信息");
            return wktResult;
        }

        try{
            WkbVersion wkbVersion=wkbVersionService.queryByAppType(jsonVersionQuery.getPlatform());
            if(wkbVersion==null){
                wktResult.getStatus().setErrorCode(2);
                wktResult.getStatus().setErrorMessage("没有版本信息");
                return wktResult;
            }else {
                JsonVersionResult jsonVersionResult=new JsonVersionResult();
                jsonVersionResult.setVersion(wkbVersion.getAppVersion());
                jsonVersionResult.setDesc(wkbVersion.getAppDesc());
                jsonVersionResult.setUrl(wkbVersion.getAppUrl());

                wktResult.setResult(jsonVersionResult);
            }
        }catch (Exception exp)
        {
            wktResult.getStatus().setErrorCode(-1);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
            logger.error("get version error:", exp);
        }
        return wktResult;
    }
}
