package com.heima.security.aop;

import com.heima.security.service.ServiceSecurityService;
import com.heima.security.service.ServiceUserMarshalService;
import com.heima.security.service.ServiceUserService;
import com.heima.security.util.HttpServiceUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-2-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Aspect
public class ServiceSecurityAspect {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ServiceSecurityAspect.class);

    @Autowired
    private ServiceSecurityService serviceSecurityService;


    public ServiceSecurityAspect()
    {
        logger.debug("ServiceSecurityAspect is created!");
    }


    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controllerBean() {}

    @Pointcut("execution(* com..*.controller.*.*(..))")
    public void methodPointcut() {}

    @Around("controllerBean() && methodPointcut() ")
    public Object aroundMethodInControllerClass(ProceedingJoinPoint pjp) throws Throwable{
        String methodName="";
        Object result=null;

        try{
            if(pjp!=null&&(pjp.getSignature() instanceof MethodSignature))
            {
                MethodSignature methodSignature=(MethodSignature)pjp.getSignature();
                methodName=methodSignature.getDeclaringType()+"."+methodSignature.getName();

                /*if(!serviceSecurityService.authenticate(HttpServiceUtils.getCurrentRequest(), HttpServiceUtils.getCurrentResponse()))
                {
                    return result;
                }*/
                if(!serviceSecurityService.authorize(HttpServiceUtils.getCurrentRequest(),methodSignature.getParameterNames(), methodSignature.getParameterTypes(), pjp.getArgs(), HttpServiceUtils.getCurrentResponse()))
                {
                    return result;
                }
                else
                {
                    //如果是退出地址，那么直接退出
                }
            }
        }catch (Exception e)
        {
            logger.error("log unkown error:"+methodName, e);
        }

        return pjp.proceed();
    }

    /*@Before("controllerBean() && methodPointcut() ")
    public void beforeMethodInControllerClass(JoinPoint jp) {
        if(jp!=null&&(jp.getSignature() instanceof MethodSignature))
        {
            MethodSignature methodSignature=(MethodSignature)jp.getSignature();

        }
    }*/
}