package com.heima.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ValidateCodeUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String genCode=(String)request.getSession().getAttribute("wkb_gen_code");
        String inputCode=(String)request.getParameter("authCode");
        if(StringUtils.isBlank(inputCode)||!inputCode.equals(genCode))
        {
            /*throw new AuthenticationServiceException(messages.getMessage("validateCode.notEquals"));*/
            throw new AuthenticationServiceException("验证码不一致");
        }
        return super.attemptAuthentication(request, response);
    }
}
