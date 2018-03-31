package com.heima.security.model;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-15
 * Time: 下午10:04
 * To change this template use File | Settings | File Templates.
 */
public class ServiceLoginResult extends ServiceResultExt {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    protected String token;
}
