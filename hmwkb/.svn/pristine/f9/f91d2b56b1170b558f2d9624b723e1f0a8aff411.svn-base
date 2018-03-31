package com.heima.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-17
 * Time: 下午10:29
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ServiceBeanNameUrlHandlerMapping extends BeanNameUrlHandlerMapping {
    @Autowired
    protected ServiceHttpRequestHandlerAdapter serviceHttpRequestHandlerAdapter;

    public ServiceBeanNameUrlHandlerMapping()
    {
        //this.setDefaultHandler(serviceHttpRequestHandlerAdapter);
    }
    @PostConstruct
    public void initAfter()
    {
        this.setDefaultHandler(serviceHttpRequestHandlerAdapter);
    }
}
