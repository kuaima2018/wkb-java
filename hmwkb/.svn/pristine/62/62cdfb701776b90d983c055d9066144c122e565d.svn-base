package com.heima.security.service.impl;

import com.heima.security.model.ServiceResultExt;
import com.heima.security.model.UserIdentity;
import com.heima.security.service.ServiceUserService;
import org.springframework.stereotype.Service;


/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-15
 * Time: 下午4:14
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ServiceUserServiceImpl implements ServiceUserService {

    @Override
    public ServiceResultExt authenticate(UserIdentity userIdentity) {
        //TODO:需要应用扩展实现
        throw new RuntimeException("需要应用实现");
        /*ServiceResult serviceResult=new ServiceResult();
        if(StringUtils.isBlank(name)||StringUtils.isBlank(checkData))
        {
            serviceResult.setCode(ServiceResultCode.AUTH_USER_NOPROVIDE.getIndex());
            serviceResult.setMessage(ServiceResultUtils.getDescFromCode(ServiceResultCode.AUTH_USER_NOPROVIDE.getIndex()));
            return serviceResult;
        }
        ServiceUser serviceUser=serviceUserDao.findUserByAppName(appId, name);
        if(serviceUser!=null)
        {
            String strCheckData =  ServiceMD5Utils.getMD5(serviceUser.getName() + serviceUser.getPassword());
            if(checkData.equals(strCheckData))
            {
                return serviceResult;
            }
            else
            {
                serviceResult.setCode(ServiceResultCode.AUTH_USER_ERROR.getIndex());
                serviceResult.setMessage(ServiceResultUtils.getDescFromCode(ServiceResultCode.AUTH_USER_ERROR.getIndex()));
            }
        }
        else
        {
            //
            List<ServiceUser> serviceUserList= serviceUserDao.findUsersByName(name);
            if(serviceUserList==null||serviceUserList.size()==0)
            {
                serviceResult.setCode(ServiceResultCode.AUTH_USER_NONEXISTENT.getIndex());
                serviceResult.setMessage(ServiceResultUtils.getDescFromCode(ServiceResultCode.AUTH_USER_NONEXISTENT.getIndex()));
            }
            else
            {
                serviceResult.setCode(ServiceResultCode.AUTH_USER_ILLEGALITY.getIndex());
                serviceResult.setMessage(ServiceResultUtils.getDescFromCode(ServiceResultCode.AUTH_USER_ILLEGALITY.getIndex()));
            }
        }
        return serviceResult; */
    }

}
