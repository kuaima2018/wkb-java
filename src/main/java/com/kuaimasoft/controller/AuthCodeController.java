package com.kuaimasoft.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kuaimasoft.common.Result;
import com.kuaimasoft.common.register.BaseServiceRegistCenter;
import com.kuaimasoft.common.util.MobileValidUtil;
import com.kuaimasoft.common.util.ResultUtil;

@RestController
@RequestMapping(value = "authcode")
public class AuthCodeController extends BaseServiceRegistCenter {

	@RequestMapping(value = "/genAuthCode")
	@ResponseBody
	public Result<Object> genAuthCode(@PathVariable("mobile") String mobile) {
		if (mobile == null || StringUtils.isBlank(mobile)) {
			return ResultUtil.error(-1, "未提供手机号");
		}
		if (mobile != null && mobile.length() != 11) {
			return ResultUtil.error(-1, "手机号应为11位数");
		}

		if (MobileValidUtil.mobileValidate(mobile)) {
			try {
				// wkt-调用短信接口，发送短信
				authCodeService.genAuthcodeAndSend(mobile);

			} catch (Exception exp) {
				logger.error("gen auth code error:", exp);
				return ResultUtil.error(-1, exp.getMessage());
			}
		}
		return ResultUtil.success();
	}
}
