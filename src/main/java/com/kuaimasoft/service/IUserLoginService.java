package com.kuaimasoft.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kuaimasoft.model.UserLogin;

public interface IUserLoginService{  
	
	UserLogin findByloginName(String userName);
	
	List<UserLogin> getAllPostsByRank(Pageable pageable);
	
	Page<UserLogin> findAll(Pageable pageable);
	
}