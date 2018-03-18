package com.kuaimasoft.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kuaimasoft.common.util.ResultUtil;
import com.kuaimasoft.model.AuthCodeInfo;
import com.kuaimasoft.service.IAuthCodeService;

@Service
public class AuthCodeServiceImpl implements IAuthCodeService {
	private final static char[] ch = "1234567890".toCharArray(); // 随即产生的字符串

	@Value("${com.heima.sms.url}")
	private String smsUrl;
	@Value("${com.heima.sms.name}")
	private String smsName;
	@Value("${com.heima.sms.password}")
	private String smsPassword;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void genAuthcodeAndSend(String mobile) {
		Map<String, String> map = new HashMap<String, String>();
		AuthCodeInfo authCodeInfo = new AuthCodeInfo();

		int length = ch.length; // 随即字符串的长度
		String sRand = ""; // 保存随即产生的字符串
		Random random = new Random();
		for (int i = 0; i < 4; i++) {
			// 随即生成0-9的数字
			String rand = new Character(ch[random.nextInt(length)]).toString();
			sRand += rand;
		}

		authCodeInfo.setKey(mobile);
		authCodeInfo.setAuthcode(sRand);
		authCodeInfo.setDate(new Date());
		map.put("result", "0");
		map.put("code", authCodeInfo.getAuthcode());
		// wkt-发送验证码到手机
		String bSucc = map.get("result");
		if ("1".equals(bSucc))
			return;

		String authCode = map.get("code");

		String strContent = String.format("您的验证码是：%s，在30分钟内有效。本软件完全免费，请放心使用。祝工作愉快！", authCode);
		String strUrl = String.format("%s?name=%s&pwd=%s&content=%s&mobile=%s&type=pt", smsUrl, smsName, smsPassword,
				strContent, mobile);

		String result;
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(strUrl, null, String.class);
			result = response.getBody();
		} catch (Exception exp) {
			throw new RuntimeException(exp.getMessage());
		}

		ResultUtil.parseResult(result);
	}

	@Override
	public int authenticate(String mobile, String authCode) {
		AuthCodeInfo authCodeInfo = null;
		if (authCodeInfo == null || authCodeInfo.getAuthcode() == null)
			return -1;
		else {
			if (!authCodeInfo.getAuthcode().equalsIgnoreCase(authCode))
				return -2;
		}
		return 0;
	}

}
