package com.chinadrtv.common.exception;

/**
 * 平台测试异常类
 * 
 * @author GaoPeng
 * @version $Id: BaseTestingException.java, v 0.1 2013-8-5 下午3:26:02 GaoPeng Exp $
 */
public class BaseTestingException extends Exception {

    /** Serial Version UID */
    private static final long serialVersionUID = -4624658772307544423L;

    /**
     * Construction
     */
    public BaseTestingException() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    public BaseTestingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public BaseTestingException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public BaseTestingException(Throwable cause) {
        super(cause);
    }

}
