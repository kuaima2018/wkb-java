package com.heima.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heima.common.WktResult;
import com.heima.common.WktStatus;
import com.heima.json.JsonUser;
import com.heima.json.JsonUserNew;
import com.heima.json.util.WktUserUtil;
import com.heima.model.WkbUser;
import com.heima.security.model.ServiceLoginResult;
import com.heima.security.model.ServiceResult;
import com.heima.security.service.ServiceResultService;
import com.heima.security.util.ServiceResultUtils;
import com.heima.web.service.SystemConfigure;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-6
 */
@Primary
@Service
public class WkbServiceResultServiceImpl implements ServiceResultService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WkbServiceResultServiceImpl.class);

    @Autowired
    private SystemConfigure systemConfigure;

    private ObjectMapper objectMapper;

    public WkbServiceResultServiceImpl()
    {
        objectMapper=new ObjectMapper();
        try{
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            objectMapper.setDateFormat(simpleDateFormat);
        }catch (Exception exp)
        {
            logger.error("init json error:",exp);
        }
    }

    @Override
    public String loginSucc(ServiceLoginResult serviceLoginResult) {
        //结果序列化输出
        WktResult wktResult=new WktResult();
        WkbUser wkbUser = (WkbUser)serviceLoginResult.getAttatchData();
        String errMsg="";
        if(wkbUser!=null)
        {
            try {
                JsonUserNew jsonUserNew=new JsonUserNew();
                WktUserUtil.convert(wkbUser,jsonUserNew);
                jsonUserNew.setToken(serviceLoginResult.getToken());
                if(StringUtils.isNotBlank(jsonUserNew.getImageUrl()))
                {
                    jsonUserNew.setImageUrl(systemConfigure.getImageServer()+jsonUserNew.getImageUrl());
                }
                wktResult.setResult(jsonUserNew);
                return objectMapper.writeValueAsString(wktResult);
            }catch (Exception exp)
            {
                logger.error("login succ to json error:",exp);
                errMsg = exp.getMessage();
            }

        }
        return String.format("{\"status\":{\"errorCode\":-1,\"errorMessage\":\"%s\"}}",errMsg);
    }

    @Override
    public String loginFail(ServiceResult serviceResult) {
        WktResult wktResult=new WktResult();
        wktResult.getStatus().setErrorCode(serviceResult.getCode());
        wktResult.getStatus().setErrorMessage(serviceResult.getMessage());
        try{
            return objectMapper.writeValueAsString(wktResult);
        }catch (Exception exp)
        {
            logger.error("login fail to json error:",exp);
            return String.format("{\"status\":{\"errorCode\":-1,\"errorMessage\":\"%s\"}}",exp.getMessage());
        }
    }

    @Override
    public String authFail(ServiceResult serviceResult) {
        //结果序列化输出
        String strMsg=serviceResult.getMessage();
        if(StringUtils.isBlank(strMsg))
            strMsg= ServiceResultUtils.getDescFromCode(serviceResult.getCode());
        return String.format("{\"status\":{\"errorCode\":%d,\"errorMessage\":\"%s\"}}",
                serviceResult.getCode(), strMsg);
    }

    @Override
    public String logoutSucc() {
        return "{\"status\":{\"errorCode\":0,\"errorMessage\":null}}";
    }
}
