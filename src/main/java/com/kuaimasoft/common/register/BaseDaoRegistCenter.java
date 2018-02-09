package com.kuaimasoft.common.register;

import org.springframework.beans.factory.annotation.Autowired;

import com.kuaimasoft.dao.UserLoginDao;

public class BaseDaoRegistCenter {
    @Autowired  
	public UserLoginDao userLoginDAO; 
}
