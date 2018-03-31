package com.heima.security.service;

import com.heima.security.model.MemServiceUser;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-15
 * Time: 下午5:33
 * To change this template use File | Settings | File Templates.
 */
public interface MemTokenService {
    MemServiceUser getToken(String token);
    MemServiceUser addToken(MemServiceUser memServiceUser);
    void invalidToken(String token);
    void refreshToken(MemServiceUser memServiceUser);
}
