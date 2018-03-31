package com.heima.web.service.impl;

import com.google.code.ssm.api.*;
import com.heima.model.SessionRegister;
import com.heima.web.service.WkbSessionRegisterService;
import com.heima.web.service.constant.CacheNS;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 会话记录器，后期改成全局缓存
 * User: 徐志凯
 * Date: 14-4-9
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class WkbSessionRegisterServiceImpl implements WkbSessionRegisterService {
    private List<SessionRegister> sessionRegisterList=new ArrayList<SessionRegister>();
    @Override
    @ReadThroughSingleCache(namespace= CacheNS.SESSION_NS, expiration=CacheNS.SESSION_TIMEOUT)
    public SessionRegister addSession(@ParameterValueKeyProvider SessionRegister sessionRegister) {
        synchronized (this)
        {
            sessionRegisterList.add(sessionRegister);
        }
        return sessionRegister;
    }

    @Override
    @InvalidateSingleCache(namespace= CacheNS.SESSION_NS)
    public void invalidSession(@ParameterValueKeyProvider SessionRegister sessionRegister) {
        synchronized (this)
        {
            int index=-1;
            for(int i=0;i<sessionRegisterList.size();i++)
            {
                if(sessionRegisterList.get(i).getSessionId().equals(sessionRegister.getSessionId()))
                {
                    index=i;
                    break;
                }
            }
            if(index>=0)
            {
                sessionRegisterList.remove(index);
            }
        }
    }

    @Override
    @UpdateSingleCache(namespace= CacheNS.SESSION_NS, expiration=CacheNS.SESSION_TIMEOUT)
    public void refreshSession(@ParameterValueKeyProvider @ParameterDataUpdateContent SessionRegister sessionRegister) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @ReadThroughSingleCache(namespace= CacheNS.SESSION_NS, expiration=CacheNS.SESSION_TIMEOUT)
    public SessionRegister getSession(@ParameterValueKeyProvider String sessionId) {
        synchronized (this)
        {
            for(SessionRegister sessionRegister:sessionRegisterList)
            {
                if(sessionId.equals(sessionRegister.getSessionId()))
                    return sessionRegister;
            }
        }

        return null;
    }
}
