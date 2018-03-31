package com.heima.security.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-15
 * Time: 下午5:51
 * To change this template use File | Settings | File Templates.
 */
public class HttpServiceUtils {
    private static ThreadLocal<HttpServletRequest> requestLocal= new ThreadLocal<HttpServletRequest>();
    private static ThreadLocal<HttpServletResponse> responseLocal= new ThreadLocal<HttpServletResponse>();

    public static String getIpAddr() {
        HttpServletRequest request = getCurrentRequest();
        String ip = request.getHeader("X-Real-IP");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return StringUtils.isNotBlank(ip) ? ip : "";
    }

    public static HttpServletRequest getCurrentRequest()
    {
        /*ServletRequestAttributes servletRequestAttributes=(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        //ServletWebRequest servletWebRequest=((ServletWebRequest) RequestContextHolder.getRequestAttributes());
        //return  ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getRequest();
        //return servletWebRequest.getRequest();
        HttpServletRequest httpServletRequest=servletRequestAttributes.getRequest();
        return httpServletRequest;*/
        return requestLocal.get();
    }

    public static HttpServletResponse getCurrentResponse()
    {
        /*HttpServletRequest httpServletRequest=null;
        //httpServletRequest.getSession();
        return ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();*/
        return responseLocal.get();
    }

    public static void setCurrentResponse(HttpServletResponse httpServletResponse)
    {
        responseLocal.set(httpServletResponse);
    }

    public static void setCurrentRequest(HttpServletRequest httpServletRequest)
    {
        requestLocal.set(httpServletRequest);
    }

    public static boolean matchUrl(String strUrl)
    {
        HttpServletRequest request = getCurrentRequest();
        String url=request.getContextPath();
        if(url.equals(strUrl))
            return true;
        return false;
    }

    public static String parsePostParms()
    {
        HttpServletRequest request = getCurrentRequest();
        if(request.getMethod().equals("POST"))
        {
            StringBuffer stringBuffer=new StringBuffer();
            try{
                BufferedReader bufferedReader=request.getReader();
                String strTemp="";
                while ((strTemp=bufferedReader.readLine())!=null)
                {
                    stringBuffer.append(strTemp);
                }
            }catch (Exception exp)
            {

            }
            return stringBuffer.toString();
        }
        return null;
    }

    public static void ajaxReturn(HttpServletResponse response,String message,String contentType){

        try {
            response.setContentType(contentType);
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.write(message);
            out.flush();
            out.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
