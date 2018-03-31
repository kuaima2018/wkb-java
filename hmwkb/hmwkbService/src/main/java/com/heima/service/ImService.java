package com.heima.service;

/**
 * Created by xuzhikai on 2015/9/5.
 */
public interface ImService {
    public void registerUser(String userId,String userName,String userPassword);
    public void resetUser(String userId,String userName,String userPassword);
    public void removeUser(String userId);
}
