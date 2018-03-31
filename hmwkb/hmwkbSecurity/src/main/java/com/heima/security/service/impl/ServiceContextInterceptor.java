package com.heima.security.service.impl;

import com.heima.security.util.HttpServiceUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-17
 * Time: 下午8:52
 * To change this template use File | Settings | File Templates.
 */
public class ServiceContextInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpServiceUtils.setCurrentRequest(request);
        HttpServiceUtils.setCurrentResponse(response);
        return true;
    }
}
