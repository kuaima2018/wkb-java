package com.kuaimasoft.common.util;

import org.apache.commons.lang.StringUtils;

import com.kuaimasoft.common.Result;

public class ResultUtil {

	public static Result<Object> success(Object object) {
		Result<Object> result = new Result<Object>();
		result.setCode(0);
		result.setMsg("sucess");
		result.setData(object);
		return result;
	}

	public static Result<Object> success() {
		return success(null);
	}

	public static Result<Object> error(Integer code, String msg) {
		Result<Object> result = new Result<Object>();
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}

	public static void parseResult(String result) {
		if (StringUtils.isBlank(result)) {
			throw new RuntimeException("未发送成功");
		} else {
			String[] values = result.split(",");
			if (values == null || values.length <= 0) {
				throw new RuntimeException("未发送成功");
			} else {
				int code = Integer.parseInt(values[0]);
				if (code != 0) {
					throw new RuntimeException(values[values.length - 1]);
				}
			}
		}
	}
}
