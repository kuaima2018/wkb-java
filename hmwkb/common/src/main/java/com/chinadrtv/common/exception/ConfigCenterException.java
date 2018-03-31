package com.chinadrtv.common.exception;

/**
 * 
 * 配置中心异常类
 * @author jianliang
 * @version $Id: ConfigCenterException.java, v 0.1 2013-7-22 下午7:39:25 jianliang Exp $
 */
public class ConfigCenterException extends RuntimeException{

    
    private static final long serialVersionUID = 77957345249090852L;

    public ConfigCenterException() {
        super();
    }

    public ConfigCenterException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public ConfigCenterException(String arg0) {
        super(arg0);
    }

    public ConfigCenterException(Throwable arg0) {
        super(arg0);
    }

}
