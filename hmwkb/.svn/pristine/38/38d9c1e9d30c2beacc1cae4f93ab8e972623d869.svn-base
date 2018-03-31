package com.heima.security.service.impl;

import com.google.code.ssm.api.*;
import com.heima.security.model.MemServiceUser;
import com.heima.security.service.MemTokenService;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-15
 * Time: 下午5:34
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MemTokenServiceImpl implements MemTokenService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MemTokenServiceImpl.class);

    private static final String CACHE_NS="com.heima.security.service.impl.MemTokenServiceImpl";

    private final static int SESSION_TIMEOUT= 86400;//1 day

    @Override
    @ReadThroughSingleCache(namespace= CACHE_NS, expiration=SESSION_TIMEOUT)
    public MemServiceUser getToken(@ParameterValueKeyProvider String token) {
        logger.debug("get token:"+token);
        return null;
    }

    @Override
    @ReadThroughSingleCache(namespace= CACHE_NS, expiration=SESSION_TIMEOUT)
    public MemServiceUser addToken(@ParameterValueKeyProvider MemServiceUser memServiceUser) {
        logger.debug("add token:"+memServiceUser.getToken());
        return memServiceUser;
    }

    @Override
    @InvalidateSingleCache(namespace= CACHE_NS)
    public void invalidToken(@ParameterValueKeyProvider String token) {
        logger.debug("invalid token:"+token);
    }

    @Override
    @UpdateSingleCache(namespace= CACHE_NS, expiration=SESSION_TIMEOUT)
    public void refreshToken(@ParameterValueKeyProvider @ParameterDataUpdateContent MemServiceUser memServiceUser) {
        logger.debug("refresh token:"+memServiceUser.getToken());
    }
}
