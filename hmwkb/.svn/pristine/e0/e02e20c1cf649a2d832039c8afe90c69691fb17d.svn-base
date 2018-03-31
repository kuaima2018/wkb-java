package com.heima.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heima.json.JsonTask;
import com.heima.json.JsonUserNameId;
import com.heima.json.JsonUserPass;
import com.heima.security.model.UserIdentity;
import com.heima.security.service.ServiceUserMarshalService;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-6
 */
@Primary
@Service
public class WkbServiceUserMarshalServiceImpl implements ServiceUserMarshalService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WkbServiceUserMarshalServiceImpl.class);

    private ObjectMapper objectMapper;

    public WkbServiceUserMarshalServiceImpl()
    {
        objectMapper=new ObjectMapper();
    }

    @Override
    public UserIdentity unmarshalUser(String data) {
        //解析登录用户信息
        if(StringUtils.isBlank(data))
            return new UserIdentity();
        else
        {
            try{
                JsonUserPass jsonUserPass = objectMapper.readValue(data, JsonUserPass.class);
                UserIdentity userIdentity=new UserIdentity();
                userIdentity.setMobile(jsonUserPass.getUserName());
                userIdentity.setPassword(jsonUserPass.getPassword());
                return userIdentity;
            }catch (Exception exp)
            {
                logger.error("unmarshal login user error:",exp);
                return new UserIdentity();
            }
        }
    }

    @Override
    public UserIdentity unmarshalLoginUser(String data) {
        if(StringUtils.isBlank(data))
            return new UserIdentity();
        else
        {
            try{
                JsonTask jsonTask=objectMapper.readValue(data,JsonTask.class);
                UserIdentity userIdentity=new UserIdentity();
                userIdentity.setUserId(jsonTask.getUserId());
                userIdentity.setCheckData(jsonTask.getToken());
                return userIdentity;
            }catch (Exception exp)
            {
                logger.error("unmarshal logout user error:",exp);
                return new UserIdentity();
            }
        }
    }
}
