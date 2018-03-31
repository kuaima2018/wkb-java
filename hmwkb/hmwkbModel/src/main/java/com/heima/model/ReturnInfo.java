package com.heima.model;

/**
 * 返回信息抽象类
 * User: 李宇
 * Date: 13-1-29
 * Time: 上午10:10
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ReturnInfo {

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 返回代码
     */
    private String code;

    /**
     * 代码描述
     */
    private String desc;
}
