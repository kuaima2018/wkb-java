package com.heima.security.service.impl;

import com.heima.security.model.UserIdentity;
import com.heima.security.service.ServiceTokenGenerator;
import com.heima.security.util.ServiceMD5Utils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-16
 */
@Service
public class ServiceTokenGeneratorImpl implements ServiceTokenGenerator {

    @Override
    public String tokenGenerator(UserIdentity userIdentity) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate=simpleDateFormat.format(new Date());
        return ServiceMD5Utils.getMD5(userIdentity.getUserId().toString() + userIdentity.getPassword() + strDate);
    }
}
