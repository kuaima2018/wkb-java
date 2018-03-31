package com.heima.security.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heima.security.model.ServiceLoginResult;
import com.heima.security.model.ServiceResult;
import com.heima.security.service.ServiceResultService;
import com.heima.security.util.ServiceResultUtils;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-16
 * 认证或者授权结果输出数据服务
 */
@Service
public class ServiceResultServiceImpl implements ServiceResultService {
    protected ObjectMapper objectMapper;

    public ServiceResultServiceImpl()
    {
        objectMapper=new ObjectMapper();
    }

    @Override
    public String loginSucc(ServiceLoginResult serviceLoginResult) {
        //TODO:需要应用扩展实现
        throw new RuntimeException("需要应用实现");

        /*try{
            if(serviceLoginResult.getCode()!=null)
                serviceLoginResult.setMessage(ServiceResultUtils.getDescFromCode(serviceLoginResult.getCode()));
            return objectMapper.writeValueAsString(serviceLoginResult);
        }catch (Exception exp)
        {
            return "";
        }*/
    }

    @Override
    public String loginFail(ServiceResult serviceResult) {
        //TODO:需要应用扩展实现
        throw new RuntimeException("需要应用实现");
        /*try{
            if(serviceResult.getCode()!=null)
                serviceResult.setMessage(ServiceResultUtils.getDescFromCode(serviceResult.getCode()));
            return objectMapper.writeValueAsString(serviceResult);
        }catch (Exception exp)
        {
            return "";
        }*/
    }

    @Override
    public String authFail(ServiceResult serviceResult) {
        //TODO:需要应用扩展实现
        throw new RuntimeException("需要应用实现");
        /*try{
            if(serviceResult.getCode()!=null)
                serviceResult.setMessage(ServiceResultUtils.getDescFromCode(serviceResult.getCode()));
            return objectMapper.writeValueAsString(serviceResult);
        }catch (Exception exp)
        {
            return "";
        }*/
    }

    @Override
    public String logoutSucc() {
        //TODO:需要应用扩展实现
        throw new RuntimeException("需要应用实现");
    }
}
