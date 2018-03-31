package com.heima.security.service;


import com.heima.security.model.ServiceLoginResult;
import com.heima.security.model.ServiceResult;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-16
 * Time: 下午8:44
 * To change this template use File | Settings | File Templates.
 */
public interface ServiceResultService {
    public String loginSucc(ServiceLoginResult serviceLoginResult);
    public String loginFail(ServiceResult serviceResult);
    public String authFail(ServiceResult serviceResult);
    public String logoutSucc();
}
