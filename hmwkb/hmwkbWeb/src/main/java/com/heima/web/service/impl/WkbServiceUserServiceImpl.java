package com.heima.web.service.impl;

import com.heima.model.WkbUser;
import com.heima.security.model.ServiceResultExt;
import com.heima.security.model.UserIdentity;
import com.heima.security.service.ServiceUserService;
import com.heima.service.WkbUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-6
 */
@Primary
@Service
public class WkbServiceUserServiceImpl implements ServiceUserService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WkbServiceUserServiceImpl.class);

    @Autowired
    private WkbUserService wkbUserService;

    @Override
    public ServiceResultExt authenticate(UserIdentity userIdentity) {
        //根据用户编号或者手机号，从数据库中获取用户信息，并验证密码
        ServiceResultExt serviceResultExt=new ServiceResultExt();
        if(userIdentity==null|| StringUtils.isBlank(userIdentity.getMobile())
                ||StringUtils.isBlank(userIdentity.getPassword()))
        {
            serviceResultExt.setCode(1);
            serviceResultExt.setMessage("未提供完整的登录信息");
            return serviceResultExt;
        }
        WkbUser wkbUser= wkbUserService.queryUserByIdentifier(userIdentity.getMobile());
        if(wkbUser==null)
        {
            serviceResultExt.setCode(2);
            serviceResultExt.setMessage("用户不存在");
            return serviceResultExt;
        }
        if(!userIdentity.getPassword().equals(wkbUser.getuPwd()))
        {
            serviceResultExt.setCode(2);
            serviceResultExt.setMessage("密码不正确");
            return serviceResultExt;
        }
        serviceResultExt.setAttatchData(wkbUser);
        userIdentity.setUserId(wkbUser.getuId());
        return serviceResultExt;
    }
}
