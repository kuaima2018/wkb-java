package com.heima.security.service;

import com.heima.security.model.ServiceResultExt;
import com.heima.security.model.UserIdentity;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-15
 * Time: 下午4:14
 * To change this template use File | Settings | File Templates.
 */
public interface ServiceUserService {
    public ServiceResultExt authenticate(UserIdentity userIdentity);
}
