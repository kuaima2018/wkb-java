package com.heima.service;

import com.heima.service.util.AuthCodeInfo;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-1
 */
public interface AuthCodeCacheService {
    public AuthCodeInfo saveAuthCode(AuthCodeInfo authCodeInfo);
    public AuthCodeInfo getAuthCode(String key);
    public void invalidAuthCode(String key);
}
