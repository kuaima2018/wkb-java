package com.heima.security.service.impl;

import com.heima.security.service.ServiceSecurityService;
import com.heima.security.util.HttpServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-17
 * Time: 下午10:32
 * To change this template use File | Settings | File Templates.
 */
@Service("com.heima.security.service.impl.ServiceRequestHandlerAdapter")
public class ServiceHttpRequestHandlerAdapter extends HttpRequestHandlerAdapter {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ServiceHttpRequestHandlerAdapter.class);

    @Autowired
    protected ServiceSecurityService serviceSecurityService;

    @Override
    public boolean supports(Object handler) {
        if(handler instanceof ServiceHttpRequestHandlerAdapter)
            return true;
        return false;    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpServiceUtils.setCurrentRequest(request);
        HttpServiceUtils.setCurrentResponse(response);
        if(!serviceSecurityService.authenticate(request, response))
        {
            logger.debug("login succ");
        }
        else if(!serviceSecurityService.logout(request,response))
        {
            logger.debug("logout succ");
        }
        else
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        return null;
    }
}
