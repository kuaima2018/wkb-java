package com.heima.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heima.common.WkbMessageEnum;
import com.heima.common.WkbResult;
import com.heima.common.WktStatus;
import com.heima.json.WktStatusResult;
import com.heima.service.MessageSource;
import com.heima.service.biz.WkbBizResult;
import com.heima.web.service.WkbSessionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class BaseController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BaseController.class);

    private ObjectMapper objectMapper=new ObjectMapper();

    @Autowired
    private MessageSource messageSource;

    @Autowired
    protected WkbSessionService wkbSessionService;

    protected void convertResult(WkbBizResult wkbBizResult, WkbResult wkbResult)
    {
       //DiskFileItemFactory
       if(!wkbBizResult.isSucc())
       {
           wkbResult.setSuccess(false);
           String errorDesc= messageSource.getMessage(wkbBizResult.getCode());
           if(StringUtils.isNotBlank(errorDesc))
               wkbResult.setMessage(errorDesc);
           else
               wkbResult.setMessage(wkbBizResult.getCode());
       }
    }

    protected String getBizError(String code)
    {
        String errorDesc= messageSource.getMessage(code);
        if(StringUtils.isNotBlank(errorDesc))
            return errorDesc;
        else
            return code;
    }

    protected void formatResult(String code,String desc, WkbResult wkbResult)
    {
        wkbResult.setSuccess(false);
        String errorDesc= messageSource.getMessage(code);
        if(StringUtils.isNotBlank(errorDesc))
            wkbResult.setMessage(errorDesc);
        else
            wkbResult.setMessage(code);
    }

    protected WkbResult checkSession(String sessionid, Integer userid)
    {
        WkbResult wkbResult=new WkbResult();
        if(StringUtils.isNotBlank(sessionid))
        {
            if(!wkbSessionService.isSessionValid(sessionid,userid))
            {
                //异常
                WkbBizResult wkbBizResult=new WkbBizResult();
                wkbBizResult.setCode(WkbMessageEnum.USER_NO_LOGIN.getCode());

                this.convertResult(wkbBizResult,wkbResult);
                return wkbResult;
            }
        }
        return wkbResult;
    }

    @ExceptionHandler
    @ResponseBody
    public void handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        logger.error("request url:"+request.getRequestURI());
        logger.error("system error-request url:"+request.getRequestURI()+":", ex);

        WktStatus wktStatus=new WktStatus();
        wktStatus.setErrorCode(-1000);
        wktStatus.setErrorMessage(ex.getMessage());

        try{

            response.getOutputStream().write(objectMapper.writeValueAsString(new WktStatusResult(wktStatus)).getBytes());
        }catch (Exception exp)
        {
            logger.error("json format error:", exp);
        }

    }
}
