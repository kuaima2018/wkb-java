package com.kuaimasoft.controller;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kuaimasoft.common.Result;
import com.kuaimasoft.common.register.BaseServiceRegistCenter;
import com.kuaimasoft.common.util.Constant;
import com.kuaimasoft.common.util.ResultUtil;
import com.kuaimasoft.model.UserLogin;

@RestController
@RequestMapping(value = "userlogin")
public class UserLoginController extends BaseServiceRegistCenter {

	@ResponseBody
	@RequestMapping("/login")
	public void login(String userName, String password) {
		UserLogin ul = userLoginService.findByloginName(userName);

		if (ul != null) {
			System.out.println("数据库返回结果为： " + ul.getLoginName() + " " + ul.getLoginPassword());
		}

	}

	@ResponseBody
	@RequestMapping("/top/pages/{pageno}/{pagesize}")
	public Result<Object>  getAllPosts(@PathVariable("pageno") int pageno, @PathVariable("pagesize") int pagesize,HttpServletRequest req, HttpServletResponse res)
			throws ServletException {

		List<UserLogin> postobj = userLoginService.getAllPostsByRank(new PageRequest(pageno, pagesize));
		return ResultUtil.success(postobj);
	}
	
	@ResponseBody
	@RequestMapping("/top/pages/")
	public Result<Object> getAllPosts(@PageableDefault(value=Constant.pagesize, page=0) Pageable pageable) throws ServletException {
		Page<UserLogin> page = userLoginService.findAll(pageable);
	    return ResultUtil.success(page);
	}
}