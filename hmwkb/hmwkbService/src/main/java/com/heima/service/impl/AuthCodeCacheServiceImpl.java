package com.heima.service.impl;

import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.heima.service.AuthCodeCacheService;
import com.heima.service.util.AuthCodeInfo;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-1
 */
@Service
public class AuthCodeCacheServiceImpl implements AuthCodeCacheService {
    private final static String AUTHCODE_NS="com.heima.dao.impl.AuthCodeDaoImpl";
    private final static int AUTHCODE_TIMEOUT=1800;//10 minute

    @Override
    @ReadThroughSingleCache(namespace= AUTHCODE_NS, expiration=AUTHCODE_TIMEOUT)
    public AuthCodeInfo saveAuthCode(@ParameterValueKeyProvider AuthCodeInfo authCodeInfo) {
        return authCodeInfo;
    }

    @Override
    @ReadThroughSingleCache(namespace= AUTHCODE_NS, expiration=AUTHCODE_TIMEOUT)
    public AuthCodeInfo getAuthCode(@ParameterValueKeyProvider String key) {
        return null;
    }

    @Override
    @InvalidateSingleCache(namespace= AUTHCODE_NS)
    public void invalidAuthCode(@ParameterValueKeyProvider String key) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
