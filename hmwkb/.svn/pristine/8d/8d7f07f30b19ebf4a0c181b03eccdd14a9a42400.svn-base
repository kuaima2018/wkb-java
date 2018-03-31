package com.heima.security.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heima.security.model.UserIdentity;
import com.heima.security.service.ServiceUserMarshalService;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-15
 * Time: 下午9:31
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ServiceUserMarshalServiceImpl implements ServiceUserMarshalService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ServiceUserMarshalServiceImpl.class);

    private ObjectMapper objectMapper;

    public ServiceUserMarshalServiceImpl()
    {
        objectMapper=new ObjectMapper();
    }

    @Override
    public UserIdentity unmarshalUser(String data) {
        //TODO:需要应用扩展实现
        throw new RuntimeException("需要应用实现");

        /*try{
            UserIdentity userIdentity= objectMapper.readValue(data, UserIdentity.class);
            return userIdentity;
        }catch (Exception exp)
        {
            logger.error("unmarshal login service user info error:"+data, exp);
            return null;
        }*/
    }

    @Override
    public UserIdentity unmarshalLoginUser(String data) {
        //TODO:需要应用扩展实现
        throw new RuntimeException("需要应用实现");
    }
}
