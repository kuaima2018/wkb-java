package com.kuaimasoft.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.google.code.ssm.api.CacheKeyMethod;

public class AuthCodeInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@CacheKeyMethod
	public String getKey() {
		return StringUtils.isEmpty(key) ? " " : key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private String key;

	public String getAuthcode() {
		return authcode;
	}

	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}

	private String authcode;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	private Date date;

}
