package com.kuaimasoft.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileValidUtil {

	public static boolean mobileValidate(String phoneNumber) {
		final String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(phoneNumber);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}
}