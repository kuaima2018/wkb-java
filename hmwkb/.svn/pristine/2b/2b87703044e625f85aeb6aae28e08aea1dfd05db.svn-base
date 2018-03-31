package com.heima.security.service.impl;

import com.heima.security.common.ServiceResultCode;
import com.heima.security.model.ServiceLoginResult;
import com.heima.security.model.ServiceResult;
import com.heima.security.model.ServiceResultExt;
import com.heima.security.model.UserIdentity;
import com.heima.security.service.*;
import com.heima.security.util.HttpServiceUtils;
import com.heima.security.util.ServiceResultUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-15
 * Time: 下午9:52
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ServiceSecurityServiceImpl implements ServiceSecurityService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ServiceSecurityServiceImpl.class);

    @Autowired
    private ServiceUserService serviceUserService;
    @Autowired
    private ServiceUserMarshalService serviceUserMarshalService;
    @Autowired
    private ServiceTokenService serviceTokenService;
    @Autowired
    private ServiceResultService serviceResultService;

    @Autowired(required=false)
    protected ServiceSecurityConfig serviceSecurityConfig;

    @PostConstruct
    public void afeterInit()
    {
        if(serviceSecurityConfig==null)
        {
            serviceSecurityConfig=new ServiceSecurityConfig();
        }
    }


    @Override
    public boolean authenticate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if(serviceSecurityConfig.requestMatcherFactory(serviceSecurityConfig.getLoginPattern()).matches(httpServletRequest))
        {
            UserIdentity userIdentity=serviceUserMarshalService.unmarshalUser(HttpServiceUtils.parsePostParms());
            //验证用户
            ServiceResultExt serviceResult=null;
            if(userIdentity==null)
            {
                serviceResult.setCode(ServiceResultCode.AUTH_USER_NOPROVIDE.getIndex());
            }
            else
                serviceResult=serviceUserService.authenticate(userIdentity);
            if(ServiceResultUtils.isSucc(serviceResult))
            {
                //输出成功结果
                String token=serviceTokenService.addToken(userIdentity);
                ServiceLoginResult serviceLoginResult=new ServiceLoginResult();
                BeanUtils.copyProperties(serviceResult,serviceLoginResult);
                serviceLoginResult.setToken(token);
                String result=serviceResultService.loginSucc(serviceLoginResult);
                HttpServiceUtils.ajaxReturn(httpServletResponse, result, "application/json");
                return false;
            }
            else
            {
                //输出失败结果
                String result=serviceResultService.loginFail(serviceResult);
                HttpServiceUtils.ajaxReturn(httpServletResponse, result, "application/json");
                return false;
            }
        }
        else
            return true;
    }

    @Override
    public boolean authorize(HttpServletRequest httpServletRequest, String[] names, Class[] types, Object[] args, HttpServletResponse httpServletResponse) {
        //授权url列表
        boolean isSecurity=isAuthUrl(httpServletRequest);
        if(isSecurity==true)
        {
            String token="";
            for(int i=0;i<types.length;i++)
            {
                token=parseTokenFromParam(names[i],types[i],args[i]);
                if(StringUtils.isNotBlank(token))
                {
                    break;
                }
            }
            //
            if(StringUtils.isBlank(token))
            {
                //输出异常
                ServiceResult serviceResult=new ServiceResult();
                serviceResult.setCode(ServiceResultCode.AUTH_NO_LOGIN.getIndex());
                String result=serviceResultService.authFail(serviceResult);
                HttpServiceUtils.ajaxReturn(httpServletResponse, result, "application/json");
                return false;
            }
            else
            {

                ServiceResult serviceResult=serviceTokenService.verifyToken(token);
                if(!ServiceResultUtils.isSucc(serviceResult))
                {
                    String result=serviceResultService.authFail(serviceResult);
                    HttpServiceUtils.ajaxReturn(httpServletResponse, result, "application/json");
                    return false;
                }
                else
                {
                    //serviceTokenService.refreshUserToken(token,null);
                }
            }
        }

        return true;
    }

    @Override
    public boolean logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        if(serviceSecurityConfig.requestMatcherFactory(serviceSecurityConfig.getLogoutPattern()).matches(httpServletRequest))
        {
            //首先要验证是否登录用户，然后退出
            UserIdentity userIdentity=serviceUserMarshalService.unmarshalLoginUser(HttpServiceUtils.parsePostParms());
            if(userIdentity!=null&&StringUtils.isNotBlank(userIdentity.getCheckData()))
            {
                //首先验证一下，是否合法
                ServiceResult serviceResult=serviceTokenService.verifyToken(userIdentity.getCheckData());
                if(!ServiceResultUtils.isSucc(serviceResult))
                {
                    String result=serviceResultService.authFail(serviceResult);
                    HttpServiceUtils.ajaxReturn(httpServletResponse, result, "application/json");
                    return false;
                }
                else
                {
                    serviceTokenService.invalidUserToken(userIdentity.getCheckData());
                    String result=serviceResultService.logoutSucc();
                    HttpServiceUtils.ajaxReturn(httpServletResponse, result, "application/json");
                    return false;
                }
            }
            else
            {
                ServiceResult serviceResult=new ServiceLoginResult();
                serviceResult.setCode(ServiceResultCode.AUTH_NO_LOGIN.getIndex());
                String result=serviceResultService.authFail(serviceResult);
                HttpServiceUtils.ajaxReturn(httpServletResponse, result, "application/json");
                return false;
            }

        }
        return true;
    }

    protected boolean isAuthUrl(HttpServletRequest httpServletRequest)
    {
        //先检查例外，然后再检查需要安全的
        if(serviceSecurityConfig.getExceptionPatternList()!=null&&serviceSecurityConfig.getExceptionPatternList().size()>0)
        {
            //TODO:对于例外检查，只使用匹配算法
            for(String pattern:serviceSecurityConfig.getExceptionPatternList())
            {
                if(serviceSecurityConfig.requestMatcherFactory(pattern).matches(httpServletRequest))
                    return false;
            }
        }

        if(serviceSecurityConfig.getFilterPatternList()!=null||serviceSecurityConfig.getFilterPatternList().size()>0)
        {
            for(String pattern:serviceSecurityConfig.getFilterPatternList())
            {
                if(serviceSecurityConfig.requestMatcherFactory(pattern).matches(httpServletRequest))
                    return true;
            }
        }
        return false;
    }

    private String parseTokenFromParam(String name, Class clazz, Object data)
    {
        if(data==null)
            return "";

        if(data instanceof String)
        {
            if(name.equals(serviceSecurityConfig.getTokenName()))
                return (String)data;
        }
        else
        {
            if(!clazz.isPrimitive()&&!clazz.isArray()&&clazz.getName().startsWith("com.heima."))
            {
                Field field;

                try{
                    //获取类属性
                    field=this.getFieldFromClass(serviceSecurityConfig.getTokenName(),clazz);
                    //查找父类中的属性
                    if(field==null)
                    {
                        clazz=clazz.getSuperclass();
                        while(clazz!=Object.class)
                        {
                            field=this.getFieldFromClass(serviceSecurityConfig.getTokenName(),clazz);
                            if(field!=null)
                                break;
                            clazz=clazz.getSuperclass();
                        }
                    }
                }
                catch (Exception exp)
                {
                    logger.warn("fetch token field info error:"+clazz.getName(), exp);
                    field=null;
                }
                if(field!=null)
                {
                    try{
                        return (String)field.get(data);
                    }catch (Exception exp)
                    {
                        logger.warn("fetch token field value error:"+clazz.getName(), exp);
                    }
                }
            }
        }

        return "";
    }

    private  Field getFieldFromClass(String fldName, Class clazz)
    {
        Field field;
        try{

            field=clazz.getDeclaredField(fldName);
            field.setAccessible(true);
        }
        catch (NoSuchFieldException noExp)
        {
            //logger.error("find token");
            field=null;
        }
        return field;
    }

}
