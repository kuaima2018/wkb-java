package com.kuaimasoft.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kuaimasoft.common.Result;
import com.kuaimasoft.common.exception.KuaiMaException;
import com.kuaimasoft.common.exception.ResultEnum;
import com.kuaimasoft.common.register.BaseServiceRegistCenter;
import com.kuaimasoft.common.util.ResultUtil;
import com.kuaimasoft.model.Gril;

@RestController
@RequestMapping(value = "grils")
public class GrilController extends BaseServiceRegistCenter{

	@RequestMapping(value = "/grils/{id}")
	@ResponseBody
	public Result<Object> grilAdd(@Valid Gril gril, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
		}
//		throw new KuaiMaException(ResultEnum.UNKONW_ERROR);
		 return ResultUtil.success(gril);
	}
}