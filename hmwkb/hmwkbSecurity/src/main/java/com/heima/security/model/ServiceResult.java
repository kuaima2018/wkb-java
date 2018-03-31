package com.heima.security.model;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-15
 * Time: 下午10:03
 * To change this template use File | Settings | File Templates.
 */
public class ServiceResult {
    protected Integer code;
    protected String message;

    public ServiceResult()
    {
        this.code=0;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
