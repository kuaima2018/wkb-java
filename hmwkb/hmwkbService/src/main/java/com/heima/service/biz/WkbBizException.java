package com.heima.service.biz;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-10
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class WkbBizException extends RuntimeException{
    public WkbBizException(String code, String desc)
    {
        this.code=code;
        this.desc=desc;
    }

    public WkbBizResult getResult()
    {
        return new WkbBizResult(this.code,this.desc);
    }

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

    private String code;
    private String desc;
}
