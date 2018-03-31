package com.heima.security.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-15
 * Time: 下午9:49
 * To change this template use File | Settings | File Templates.
 */
public interface ServiceSecurityService {
    /**
     * 认证用户
     * @param httpServletRequest
     * @param httpServletResponse
     * @return true-继续处理 false-终止后续处理
     */
    public boolean authenticate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    /**
     * 授权服务
     * @param httpServletRequest
     * @param httpServletResponse
     * @return true-继续处理 false-终止后续处理
     */
    public boolean authorize(HttpServletRequest httpServletRequest,String[] names,Class[] types,Object[] args,HttpServletResponse httpServletResponse);

    public boolean logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
