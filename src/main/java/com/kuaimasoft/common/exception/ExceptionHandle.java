package com.kuaimasoft.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kuaimasoft.common.Result;
import com.kuaimasoft.common.util.LogHandler;
import com.kuaimasoft.common.util.ResultUtil;

@ControllerAdvice
public class ExceptionHandle extends LogHandler{

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Result<?> handle(Exception e) {
		if (e instanceof KuaiMaException) {
			KuaiMaException girlException = (KuaiMaException) e;
			return ResultUtil.error(girlException.getCode(), girlException.getMessage());
		} else {
			logger.error("【系统异常】{}", e);
			return ResultUtil.error(ResultEnum.UNKONW_ERROR.getCode(), ResultEnum.UNKONW_ERROR.getMsg());
		}
	}
}
