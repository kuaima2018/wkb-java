package com.heima.security.service.impl;

import com.heima.security.common.ServiceResultCode;
import com.heima.security.model.MemServiceUser;
import com.heima.security.model.ServiceResult;
import com.heima.security.model.UserIdentity;
import com.heima.security.service.MemTokenService;
import com.heima.security.service.ServiceTokenGenerator;
import com.heima.security.service.ServiceTokenService;
import com.heima.security.util.HttpServiceUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-15
 * Time: 下午5:30
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ServiceTokenServiceImpl implements ServiceTokenService {

    @Autowired
    protected MemTokenService memTokenService;
    @Autowired
    protected ServiceTokenGenerator serviceTokenGenerator;

    @Override
    public UserIdentity getUserFromToken(String token) {
        return memTokenService.getToken(token);
    }

    @Override
    public String addToken(UserIdentity userIdentity) {
        String token=serviceTokenGenerator.tokenGenerator(userIdentity);
        MemServiceUser memServiceUser=new MemServiceUser();
        BeanUtils.copyProperties(userIdentity,memServiceUser);
        memServiceUser.setLoginDate(new Date());
        memServiceUser.setToken(token);
        memServiceUser.setUserIp(HttpServiceUtils.getIpAddr());
        memServiceUser.setUpdateDate(new Date());

        memTokenService.addToken(memServiceUser);
        return token;
    }

    @Override
    public void invalidUserToken(String token) {
        memTokenService.invalidToken(token);
    }

    @Override
    public void refreshUserToken(String token, UserIdentity userIdentity) {
        if(userIdentity instanceof MemServiceUser)
        {
            ((MemServiceUser)userIdentity).setUpdateDate(new Date());
            memTokenService.refreshToken((MemServiceUser)userIdentity);
        }
        else
        {
             //先从缓存查找，找到就更新
            MemServiceUser memServiceUser=memTokenService.getToken(token);
            if(memServiceUser!=null)
            {
                memServiceUser.setUpdateDate(new Date());
                memTokenService.refreshToken(memServiceUser);
            }
        }
    }

    @Override
    public ServiceResult verifyToken(String token) {
        //失败：1.token对应的信息不存在
        //          2.token对应的用户信息不正确（比如IP不一致）
        //          3.token对应的信息过期
        ServiceResult serviceResult=new ServiceResult();
        MemServiceUser memServiceUser=memTokenService.getToken(token);
        if(memServiceUser==null)
        {
            serviceResult.setCode(ServiceResultCode.AUTH_NO_LOGIN.getIndex());
        }
        else
        {
            /*if(!HttpServiceUtils.getIpAddr().equals(memServiceUser.getUserIp()))
            {
                serviceResult.setCode(ServiceResultCode.AUTH_TOKEN_INVALID.getIndex());
            }
            else*/
            {
                if(!DateUtils.isSameDay(memServiceUser.getLoginDate(),new Date()))
                {
                    serviceResult.setCode(ServiceResultCode.AUTH_TOKEN_OVERDUE.getIndex());
                }
            }

            //TODO:wky-最后更新时间(如果长时间不用，那么也无效)
            //memServiceUser.setUpdateDate(new Date());
            //memTokenService.refreshToken(memServiceUser);
        }
        return serviceResult;
    }
}
