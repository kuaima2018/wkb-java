package com.heima.web.service.impl;

import com.heima.model.SessionRegister;
import com.heima.util.MD5Util;
import com.heima.web.service.WkbSessionRegisterService;
import com.heima.web.service.WkbSessionService;
import com.heima.web.service.constant.CacheNS;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-9
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class WkbSessionServiceImpl implements WkbSessionService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WkbSessionServiceImpl.class);

    @Autowired
    private WkbSessionRegisterService wkbSessionRegisterService;

    @Override
    public String getNewSession(String uId) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.format(new Date());

        SessionRegister sessionRegister=new SessionRegister();
        sessionRegister.setLoginDate(new Date());
        try{
            if(StringUtils.isNotBlank(uId))
                sessionRegister.setuId(Integer.parseInt(uId));
        }catch (Exception exp)
        {
            logger.error("set new session uer error:",exp);
            sessionRegister.setuId(null);
        }

        try{
             sessionRegister.setSessionId(MD5Util.getMD5(uId + ":" + simpleDateFormat.format(new Date())));
        }catch (Exception exp)
        {
            logger.error("get new session error:", exp);
            sessionRegister.setSessionId("");
        }

        wkbSessionRegisterService.addSession(sessionRegister);
        return sessionRegister.getSessionId();
    }

    @Override
    public boolean isSessionValid(String session, Integer uId) {
        SessionRegister sessionRegister=wkbSessionRegisterService.getSession(session);
        if(sessionRegister==null)
            return false;
        if(uId==null)
            return true;
        if(sessionRegister.getuId()!=null && !sessionRegister.getuId().equals(uId))
            return false;
        //更新缓存
        try{
        Date lastDate = DateUtils.addMinutes(sessionRegister.getLoginDate(), CacheNS.SESSION_REFRESH_INTERVAL);
        if(lastDate.compareTo(new Date())<0)
        {
            wkbSessionRegisterService.refreshSession(sessionRegister);
        }
        }catch (Exception exp)
        {
            logger.error("refresh cache error:", exp);
        }
        return true;
    }

    @Override
    public SessionRegister getSession(String sessionId) {
        return wkbSessionRegisterService.getSession(sessionId);
    }

    @Override
    public void invalidSession(String sessionId) {
        SessionRegister sessionRegister=new SessionRegister();
        sessionRegister.setSessionId(sessionId);
        wkbSessionRegisterService.invalidSession(sessionRegister);
    }
}
