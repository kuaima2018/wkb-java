package com.heima.security.service;


import com.heima.security.model.ServiceResult;
import com.heima.security.model.UserIdentity;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-15
 * Time: 下午5:27
 * To change this template use File | Settings | File Templates.
 */
public interface ServiceTokenService {
    public UserIdentity getUserFromToken(String token);
    public String addToken(UserIdentity userIdentity);
    public void invalidUserToken(String token);
    public void refreshUserToken(String token, UserIdentity userIdentity);
    public ServiceResult verifyToken(String token);
}
