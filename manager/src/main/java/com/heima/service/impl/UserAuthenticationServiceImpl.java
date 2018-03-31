package com.heima.service.impl;

import com.heima.model.WkbUser;
import com.heima.service.WkbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-2
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
//@Service
public class UserAuthenticationServiceImpl implements UserDetailsService {
    @Autowired
    private WkbUserService wkbUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WkbUser wkbUser=wkbUserService.getUser(Integer.parseInt(username));
        WkbUserDetails wkbUserDetails=new WkbUserDetails(wkbUser.getcId().toString(),wkbUser.getuPwd());
        return wkbUserDetails;
    }
}
