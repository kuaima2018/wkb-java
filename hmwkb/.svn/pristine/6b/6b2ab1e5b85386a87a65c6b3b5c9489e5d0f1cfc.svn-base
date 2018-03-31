package com.heima.service.impl;

import com.heima.service.AuthCodeCacheService;
import com.heima.service.AuthCodeService;
import com.heima.service.util.AuthCodeInfo;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-1
 */
@Service
public class AuthCodeServiceImpl implements AuthCodeService {
    private final static char[] ch = "1234567890".toCharArray(); // 随即产生的字符串
    // 不包括 i
    // l(小写L)
    // o（小写O）
    // 1（数字1）0(数字0)

    @Autowired
    private AuthCodeCacheService authCodeCacheService;

    @Override
    public Map<String,String> genAuthCode(String key,int len) {
        Map<String,String> map=new HashMap<String, String>();
        AuthCodeInfo authCodeInfo=this.authCodeCacheService.getAuthCode(key);
        //如果没有超过10分钟，那么有效
        if(authCodeInfo!=null&&authCodeInfo.getDate()!=null)
        {
            Date dateLog=DateUtils.addMinutes(authCodeInfo.getDate(),10);
            if(dateLog.compareTo(new Date())>=0) {
                map.put("result","1");
                map.put("code",authCodeInfo.getAuthcode());
                return map;
            }
        }

        int length = ch.length; // 随即字符串的长度
        String sRand = ""; // 保存随即产生的字符串
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            // 随即生成0-9的数字
            String rand = new Character(ch[random.nextInt(length)]).toString();
            sRand += rand;
        }

        authCodeInfo=new AuthCodeInfo();
        authCodeInfo.setKey(key);
        authCodeInfo.setAuthcode(sRand);
        authCodeInfo.setDate(new Date());
        this.authCodeCacheService.invalidAuthCode(key);
        this.authCodeCacheService.saveAuthCode(authCodeInfo);

        map.put("result","0");
        map.put("code",authCodeInfo.getAuthcode());
        return map;
    }

    @Override
    public int authenticate(String key, String authCode) {
        AuthCodeInfo authCodeInfo=this.authCodeCacheService.getAuthCode(key);
        if(authCodeInfo==null||authCodeInfo.getAuthcode()==null)
            return -1;
        else
        {
            if(!authCodeInfo.getAuthcode().equalsIgnoreCase(authCode))
                return -2;
        }
        this.authCodeCacheService.invalidAuthCode(key);
        return 0;
    }
}
