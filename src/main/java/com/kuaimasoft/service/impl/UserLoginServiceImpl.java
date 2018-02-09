package com.kuaimasoft.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kuaimasoft.common.register.BaseDaoRegistCenter;
import com.kuaimasoft.model.UserLogin;
import com.kuaimasoft.service.IUserLoginService;


@Service("userLoginService")  
public class UserLoginServiceImpl  extends BaseDaoRegistCenter implements IUserLoginService {  

    public UserLogin findByloginName(String userName){  
		return userLoginDAO.findByloginName(userName);

    }

	@Override
	public List<UserLogin> getAllPostsByRank(Pageable pageable) {
		return userLoginDAO.getAllPostsByRank(pageable);
	}

	@Override
	public Page<UserLogin> findAll(Pageable pageable) {
		return userLoginDAO.findAll(pageable);
	}  
}
