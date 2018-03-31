package com.heima.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heima.common.WkbMessageEnum;
import com.heima.common.WkbResult;
import com.heima.dto.WkbUserQueryDto;
import com.heima.service.MessageSource;
import com.heima.service.biz.WkbBizException;
import com.heima.service.biz.WkbBizResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

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

    protected String getMsgFromWkbExp(WkbBizException wkbExp)
    {
        return messageSource.getMessage(wkbExp.getCode());
    }

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

    protected void formatResult(String code,String desc, WkbResult wkbResult)
    {
        wkbResult.setSuccess(false);
        String errorDesc= messageSource.getMessage(code);
        if(StringUtils.isNotBlank(errorDesc))
            wkbResult.setMessage(errorDesc);
        else
            wkbResult.setMessage(code);
    }

    protected void formatQueryDto(WkbUserQueryDto wkbUserQueryDto)
    {
        if(StringUtils.isBlank(wkbUserQueryDto.getcId()))
            wkbUserQueryDto.setcId(null);
        if(StringUtils.isBlank(wkbUserQueryDto.getuEmail()))
            wkbUserQueryDto.setuEmail(null);
        if(StringUtils.isBlank(wkbUserQueryDto.getuName()))
            wkbUserQueryDto.setuName(null);
        if(StringUtils.isBlank(wkbUserQueryDto.getuMobile()))
            wkbUserQueryDto.setuMobile(null);
        if(StringUtils.isBlank(wkbUserQueryDto.getuTel()))
            wkbUserQueryDto.setuTel(null);
        try
        {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat simpleDateFormatFull=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(wkbUserQueryDto.getApplyBeginTime()!=null)
            {
                String str=simpleDateFormat.format(wkbUserQueryDto.getApplyBeginTime());
                wkbUserQueryDto.setApplyBeginTime(simpleDateFormatFull.parse(str+" 00:00:00"));
            }
            if(wkbUserQueryDto.getApplyEndTime()!=null)
            {
                String str=simpleDateFormat.format(wkbUserQueryDto.getApplyEndTime());
                wkbUserQueryDto.setApplyEndTime(simpleDateFormatFull.parse(str+" 23:59:59"));
            }
            if(wkbUserQueryDto.getJointBeginTime()!=null)
            {
                String str=simpleDateFormat.format(wkbUserQueryDto.getJointBeginTime());
                wkbUserQueryDto.setJointBeginTime(simpleDateFormatFull.parse(str+" 00:00:00"));
            }
            if(wkbUserQueryDto.getJointEndTime()!=null)
            {
                String str=simpleDateFormat.format(wkbUserQueryDto.getJointEndTime());
                wkbUserQueryDto.setJointEndTime(simpleDateFormatFull.parse(str+" 23:59:59"));
            }
        }catch (Exception exp)
        {
            logger.error("format date error:",exp);
        }
    }


    @ExceptionHandler
    @ResponseBody
    public void handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        logger.error("request url:"+request.getRequestURI());
        logger.error("system error-request url:"+request.getRequestURI()+":", ex);
        WkbResult wkbResult=new WkbResult();
        wkbResult.setSuccess(false);
        wkbResult.setMessage(ex.getMessage());

        try{

            response.getOutputStream().write(objectMapper.writeValueAsString(wkbResult).getBytes());
        }catch (Exception exp)
        {
            logger.error("json format error:", exp);
        }

    }
}
