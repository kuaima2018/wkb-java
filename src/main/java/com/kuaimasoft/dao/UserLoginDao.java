package com.kuaimasoft.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.kuaimasoft.model.UserLogin;

@Transactional
public interface UserLoginDao extends CrudRepository<UserLogin, Long> {
	UserLogin findByloginName(String loginName);
	
	Page<UserLogin> findAll(Pageable pageable);
	
	@Query(getAllPostsByRank)
	List<UserLogin> getAllPostsByRank(Pageable pageable);

	final String getAllPostsByRank= "from UserLogin order by loginName DESC";
}
