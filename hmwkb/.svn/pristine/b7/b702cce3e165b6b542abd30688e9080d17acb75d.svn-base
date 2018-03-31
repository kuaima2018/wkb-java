package com.heima.security.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务返回码枚举
 * User: xuzk
 * Date: 14-12-18
 */
public enum ServiceResultCode {
    AUTH_NO_LOGIN(-1,"未登陆"),
    AUTH_TOKEN_INVALID(-2,"凭据信息不正确"),
    AUTH_TOKEN_OVERDUE(-3,"凭据信息过期"),
    AUTH_USER_NONEXISTENT(-4,"用户不存在"),
    AUTH_USER_ERROR(-5,"用户名或密码错误"),
    AUTH_USER_ILLEGALITY(-6,"用户无权调用此服务"),
    AUTH_USER_NOPROVIDE(-7,"登录验证未提供完整的用户和校验信息"),

    SYSTEM_UNKNOWN_ERROR(-10000,"系统错误");

    private String name;
    private int index;

    ServiceResultCode(int index, String name) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    public static ServiceResultCode getFromIndex(int index)
    {
        for(ServiceResultCode src : ServiceResultCode.values()) {
            if(index==src.getIndex())
                return src;
        }
        return null;
    }

    static public List<String> toList() {
        List<String> contents = new ArrayList<String>();
        for(ServiceResultCode src : ServiceResultCode.values()) {
            contents.add(src.name);
        }
        return contents;
    }

    static public String fromOrdinal(int num) {
        return ServiceResultCode.values()[num].name;
    }
}
