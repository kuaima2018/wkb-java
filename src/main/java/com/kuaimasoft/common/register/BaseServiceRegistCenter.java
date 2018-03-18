package com.kuaimasoft.common.register;

import javax.annotation.Resource;

import com.kuaimasoft.common.util.LogHandler;
import com.kuaimasoft.service.IAuthCodeService;
import com.kuaimasoft.service.IUserLoginService;

public class BaseServiceRegistCenter extends LogHandler {

	@Resource
	public IUserLoginService userLoginService;

	@Resource
	public IAuthCodeService authCodeService;
}
